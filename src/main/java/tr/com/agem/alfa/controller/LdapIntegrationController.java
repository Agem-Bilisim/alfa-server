package tr.com.agem.alfa.controller;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.springframework.web.servlet.mvc.method.annotation.SseEmitter.event;

import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import tr.com.agem.alfa.form.LdapIntegrationForm;
import tr.com.agem.alfa.ldap.LdapSyncService;
import tr.com.agem.alfa.ldap.util.LdapUtils;
import tr.com.agem.alfa.mapper.SysMapper;
import tr.com.agem.alfa.model.CurrentUser;
import tr.com.agem.alfa.model.LdapIntegration;
import tr.com.agem.alfa.security.EncryptionService;
import tr.com.agem.alfa.service.LdapService;
import tr.com.agem.alfa.validator.LdapIntegrationFormValidator;

/**
 * @author <a href="mailto:emre.akkaya@agem.com.tr">Emre Akkaya</a>
 */
@PreAuthorize("hasRole('ROLE_ADMIN')")
@Controller
public class LdapIntegrationController {

	private static final Logger log = LoggerFactory.getLogger(LdapIntegrationController.class);

	private final LdapService ldapService;
	private final LdapSyncService ldapSyncService;
	private final EncryptionService encryptionService;
	private final LdapIntegrationFormValidator validator;
	private final SysMapper mapper;

	@Value("${sys.page-size}")
	private Integer sysPageSize;

	/**
	 * Emitter pool that we add for each client connection and remove for each
	 * completion and timeout event.
	 */
	private final CopyOnWriteArrayList<SseEmitter> emitters = new CopyOnWriteArrayList<SseEmitter>();

	@Autowired
	public LdapIntegrationController(LdapService ldapService, LdapSyncService ldapSyncService,
			EncryptionService encryptionService, LdapIntegrationFormValidator validator, SysMapper mapper) {
		this.ldapService = ldapService;
		this.ldapSyncService = ldapSyncService;
		this.encryptionService = encryptionService;
		this.validator = validator;
		this.mapper = mapper;
	}

	@InitBinder("form")
	public void initBinder(WebDataBinder binder) {
		binder.addValidators(validator);
	}

	@GetMapping("/ldap/integration/create")
	public ModelAndView getIntegrationCreatePage() {
		return new ModelAndView("ldap/integration/create", "form", new LdapIntegrationForm());
	}

	@PostMapping("/ldap/integration/create")
	public String handleIntegrationCreateForm(@Valid @ModelAttribute("form") LdapIntegrationForm form,
			BindingResult bindingResult, Authentication authentication) {
		if (bindingResult.hasErrors()) {
			// failed validation
			return "ldap/integration/create";
		}
		try {
			CurrentUser user = (CurrentUser) authentication.getPrincipal();
			checkNotNull(user, "Current user not found.");
			LdapIntegration integration = toIntegrationEntity(form, user.getUsername());
			boolean success = LdapUtils.testConnection(integration, encryptionService);
			if (!success) {
				log.error("LDAP connection failed for host: {}:{}", integration.getIpAddress(), integration.getPort());
				bindingResult.reject("connection.error", "Connection test failed. Check parameters and try again.");
				return "ldap/integration/create";
			}
			ldapService.save(integration);
		} catch (Exception e) {
			log.error("Exception occurred when trying to save the LDAP integration", e);
			bindingResult.reject("unexpected.error", "Unexpected error.");
			return "ldap/integration/create";
		}
		// everything fine redirect to list
		return "redirect:/ldap/integration/list";
	}

	@GetMapping("/ldap/integration/{id}")
	public ModelAndView getIntegrationEditPage(@PathVariable Long id) {
		LdapIntegration integration = ldapService.getIntegration(id);
		checkNotNull(integration, String.format("Integration:%d not found.", id));
		return new ModelAndView("ldap/integration/edit", "form", mapper.toIntegrationForm(integration));
	}

	@PostMapping("/ldap/integration/{id}")
	public String handleIntegrationUpdate(@PathVariable Long id,
			@Valid @ModelAttribute("form") LdapIntegrationForm form, BindingResult bindingResult,
			Authentication authentication) {
		if (bindingResult.hasErrors()) {
			// failed validation
			return "ldap/integration/edit";
		}
		try {
			CurrentUser user = (CurrentUser) authentication.getPrincipal();
			checkNotNull(user, "Current user not found.");
			LdapIntegration integration = toIntegrationEntity(form, user.getUsername());
			boolean success = LdapUtils.testConnection(integration, encryptionService);
			if (!success) {
				log.error("LDAP connection failed for host: {}:{}", integration.getIpAddress(), integration.getPort());
				bindingResult.reject("connection.error", "Connection test failed. Check parameters and try again.");
				return "ldap/integration/create";
			}
			ldapService.save(integration);
		} catch (Exception e) {
			log.warn("Exception occurred when trying to save the user, duplicate email or username", e);
			bindingResult.reject("username.or.email.exists", "Username or email already exist");
			return "ldap/integration/edit";
		}
		// everything fine redirect to list
		return "redirect:/ldap/integration/list";
	}

	@GetMapping("/ldap/integration/{id}/snyc")
	public ResponseEntity<?> handleLdapSync(@PathVariable Long id, Authentication authentication) {
		RestResponseBody result = new RestResponseBody();
		try {
			CurrentUser user = (CurrentUser) authentication.getPrincipal();
			checkNotNull(user, "Current user not found.");
			LdapIntegration integration = ldapService.getIntegration(id);
			checkNotNull(integration, String.format("Integration:%d not found.", id));
			// Handle sync in another thread since it may take a while to finish!
			Thread thread = new Thread(new LdapSyncHandler(integration, user.getUsername()));
			thread.start();
			result.setMessage(
					String.format("LDAP: %s senkronize ediliyor. Tamamlandığında bildirim gönderilecek.",
							integration.getIpAddress()));
		} catch (Exception e) {
			log.error("Exception occurred when trying to sync LDAP users", e);
			result.setMessage(e.getMessage());
			return ResponseEntity.badRequest().body(result);
		}
		return ResponseEntity.ok(result);
	}

	protected class LdapSyncHandler implements Runnable {

		private final List<SseEmitter> deadEmitters = new ArrayList<SseEmitter>();
		private final LdapIntegration integration;
		private final String principal;

		public LdapSyncHandler(LdapIntegration integration, String principal) {
			this.integration = integration;
			this.principal = principal;
		}

		@Override
		public void run() {
			boolean success = false;
			try {
				ldapSyncService.doSync(integration, principal);
				success = true;
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
			for (SseEmitter emitter : emitters) {
				Date date = new Date();
				try {
					RestResponseBody data = new RestResponseBody();
					data.setMessage(
							success ? "LDAP senkronizasyonu başarıyla tamamlandı." : "LDAP kullanıcıları senkronize edilirken hata oluştu.");
					data.add("success", success);
					emitter.send(event().reconnectTime(500).name("ldap-sync-status").id(integration.getId() + "-" + date.getTime()).data(data,
							MediaType.APPLICATION_JSON_UTF8));
				} catch (Exception e) {
					deadEmitters.add(emitter);
				}
			}
			if (!deadEmitters.isEmpty()) {
				emitters.removeAll(deadEmitters);
			}
		}

	}

	@GetMapping("/ldap/sync/status")
	public SseEmitter handleDashboardEmitter() {
		final SseEmitter emitter = new SseEmitter(60000L);
		emitter.onCompletion(new Runnable() {
			@Override
			public void run() {
				emitters.remove(emitter);
			}
		});
		emitter.onTimeout(new Runnable() {
			@Override
			public void run() {
				emitters.remove(emitter);
			}
		});
		this.emitters.add(emitter);
		return emitter;
	}

	@GetMapping("/ldap/integration/list")
	public String getIntegrationPage() {
		return "ldap/integration/list";
	}

	@GetMapping("/ldap/integration/list-paginated")
	public ResponseEntity<?> handleList(@RequestParam(value = "search", required = false) String search,
			Pageable pageable) {
		RestResponseBody result = new RestResponseBody();
		try {
			Page<LdapIntegration> integrations = ldapService.getIntegrations(pageable, search);
			result.add("integrations", integrations);
		} catch (Exception e) {
			log.error("Exception occurred when trying to find integrations", e);
			result.setMessage(e.getMessage());
			return ResponseEntity.badRequest().body(result);
		}
		return ResponseEntity.ok(result);
	}

	private LdapIntegration toIntegrationEntity(LdapIntegrationForm form, String username)
			throws GeneralSecurityException {
		LdapIntegration entity = mapper.toIntegrationEntity(form);
		entity.setEncryptedSearchPassword(encryptionService.encode(form.getPassword()));
		Date date = new Date();
		entity.setCreatedBy(username);
		entity.setCreatedDate(date);
		entity.setLastModifiedBy(username);
		entity.setLastModifiedDate(date);
		return entity;
	}

}

package tr.com.agem.alfa.controller;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Strings.isNullOrEmpty;
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
import tr.com.agem.alfa.model.CurrentUser;
import tr.com.agem.alfa.model.LdapIntegration;
import tr.com.agem.alfa.model.enums.LdapEncryptionType;
import tr.com.agem.alfa.model.enums.LdapType;
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

	@Value("${sys.page-size}")
	private Integer sysPageSize;

	/**
	 * Emitter pool that we add for each client connection and remove for each
	 * completion and timeout event.
	 */
	private final CopyOnWriteArrayList<SseEmitter> emitters = new CopyOnWriteArrayList<SseEmitter>();

	@Autowired
	public LdapIntegrationController(LdapService ldapService, LdapSyncService ldapSyncService,
			EncryptionService encryptionService, LdapIntegrationFormValidator validator) {
		this.ldapService = ldapService;
		this.ldapSyncService = ldapSyncService;
		this.encryptionService = encryptionService;
		this.validator = validator;
	}

	@InitBinder("form")
	public void initBinder(WebDataBinder binder) {
		binder.addValidators(validator);
	}

	@GetMapping("/ldap/integration/create")
	public ModelAndView getIntegrationCreatePage() {
		log.info("Getting integration create form");
		return new ModelAndView("ldap/integration/create", "form", new LdapIntegrationForm());
	}

	@PostMapping("/ldap/integration/create")
	public String handleIntegrationCreateForm(@Valid @ModelAttribute("form") LdapIntegrationForm form,
			BindingResult bindingResult, Authentication authentication) {
		log.info("Processing integration create form:{}, bindingResult:{}", form, bindingResult);
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
		log.info("Getting page for integration:{}", id);
		LdapIntegration integration = ldapService.getIntegration(id);
		checkNotNull(integration, String.format("Integration:%d not found.", id));
		return new ModelAndView("ldap/integration/edit", "form", toIntegrationForm(integration));
	}

	@PostMapping("/ldap/integration/{id}")
	public String handleIntegrationUpdate(@PathVariable Long id,
			@Valid @ModelAttribute("form") LdapIntegrationForm form, BindingResult bindingResult,
			Authentication authentication) {
		log.info("Processing integration update form:{}, bindingResult:{}", form, bindingResult);
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
		log.info("Syncing integration:{}", id);
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
					String.format("Synchronizing LDAP server: %s. A notification will be sent upon completion.",
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
							success ? "LDAP sync finished successfully." : "Error occurred while syncing LDAP users.");
					data.add("success", success);
					emitter.send(event().reconnectTime(500).name("ldap-sync-status").id(date.getTime() + "").data(data,
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
		log.info("Getting SSE emitter for LDAP sync.");
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
		log.info("Getting integration list page.");
		return "ldap/integration/list";
	}

	@GetMapping("/ldap/integration/list-paginated")
	public ResponseEntity<?> handleList(@RequestParam(value = "search", required = false) String search,
			Pageable pageable) {
		log.info("Getting package page with page number:{} and size: {}", pageable.getPageNumber(),
				pageable.getPageSize());
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
		checkArgument(form != null, "Form cannot be null.");
		checkArgument(!isNullOrEmpty(username), "Username cannot be null or empty.");
		LdapIntegration integration = new LdapIntegration();
		Date date = new Date();
		integration.setId(form.getId());
		integration.setCreatedBy(form.getCreatedBy() != null ? form.getCreatedBy() : username);
		integration.setCreatedDate(form.getCreatedDate() != null ? form.getCreatedDate() : date);
		integration.setLastModifiedBy(username);
		integration.setLastModifiedDate(date);
		integration.setIpAddress(form.getIpAddress());
		integration.setPort(form.getPort());
		integration.setLdapType(LdapType.getType(form.getLdapType()));
		integration.setTimeout(form.getTimeout());
		integration.setEncryptionType(LdapEncryptionType.getType(form.getEncryptionType()));
		integration.setValidateServerCert(form.getValidateServerCert());
		integration.setSslCertFilePath(form.getSslCertFilePath());
		integration.setSearchDn(form.getSearchDn());
		integration.setEncryptedSearchPassword(encryptionService.encode(form.getPassword()));
		integration.setUserDnPattern(form.getUserDnPattern());
		integration.setSearchForDn(form.getSearchForDn());
		integration.setSearchFilter(form.getSearchFilter());
		integration.setSearchContexts(form.getSearchContexts());
		integration.setAllowEmptyPasswords(form.getAllowEmptyPasswords());
		integration.setUserIdentifierAttribute(form.getUserIdentifierAttribute());
		return integration;
	}

	private LdapIntegrationForm toIntegrationForm(LdapIntegration integration) {
		LdapIntegrationForm form = new LdapIntegrationForm();
		form.setId(integration.getId());
		form.setAllowEmptyPasswords(integration.getAllowEmptyPasswords());
		form.setCreatedBy(integration.getCreatedBy());
		form.setCreatedDate(integration.getCreatedDate());
		form.setEncryptionType(integration.getEncryptionType().getId());
		form.setIpAddress(integration.getIpAddress());
		form.setLastModifiedBy(integration.getLastModifiedBy());
		form.setLastModifiedDate(integration.getLastModifiedDate());
		form.setLdapType(integration.getLdapType().getId());
		form.setPort(integration.getPort());
		form.setSearchContexts(integration.getSearchContexts());
		form.setSearchDn(integration.getSearchDn());
		form.setSearchFilter(integration.getSearchFilter());
		form.setSearchForDn(integration.getSearchForDn());
		form.setSslCertFilePath(integration.getSslCertFilePath());
		form.setTimeout(integration.getTimeout());
		form.setUserDnPattern(integration.getUserDnPattern());
		form.setUserIdentifierAttribute(integration.getUserIdentifierAttribute());
		form.setValidateServerCert(integration.getValidateServerCert());
		// We omit password field intentionally
		return form;
	}

}

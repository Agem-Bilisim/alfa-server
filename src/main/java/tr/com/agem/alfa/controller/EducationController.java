package tr.com.agem.alfa.controller;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import tr.com.agem.alfa.exception.AlfaException;
import tr.com.agem.alfa.form.EducationForm;
import tr.com.agem.alfa.lms.LmsUser;
import tr.com.agem.alfa.mapper.SysMapper;
import tr.com.agem.alfa.messaging.factory.MessageFactory;
import tr.com.agem.alfa.messaging.message.ServerBaseMessage;
import tr.com.agem.alfa.messaging.service.MessagingService;
import tr.com.agem.alfa.model.CurrentUser;
import tr.com.agem.alfa.model.Education;
import tr.com.agem.alfa.model.EducationLdapUser;
import tr.com.agem.alfa.model.LdapUser;
import tr.com.agem.alfa.model.enums.EducationStatus;
import tr.com.agem.alfa.service.AgentService;
import tr.com.agem.alfa.service.EducationService;
import tr.com.agem.alfa.service.LdapService;

/**
 * @author <a href="mailto:emre.akkaya@agem.com.tr">Emre Akkaya</a>
 */
@Controller
public class EducationController {

	private static final Logger log = LoggerFactory.getLogger(EducationController.class);

	private final EducationService educationService;
	private final LdapService ldapService;
	private final AgentService agentService;
	private final MessagingService messagingService;
	private final SysMapper mapper;

	@Value("${sys.page-size}")
	private Integer sysPageSize;

	@Autowired
	public EducationController(EducationService educationService, LdapService ldapService, AgentService agentService,
			MessagingService messagingService, SysMapper mapper) {
		this.educationService = educationService;
		this.ldapService = ldapService;
		this.agentService = agentService;
		this.messagingService = messagingService;
		this.mapper = mapper;
	}

	@GetMapping("/education/create")
	public String getCreatePage(Model model) {
		model.addAttribute("form", new EducationForm());
		return "education/create";
	}

	@PostMapping("/education/create")
	public String handleEducationCreate(@Valid @ModelAttribute("form") EducationForm form, BindingResult bindingResult,
			Model model, Authentication authentication) {
		if (bindingResult.hasErrors()) {
			// failed validation
			return "education/create";
		}
		try {
			CurrentUser user = (CurrentUser) authentication.getPrincipal();
			checkNotNull(user, "Current user not found.");
			educationService.saveEducation(toEducationEntity(form, user.getUsername()));
		} catch (Exception e) {
			log.error("Exception occurred when trying to save the education, assuming invalid parameters", e);
			bindingResult.reject("unexpected.error", "Beklenmeyen hata oluştu.");
			return "education/create";
		}
		// everything fine redirect to list
		return "redirect:/education/list";
	}

	@PostMapping("/education/{educationId}/send/{messagingId}")
	public ResponseEntity<?> handleURLSend(@PathVariable(name = "educationId") Long educationId,
			@PathVariable(name = "messagingId") String messagingId) {
		RestResponseBody result = new RestResponseBody();
		try {
			Education education = educationService.getEducation(educationId);
			checkNotNull(education, String.format("Education:%d not found.", educationId));
			ServerBaseMessage message = MessageFactory.newURLRedirectionMessage(messagingId, education);
			messagingService.send(message);
		} catch (Exception e) {
			log.error("Exception occurred when trying to send URL, assuming invalid parameters", e);
			result.setMessage(e.getMessage());
			return ResponseEntity.badRequest().body(result);
		}
		return ResponseEntity.ok(result);
	}

	@GetMapping("/education/{id}")
	public String getEducation(@PathVariable Long id, Model model) {
		Education education = educationService.getEducation(id);
		checkNotNull(education, String.format("Education:%d not found.", id));
		model.addAttribute("form", mapper.toEducationForm(education));
		return "education/edit";
	}

	@PostMapping("/education/{id}")
	public String handleEducationUpdate(@PathVariable Long id, @Valid @ModelAttribute("form") EducationForm form,
			BindingResult bindingResult, Model model, Authentication authentication) {
		if (bindingResult.hasErrors()) {
			// failed validation
			return "education/edit";
		}
		try {
			CurrentUser user = (CurrentUser) authentication.getPrincipal();
			checkNotNull(user, "Current user not found.");
			educationService.saveEducation(toEducationEntity(form, user.getUsername()));
		} catch (Exception e) {
			log.warn("Exception occurred when trying to save the education, invalid parameters.", e);
			bindingResult.reject("unexpected.error", "Beklenmeyen hata oluştu.");
			return "education/edit";
		}
		// everything fine redirect to list
		return "redirect:/education/list";
	}

	@GetMapping("/education/list")
	public String getListPage(Model model) {
		model.addAttribute("agents", agentService.getAgents());
		return "education/list";
	}

	@GetMapping("/education/list-paginated")
	public ResponseEntity<?> handleEducationList(@RequestParam(value = "search", required = false) String search,
			Pageable pageable) {
		RestResponseBody result = new RestResponseBody();
		try {
			Page<Education> educations = educationService.getEducations(pageable, search);
			result.add("educations", checkNotNull(educations, "Educations not found."));
		} catch (Exception e) {
			log.error("Exception occurred when trying to find educations, assuming invalid parameters", e);
			result.setMessage(e.getMessage());
			return ResponseEntity.badRequest().body(result);
		}
		return ResponseEntity.ok(result);
	}

	@PostMapping("/education/{id}/delete")
	public ResponseEntity<?> handleDelete(@PathVariable Long id) {
		RestResponseBody result = new RestResponseBody();
		try {
			educationService.deleteEducation(checkNotNull(id, "ID not found."));
		} catch (Exception e) {
			log.error("Exception occurred when trying to delete education, assuming invalid parameters", e);
			result.setMessage(e.getMessage());
			return ResponseEntity.badRequest().body(result);
		}
		return ResponseEntity.ok(result);
	}

	@GetMapping("/education/snyc")
	public ResponseEntity<?> handleLmsSync(Authentication authentication) {
		RestResponseBody result = new RestResponseBody();
		try {
			CurrentUser user = (CurrentUser) authentication.getPrincipal();
			checkNotNull(user, "Current user not found.");
			List<LmsUser> lmsUsers = new ObjectMapper().readValue(
					this.getClass().getClassLoader().getResourceAsStream("lms-sync.json"),
					new TypeReference<List<LmsUser>>() {
					});
			if (lmsUsers == null) {
				throw new AlfaException("LMS sunucuya erişilemedi.");
			}
			for (LmsUser lmsUser : lmsUsers) {
				// Try to find education
				Education education = checkNotNull(educationService.getEducationByLmsIdOrLabel(lmsUser.getId(), lmsUser.getUrunAdi()), "Education not found.");
				// Update/set LMS ID of the education record if necessary
				if (!lmsUser.getId().equals(education.getLmsEducationId())) {
					education.setLmsEducationId(lmsUser.getId());
					this.educationService.saveEducation(education);
				}
				
				// Try to find LDAP user
				LdapUser ldapUser = checkNotNull(ldapService.getUserByLmsIdOrEmail(lmsUser.getUserId(), lmsUser.getEmail()), "LDAP user not found.");
				// Update/set LMS ID of the LDAP user record if necessary
				if (!lmsUser.getUserId().equals(ldapUser.getLmsUserId())) {
					ldapUser.setLmsUserId(lmsUser.getUserId());
					this.ldapService.save(ldapUser);
				}
				
				EducationLdapUser elu = new EducationLdapUser(education, ldapUser,
						EducationStatus.getTypeFromLabel(lmsUser.getDurumu()).getId(), lmsUser.getSure(),
						lmsUser.getSinavPuani(), lmsUser.getSinavDurumu());
				education.addEducationUser(elu);
				educationService.saveEducation(education);
			}
			result.setMessage(String.format("LMS eğitim kayıtları başarıyla senkronize edildi."));
		} catch (Exception e) {
			log.error("Exception occurred when trying to sync LMS education records", e);
			result.setMessage(e.getMessage());
			return ResponseEntity.badRequest().body(result);
		}
		return ResponseEntity.ok(result);
	}

	@GetMapping("/education/{educationId}/users")
	public @ResponseBody ResponseEntity<?> getEducationUsersPage(@PathVariable Long educationId) {
		log.info("Getting details for education with education id:{}", educationId);
		RestResponseBody result = new RestResponseBody();
		try {
			result.add("users", educationService.getEducationUsers(educationId));
		} catch (Exception e) {
			log.error("Exception occurred when trying to find education", e);
			result.setMessage(e.getMessage());
			return ResponseEntity.badRequest().body(result);
		}
		return ResponseEntity.ok(result);
	}

	private Education toEducationEntity(EducationForm form, String username) {
		Education entity = mapper.toEducationEntity(form);
		Date date = new Date();
		entity.setCreatedBy(username);
		entity.setCreatedDate(date);
		entity.setLastModifiedBy(username);
		entity.setLastModifiedDate(date);
		return entity;
	}

}

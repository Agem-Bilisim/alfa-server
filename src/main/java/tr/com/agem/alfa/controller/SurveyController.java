package tr.com.agem.alfa.controller;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Date;

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

import tr.com.agem.alfa.form.SurveyForm;
import tr.com.agem.alfa.mapper.SysMapper;
import tr.com.agem.alfa.model.CurrentUser;
import tr.com.agem.alfa.model.Survey;
import tr.com.agem.alfa.service.SurveyService;

/**
 * @author <a href="mailto:emre.akkaya@agem.com.tr">Emre Akkaya</a>
 */
@Controller
public class SurveyController {

	private static final Logger log = LoggerFactory.getLogger(SurveyController.class);

	private final SurveyService surveyService;
	private final SysMapper mapper;

	@Value("${sys.page-size}")
	private Integer sysPageSize;

	@Autowired
	public SurveyController(SurveyService surveyService, SysMapper mapper) {
		this.surveyService = surveyService;
		this.mapper = mapper;
	}

	@GetMapping("/survey/create")
	public String getCreatePage(Model model) {
		log.debug("Getting survey create form");
		model.addAttribute("form", new SurveyForm());
		return "survey/create";
	}

	@PostMapping("/survey/create")
	public String handleSurveyCreate(@Valid @ModelAttribute("form") SurveyForm form, BindingResult bindingResult,
			Model model, Authentication authentication) {
		log.debug("Processing survey create form:{}, bindingResult:{}", form, bindingResult);
		if (bindingResult.hasErrors()) {
			// failed validation
			return "survey/create";
		}
		try {
			CurrentUser user = (CurrentUser) authentication.getPrincipal();
			checkNotNull(user, "Current user not found.");
			surveyService.saveSurvey(toSurveyEntity(form, user.getUsername()));
		} catch (Exception e) {
			log.error("Exception occurred when trying to save the survey, assuming invalid parameters", e);
			bindingResult.reject("unexpected.error", "Beklenmeyen hata oluştu.");
			return "survey/create";
		}
		// everything fine redirect to list
		return "redirect:/survey/list";
	}

	@GetMapping("/survey/{id}")
	public String getSurvey(@PathVariable Long id, Model model) {
		log.debug("Getting page for survey:{}", id);
		Survey survey = surveyService.getSurvey(id);
		checkNotNull(survey, String.format("Survey:%d not found.", id));
		model.addAttribute("form", mapper.toSurveyForm(survey));
		return "survey/edit";
	}

	@PostMapping("/survey/{id}")
	public String handleSurveyUpdate(@PathVariable Long id, @Valid @ModelAttribute("form") SurveyForm form,
			BindingResult bindingResult, Model model, Authentication authentication) {
		log.debug("Processing survey update form:{}, bindingResult:{}", form, bindingResult);
		if (bindingResult.hasErrors()) {
			// failed validation
			return "survey/edit";
		}
		try {
			CurrentUser user = (CurrentUser) authentication.getPrincipal();
			checkNotNull(user, "Current user not found.");
			surveyService.saveSurvey(toSurveyEntity(form, user.getUsername()));
		} catch (Exception e) {
			log.warn("Exception occurred when trying to save the survey, duplicate email or surveyname", e);
			bindingResult.reject("surveyname.or.email.exists", "Kullanıcı adı ya da e-posta zaten mevcut.");
			return "survey/edit";
		}
		// everything fine redirect to list
		return "redirect:/survey/list";
	}

	@GetMapping("/survey/list")
	public String getListPage() {
		log.debug("Getting survey list page");
		return "survey/list";
	}

	@GetMapping("/survey/list-paginated")
	public ResponseEntity<?> handleSurveyList(@RequestParam(value = "search", required = false) String search,
			Pageable pageable) {
		log.info("Getting survey page with page number:{} and size: {}", pageable.getPageNumber(),
				pageable.getPageSize());
		RestResponseBody result = new RestResponseBody();
		try {
			Page<Survey> surveys = surveyService.getSurveys(pageable, search);
			result.add("surveys", checkNotNull(surveys, "Surveys not found."));
		} catch (Exception e) {
			log.error("Exception occurred when trying to find surveys, assuming invalid parameters", e);
			result.setMessage(e.getMessage());
			return ResponseEntity.badRequest().body(result);
		}
		return ResponseEntity.ok(result);
	}

	private Survey toSurveyEntity(SurveyForm form, String username) {
		Survey entity = mapper.toSurveyEntity(form);
		Date date = new Date();
		entity.setCreatedBy(username);
		entity.setCreatedDate(date);
		entity.setLastModifiedBy(username);
		entity.setLastModifiedDate(date);
		return entity;
	}

}

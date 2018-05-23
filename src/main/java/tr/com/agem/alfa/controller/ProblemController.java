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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import tr.com.agem.alfa.form.ProblemForm;
import tr.com.agem.alfa.model.CurrentUser;
import tr.com.agem.alfa.model.Problem;
import tr.com.agem.alfa.service.ProblemService;
import tr.com.agem.alfa.util.AlfaBeanUtils;

/**
 * @author <a href="mailto:emre.akkaya@agem.com.tr">Emre Akkaya</a>
 */
@Controller
public class ProblemController {

	private static final Logger log = LoggerFactory.getLogger(ProblemController.class);

	private final ProblemService problemService;

	@Value("${sys.page-size}")
	private Integer sysPageSize;

	@Autowired
	public ProblemController(ProblemService problemService) {
		this.problemService = problemService;
	}
	
	@GetMapping("/problem/create")
	public ModelAndView getCreatePage(@RequestAttribute(name="redirect", required=false) String redirect) {
		log.debug("Getting problem create form");
		return new ModelAndView("problem/create", "form", new ProblemForm().setRedirect(redirect));
	}
	
	@PostMapping("/problem/create")
	public String handleProblemCreate(@Valid @ModelAttribute("form") ProblemForm form, BindingResult bindingResult,
			Authentication authentication) {
		log.debug("Processing create:{}, bindingResult:{}", form, bindingResult);
		if (bindingResult.hasErrors()) {
			// failed validation
			return "problem/create";
		}
		try {
			CurrentUser user = (CurrentUser) authentication.getPrincipal();
			checkNotNull(user, "Current user not found.");
			problemService.saveProblem(toProblemEntity(form, user.getUsername()));
		} catch (Exception e) {
			log.error("Exception occurred when trying to save the problem, assuming invalid parameters", e);
			bindingResult.reject("unexpected",
					"Beklenmeyen hata oluştu.");
			return "problem/create";
		}
		// everything fine redirect to list
		return ControllerUtils.getRedirectMapping(form, "/problem/list");
	}
	
	@GetMapping("/problem/{id}")
	public ModelAndView getProblem(@PathVariable Long id,
			@RequestAttribute(name="redirect", required=false) String redirect) {
		log.debug("Getting page for the problem:{}", id);
		Problem problem = problemService.getProblem(id);
		checkNotNull(problem, String.format("Problem:%d not found.", id));
		return new ModelAndView("problem/edit", "form", toProblemForm(problem, redirect));
	}
	
	@PostMapping("/problem/{id}")
	public String handleProcessUpdate(@PathVariable Long id, @Valid @ModelAttribute("form") ProblemForm form,
			BindingResult bindingResult, Authentication authentication) {
		log.debug("Processing update:{}, bindingResult:{}", form, bindingResult);
		if (bindingResult.hasErrors()) {
			// failed validation
			return "problem/edit";
		}
		try {
			CurrentUser user = (CurrentUser) authentication.getPrincipal();
			checkNotNull(user, "Current user not found.");
			form.setId(id);
			problemService.saveProblem(toProblemEntity(form, user.getUsername()));
		} catch (Exception e) {
			log.error("Exception occurred when trying to save the problem, assuming invalid parameters", e);
			bindingResult.reject("unexpected",
					"Beklenmeyen hata oluştu.");
			return "problem/edit";
		}
		// everything fine redirect to list
		return ControllerUtils.getRedirectMapping(form, "/problem/list");
	}
	
	@GetMapping("/problem/list")
	public String getListPage() {
		log.debug("Getting list page");
		return "problem/list";
	}
	
	@GetMapping("/problem/list-paginated")
	public ResponseEntity<?> handleList(@RequestParam(value = "search", required = false) String search,
			@RequestParam(value = "referenceType", required = false) Integer referenceType, Pageable pageable) {
		log.info("Getting problem page with page number:{} and size: {}", pageable.getPageNumber(),
				pageable.getPageSize());
		RestResponseBody result = new RestResponseBody();
		try {
			Page<Problem> packages = problemService.getProblems(pageable, search, referenceType);
			result.add("problems", checkNotNull(packages, "Problems not found."));
		} catch (Exception e) {
			log.error("Exception occurred when trying to find problems, assuming invalid parameters", e);
			result.setMessage(e.getMessage());
			return ResponseEntity.badRequest().body(result);
		}
		return ResponseEntity.ok(result);
	}
	
	private Problem toProblemEntity(ProblemForm form, String username) {
		Problem entity = new Problem();
		AlfaBeanUtils.getInstance().copyProperties(form, entity);
		Date date = new Date();
		entity.setCreatedBy(username);
		entity.setCreatedDate(date);
		entity.setLastModifiedBy(username);
		entity.setLastModifiedDate(date);
		return entity;
	}
	
	private ProblemForm toProblemForm(Problem entity, String redirect) {
		ProblemForm form = new ProblemForm();
		AlfaBeanUtils.getInstance().copyProperties(entity, form);
		form.setRedirect(redirect);
		return form;
	}

}

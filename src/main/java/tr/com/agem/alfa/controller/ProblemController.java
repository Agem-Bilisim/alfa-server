package tr.com.agem.alfa.controller;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import tr.com.agem.alfa.form.GpuForm;
import tr.com.agem.alfa.form.PackageForm;
import tr.com.agem.alfa.form.PeripheralDeviceForm;
import tr.com.agem.alfa.form.ProblemForm;
import tr.com.agem.alfa.mapper.SysMapper;
import tr.com.agem.alfa.model.CurrentUser;
import tr.com.agem.alfa.model.Gpu;
import tr.com.agem.alfa.model.InstalledPackage;
import tr.com.agem.alfa.model.PeripheralDevice;
import tr.com.agem.alfa.model.Problem;
import tr.com.agem.alfa.model.ProblemReference;
import tr.com.agem.alfa.model.enums.ProblemReferenceType;
import tr.com.agem.alfa.service.HardwareService;
import tr.com.agem.alfa.service.ProblemService;
import tr.com.agem.alfa.service.SoftwareService;
import tr.com.agem.alfa.util.SelectboxBuilder;
import tr.com.agem.alfa.util.SelectboxBuilder.OptionFormConvertable;

/**
 * @author <a href="mailto:emre.akkaya@agem.com.tr">Emre Akkaya</a>
 */
@Controller
public class ProblemController {

	private static final Logger log = LoggerFactory.getLogger(ProblemController.class);

	private final ProblemService problemService;
	private final SoftwareService softwareService;
	private final HardwareService hardwareService;
	private final MessageSource messageSource;
	private final SysMapper mapper;

	@Value("${sys.page-size}")
	private Integer sysPageSize;

	@Value("${sys.locale}")
	private String locale;

	@Autowired
	public ProblemController(ProblemService problemService, SoftwareService softwareService,
			HardwareService hardwareService, MessageSource messageSource, SysMapper mapper) {
		this.problemService = problemService;
		this.softwareService = softwareService;
		this.hardwareService = hardwareService;
		this.messageSource = messageSource;
		this.mapper = mapper;
	}

	@GetMapping("/problem/create")
	public ModelAndView getCreatePage(@RequestParam(value = "referenceType", required = false) Integer referenceType,
			@RequestParam(name = "redirect", required = false) String redirect) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			// @formatter:off
			SelectboxBuilder builder = SelectboxBuilder
										.newSelectbox();
			if (referenceType == ProblemReferenceType.PACKAGE.getId()) {
				builder.add(toPackageFormList(softwareService.getPackages()));
			} else if (referenceType == ProblemReferenceType.HARDWARE.getId()) {
				builder.add(toPeripheralFormList(hardwareService.getPeripherals()));
				builder.add(toGpuFormList(hardwareService.getGpus()));
			} else { // ALL
				builder.add(toPackageFormList(softwareService.getPackages()));
				builder.add(toPeripheralFormList(hardwareService.getPeripherals()));
				builder.add(toGpuFormList(hardwareService.getGpus()));
			}
			// @formatter:on
			model.put("possiblerefs", builder.build());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		model.put("form", new ProblemForm().setRedirect(redirect));
		return new ModelAndView("problem/create", model);
	}

	@PostMapping("/problem/create")
	public String handleProblemCreate(@Valid @ModelAttribute("form") ProblemForm form, BindingResult bindingResult,
			Authentication authentication) {
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
			bindingResult.reject("unexpected", "Beklenmeyen hata oluştu.");
			return "problem/create";
		}
		// everything fine redirect to list
		return ControllerUtils.getRedirectMapping(form, "/problem/list");
	}

	@GetMapping("/problem/{id}")
	public ModelAndView getProblem(@PathVariable Long id,
			@RequestParam(value = "referenceType", required = false) Integer referenceType,
			@RequestParam(name = "redirect", required = false) String redirect) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			// @formatter:off
			SelectboxBuilder builder = SelectboxBuilder
										.newSelectbox();
			if (referenceType == ProblemReferenceType.PACKAGE.getId()) {
				builder.add(toPackageFormList(softwareService.getPackages()));
			} else if (referenceType == ProblemReferenceType.HARDWARE.getId()) {
				builder.add(toPeripheralFormList(hardwareService.getPeripherals()));
				builder.add(toGpuFormList(hardwareService.getGpus()));
			} else { // ALL
				builder.add(toPackageFormList(softwareService.getPackages()));
				builder.add(toPeripheralFormList(hardwareService.getPeripherals()));
				builder.add(toGpuFormList(hardwareService.getGpus()));
			}
			// @formatter:on
			model.put("possiblerefs", builder.build());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		Problem problem = problemService.getProblem(id);
		model.put("form", mapper.toProblemForm(checkNotNull(problem, String.format("Problem:%d not found.", id)))
				.setRedirect(redirect));
		model.put("rlabel", getRedirectionLabel(redirect));
		return new ModelAndView("problem/edit", model);
	}

	@PostMapping("/problem/{id}")
	public String handleProcessUpdate(@PathVariable Long id, @Valid @ModelAttribute("form") ProblemForm form,
			BindingResult bindingResult, Authentication authentication) {
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
			bindingResult.reject("unexpected", "Beklenmeyen hata oluştu.");
			return "problem/edit";
		}
		// everything fine redirect to list
		return ControllerUtils.getRedirectMapping(form, "/problem/list");
	}

	@PostMapping("/problem/{id}/delete")
	public ResponseEntity<?> handleProcessDelete(@PathVariable Long id) {
		RestResponseBody result = new RestResponseBody();
		try {
			problemService.deleteProblem(checkNotNull(id, "ID not found."));
		} catch (Exception e) {
			log.error("Exception occurred when trying to delete problem, assuming invalid parameters", e);
			result.setMessage(e.getMessage());
			return ResponseEntity.badRequest().body(result);
		}
		return ResponseEntity.ok(result);
	}

	@GetMapping("/problem/list")
	public String getListPage() {
		return "problem/list";
	}

	@GetMapping("/problem/list-paginated")
	public ResponseEntity<?> handleList(@RequestParam(value = "search", required = false) String search,
			@RequestParam(value = "referenceType", required = false) Integer referenceType, Pageable pageable) {
		RestResponseBody result = new RestResponseBody();
		try {
			Page<Problem> problems = problemService.getProblems(pageable, search, referenceType);
			result.add("problems", checkNotNull(problems, "Problems not found."));
		} catch (Exception e) {
			log.error("Exception occurred when trying to find problems, assuming invalid parameters", e);
			result.setMessage(e.getMessage());
			return ResponseEntity.badRequest().body(result);
		}
		return ResponseEntity.ok(result);
	}

	private Problem toProblemEntity(ProblemForm form, String username) {
		Problem entity = mapper.toProblemEntity(form);
		Date date = new Date();
		entity.setCreatedBy(username);
		entity.setCreatedDate(date);
		entity.setLastModifiedBy(username);
		entity.setLastModifiedDate(date);
		if (entity.getReferences() != null) {
			Iterator<ProblemReference> it = entity.getReferences().iterator();
			while (it.hasNext()) {
				ProblemReference ref = it.next();
				ref.setProblem(entity);
			}
		}
		return entity;
	}

	private List<? extends OptionFormConvertable> toPackageFormList(List<InstalledPackage> entities) {
		if (entities == null || entities.isEmpty()) return null;
		List<PackageForm> forms = new ArrayList<PackageForm>();
		for (InstalledPackage entity : entities) {
			forms.add(mapper.toPackageForm(entity));
		}
		return forms;
	}

	private List<? extends OptionFormConvertable> toPeripheralFormList(List<PeripheralDevice> entities) {
		if (entities == null || entities.isEmpty()) return null;
		List<PeripheralDeviceForm> forms = new ArrayList<PeripheralDeviceForm>();
		for (PeripheralDevice entity : entities) {
			forms.add(mapper.toPeripheralDeviceForm(entity));
		}
		return forms;
	}

	private List<? extends OptionFormConvertable> toGpuFormList(List<Gpu> entities) {
		if (entities == null || entities.isEmpty()) return null;
		List<GpuForm> forms = new ArrayList<GpuForm>();
		for (Gpu entity : entities) {
			forms.add(mapper.toGpuForm(entity));
		}
		return forms;
	}

	/**
	 * @param redirect
	 * @return
	 * @throws NoSuchMessageException
	 */
	private String getRedirectionLabel(String redirect) throws NoSuchMessageException {
		return messageSource.getMessage(ControllerUtils.getRedirectUrl(redirect, "/problem/list").replace("/", "."),
				null, Locale.forLanguageTag(locale));
	}

}

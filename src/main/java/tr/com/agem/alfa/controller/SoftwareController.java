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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import tr.com.agem.alfa.form.PackageForm;
import tr.com.agem.alfa.form.ProcessForm;
import tr.com.agem.alfa.mapper.SysMapper;
import tr.com.agem.alfa.model.CurrentUser;
import tr.com.agem.alfa.model.InstalledPackage;
import tr.com.agem.alfa.model.RunningProcess;
import tr.com.agem.alfa.service.SoftwareService;

/**
 * @author <a href="mailto:emre.akkaya@agem.com.tr">Emre Akkaya</a>
 */
@Controller
public class SoftwareController {

	private static final Logger log = LoggerFactory.getLogger(SoftwareController.class);

	private final SoftwareService softwareService;
	private final SysMapper mapper;

	@Value("${sys.page-size}")
	private Integer sysPageSize;

	@Autowired
	public SoftwareController(SoftwareService softwareService, SysMapper mapper) {
		this.softwareService = softwareService;
		this.mapper = mapper;
	}

	@GetMapping("/installed-package/create")
	public ModelAndView getPackageCreatePage() {
		log.debug("Getting installed package create form");
		return new ModelAndView("installed-package/create", "form", new PackageForm());
	}
	
	@GetMapping("/process/create")
	public ModelAndView getProcessCreatePage() {
		log.debug("Getting process create form");
		return new ModelAndView("process/create", "form", new ProcessForm());
	}

	@PostMapping("/installed-package/create")
	public String handlePackageCreate(@Valid @ModelAttribute("form") PackageForm form, BindingResult bindingResult,
			Authentication authentication) {
		log.debug("Processing create:{}, bindingResult:{}", form, bindingResult);
		if (bindingResult.hasErrors()) {
			// failed validation
			return "installed-package/create";
		}
		try {
			CurrentUser user = (CurrentUser) authentication.getPrincipal();
			checkNotNull(user, "Current user not found.");
			softwareService.savePackage(toPackageEntity(form, user.getUsername()));
		} catch (Exception e) {
			log.error("Exception occurred when trying to save the package, assuming duplicate name-version", e);
			bindingResult.reject("name.version.exists",
					"Hata oluştu. Aynı paket adı ve sürümüyle birden fazla kayıt olamaz.");
			return "installed-package/create";
		}
		// everything fine redirect to list
		return "redirect:/software/list";
	}
	
	@PostMapping("/process/create")
	public String handleProcessCreate(@Valid @ModelAttribute("form") ProcessForm form, BindingResult bindingResult,
			Authentication authentication) {
		log.debug("Processing create:{}, bindingResult:{}", form, bindingResult);
		if (bindingResult.hasErrors()) {
			// failed validation
			return "process/create";
		}
		try {
			CurrentUser user = (CurrentUser) authentication.getPrincipal();
			checkNotNull(user, "Current user not found.");
			softwareService.saveProcess(toProcessEntity(form, user.getUsername()));
		} catch (Exception e) {
			log.error("Exception occurred when trying to save the process, assuming duplicate name", e);
			bindingResult.reject("name.exists",
					"Hata oluştu. Aynı hizmet adıyla birden fazla kayıt olamaz.");
			return "process/create";
		}
		// everything fine redirect to list
		return "redirect:/software/list";
	}

	@GetMapping("/installed-package/{id}")
	public ModelAndView getPackage(@PathVariable Long id) {
		log.debug("Getting page for the package:{}", id);
		InstalledPackage _package = softwareService.getPackage(id);
		checkNotNull(_package, String.format("Package:%d not found.", id));
		return new ModelAndView("installed-package/edit", "form", mapper.toPackageForm(_package));
	}
	
	@GetMapping("/process/{id}")
	public ModelAndView getProcess(@PathVariable Long id) {
		log.debug("Getting page for the process:{}", id);
		RunningProcess process = softwareService.getProcess(id);
		checkNotNull(process, String.format("Process:%d not found.", id));
		return new ModelAndView("process/edit", "form", mapper.toProcessForm(process));
	}

	@PostMapping("/installed-package/{id}")
	public String handlePackageUpdate(@PathVariable Long id, @Valid @ModelAttribute("form") PackageForm form,
			BindingResult bindingResult, Authentication authentication) {
		log.debug("Processing update:{}, bindingResult:{}", form, bindingResult);
		if (bindingResult.hasErrors()) {
			// failed validation
			return "installed-package/edit";
		}
		try {
			CurrentUser user = (CurrentUser) authentication.getPrincipal();
			checkNotNull(user, "Current user not found.");
			form.setId(id);
			softwareService.savePackage(toPackageEntity(form, user.getUsername()));
		} catch (Exception e) {
			log.warn("Exception occurred when trying to save the package, duplicate name and version", e);
			bindingResult.reject("name.version.exists",
					"Hata oluştu. Aynı paket adı ve sürümüyle birden fazla kayıt olamaz.");
			return "installed-package/edit";
		}
		// everything fine redirect to list
		return "redirect:/software/list";
	}
	
	@PostMapping("/process/{id}")
	public String handleProcessUpdate(@PathVariable Long id, @Valid @ModelAttribute("form") ProcessForm form,
			BindingResult bindingResult, Authentication authentication) {
		log.debug("Processing update:{}, bindingResult:{}", form, bindingResult);
		if (bindingResult.hasErrors()) {
			// failed validation
			return "process/edit";
		}
		try {
			CurrentUser user = (CurrentUser) authentication.getPrincipal();
			checkNotNull(user, "Current user not found.");
			form.setId(id);
			softwareService.saveProcess(toProcessEntity(form, user.getUsername()));
		} catch (Exception e) {
			log.warn("Exception occurred when trying to save the process, duplicate name", e);
			bindingResult.reject("name.exists",
					"Hata oluştu. Aynı hizmet adıyla birden fazla kayıt olamaz.");
			return "process/edit";
		}
		// everything fine redirect to list
		return "redirect:/software/list";
	}
	
	@PostMapping("/installed-package/{id}/delete")
	public ResponseEntity<?> handlePackageDelete(@PathVariable Long id) {
		log.debug("Processing delete for package:{}}", id);
		RestResponseBody result = new RestResponseBody();
		try {
			softwareService.deletePackage(checkNotNull(id, "ID not found."));
		} catch (Exception e) {
			log.error("Exception occurred when trying to delete package, assuming invalid parameters", e);
			result.setMessage(e.getMessage());
			return ResponseEntity.badRequest().body(result);
		}
		return ResponseEntity.ok(result);
	}
	
	@PostMapping("/process/{id}/delete")
	public ResponseEntity<?> handleProcessDelete(@PathVariable Long id) {
		log.debug("Processing delete for running process:{}}", id);
		RestResponseBody result = new RestResponseBody();
		try {
			softwareService.deleteProcess(checkNotNull(id, "ID not found."));
		} catch (Exception e) {
			log.error("Exception occurred when trying to delete process, assuming invalid parameters", e);
			result.setMessage(e.getMessage());
			return ResponseEntity.badRequest().body(result);
		}
		return ResponseEntity.ok(result);
	}

	@GetMapping("/software/list")
	public String getListPage() {
		log.debug("Getting list page");
		return "software/list";
	}

	@GetMapping("/installed-package/list-paginated")
	public ResponseEntity<?> handlePackageList(@RequestParam(value = "search", required = false) String search,
			Pageable pageable) {
		log.info("Getting package page with page number:{} and size: {}", pageable.getPageNumber(),
				pageable.getPageSize());
		RestResponseBody result = new RestResponseBody();
		try {
			Page<InstalledPackage> packages = softwareService.getPackages(pageable, search);
			result.add("packages", checkNotNull(packages, "Packages not found."));
		} catch (Exception e) {
			log.error("Exception occurred when trying to find packages, assuming invalid parameters", e);
			result.setMessage(e.getMessage());
			return ResponseEntity.badRequest().body(result);
		}
		return ResponseEntity.ok(result);
	}
	
	@GetMapping("/process/list-paginated")
	public ResponseEntity<?> handleProcessList(@RequestParam(value = "search", required = false) String search,
			Pageable pageable) {
		log.info("Getting process page with page number:{} and size: {}", pageable.getPageNumber(),
				pageable.getPageSize());
		RestResponseBody result = new RestResponseBody();
		try {
			Page<RunningProcess> processes = softwareService.getProcesses(pageable, search);
			result.add("processes", checkNotNull(processes, "Processes not found."));
		} catch (Exception e) {
			log.error("Exception occurred when trying to find processes, assuming invalid parameters", e);
			result.setMessage(e.getMessage());
			return ResponseEntity.badRequest().body(result);
		}
		return ResponseEntity.ok(result);
	}

	private InstalledPackage toPackageEntity(PackageForm form, String username) {
		InstalledPackage entity = mapper.toPackageEntity(form);
		Date date = new Date();
		entity.setCreatedBy(username);
		entity.setCreatedDate(date);
		entity.setLastModifiedBy(username);
		entity.setLastModifiedDate(date);
		return entity;
	}
	
	private RunningProcess toProcessEntity(ProcessForm form, String username) {
		RunningProcess entity = mapper.toProcessEntity(form);
		Date date = new Date();
		entity.setCreatedBy(username);
		entity.setCreatedDate(date);
		entity.setLastModifiedBy(username);
		entity.setLastModifiedDate(date);
		return entity;
	}

}

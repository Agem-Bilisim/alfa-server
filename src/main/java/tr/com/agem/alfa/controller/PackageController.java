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

import tr.com.agem.alfa.dto.PackageForm;
import tr.com.agem.alfa.model.CurrentUser;
import tr.com.agem.alfa.model.InstalledPackage;
import tr.com.agem.alfa.service.PackageService;

/**
 * @author <a href="mailto:emre.akkaya@agem.com.tr">Emre Akkaya</a>
 */
@Controller
public class PackageController {

	private static final Logger log = LoggerFactory.getLogger(PackageController.class);

	private final PackageService packageService;

	@Value("${sys.page-size}")
	private Integer sysPageSize;

	@Autowired
	public PackageController(PackageService packageService) {
		this.packageService = packageService;
	}

	@GetMapping("/installed-package/create")
	public ModelAndView getCreatePage() {
		log.debug("Getting installed package create form");
		return new ModelAndView("installed-package/create", "form", new PackageForm());
	}

	@PostMapping("/installed-package/create")
	public String handleCreate(@Valid @ModelAttribute("form") PackageForm form, BindingResult bindingResult,
			Authentication authentication) {
		log.debug("Processing create:{}, bindingResult:{}", form, bindingResult);
		if (bindingResult.hasErrors()) {
			// failed validation
			return "installed-package/create";
		}
		try {
			CurrentUser user = (CurrentUser) authentication.getPrincipal();
			checkNotNull(user, "Current user not found.");
			packageService.save(toPackageEntity(form, user.getUsername()));
		} catch (Exception e) {
			log.error("Exception occurred when trying to save the package, assuming duplicate name-version", e);
			bindingResult.reject("name.version.exists", "Hata oluştu. Aynı paket adı ve sürümüyle birden fazla kayıt olamaz.");
			return "installed-package/create";
		}
		// everything fine redirect to list
		return "redirect:/installed-package/list";
	}

	@GetMapping("/installed-package/{id}")
	public ModelAndView getPackage(@PathVariable Long id) {
		log.debug("Getting page for the package:{}", id);
		InstalledPackage _package = packageService.getPackage(id);
		checkNotNull(_package, String.format("Package:%d not found.", id));
		return new ModelAndView("installed-package/edit", "form", toPackageForm(_package));
	}

	@PostMapping("/installed-package/{id}")
	public String handleUpdate(@PathVariable Long id, @Valid @ModelAttribute("form") PackageForm form,
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
			packageService.save(toPackageEntity(form, user.getUsername()));
		} catch (Exception e) {
			log.warn("Exception occurred when trying to save the package, duplicate name and version", e);
			bindingResult.reject("name.version.exists", "Hata oluştu. Aynı paket adı ve sürümüyle birden fazla kayıt olamaz.");
			return "installed-package/edit";
		}
		// everything fine redirect to list
		return "redirect:/installed-package/list";
	}

	@GetMapping("/installed-package/list")
	public String getListPage() {
		log.debug("Getting installed package list page");
		return "installed-package/list";
	}

	@GetMapping("/installed-package/list-paginated")
	public ResponseEntity<?> handleList(@RequestParam(value = "search", required = false) String search,
			Pageable pageable) {
		log.info("Getting package page with page number:{} and size: {}", pageable.getPageNumber(),
				pageable.getPageSize());
		RestResponseBody result = new RestResponseBody();
		try {
			Page<InstalledPackage> packages = packageService.getPackages(pageable, search);
			result.add("packages", checkNotNull(packages, "Packages not found."));
		} catch (Exception e) {
			log.error("Exception occurred when trying to find packages, assuming invalid parameters", e);
			result.setMessage(e.getMessage());
			return ResponseEntity.badRequest().body(result);
		}
		return ResponseEntity.ok(result);
	}

	private InstalledPackage toPackageEntity(PackageForm form, String username) {
		InstalledPackage entity = new InstalledPackage();
		Date date = new Date();
		entity.setId(form.getId());
		entity.setName(form.getName());
		entity.setVersion(form.getVersion());
		entity.setCreatedBy(username);
		entity.setCreatedDate(date);
		entity.setLastModifiedBy(username);
		entity.setLastModifiedDate(date);
		return entity;
	}

	private PackageForm toPackageForm(InstalledPackage entity) {
		PackageForm form = new PackageForm();
		form.setId(entity.getId());
		form.setName(entity.getName());
		form.setVersion(entity.getVersion());
		form.setCreatedBy(entity.getCreatedBy());
		form.setCreatedDate(entity.getCreatedDate());
		form.setLastModifiedBy(entity.getLastModifiedBy());
		form.setLastModifiedDate(entity.getLastModifiedDate());
		return form;
	}

}

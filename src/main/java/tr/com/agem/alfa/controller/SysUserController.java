package tr.com.agem.alfa.controller;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

import tr.com.agem.alfa.form.SysUserForm;
import tr.com.agem.alfa.model.CurrentUser;
import tr.com.agem.alfa.model.SysRole;
import tr.com.agem.alfa.model.SysUser;
import tr.com.agem.alfa.service.SysUserService;
import tr.com.agem.alfa.util.AlfaBeanUtils;
import tr.com.agem.alfa.validator.SysUserFormValidator;

/**
 * @author <a href="mailto:emre.akkaya@agem.com.tr">Emre Akkaya</a>
 */
@PreAuthorize("hasRole('ROLE_ADMIN')")
@Controller
public class SysUserController {

	private static final Logger log = LoggerFactory.getLogger(SysUserController.class);

	private final SysUserService sysUserService;
	private final SysUserFormValidator validator;
	private final BCryptPasswordEncoder encoder;

	@Value("${sys.page-size}")
	private Integer sysPageSize;

	@Autowired
	public SysUserController(SysUserService sysUserService, SysUserFormValidator validator,
			BCryptPasswordEncoder encoder) {
		this.sysUserService = sysUserService;
		this.validator = validator;
		this.encoder = encoder;
	}

	@InitBinder("form")
	public void initBinder(WebDataBinder binder) {
		binder.addValidators(validator);
	}

	@GetMapping("/sysuser/create")
	public ModelAndView getCreatePage() {
		log.debug("Getting user create form");
		List<SysRole> roles = sysUserService.getRoles();
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("roles", roles);
		model.put("form", new SysUserForm());
		return new ModelAndView("sysuser/create", model);
	}

	@PostMapping("/sysuser/create")
	public String handleUserCreate(@Valid @ModelAttribute("form") SysUserForm form, BindingResult bindingResult,
			Authentication authentication) {
		log.debug("Processing user create form:{}, bindingResult:{}", form, bindingResult);
		if (bindingResult.hasErrors()) {
			// failed validation
			return "sysuser/create";
		}
		try {
			CurrentUser user = (CurrentUser) authentication.getPrincipal();
			checkNotNull(user, "Current user not found.");
			sysUserService.saveUser(toUserEntity(form, user.getUsername()));
		} catch (Exception e) {
			log.error("Exception occurred when trying to save the user, assuming duplicate email", e);
			bindingResult.reject("email.exists", "E-posta adresi ile kullanıcı zaten mevcut.");
			return "sysuser/create";
		}
		// everything fine redirect to list
		return "redirect:/sysuser/list";
	}

	@GetMapping("/sysuser/{id}")
	public ModelAndView getUser(@PathVariable Long id) {
		log.debug("Getting page for user:{}", id);
		SysUser user = sysUserService.getUser(id);
		checkNotNull(user, String.format("User:%d not found.", id));
		List<SysRole> roles = sysUserService.getRoles();
		checkNotNull(roles, "Roles not found.");
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("form", toUserForm(user));
		model.put("roles", roles);
		return new ModelAndView("sysuser/edit", model);
	}

	@PostMapping("/sysuser/{id}")
	public String handleUserUpdate(@PathVariable Long id, @Valid @ModelAttribute("form") SysUserForm form,
			BindingResult bindingResult, Authentication authentication) {
		log.debug("Processing user update form:{}, bindingResult:{}", form, bindingResult);
		if (bindingResult.hasErrors()) {
			// failed validation
			return "sysuser/edit";
		}
		try {
			CurrentUser user = (CurrentUser) authentication.getPrincipal();
			checkNotNull(user, "Current user not found.");
			sysUserService.saveUser(toUserEntity(form, user.getUsername()));
		} catch (Exception e) {
			log.warn("Exception occurred when trying to save the user, duplicate email or username", e);
			bindingResult.reject("username.or.email.exists", "Kullanıcı adı ya da e-posta zaten mevcut.");
			return "sysuser/edit";
		}
		// everything fine redirect to list
		return "redirect:/sysuser/list";
	}

	@GetMapping("/sysuser/list")
	public String getListPage() {
		log.debug("Getting user list page");
		return "sysuser/list";
	}

	@GetMapping("/sysuser/list-paginated")
	public ResponseEntity<?> handleUserList(@RequestParam(value = "search", required = false) String search,
			Pageable pageable) {
		log.info("Getting user page with page number:{} and size: {}", pageable.getPageNumber(),
				pageable.getPageSize());
		RestResponseBody result = new RestResponseBody();
		try {
			Page<SysUser> users = sysUserService.getUsers(pageable, search);
			result.add("users", checkNotNull(users, "Users not found."));
		} catch (Exception e) {
			log.error("Exception occurred when trying to find users, assuming invalid parameters", e);
			result.setMessage(e.getMessage());
			return ResponseEntity.badRequest().body(result);
		}
		return ResponseEntity.ok(result);
	}
	
	private SysUser toUserEntity(SysUserForm form, String username) {
		SysUser entity = new SysUser();
		AlfaBeanUtils.getInstance().copyProperties(form, entity);
		entity.setPasswordHash(encoder.encode(form.getPassword()));
		Date date = new Date();
		entity.setCreatedBy(username);
		entity.setCreatedDate(date);
		entity.setLastModifiedBy(username);
		entity.setLastModifiedDate(date);
		return entity;
	}
	
	private SysUserForm toUserForm(SysUser entity) {
		SysUserForm form = new SysUserForm();
		AlfaBeanUtils.getInstance().copyProperties(entity, form);
		return form;
	}

}

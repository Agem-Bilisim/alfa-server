package tr.com.agem.alfa.controller;

import static com.google.common.base.Preconditions.checkNotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import tr.com.agem.alfa.model.LdapUser;
import tr.com.agem.alfa.service.LdapService;

/**
 * @author <a href="mailto:emre.akkaya@agem.com.tr">Emre Akkaya</a>
 */
@PreAuthorize("hasRole('ROLE_ADMIN')")
@Controller
public class LdapUserController {

	private static final Logger log = LoggerFactory.getLogger(LdapUserController.class);

	private final LdapService ldapService;

	@Value("${sys.page-size}")
	private Integer sysPageSize;

	@Autowired
	public LdapUserController(LdapService ldapService) {
		this.ldapService = ldapService;
	}

	@GetMapping("/ldap/user/list")
	public ModelAndView getUserListPage(@RequestParam Long integrationId) {
		return new ModelAndView("ldap/user/list", "integrationId", integrationId);
	}

	@GetMapping("/ldap/user/list-paginated")
	public ResponseEntity<?> handleList(@RequestParam(value = "search", required = false) String search,
			@RequestParam(value = "integrationId", required = false) Long integrationId,
			Pageable pageable) {
		RestResponseBody result = new RestResponseBody();
		try {
			Page<LdapUser> users = ldapService.getUsers(pageable, integrationId, search);
			result.add("users", users);
		} catch (Exception e) {
			log.error("Exception occurred when trying to find tasks, assuming invalid parameters", e);
			result.setMessage(e.getMessage());
			return ResponseEntity.badRequest().body(result);
		}
		return ResponseEntity.ok(result);
	}

	@GetMapping("/ldap/user/{userId}/detail")
	public @ResponseBody ResponseEntity<?> getUserDetails(@PathVariable Long userId) {
		RestResponseBody result = new RestResponseBody();
		try {
			LdapUser user = ldapService.getUser(userId);
			result.add("user", checkNotNull(user, "User not found."));
		} catch (Exception e) {
			log.error("Exception occurred when trying to find user", e);
			result.setMessage(e.getMessage());
			return ResponseEntity.badRequest().body(result);
		}
		return ResponseEntity.ok(result);
	}

}

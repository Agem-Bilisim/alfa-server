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
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import tr.com.agem.alfa.form.PlanForm;
import tr.com.agem.alfa.form.TagForm;
import tr.com.agem.alfa.mapper.SysMapper;
import tr.com.agem.alfa.model.CurrentUser;
import tr.com.agem.alfa.model.Tag;
import tr.com.agem.alfa.service.MonitorService;

/**
 * @author <a href="mailto:emre.akkaya@agem.com.tr">Emre Akkaya</a>
 */
@PreAuthorize("hasRole('ROLE_ADMIN')")
@Controller
public class MonitorController {

	private static final Logger log = LoggerFactory.getLogger(MonitorController.class);

	private MonitorService monitorService;
	private SysMapper sysMapper;

	@Value("${sys.page-size}")
	private Integer sysPageSize;

	@Autowired
	public MonitorController(MonitorService monitorService, SysMapper sysMapper) {
		this.monitorService = monitorService;
		this.sysMapper = sysMapper;
		
	}
	
	@GetMapping("/monitor/main")
	public String mainPage() { 
		log.debug("Monitor page");
		return "monitor/main";
	}

	@GetMapping("/plan/list-paginated")
	public ResponseEntity<?> handlePlanList(@RequestParam(value = "search", required = false) String search, Pageable pageable) {
		RestResponseBody result = new RestResponseBody();
		try {
			Page<Tag> tags = monitorService.getTags(pageable, search);
			result.add("tags", checkNotNull(tags, "Tags not found."));
		} catch (Exception e) {
			log.error("Exception occurred when trying to find tags, assuming invalid parameters", e);
			result.setMessage(e.getMessage());
			return ResponseEntity.badRequest().body(result);
		}
		return ResponseEntity.ok(result);
	}
	
	@PostMapping("/plan/save")
	public ResponseEntity<?> handleSavePlan(@RequestBody PlanForm plan, Authentication authentication) {
		RestResponseBody result = new RestResponseBody();
		try {
			CurrentUser user = (CurrentUser) authentication.getPrincipal();
			checkNotNull(user, "Current user not found.");
			TagForm[] tags = plan.getTags();
			for (TagForm tagForm : tags) {
				Tag tag = sysMapper.toTagEntity(tagForm);
				tag.setLastModifiedBy(user.getUsername());
				monitorService.updateTag(tag);
			}
		} catch (Exception e) {
			log.error("Exception occurred when trying to save tags for lan, assuming invalid parameters", e);
			result.setMessage(e.getMessage());
			return ResponseEntity.badRequest().body(result);
		}
		return ResponseEntity.ok(result);
	}
}

package tr.com.agem.alfa.controller;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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

import tr.com.agem.alfa.form.PeripheralDeviceForm;
import tr.com.agem.alfa.mapper.SysMapper;
import tr.com.agem.alfa.model.CurrentUser;
import tr.com.agem.alfa.model.PeripheralDevice;
import tr.com.agem.alfa.service.AgentService;
import tr.com.agem.alfa.service.PeripheralService;

/**
 * @author <a href="mailto:emre.akkaya@agem.com.tr">Emre Akkaya</a>
 */
@Controller
public class PeripheralController {

	private static final Logger log = LoggerFactory.getLogger(PeripheralController.class);

	private final PeripheralService peripheralService;
	private final AgentService agentService;
	private final SysMapper mapper;

	@Value("${sys.page-size}")
	private Integer sysPageSize;

	@Autowired
	public PeripheralController(PeripheralService peripheralService, AgentService agentService, SysMapper mapper) {
		this.peripheralService = peripheralService;
		this.agentService = agentService;
		this.mapper = mapper;
	}

	@GetMapping("/peripheral/create")
	public ModelAndView getPeripheralCreatePage() {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			model.put("agents", agentService.getAgents());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		model.put("form", new PeripheralDeviceForm());
		return new ModelAndView("peripheral/create", model);
	}

	@PostMapping("/peripheral/create")
	public String handlePeripheralCreate(@Valid @ModelAttribute("form") PeripheralDeviceForm form,
			BindingResult bindingResult, Authentication authentication) {
		if (bindingResult.hasErrors()) {
			// failed validation
			return "/peripheral/create";
		}
		try {
			CurrentUser user = (CurrentUser) authentication.getPrincipal();
			checkNotNull(user, "Current user not found.");
			peripheralService.savePeripheralDevice(toPeripheralDeviceEntity(form, user.getUsername()),
					form.getDeviceId(), form.getDevicePath(), form.getAgentIds());
		} catch (Exception e) {
			log.error("Exception occurred when trying to save peripheral, assuming invalid parameters", e);
			bindingResult.reject("unexpected", "Beklenmeyen hata oluştu.");
			return "/peripheral/create";
		}
		// everything fine redirect to list
		return ControllerUtils.getRedirectMapping(form, "/peripheral/list");
	}
	
	@PostMapping("/peripheral/{id}/delete")
	public ResponseEntity<?> handleDelete(@PathVariable Long id) {
		RestResponseBody result = new RestResponseBody();
		try {
			peripheralService.deletePeripheral(checkNotNull(id, "ID not found."));
		} catch (Exception e) {
			log.error("Exception occurred when trying to delete package, assuming invalid parameters", e);
			result.setMessage(e.getMessage());
			return ResponseEntity.badRequest().body(result);
		}
		return ResponseEntity.ok(result);
	}

	@GetMapping("/peripheral/list")
	public String getPeripheralListPage() {
		return "peripheral/list";
	}

	@GetMapping("/peripheral/list-paginated")
	public ResponseEntity<?> handlePeripheralList(@RequestParam(value = "search", required = false) String search,
			Pageable pageable) {
		RestResponseBody result = new RestResponseBody();
		try {
			Page<PeripheralDevice> peripherals = peripheralService.getPeripherals(pageable, search);
			result.add("peripherals", checkNotNull(peripherals, "Peripherals not found."));
		} catch (Exception e) {
			log.error("Exception occurred when trying to find peripherals, assuming invalid parameters", e);
			result.setMessage(e.getMessage());
			return ResponseEntity.badRequest().body(result);
		}
		return ResponseEntity.ok(result);
	}

	private PeripheralDevice toPeripheralDeviceEntity(PeripheralDeviceForm form, String username) {
		PeripheralDevice entity = mapper.toPeripheralDeviceEntity(form);
		Date date = new Date();
		entity.setCreatedBy(username);
		entity.setCreatedDate(date);
		entity.setLastModifiedBy(username);
		entity.setLastModifiedDate(date);
		return entity;
	}

}

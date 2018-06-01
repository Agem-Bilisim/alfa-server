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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import tr.com.agem.alfa.form.BiosForm;
import tr.com.agem.alfa.form.CpuForm;
import tr.com.agem.alfa.form.DiskForm;
import tr.com.agem.alfa.form.GpuForm;
import tr.com.agem.alfa.form.MemoryForm;
import tr.com.agem.alfa.form.NetworkInterfaceForm;
import tr.com.agem.alfa.mapper.SysMapper;
import tr.com.agem.alfa.model.Bios;
import tr.com.agem.alfa.model.Cpu;
import tr.com.agem.alfa.model.CurrentUser;
import tr.com.agem.alfa.model.Disk;
import tr.com.agem.alfa.model.Gpu;
import tr.com.agem.alfa.model.Memory;
import tr.com.agem.alfa.model.NetworkInterface;
import tr.com.agem.alfa.service.AgentService;
import tr.com.agem.alfa.service.HardwareService;

/**
 * @author <a href="mailto:emre.akkaya@agem.com.tr">Emre Akkaya</a>
 */
@Controller
public class HardwareController {

	private static final Logger log = LoggerFactory.getLogger(HardwareController.class);

	private final HardwareService hardwareService;
	private final AgentService agentService;
	private final SysMapper mapper;

	@Value("${sys.page-size}")
	private Integer sysPageSize;

	@Autowired
	public HardwareController(HardwareService hardwareService, AgentService agentService, SysMapper mapper) {
		this.hardwareService = hardwareService;
		this.agentService = agentService;
		this.mapper = mapper;
	}

	@GetMapping("/hardware/list")
	public String getListPage() {
		return "hardware/list";
	}
	
	@GetMapping("/hardware/cpu/list-paginated")
	public ResponseEntity<?> handleCpuList(@RequestParam(value = "search", required = false) String search,
			Pageable pageable) {
		RestResponseBody result = new RestResponseBody();
		try {
			Page<Cpu> cpus = hardwareService.getCpus(pageable, search);
			result.add("cpus", checkNotNull(cpus, "CPUs not found."));
		} catch (Exception e) {
			log.error("Exception occurred when trying to find CPUs, assuming invalid parameters", e);
			result.setMessage(e.getMessage());
			return ResponseEntity.badRequest().body(result);
		}
		return ResponseEntity.ok(result);
	}

	@GetMapping("/hardware/gpu/list-paginated")
	public ResponseEntity<?> handleGpuList(@RequestParam(value = "search", required = false) String search,
			Pageable pageable) {
		RestResponseBody result = new RestResponseBody();
		try {
			Page<Gpu> gpus = hardwareService.getGpus(pageable, search);
			result.add("gpus", checkNotNull(gpus, "GPUs not found."));
		} catch (Exception e) {
			log.error("Exception occurred when trying to find GPUs, assuming invalid parameters", e);
			result.setMessage(e.getMessage());
			return ResponseEntity.badRequest().body(result);
		}
		return ResponseEntity.ok(result);
	}

	@GetMapping("/hardware/disk/list-paginated")
	public ResponseEntity<?> handleDiskList(@RequestParam(value = "search", required = false) String search,
			Pageable pageable) {
		RestResponseBody result = new RestResponseBody();
		try {
			Page<Disk> disks = hardwareService.getDisks(pageable, search);
			result.add("disks", checkNotNull(disks, "Disks not found."));
		} catch (Exception e) {
			log.error("Exception occurred when trying to find disks, assuming invalid parameters", e);
			result.setMessage(e.getMessage());
			return ResponseEntity.badRequest().body(result);
		}
		return ResponseEntity.ok(result);
	}

	@GetMapping("/hardware/memory/list-paginated")
	public ResponseEntity<?> handleMemoryList(@RequestParam(value = "search", required = false) String search,
			Pageable pageable) {
		RestResponseBody result = new RestResponseBody();
		try {
			Page<Memory> memories = hardwareService.getMemories(pageable, search);
			result.add("memories", checkNotNull(memories, "Memories not found."));
		} catch (Exception e) {
			log.error("Exception occurred when trying to find memories, assuming invalid parameters", e);
			result.setMessage(e.getMessage());
			return ResponseEntity.badRequest().body(result);
		}
		return ResponseEntity.ok(result);
	}
	
	@GetMapping("/hardware/bios/list-paginated")
	public ResponseEntity<?> handleBiosList(@RequestParam(value = "search", required = false) String search,
			Pageable pageable) {
		RestResponseBody result = new RestResponseBody();
		try {
			Page<Bios> bioss = hardwareService.getBioss(pageable, search);
			result.add("bioss", checkNotNull(bioss, "BIOSs not found."));
		} catch (Exception e) {
			log.error("Exception occurred when trying to find BIOSs, assuming invalid parameters", e);
			result.setMessage(e.getMessage());
			return ResponseEntity.badRequest().body(result);
		}
		return ResponseEntity.ok(result);
	}

	@GetMapping("/hardware/networkInterface/list-paginated")
	public ResponseEntity<?> handleNetworkList(@RequestParam(value = "search", required = false) String search,
			Pageable pageable) {
		RestResponseBody result = new RestResponseBody();
		try {
			Page<NetworkInterface> networkInterfaces = hardwareService.getNetworkInterfaces(pageable, search);
			result.add("networkInterfaces", checkNotNull(networkInterfaces, "Network interfaces not found."));
		} catch (Exception e) {
			log.error("Exception occurred when trying to find network interfaces, assuming invalid parameters", e);
			result.setMessage(e.getMessage());
			return ResponseEntity.badRequest().body(result);
		}
		return ResponseEntity.ok(result);
	}
	
	@GetMapping("/hardware/cpu/create")
	public ModelAndView getCpuCreatePage(@RequestParam(name = "redirect", required = false) String redirect) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			model.put("possibleagents", agentService.getAgents());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		model.put("form", new CpuForm().setRedirect(redirect));
		return new ModelAndView("hardware/cpu/create", model);
	}

	@GetMapping("/hardware/gpu/create")
	public ModelAndView getGpuCreatePage(@RequestParam(name = "redirect", required = false) String redirect) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			model.put("agents", agentService.getAgents());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		model.put("form", new GpuForm().setRedirect(redirect));
		return new ModelAndView("hardware/gpu/create", model);
	}

	@GetMapping("/hardware/disk/create")
	public ModelAndView getDiskCreatePage(@RequestParam(name = "redirect", required = false) String redirect) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			model.put("agents", agentService.getAgents());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		model.put("form", new DiskForm().setRedirect(redirect));
		return new ModelAndView("hardware/disk/create", model);
	}

	@GetMapping("/hardware/memory/create")
	public ModelAndView getMemoryCreatePage(@RequestParam(name = "redirect", required = false) String redirect) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			model.put("agents", agentService.getAgents());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		model.put("form", new MemoryForm().setRedirect(redirect));
		return new ModelAndView("hardware/memory/create", model);
	}

	@GetMapping("/hardware/bios/create")
	public ModelAndView getBiosCreatePage(@RequestParam(name = "redirect", required = false) String redirect) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			model.put("agents", agentService.getAgents());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		model.put("form", new BiosForm().setRedirect(redirect));
		return new ModelAndView("hardware/bios/create", model);
	}
	
	@GetMapping("/hardware/networkInterface/create")
	public ModelAndView getNetworkInterfaceCreatePage(@RequestParam(name = "redirect", required = false) String redirect) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			model.put("agents", agentService.getAgents());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		model.put("form", new NetworkInterfaceForm().setRedirect(redirect));
		return new ModelAndView("hardware/networkInterface/create", model);
	}
	
	@PostMapping("/hardware/cpu/create")
	public String handleCpuCreate(@Valid @ModelAttribute("form") CpuForm form, BindingResult bindingResult,
			Authentication authentication) {
		if (bindingResult.hasErrors()) {
			// failed validation
			return "/hardware/cpu/create";
		}
		try {
			CurrentUser user = (CurrentUser) authentication.getPrincipal();
			checkNotNull(user, "Current user not found.");
			hardwareService.saveCpu(toCpuEntity(form, user.getUsername()));
		} catch (Exception e) {
			log.error("Exception occurred when trying to save CPU, assuming invalid parameters", e);
			bindingResult.reject("unexpected", "Beklenmeyen hata oluştu.");
			return "/hardware/cpu/create";
		}
		// everything fine redirect to list
		return ControllerUtils.getRedirectMapping(form, "/hardware/list");
	}
	
	private Cpu toCpuEntity(CpuForm form, String username) {
		Cpu entity = mapper.toCpuEntity(form);
		Date date = new Date();
		entity.setCreatedBy(username);
		entity.setCreatedDate(date);
		entity.setLastModifiedBy(username);
		entity.setLastModifiedDate(date);

		return entity;
	}
	
	@PostMapping("/hardware/gpu/create")
	public String handleGpuCreate(@Valid @ModelAttribute("form") GpuForm form, BindingResult bindingResult,
			Authentication authentication) {
		if (bindingResult.hasErrors()) {
			// failed validation
			return "/hardware/gpu/create";
		}
		try {
			CurrentUser user = (CurrentUser) authentication.getPrincipal();
			checkNotNull(user, "Current user not found.");
			hardwareService.saveGpu(toGpuEntity(form, user.getUsername()));
		} catch (Exception e) {
			log.error("Exception occurred when trying to save GPU, assuming invalid parameters", e);
			bindingResult.reject("unexpected", "Beklenmeyen hata oluştu.");
			return "/hardware/gpu/create";
		}
		// everything fine redirect to list
		return ControllerUtils.getRedirectMapping(form, "/hardware/list");
	}
	
	private Gpu toGpuEntity(GpuForm form, String username) {
		Gpu entity = mapper.toGpuEntity(form);
		Date date = new Date();
		entity.setCreatedBy(username);
		entity.setCreatedDate(date);
		entity.setLastModifiedBy(username);
		entity.setLastModifiedDate(date);
		
		return entity;
	}

	@PostMapping("/hardware/disk/create")
	public String handleDiskCreate(@Valid @ModelAttribute("form") DiskForm form, BindingResult bindingResult,
			Authentication authentication) {
		if (bindingResult.hasErrors()) {
			// failed validation
			return "/hardware/disk/create";
		}
		try {
			CurrentUser user = (CurrentUser) authentication.getPrincipal();
			checkNotNull(user, "Current user not found.");
			hardwareService.saveDisk(toDiskEntity(form, user.getUsername()));
		} catch (Exception e) {
			log.error("Exception occurred when trying to save disk, assuming invalid parameters", e);
			bindingResult.reject("unexpected", "Beklenmeyen hata oluştu.");
			return "/hardware/disk/create";
		}
		// everything fine redirect to list
		return ControllerUtils.getRedirectMapping(form, "/hardware/list");
	}
	
	private Disk toDiskEntity(DiskForm form, String username) {
		Disk entity = mapper.toDiskEntity(form);
		Date date = new Date();
		entity.setCreatedBy(username);
		entity.setCreatedDate(date);
		entity.setLastModifiedBy(username);
		entity.setLastModifiedDate(date);
		
		return entity;
	}

	@PostMapping("/hardware/memory/create")
	public String handleMemoryCreate(@Valid @ModelAttribute("form") MemoryForm form, BindingResult bindingResult,
			Authentication authentication) {
		if (bindingResult.hasErrors()) {
			// failed validation
			return "/hardware/memory/create";
		}
		try {
			CurrentUser user = (CurrentUser) authentication.getPrincipal();
			checkNotNull(user, "Current user not found.");
			hardwareService.saveMemory(toMemoryEntity(form, user.getUsername()));
		} catch (Exception e) {
			log.error("Exception occurred when trying to save memory, assuming invalid parameters", e);
			bindingResult.reject("unexpected", "Beklenmeyen hata oluştu.");
			return "/hardware/memory/create";
		}
		// everything fine redirect to list
		return ControllerUtils.getRedirectMapping(form, "/hardware/list");
	}
	
	private Memory toMemoryEntity(MemoryForm form, String username) {
		Memory entity = mapper.toMemoryEntity(form);
		Date date = new Date();
		entity.setCreatedBy(username);
		entity.setCreatedDate(date);
		entity.setLastModifiedBy(username);
		entity.setLastModifiedDate(date);
		
		return entity;
	}

	@PostMapping("/hardware/bios/create")
	public String handleBiosCreate(@Valid @ModelAttribute("form") BiosForm form, BindingResult bindingResult,
			Authentication authentication) {
		if (bindingResult.hasErrors()) {
			// failed validation
			return "/hardware/bios/create";
		}
		try {
			CurrentUser user = (CurrentUser) authentication.getPrincipal();
			checkNotNull(user, "Current user not found.");
			hardwareService.saveBios(toBiosEntity(form, user.getUsername()));
		} catch (Exception e) {
			log.error("Exception occurred when trying to save BIOS, assuming invalid parameters", e);
			bindingResult.reject("unexpected", "Beklenmeyen hata oluştu.");
			return "/hardware/bios/create";
		}
		// everything fine redirect to list
		return ControllerUtils.getRedirectMapping(form, "/hardware/list");
	}
	
	private Bios toBiosEntity(BiosForm form, String username) {
		Bios entity = mapper.toBiosEntity(form);
		Date date = new Date();
		entity.setCreatedBy(username);
		entity.setCreatedDate(date);
		entity.setLastModifiedBy(username);
		entity.setLastModifiedDate(date);
		
		return entity;
	}

	@PostMapping("/hardware/networkInterface/create")
	public String handleNetworkInterfaceCreate(@Valid @ModelAttribute("form") NetworkInterfaceForm form, BindingResult bindingResult,
			Authentication authentication) {
		if (bindingResult.hasErrors()) {
			// failed validation
			return "/hardware/networkInterface/create";
		}
		try {
			CurrentUser user = (CurrentUser) authentication.getPrincipal();
			checkNotNull(user, "Current user not found.");
			hardwareService.saveNetworkInterface(toNetworkInterfaceEntity(form, user.getUsername()));
		} catch (Exception e) {
			log.error("Exception occurred when trying to save networkInterface, assuming invalid parameters", e);
			bindingResult.reject("unexpected", "Beklenmeyen hata oluştu.");
			return "/hardware/networkInterface/create";
		}
		// everything fine redirect to list
		return ControllerUtils.getRedirectMapping(form, "/hardware/list");
	}
	
	private NetworkInterface toNetworkInterfaceEntity(NetworkInterfaceForm form, String username) {
		NetworkInterface entity = mapper.toNetworkInterfaceEntity(form);
		Date date = new Date();
		entity.setCreatedBy(username);
		entity.setCreatedDate(date);
		entity.setLastModifiedBy(username);
		entity.setLastModifiedDate(date);
		
		return entity;
	}
}

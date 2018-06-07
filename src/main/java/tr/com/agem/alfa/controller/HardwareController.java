package tr.com.agem.alfa.controller;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import tr.com.agem.alfa.form.BiosForm;
import tr.com.agem.alfa.form.CpuForm;
import tr.com.agem.alfa.form.DiskForm;
import tr.com.agem.alfa.form.GpuForm;
import tr.com.agem.alfa.form.MemoryForm;
import tr.com.agem.alfa.form.NetworkInterfaceForm;
import tr.com.agem.alfa.form.PeripheralDeviceForm;
import tr.com.agem.alfa.mapper.SysMapper;
import tr.com.agem.alfa.model.Agent;
import tr.com.agem.alfa.model.AgentCpu;
import tr.com.agem.alfa.model.Bios;
import tr.com.agem.alfa.model.Cpu;
import tr.com.agem.alfa.model.CurrentUser;
import tr.com.agem.alfa.model.Disk;
import tr.com.agem.alfa.model.Gpu;
import tr.com.agem.alfa.model.Memory;
import tr.com.agem.alfa.model.NetworkInterface;
import tr.com.agem.alfa.model.PeripheralDevice;
import tr.com.agem.alfa.service.AgentService;
import tr.com.agem.alfa.service.HardwareService;

/**
 * @author <a href="mailto:emre.akkaya@agem.com.tr">Emre Akkaya</a>
 */
@Controller
@Transactional
public class HardwareController {

	private static final Logger log = LoggerFactory.getLogger(HardwareController.class);

	private final HardwareService hardwareService;
	private final AgentService agentService;
	private final SysMapper mapper;

	@PersistenceContext
	private EntityManager em;
	
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
			model.put("agents", agentService.getAgents());
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
			Cpu savedCpu = hardwareService.saveCpu(toCpuEntity(form, user.getUsername()));
			// TODO
//			Agent agent = form.getAgents().get(0);
//			Query query = em.createNativeQuery("INSERT INTO c_agent_cpu_agent (agent_id, cpu_id) VALUES (:agentId, :cpuId)");
//			query.setParameter("agentId", agent.getId());
//			query.setParameter("cpuId", savedCpu.getId());
//			query.executeUpdate();
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
			Gpu gpuEntity = toGpuEntity(form, user.getUsername());
			if (form.getAgentIds() != null) {
				for (Long agentId : form.getAgentIds()) {
					gpuEntity.addAgent(agentService.getAgent(agentId));
				}
			}
			hardwareService.saveGpu(gpuEntity);
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
			Disk diskEntity = toDiskEntity(form, user.getUsername());
			if (form.getAgentIds() != null) {
				for (Long agentId : form.getAgentIds()) {
					diskEntity.addAgent(agentService.getAgent(agentId));
				}
			}
			hardwareService.saveDisk(diskEntity);
			
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
			Memory memoryEntity = toMemoryEntity(form, user.getUsername());
			if (form.getAgentIds() != null) {
				for (Long agentId : form.getAgentIds()) {
					memoryEntity.addAgent(agentService.getAgent(agentId));
				}
			}
			hardwareService.saveMemory(memoryEntity);
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
			Bios biosEntity = toBiosEntity(form, user.getUsername());
			if (form.getAgentIds() != null) {
				for (Long agentId : form.getAgentIds()) {
					biosEntity.addAgent(agentService.getAgent(agentId));
				}
			}
			hardwareService.saveBios(biosEntity);
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
			NetworkInterface networkInterfaceEntity = toNetworkInterfaceEntity(form, user.getUsername());
			if (form.getAgentIds() != null) {
				for (Long agentId : form.getAgentIds()) {
					networkInterfaceEntity.addAgent(agentService.getAgent(agentId));
				}
			}
			hardwareService.saveNetworkInterface(networkInterfaceEntity);
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
	
	@GetMapping("/peripheral/list")
	public String getPeripheralListPage() {
		return "peripheral/list";
	}
	
	@GetMapping("/peripheral/create")
	public ModelAndView getPeripheralCreatePage(@RequestParam(name = "redirect", required = false) String redirect) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			model.put("agents", agentService.getAgents());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		model.put("form", new PeripheralDeviceForm().setRedirect(redirect));
		return new ModelAndView("peripheral/create", model);
	}
	
	@PostMapping("/peripheral/create")
	public String handlePeripheralCreate(@Valid @ModelAttribute("form") PeripheralDeviceForm form, BindingResult bindingResult,
			Authentication authentication) {
		if (bindingResult.hasErrors()) {
			// failed validation
			return "/peripheral/create";
		}
		try {
			CurrentUser user = (CurrentUser) authentication.getPrincipal();
			checkNotNull(user, "Current user not found.");
			PeripheralDevice savedPeripheralDevice = hardwareService.savePeripheralDevice(toPeripheralDeviceEntity(form, user.getUsername()));
			Agent agent = form.getAgents().get(0);
			Query query = em.createNativeQuery("INSERT INTO c_agent_peripheral_agent (agent_id, peripheral_id, device_id, device_path) VALUES (:agentId, :peripheralId, :deviceId, :devicePath)");
			query.setParameter("agentId", agent.getId());
			query.setParameter("peripheralId", savedPeripheralDevice.getId());
			query.setParameter("deviceId", form.getDeviceId());
			query.setParameter("devicePath", form.getDevicePath());
			query.executeUpdate();
		} catch (Exception e) {
			log.error("Exception occurred when trying to save peripheral, assuming invalid parameters", e);
			bindingResult.reject("unexpected", "Beklenmeyen hata oluştu.");
			return "/peripheral/create";
		}
		// everything fine redirect to list
		return ControllerUtils.getRedirectMapping(form, "/peripheral/list");
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
	
	@GetMapping("/peripheral/list-paginated")
	public ResponseEntity<?> handlePeripheralList(@RequestParam(value = "search", required = false) String search,
			Pageable pageable) {
		RestResponseBody result = new RestResponseBody();
		try {
			Page<PeripheralDevice> peripherals = hardwareService.getPeripherals(pageable, search);
			result.add("peripherals", checkNotNull(peripherals, "Peripherals not found."));
		} catch (Exception e) {
			log.error("Exception occurred when trying to find peripherals, assuming invalid parameters", e);
			result.setMessage(e.getMessage());
			return ResponseEntity.badRequest().body(result);
		}
		return ResponseEntity.ok(result);
	}
	
	@GetMapping("/hardware/cpu/{id}")
	public ModelAndView getCpu(@PathVariable Long id) {
		Cpu _cpu = hardwareService.getCpu(id);
		checkNotNull(_cpu, String.format("CPU:%d not found.", id));
		
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			model.put("agents", agentService.getAgents());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		CpuForm cpuForm = mapper.toCpuForm(_cpu);
		List<Long> agentIds = new ArrayList<Long>();
		for (AgentCpu agentCpu : _cpu.getAgentCpus()) {
			agentIds.add(agentCpu.getAgent().getId());
		}
		cpuForm.setAgentIds(agentIds.toArray(new Long[] {}));
		model.put("form", cpuForm);
		
		return new ModelAndView("/hardware/cpu/edit", model);
	}
	
	@PostMapping("/hardware/cpu/{id}")
	public String handleCpuUpdate(@PathVariable Long id, @Valid @ModelAttribute("form") CpuForm form,
			BindingResult bindingResult, Authentication authentication) {
		if (bindingResult.hasErrors()) {
			// failed validation
			return "/hardware/cpu/edit";
		}
		try {
			CurrentUser user = (CurrentUser) authentication.getPrincipal();
			checkNotNull(user, "Current user not found.");
			Query query = em.createNativeQuery("DELETE FROM c_agent_cpu_agent WHERE cpu_id = :cpuId");
			query.setParameter("cpuId", id);
			query.executeUpdate();
			Cpu cpuEntity = hardwareService.getCpu(id);
			if (form.getAgentIds() != null) {
				for (Long agentId : form.getAgentIds()) {
					// TODO
//					cpuEntity.addAgent(agentService.getAgent(agentId));
				}
			}
			hardwareService.saveCpu(cpuEntity);
		} catch (Exception e) {
			log.warn("Exception occurred when trying to save the CPU", e);
			bindingResult.reject("save.error",
					"Hata oluştu.");
			return "/hardware/cpu/edit";
		}
		// everything fine redirect to list
		return "redirect:/hardware/list";
	}

	@GetMapping("/hardware/gpu/{id}")
	public ModelAndView getGpu(@PathVariable Long id) {
		Gpu _gpu = hardwareService.getGpu(id);
		checkNotNull(_gpu, String.format("GPU:%d not found.", id));
		
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			model.put("agents", agentService.getAgents());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		GpuForm gpuForm = mapper.toGpuForm(_gpu);
		List<Long> agentIds = new ArrayList<Long>();
		for (Agent agent : _gpu.getAgents()) {
			agentIds.add(agent.getId());
		}
		gpuForm.setAgentIds(agentIds.toArray(new Long[] {}));
		model.put("form", gpuForm);
		
		return new ModelAndView("/hardware/gpu/edit", model);
	}
	
	@PostMapping("/hardware/gpu/{id}")
	public String handleGpuUpdate(@PathVariable Long id, @Valid @ModelAttribute("form") GpuForm form,
			BindingResult bindingResult, Authentication authentication) {
		if (bindingResult.hasErrors()) {
			// failed validation
			return "/hardware/gpu/edit";
		}
		try {
			CurrentUser user = (CurrentUser) authentication.getPrincipal();
			checkNotNull(user, "Current user not found.");
			Query query = em.createNativeQuery("DELETE FROM c_agent_gpu_agent WHERE gpu_id = :gpuId");
			query.setParameter("gpuId", id);
			query.executeUpdate();
			Gpu gpuEntity = hardwareService.getGpu(id);
			if (form.getAgentIds() != null) {
				for (Long agentId : form.getAgentIds()) {
					gpuEntity.addAgent(agentService.getAgent(agentId));
				}
			}
			gpuEntity.setSubsystem(form.getSubsystem());
			gpuEntity.setKernel(form.getKernel());
			gpuEntity.setMemory(form.getMemory());
			hardwareService.saveGpu(gpuEntity);
		} catch (Exception e) {
			log.warn("Exception occurred when trying to save the GPU", e);
			bindingResult.reject("save.error",
					"Hata oluştu.");
			return "/hardware/gpu/edit";
		}
		// everything fine redirect to list
		return "redirect:/hardware/list";
	}

	@GetMapping("/hardware/disk/{id}")
	public ModelAndView getDisk(@PathVariable Long id) {
		Disk _disk = hardwareService.getDisk(id);
		checkNotNull(_disk, String.format("Disk:%d not found.", id));
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			model.put("agents", agentService.getAgents());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		DiskForm diskForm = mapper.toDiskForm(_disk);
		List<Long> agentIds = new ArrayList<Long>();
		for (Agent agent : _disk.getAgents()) {
			agentIds.add(agent.getId());
		}
		diskForm.setAgentIds(agentIds.toArray(new Long[] {}));
		model.put("form", diskForm);
		
		return new ModelAndView("/hardware/disk/edit", model);
	}
	
	@PostMapping("/hardware/disk/{id}")
	public String handleDiskUpdate(@PathVariable Long id, @Valid @ModelAttribute("form") DiskForm form,
			BindingResult bindingResult, Authentication authentication) {
		if (bindingResult.hasErrors()) {
			// failed validation
			return "/hardware/disk/edit";
		}
		try {
			CurrentUser user = (CurrentUser) authentication.getPrincipal();
			checkNotNull(user, "Current user not found.");
			Query query = em.createNativeQuery("DELETE FROM c_agent_disk_agent WHERE disk_id = :diskId");
			query.setParameter("diskId", id);
			query.executeUpdate();
			Disk diskEntity = hardwareService.getDisk(id);
			if (form.getAgentIds() != null) {
				for (Long agentId : form.getAgentIds()) {
					diskEntity.addAgent(agentService.getAgent(agentId));
				}
			}
			diskEntity.setVendor(form.getVendor());
			diskEntity.setDescription(form.getDescription());
			diskEntity.setVersion(form.getVersion());
			diskEntity.setProduct(form.getProduct());
			diskEntity.setSerial(form.getSerial());
			hardwareService.saveDisk(diskEntity);
		} catch (Exception e) {
			log.warn("Exception occurred when trying to save the Disk", e);
			bindingResult.reject("save.error",
					"Hata oluştu.");
			return "/hardware/disk/edit";
		}
		// everything fine redirect to list
		return "redirect:/hardware/list";
	}

	@GetMapping("/hardware/memory/{id}")
	public ModelAndView getMemory(@PathVariable Long id) {
		Memory _memory = hardwareService.getMemory(id);
		checkNotNull(_memory, String.format("Memory:%d not found.", id));
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			model.put("agents", agentService.getAgents());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		MemoryForm memoryForm = mapper.toMemoryForm(_memory);
		List<Long> agentIds = new ArrayList<Long>();
		for (Agent agent : _memory.getAgents()) {
			agentIds.add(agent.getId());
		}
		memoryForm.setAgentIds(agentIds.toArray(new Long[] {}));
		model.put("form", memoryForm);
		
		return new ModelAndView("/hardware/memory/edit", model);
	}
	
	@PostMapping("/hardware/memory/{id}")
	public String handleMemoryUpdate(@PathVariable Long id, @Valid @ModelAttribute("form") MemoryForm form,
			BindingResult bindingResult, Authentication authentication) {
		if (bindingResult.hasErrors()) {
			// failed validation
			return "/hardware/memory/edit";
		}
		try {
			CurrentUser user = (CurrentUser) authentication.getPrincipal();
			checkNotNull(user, "Current user not found.");
			Query query = em.createNativeQuery("DELETE FROM c_agent_memory_agent WHERE memory_id = :memoryId");
			query.setParameter("memoryId", id);
			query.executeUpdate();
			Memory memoryEntity = hardwareService.getMemory(id);
			if (form.getAgentIds() != null) {
				for (Long agentId : form.getAgentIds()) {
					memoryEntity.addAgent(agentService.getAgent(agentId));
				}
			}
			memoryEntity.setSpeed(form.getSpeed());
			memoryEntity.setSize(form.getSize());
			memoryEntity.setType(form.getType());
			memoryEntity.setManufacturer(form.getManufacturer());
			hardwareService.saveMemory(memoryEntity);
		} catch (Exception e) {
			log.warn("Exception occurred when trying to save the Memory", e);
			bindingResult.reject("save.error",
					"Hata oluştu.");
			return "/hardware/memory/edit";
		}
		// everything fine redirect to list
		return "redirect:/hardware/list";
	}

	@GetMapping("/hardware/bios/{id}")
	public ModelAndView getBios(@PathVariable Long id) {
		Bios _bios = hardwareService.getBios(id);
		checkNotNull(_bios, String.format("Bios:%d not found.", id));
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			model.put("agents", agentService.getAgents());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		BiosForm biosForm = mapper.toBiosForm(_bios);
		List<Long> agentIds = new ArrayList<Long>();
		for (Agent agent : _bios.getAgents()) {
			agentIds.add(agent.getId());
		}
		biosForm.setAgentIds(agentIds.toArray(new Long[] {}));
		model.put("form", biosForm);
		
		return new ModelAndView("/hardware/bios/edit", model);
	}
	
	@PostMapping("/hardware/bios/{id}")
	public String handleBiosUpdate(@PathVariable Long id, @Valid @ModelAttribute("form") BiosForm form,
			BindingResult bindingResult, Authentication authentication) {
		if (bindingResult.hasErrors()) {
			// failed validation
			return "/hardware/bios/edit";
		}
		try {
			CurrentUser user = (CurrentUser) authentication.getPrincipal();
			checkNotNull(user, "Current user not found.");
			Bios biosEntity = hardwareService.getBios(id);
			biosEntity.getAgents().clear();
			if (form.getAgentIds() != null) {
				for (Long agentId : form.getAgentIds()) {
					biosEntity.addAgent(agentService.getAgent(agentId));
				}
			}
			biosEntity.setVendor(form.getVendor());
			biosEntity.setVersion(form.getVersion());
			biosEntity.setReleaseDate(form.getReleaseDate());
			hardwareService.saveBios(biosEntity);
		} catch (Exception e) {
			log.warn("Exception occurred when trying to save the Bios", e);
			bindingResult.reject("save.error",
					"Hata oluştu.");
			return "/hardware/bios/edit";
		}
		// everything fine redirect to list
		return "redirect:/hardware/list";
	}

	@GetMapping("/hardware/networkInterface/{id}")
	public ModelAndView getNetworkInterface(@PathVariable Long id) {
		NetworkInterface _networkInterface = hardwareService.getNetworkInterface(id);
		checkNotNull(_networkInterface, String.format("NetworkInterface:%d not found.", id));
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			model.put("agents", agentService.getAgents());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		NetworkInterfaceForm networkInterfaceForm = mapper.toNetworkInterfaceForm(_networkInterface);
		List<Long> agentIds = new ArrayList<Long>();
		for (Agent agent : _networkInterface.getAgents()) {
			agentIds.add(agent.getId());
		}
		networkInterfaceForm.setAgentIds(agentIds.toArray(new Long[] {}));
		model.put("form", networkInterfaceForm);
		
		return new ModelAndView("/hardware/networkInterface/edit", model);
	}
	
	@PostMapping("/hardware/networkInterface/{id}")
	public String handleNetworkInterfaceUpdate(@PathVariable Long id, @Valid @ModelAttribute("form") NetworkInterfaceForm form,
			BindingResult bindingResult, Authentication authentication) {
		if (bindingResult.hasErrors()) {
			// failed validation
			return "/hardware/networkInterface/edit";
		}
		try {
			CurrentUser user = (CurrentUser) authentication.getPrincipal();
			checkNotNull(user, "Current user not found.");
			Query query = em.createNativeQuery("DELETE FROM c_agent_inet_agent WHERE network_interface_id = :networkInterfaceId");
			query.setParameter("networkInterfaceId", id);
			query.executeUpdate();
			NetworkInterface networkInterfaceEntity = hardwareService.getNetworkInterface(id);
			if (form.getAgentIds() != null) {
				for (Long agentId : form.getAgentIds()) {
					networkInterfaceEntity.addAgent(agentService.getAgent(agentId));
				}
			}
			networkInterfaceEntity.setVendor(form.getVendor());
			networkInterfaceEntity.setVersion(form.getVersion());
			networkInterfaceEntity.setProduct(form.getProduct());
			networkInterfaceEntity.setCapabilities(form.getCapabilities());
			hardwareService.saveNetworkInterface(networkInterfaceEntity);
		} catch (Exception e) {
			log.warn("Exception occurred when trying to save the NetworkInterface", e);
			bindingResult.reject("save.error",
					"Hata oluştu.");
			return "/hardware/networkInterface/edit";
		}
		// everything fine redirect to list
		return "redirect:/hardware/list";
	}
	
//	@PostMapping("/hardware/cpu/{id}/delete")
//	public ResponseEntity<?> handleCpuDelete(@PathVariable Long id) {
//		RestResponseBody result = new RestResponseBody();
//		try {
//			hardwareService.deleteCpu(checkNotNull(id, "ID not found."));
//		} catch (Exception e) {
//			log.error("Exception occurred when trying to delete CPU, assuming invalid parameters", e);
//			result.setMessage(e.getMessage());
//			return ResponseEntity.badRequest().body(result);
//		}
//		return ResponseEntity.ok(result);
//	}
//
//	@PostMapping("/hardware/gpu/{id}/delete")
//	public ResponseEntity<?> handleGpuDelete(@PathVariable Long id) {
//		RestResponseBody result = new RestResponseBody();
//		try {
//			hardwareService.deleteGpu(checkNotNull(id, "ID not found."));
//		} catch (Exception e) {
//			log.error("Exception occurred when trying to delete GPU, assuming invalid parameters", e);
//			result.setMessage(e.getMessage());
//			return ResponseEntity.badRequest().body(result);
//		}
//		return ResponseEntity.ok(result);
//	}
}

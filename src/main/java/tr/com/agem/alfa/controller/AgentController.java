package tr.com.agem.alfa.controller;

import static com.google.common.base.Preconditions.checkNotNull;
import static tr.com.agem.alfa.util.CommonUtils.isNullOrEmpty;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import tr.com.agem.alfa.agent.sysinfo.Device;
import tr.com.agem.alfa.agent.sysinfo.GpuDevice;
import tr.com.agem.alfa.agent.sysinfo.InstalledPackage;
import tr.com.agem.alfa.agent.sysinfo.MemoryDevice;
import tr.com.agem.alfa.form.AgentForm;
import tr.com.agem.alfa.form.TagForm;
import tr.com.agem.alfa.mapper.SysMapper;
import tr.com.agem.alfa.messaging.message.SurveyResultMessage;
import tr.com.agem.alfa.messaging.message.SysInfoResultMessage;
import tr.com.agem.alfa.messaging.service.MessagingService;
import tr.com.agem.alfa.model.Agent;
import tr.com.agem.alfa.model.AgentCpu;
import tr.com.agem.alfa.model.AgentPeripheralDevice;
import tr.com.agem.alfa.model.AgentRunningProcess;
import tr.com.agem.alfa.model.AgentUser;
import tr.com.agem.alfa.model.Cpu;
import tr.com.agem.alfa.model.Disk;
import tr.com.agem.alfa.model.Gpu;
import tr.com.agem.alfa.model.Memory;
import tr.com.agem.alfa.model.NetworkInterface;
import tr.com.agem.alfa.model.PeripheralDevice;
import tr.com.agem.alfa.model.Platform;
import tr.com.agem.alfa.model.RunningProcess;
import tr.com.agem.alfa.model.Survey;
import tr.com.agem.alfa.model.SurveyResult;
import tr.com.agem.alfa.model.Tag;
import tr.com.agem.alfa.model.enums.AgentType;
import tr.com.agem.alfa.service.AgentService;
import tr.com.agem.alfa.service.HardwareService;
import tr.com.agem.alfa.service.PeripheralService;
import tr.com.agem.alfa.service.SoftwareService;
import tr.com.agem.alfa.service.SurveyService;
import tr.com.agem.alfa.util.CommonUtils;

/**
 * @author <a href="mailto:emre.akkaya@agem.com.tr">Emre Akkaya</a>
 */
@Controller
@Transactional(rollbackFor = Exception.class)
public class AgentController {

	private static final Logger log = LoggerFactory.getLogger(AgentController.class);

	private final AgentService agentService;
	private final SurveyService surveyService;
	private final MessagingService messagingService;
	private final MessageSource messageSource;
	private final HardwareService hardwareService;
	private final SoftwareService softwareService;
	private final PeripheralService peripheralService;
	private final SysMapper sysMapper;
	
	@PersistenceContext
	private EntityManager em;

	@Value("${sys.page-size}")
	private Integer sysPageSize;

	@Value("${sys.locale}")
	private String locale;

	@Autowired
	public AgentController(AgentService agentService, SurveyService surveyService, MessagingService messagingService,
			MessageSource messageSource, HardwareService hardwareService, SoftwareService softwareService, PeripheralService peripheralService, SysMapper sysMapper) {
		this.agentService = agentService;
		this.surveyService = surveyService;
		this.messagingService = messagingService;
		this.messageSource = messageSource;
		this.hardwareService = hardwareService;
		this.softwareService = softwareService;
		this.peripheralService = peripheralService;
		this.sysMapper = sysMapper;
	}

	@GetMapping("/agent/{agentId}/detail")
	public @ResponseBody ResponseEntity<?> getAgentDetailsPage(@PathVariable Long agentId) {
		log.info("Getting details for agent with agent id:{}", agentId);
		RestResponseBody result = new RestResponseBody();
		try {
			Agent agent = agentService.getAgentDetail(agentId, true);
			result.add("agent", checkNotNull(agent, "Agent not found."));
			result.add("agent-type",
					messageSource.getMessage(agent.getTypeLabel(), null, Locale.forLanguageTag(locale)));
		} catch (Exception e) {
			log.error("Exception occurred when trying to find agent", e);
			result.setMessage(e.getMessage());
			return ResponseEntity.badRequest().body(result);
		}
		return ResponseEntity.ok(result);
	}
	
	@GetMapping("/agent/{agentId}/online")
	public @ResponseBody ResponseEntity<?> isAgentOnline(@PathVariable Long agentId) {
		log.info("Checking agent online status with agent id:{}", agentId);
		RestResponseBody result = new RestResponseBody();
		try {
			Agent agent = agentService.getAgentDetail(agentId, false);
			result.add("online-status", messagingService.isOnline(agent));
		} catch (Exception e) {
			log.error("Exception occurred when trying to find agent", e);
			result.setMessage(e.getMessage());
			return ResponseEntity.badRequest().body(result);
		}
		return ResponseEntity.ok(result);
	}

	@PostMapping("/agent/sysinfo-result")
	public @ResponseBody ResponseEntity<?> handleSysInfoResult(@Valid @RequestBody SysInfoResultMessage message,
			BindingResult bindingResult) {
		RestResponseBody result = new RestResponseBody();
		if (bindingResult.hasErrors()) {
			String error = ControllerUtils.toErrorMessage(bindingResult);
			log.error(error);
			result.setMessage(error);
			return ResponseEntity.badRequest().body(result);
		}
		boolean oldWindows = false;
		try {
			Agent agent = agentService.getAgentByMessagingIdOrMacAddresses(message.getFrom(), excludeInvalid(message.getNetwork().getMacAddresses()));

			if (agent != null && AgentType.WINDOWS_BASED.equals(agent.getType())) {
				oldWindows= true;
			}
			
			agent = toAgentEntity(message, agent);
			
			if (AgentType.DEBIAN_BASED.equals(agent.getType()) && oldWindows) {
				agent.setLastInstallationDate(new Date());
			}
			
			agentService.saveOrUpdate(agent);
			log.info("Agent and its system info created/updated successfully.");
		} catch (Exception e) {
			String error = "Exception occurred when trying to handle system info.";
			log.error(error, e);
			result.setMessage(error);
			return ResponseEntity.badRequest().body(result);
		}
		return ResponseEntity.ok(result);
	}
	
	@PostMapping("/agent/tag/save")
	public @ResponseBody ResponseEntity<?> handleTagSave(@Valid @RequestBody AgentForm form,
			BindingResult bindingResult) {
		RestResponseBody result = new RestResponseBody();
		if (bindingResult.hasErrors()) {
			String error = ControllerUtils.toErrorMessage(bindingResult);
			log.error(error);
			result.setMessage(error);
			return ResponseEntity.badRequest().body(result);
		}
		try {
			Agent agent = agentService.getAgent(form.getId());
			List<Tag> tags = null;
			if (form.getTags() != null) {
				tags = new ArrayList<Tag>();
				for (TagForm tagForm : form.getTags()) {
					tags.add(sysMapper.toTagEntity(tagForm));
				}
			}
			agentService.saveTags(agent, tags);
			log.info("Tags created/updated successfully.");
		} catch (Exception e) {
			String error = "Exception occurred when trying to handle system info.";
			log.error(error, e);
			result.setMessage(error);
			return ResponseEntity.badRequest().body(result);
		}
		return ResponseEntity.ok(result);
	}

	@PostMapping("/agent/survey-result")
	public @ResponseBody ResponseEntity<?> handleSurveyResult(@Valid @RequestBody SurveyResultMessage message,
			BindingResult bindingResult) {
		RestResponseBody result = new RestResponseBody();
		if (bindingResult.hasErrors()) {
			String error = ControllerUtils.toErrorMessage(bindingResult);
			log.error(error);
			result.setMessage(error);
			return ResponseEntity.badRequest().body(result);
		}
		try {
			Agent agent = agentService.getAgentByMessagingId(message.getFrom());
			Survey survey = surveyService.getSurvey(message.getSurveyId());
			surveyService.saveResult(toSurveyResultEntity(message, checkNotNull(agent, "Agent not found."),
					checkNotNull(survey, "Sruvey not found.")));
			log.info("Survey result created successfully.");
		} catch (Exception e) {
			String error = "Exception occurred when trying to handle system info.";
			log.error(error, e);
			result.setMessage(error);
			return ResponseEntity.badRequest().body(result);
		}
		return ResponseEntity.ok(result);
	}

	@GetMapping("/agent/list")
	public String getListPage(Model model) {
		model.addAttribute("tags", agentService.getTags());
		return "agent/list";
	}

	@GetMapping("/agent/list-paginated")
	public ResponseEntity<?> handleAgentList(@RequestParam(value = "search", required = false) String search,
			Pageable pageable) {
		RestResponseBody result = new RestResponseBody();
		try {
			Page<Agent> agents = agentService.getAgents(pageable, search);
			result.add("agents", checkNotNull(agents, "Agents not found."));
		} catch (Exception e) {
			log.error("Exception occurred when trying to find agents, assuming invalid parameters", e);
			result.setMessage(e.getMessage());
			return ResponseEntity.badRequest().body(result);
		}
		return ResponseEntity.ok(result);
	}

	private Agent toAgentEntity(SysInfoResultMessage message, Agent origAgent) throws JsonProcessingException {
		Agent agent = origAgent != null ? origAgent : new Agent();
		//
		// Agent
		//
		agent.setType(message.getPlatform().getSystem() != null
				&& message.getPlatform().getSystem().toLowerCase(Locale.ENGLISH).contains("win")
						? AgentType.WINDOWS_BASED
						: AgentType.DEBIAN_BASED);
		agent.setMessagingId(message.getFrom());
		agent.setDeleted(false);
		agent.setInstallPath(message.getAgentInstallPath());
		agent.setHostName(message.getPlatform().getNode());
		agent.setIpAddresses(CommonUtils.join(",", excludeLocalhost(message.getNetwork().getIpAddresses())
				.toArray(new String[message.getNetwork().getIpAddresses().size()])));
		agent.setMacAddresses(CommonUtils.join(",", excludeInvalid(message.getNetwork().getMacAddresses())
				.toArray(new String[message.getNetwork().getMacAddresses().size()])));
		agent.setSysinfo(new ObjectMapper().writeValueAsBytes(message));
		//
		// Disk
		//
		if (message.getDisk() != null) {
			for (Device d : message.getDisk().getDevices()) {
				if (isNullOrEmpty(d.getVersion(), d.getProduct())) {
					log.warn("Disk version or product cannot be null! Skipping...");
					continue;
				}
				Disk disk = new Disk();
				disk.setVendor(d.getVendor());
				disk.setDescription(d.getDescription());
				disk.setVersion(d.getVersion());
				disk.setProduct(d.getProduct());
				disk.setSerial(d.getSerial());
				Disk tmp = this.hardwareService.getDisk(disk.getProduct(), disk.getVersion());
				agent.addDisk(tmp != null ? tmp : disk);
			}
		}
		//
		// Network interfaces
		//
		if (message.getNetwork() != null && message.getNetwork().getDevices() != null) {
			for (Device d : message.getNetwork().getDevices()) {
				if (isNullOrEmpty(d.getVersion(), d.getProduct())) {
					log.warn("Network interface version or product cannot be null! Skipping...");
					continue;
				}
				NetworkInterface inet = new NetworkInterface();
				inet.setVendor(d.getVendor());
				inet.setVersion(d.getVersion());
				inet.setProduct(d.getProduct());
				inet.setCapabilities(toCapabilityString(d.getCapabilities()));
				agent.addNetworkInterface(inet);
			}
		}
		//
		// Installed packages
		//
		if (message.getInstalledPackages() != null) {
			for (InstalledPackage _package : message.getInstalledPackages()) {
				if (isNullOrEmpty(_package.getName(), _package.getVersion())) {
					log.warn("Package name or version cannot be null! Skipping...");
					continue;
				}
				tr.com.agem.alfa.model.InstalledPackage mPackage = new tr.com.agem.alfa.model.InstalledPackage();
				mPackage.setName(_package.getName());
				mPackage.setVersion(_package.getVersion());
				tr.com.agem.alfa.model.InstalledPackage tmp = this.softwareService.getPackage(mPackage.getName(), mPackage.getVersion());
				agent.addInstalledPackage(tmp != null ? tmp : mPackage);
			}
		}
		//
		// Memories
		//
		if (message.getMemory() != null) {
			for (MemoryDevice d : message.getMemory().getDevices()) {
				if (isNullOrEmpty(d.getSize(), d.getSpeed(), d.getType(), d.getManufacturer())) {
					log.warn("Memory speed, size, type, manufacturer cannot be null! Skipping...");
					continue;
				}
				Memory mem = new Memory();
				mem.setSpeed(d.getSpeed());
				mem.setSize(d.getSize());
				mem.setType(d.getType());
				mem.setManufacturer(d.getManufacturer());
				agent.addMemory(mem);
			}
		}
		//
		// GPU
		//
		if (message.getGpu() != null) {
			for (GpuDevice d : message.getGpu().getDevices()) {
				if (isNullOrEmpty(d.getSubsystem(), d.getKernel(), d.getMemory())) {
					log.warn("GPU kernel, subsystem, memory cannot be null! Skipping...");
					continue;
				}
				Gpu gpu = new Gpu();
				gpu.setSubsystem(d.getSubsystem());
				gpu.setKernel(d.getKernel());
				gpu.setMemory(d.getMemory());
				gpu.setDriverDate(d.getDriverDate());
				gpu.setDriverVersion(d.getDriverVersion());
				agent.addGpu(gpu);
			}
		}
		//
		// BIOS
		//
		if (message.getBios() != null) {
			if (isNullOrEmpty(message.getBios().getVendor(), message.getBios().getVersion())) {
				log.warn("BIOS vendor, version cannot be null! Skipping...");
			} else {
				tr.com.agem.alfa.model.Bios bios = agent.getBios() != null ? agent.getBios()
						: new tr.com.agem.alfa.model.Bios();
				bios.setVendor(message.getBios().getVendor());
				bios.setVersion(message.getBios().getVersion());
				bios.setReleaseDate(message.getBios().getReleaseDate());
				tr.com.agem.alfa.model.Bios _tmp = this.hardwareService.getBios(bios.getVersion(), bios.getVendor());
				agent.setBios(_tmp != null ? _tmp : bios);
			}
		}
		//
		// Platform
		//
		Platform pl = agent.getPlatform() != null ? agent.getPlatform() : new Platform();
		pl.setRelease(message.getPlatform().getRelease());
		pl.setVersion(message.getPlatform().getVersion());
		pl.setSystem(message.getPlatform().getSystem());
		pl.setMachine(message.getPlatform().getMachine());
		Platform __tmp = this.hardwareService.getPlatform(pl.getSystem(), pl.getRelease());
		agent.setPlatform(__tmp != null ? __tmp : pl);
		//
		// Users
		//
		if (message.getUsers() != null) {
			for (String u : message.getUsers()) {
				AgentUser user = new AgentUser();
				user.setName(u);
				AgentUser tmp = this.agentService.getAgentUser(user.getName());
				agent.addUser(tmp != null ? tmp : user);
			}
		}
		//
		// Processes
		//
		if (message.getProcesses() != null) {
			if (agent.getAgentRunningProcesses() != null) {
				Iterator<AgentRunningProcess> it = agent.getAgentRunningProcesses().iterator();
				while (it.hasNext()) {
					AgentRunningProcess _p = it.next();
					if (_p.getId() != null) {
						this.em.remove(_p);;
//						this.em.createNativeQuery(
//								"DELETE FROM c_agent_process_agent WHERE AGENT_PROCESS_AGENT_ID = :id")
//								.setParameter("id", _p.getId()).executeUpdate();
						this.em.flush();
						it.remove();
					} else {
						this.em.merge(_p);
					}
				}
			}
			for (tr.com.agem.alfa.agent.sysinfo.Process p : message.getProcesses()) {
				if (isNullOrEmpty(p.getName(), p.getUsername(), (p.getPid() != null ? p.getPid().toString() : null))) {
					log.warn("Process name, username, pid cannot be null. Skipping...");
					continue;
				}
				RunningProcess process = new RunningProcess();
				process.setName(p.getName());
				RunningProcess tmp = this.softwareService.getProcess(process.getName());
				process = tmp != null ? tmp : process;

				AgentRunningProcess cross = new AgentRunningProcess();
				cross.setCpuPercent(p.getCpuPercent() != null ? p.getCpuPercent().toString() : null);
				cross.setCpuTimes(p.getCpuTimes() != null ? StringUtils.join(",", p.getCpuTimes()) : null);
				cross.setMemoryInfo(p.getMemoryInfo() != null ? StringUtils.join(",", p.getMemoryInfo()) : null);
				cross.setPid(p.getPid() != null ? p.getPid().toString() : null);
				cross.setUsername(p.getUsername() != null ? p.getUsername() : "SYSTEM");
				cross.setAgent(agent);
				cross.setRunningProcess(process);

				agent.getAgentRunningProcesses().add(cross);
			}
		}
		//
		// CPU
		//
		if (message.getCpu() != null) {
			// Remove CPUs
			if (agent.getAgentCpus() != null) {
				Iterator<AgentCpu> it = agent.getAgentCpus().iterator();
				while (it.hasNext()) {
					AgentCpu _c = it.next();
					if (_c.getId() != null) {
						this.em.remove(_c);
						this.em.flush();
						it.remove();
					} else {
						this.em.persist(_c);
					}
				}
			}
			if (isNullOrEmpty(message.getCpu().getHzAdvertised(), message.getCpu().getProcessor(), message.getCpu().getPyhsicalCoreCount(), message.getCpu().getLogicalCoreCount())) {
				log.warn("CPU processor, hz, pyhsical and logical core counts cannot be null! Skipping...");
			} else {
				Cpu cpu = new Cpu();
				cpu.setArch(message.getCpu().getArch());
				cpu.setBits(message.getCpu().getBits() != null ? message.getCpu().getBits().toString() : null);
				cpu.setBrand(message.getCpu().getBrand());
				cpu.setCount(message.getCpu().getCount());
				cpu.setExtendedFamily(
						message.getCpu().getExtendedFamily() != null ? message.getCpu().getExtendedFamily().toString()
								: null);
				cpu.setFamily(message.getCpu().getFamily() != null ? message.getCpu().getFamily().toString() : null);
				cpu.setHzAdvertised(message.getCpu().getHzAdvertised());
				cpu.setL2CacheAssociativity(message.getCpu().getL2CacheAssociativity());
				cpu.setL2CacheLineSize(
						message.getCpu().getL2CacheLineSize() != null ? message.getCpu().getL2CacheLineSize().toString()
								: null);
				cpu.setL2CacheSize(message.getCpu().getL2CacheSize());
				cpu.setLogicalCoreCount(message.getCpu().getLogicalCoreCount());
				cpu.setModel(message.getCpu().getModel() != null ? message.getCpu().getModel().toString() : null);
				cpu.setProcessor(message.getCpu().getProcessor());
				cpu.setPyhsicalCoreCount(message.getCpu().getPyhsicalCoreCount());
				cpu.setRawArchString(message.getCpu().getRawArchString());
				cpu.setVendorId(message.getCpu().getVendorId());
				Cpu tmp = this.hardwareService.getCpu(cpu.getBrand(), cpu.getProcessor());
				cpu = tmp != null ? tmp : cpu;
				
				AgentCpu cross = new AgentCpu();
				cross.setCommaSeparatedCpuTimes(
						message.getCpu().getCpuTimes() != null ? StringUtils.join(",", message.getCpu().getCpuTimes())
								: null);
				cross.setCommaSeparatedFlags(
						message.getCpu().getFlags() != null ? StringUtils.join(",", message.getCpu().getFlags()) : null);
				cross.setCommaSeparatedStats(
						message.getCpu().getStats() != null ? StringUtils.join(",", message.getCpu().getStats()) : null);
				cross.setHzActual(message.getCpu().getHzActual());
				cross.setAgent(agent);
				cross.setCpu(cpu);
				
				agent.getAgentCpus().add(cross);
			}
		}
		//
		// Peripherals
		//
		if (message.getPeripheralDevices() != null) {
			// Remove peripherals
			if (agent.getAgentPeripheralDevices() != null) {
				Iterator<AgentPeripheralDevice> it = agent.getAgentPeripheralDevices().iterator();
				while (it.hasNext()) {
					AgentPeripheralDevice _p = it.next();
					if (_p.getId() != null) {
						this.em.remove(_p);
						this.em.flush();
						it.remove();
					} else {
						this.em.persist(_p);
					}
				}
			}
			for (tr.com.agem.alfa.agent.sysinfo.PeripheralDevice p : message.getPeripheralDevices()) {
				if (isNullOrEmpty(p.getTag())) {
					log.warn("Peripheral device tag cannot be null! Skipping...");
					continue;
				}
				PeripheralDevice peripheral = new PeripheralDevice();
				peripheral.setShowInSurvey(false);
				peripheral.setTag(p.getTag());
				PeripheralDevice tmp = this.peripheralService.getPeripheral(peripheral.getTag());
				peripheral = tmp != null ? tmp : peripheral;

				AgentPeripheralDevice cross = new AgentPeripheralDevice();
				cross.setDeviceId(p.getDevice());
				cross.setDevicePath(p.getDevice());
				cross.setAgent(agent);
				cross.setPeripheralDevice(peripheral);

				agent.getAgentPeripheralDevices().add(cross);
			}
		}
		return agent;
	}

	private List<String> excludeInvalid(List<String> macAddresses) {
		Iterator<String> it = macAddresses.iterator();
		while (it.hasNext()) {
			String macAddr = it.next();
			if ("00:00:00:00:00:00".equals(macAddr) || "00-00-00-00-00-00-00-E0".equalsIgnoreCase(macAddr)) {
				it.remove();
			}
		}
		return macAddresses;
	}

	private List<String> excludeLocalhost(List<String> ipAddresses) {
		Iterator<String> it = ipAddresses.iterator();
		while(it.hasNext()) {
			String ipAddr = it.next();
			if ("127.0.0.1".equals(ipAddr) || "localhost".equalsIgnoreCase(ipAddr)) {
				it.remove();
			}
		}
		return ipAddresses;
	}

	private String toCapabilityString(Map<String, String> capabilities) {
		if (capabilities == null || capabilities.isEmpty()) return null;
		StringBuilder cap = new StringBuilder();
		for (Entry<String, String> e : capabilities.entrySet()) {
			cap.append(e.getKey()).append(":").append(e.getValue()).append(",");
		}
		return cap.toString();
	}

	private SurveyResult toSurveyResultEntity(SurveyResultMessage message, Agent agent, Survey survey)
			throws JsonProcessingException {
		SurveyResult entity = new SurveyResult();
		entity.setAgent(agent);
		agent.getSurveyResults().add(entity);
		entity.setSurvey(survey);
		survey.getSurveyResults().add(entity);
		entity.setResult(new ObjectMapper().writeValueAsBytes(message.getResult()));
		return entity;
	}

}

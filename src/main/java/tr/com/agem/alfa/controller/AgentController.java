package tr.com.agem.alfa.controller;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
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
import tr.com.agem.alfa.messaging.message.SysInfoResultMessage;
import tr.com.agem.alfa.model.Agent;
import tr.com.agem.alfa.model.Disk;
import tr.com.agem.alfa.model.Gpu;
import tr.com.agem.alfa.model.Memory;
import tr.com.agem.alfa.model.NetworkInterface;
import tr.com.agem.alfa.model.Platform;
import tr.com.agem.alfa.model.enums.AgentType;
import tr.com.agem.alfa.service.AgentService;
import tr.com.agem.alfa.util.AlfaBeanUtils;
import tr.com.agem.alfa.util.CommonUtils;

@Controller
public class AgentController {

	private static final Logger log = LoggerFactory.getLogger(AgentController.class);

	@Autowired
	private AgentService agentService;

	@PostMapping("/agent/sysinfo-result")
	public @ResponseBody ResponseEntity<?> handleSysInfoResult(@Valid @RequestBody SysInfoResultMessage message,
			BindingResult bindingResult) {
		log.info("Processing system info from agent:{}", message.getFrom());
		RestResponseBody result = new RestResponseBody();
		if (bindingResult.hasErrors()) {
			String error = ControllerUtils.toErrorMessage(bindingResult);
			log.error(error);
			result.setMessage(error);
			return ResponseEntity.badRequest().body(result);
		}
		try {
			Agent agent = agentService.getAgentByMessagingId(message.getFrom());
			agent = toAgentEntity(message, "SYSTEM", agent);
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

	private Agent toAgentEntity(SysInfoResultMessage message, String principal, Agent origAgent)
			throws JsonProcessingException {
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
		agent.setIpAddresses(CommonUtils.join(",", message.getNetwork().getIpAddresses()
				.toArray(new String[message.getNetwork().getIpAddresses().size()])));
		agent.setMacAddresses(CommonUtils.join(",", message.getNetwork().getMacAddresses()
				.toArray(new String[message.getNetwork().getMacAddresses().size()])));
		agent.setLastInstallationDate(new Date());
		agent.setSysinfo(new ObjectMapper().writeValueAsBytes(message));
		//
		// Disk
		//
		for (Device d : message.getDisk().getDevices()) {
			Disk disk = new Disk();
			AlfaBeanUtils.getInstance().copyProperties(d, disk);
			agent.addDisk(disk);
		}
		//
		// Network interfaces
		//
		for (Device d : message.getNetwork().getDevices()) {
			NetworkInterface inet = new NetworkInterface();
			AlfaBeanUtils.getInstance().copyProperties(d, inet);
			inet.setCapabilities(toCapabilityString(d.getCapabilities()));
			agent.addNetworkInterface(inet);
		}
		//
		// Installed packages
		//
		for (InstalledPackage _package : message.getInstalledPackages()) {
			tr.com.agem.alfa.model.InstalledPackage mPackage = new tr.com.agem.alfa.model.InstalledPackage();
			AlfaBeanUtils.getInstance().copyProperties(_package, mPackage);
			agent.addInstalledPackage(mPackage);
		}
		//
		// Memories
		//
		for (MemoryDevice d : message.getMemory().getDevices()) {
			Memory mem = new Memory();
			AlfaBeanUtils.getInstance().copyProperties(d, mem);
			agent.addMemory(mem);
		}
		//
		// GPU
		//
		for (GpuDevice d : message.getGpu().getDevices()) {
			Gpu gpu = new Gpu();
			AlfaBeanUtils.getInstance().copyProperties(d, gpu);			
			agent.addGpu(gpu);
		}
		//
		// BIOS
		//
		tr.com.agem.alfa.model.Bios bios = agent.getBios() != null ? agent.getBios()
				: new tr.com.agem.alfa.model.Bios();
		AlfaBeanUtils.getInstance().copyProperties(message.getBios(), bios);			
		agent.setBios(bios);
		//
		// Platform
		//
		Platform pl = agent.getPlatform() != null ? agent.getPlatform() : new Platform();
		AlfaBeanUtils.getInstance().copyProperties(message.getPlatform(), pl);			
		agent.setPlatform(pl);
		
		// TODO users, processes, cpus, peripheral
		return agent;
	}

	private String toCapabilityString(Map<String, String> capabilities) {
		StringBuilder cap = new StringBuilder();
		for (Entry<String, String> e : capabilities.entrySet()) {
			cap.append(e.getKey()).append(":").append(e.getValue()).append(",");
		}
		return cap.toString();
	}
	
	@GetMapping("/agent/list")
	public String getListPage() {
		log.debug("Getting agent list page");
		return "agent/list";
	}
	
	@GetMapping("/agent/list-paginated")
	public ResponseEntity<?> handlePackageList(@RequestParam(value = "search", required = false) String search,
			Pageable pageable) {
		log.info("Getting agent page with page number:{} and size: {}", pageable.getPageNumber(),
				pageable.getPageSize());
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

}

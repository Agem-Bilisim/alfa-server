package tr.com.agem.alfa.controller;

import java.util.Date;
import java.util.Locale;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import tr.com.agem.alfa.dto.Device;
import tr.com.agem.alfa.messaging.message.SysInfoResultMessage;
import tr.com.agem.alfa.model.Agent;
import tr.com.agem.alfa.model.Disk;
import tr.com.agem.alfa.model.NetworkInterface;
import tr.com.agem.alfa.model.enums.AgentType;
import tr.com.agem.alfa.service.AgentService;
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
			Agent agent = toAgentEntity(message, "SYSTEM");
			agentService.createOrUpdate(agent);
			log.info("Agent and its system info created/updated successfully.");
		} catch (Exception e) {
			String error = "Exception occurred when trying to handle system info.";
			log.error(error, e);
			result.setMessage(error);
			return ResponseEntity.badRequest().body(result);
		}
		return ResponseEntity.ok(result);
	}

	private Agent toAgentEntity(SysInfoResultMessage message, String principal) throws JsonProcessingException {
		Agent agent = new Agent();
		//
		// Agent
		//
		agent.setType(message.getPlatform().getSystem() != null
				&& message.getPlatform().getSystem().toLowerCase(Locale.ENGLISH).contains("win")
						? AgentType.WINDOWS_BASED.getId()
						: AgentType.DEBIAN_BASED.getId());
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
			disk.setVendor(d.getVendor());
			disk.setDescription(d.getDescription());
			disk.setVersion(d.getVersion());
			disk.setProduct(d.getProduct());
			disk.setSerial(d.getSerial());
			agent.addDisk(disk);
		}
		//
		// Network interfaces
		//
		for (Device d : message.getNetwork().getDevices()) {
			NetworkInterface inet = new NetworkInterface();
			inet.setVendor(d.getVendor());
			inet.setVersion(d.getVersion());
			inet.setProduct(d.getProduct());
			inet.setCapabilities(capabilities);
			agent.addNetworkInterface(inet);
		}
		return agent;
	}

}

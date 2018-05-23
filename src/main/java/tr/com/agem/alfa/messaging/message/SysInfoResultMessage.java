package tr.com.agem.alfa.messaging.message;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import tr.com.agem.alfa.agent.sysinfo.Bios;
import tr.com.agem.alfa.agent.sysinfo.Cpu;
import tr.com.agem.alfa.agent.sysinfo.Disk;
import tr.com.agem.alfa.agent.sysinfo.Gpu;
import tr.com.agem.alfa.agent.sysinfo.Memory;
import tr.com.agem.alfa.agent.sysinfo.Network;
import tr.com.agem.alfa.agent.sysinfo.InstalledPackage;
import tr.com.agem.alfa.agent.sysinfo.PeripheralDevice;
import tr.com.agem.alfa.agent.sysinfo.Platform;
import tr.com.agem.alfa.agent.sysinfo.Process;

/**
 * Classes are auto-generated from JSON using online tools! DO NOT change anything!
 * 
 * @author <a href="mailto:emre.akkaya@agem.com.tr">Emre Akkaya</a>
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "platform", "installed_packages", "disk", "cpu", "bios", "peripheral_devices", "gpu", "processes",
		"users", "network", "memory" })
public class SysInfoResultMessage extends AgentBaseMessage {

	private static final long serialVersionUID = 1135297498132000603L;

	@NotEmpty
	private String from;

	@NotEmpty
	@JsonProperty("agent_install_path")
	private String agentInstallPath;

	@NotNull
	@JsonProperty("platform")
	private Platform platform;

	@JsonProperty("installed_packages")
	private List<InstalledPackage> installedPackages = null;

	@JsonProperty("disk")
	private Disk disk;

	@NotNull
	@JsonProperty("cpu")
	private Cpu cpu;

	@NotNull
	@JsonProperty("bios")
	private Bios bios;

	@JsonProperty("peripheral_devices")
	private List<PeripheralDevice> peripheralDevices = null;

	@JsonProperty("gpu")
	private Gpu gpu;

	@JsonProperty("processes")
	private List<Process> processes = null;

	@JsonProperty("users")
	private List<String> users = null;

	@JsonProperty("network")
	private Network network;

	@JsonProperty("memory")
	private Memory memory;

	public Disk getDisk() {
		return disk;
	}

	public void setDisk(Disk disk) {
		this.disk = disk;
	}

	public Network getNetwork() {
		return network;
	}

	public void setNetwork(Network network) {
		this.network = network;
	}

	public List<Process> getProcesses() {
		return processes;
	}

	public void setProcesses(List<Process> processes) {
		this.processes = processes;
	}

	public Bios getBios() {
		return bios;
	}

	public void setBios(Bios bios) {
		this.bios = bios;
	}

	public Cpu getCpu() {
		return cpu;
	}

	public void setCpu(Cpu cpu) {
		this.cpu = cpu;
	}

	public Platform getPlatform() {
		return platform;
	}

	public void setPlatform(Platform platform) {
		this.platform = platform;
	}

	public Gpu getGpu() {
		return gpu;
	}

	public void setGpu(Gpu gpu) {
		this.gpu = gpu;
	}

	public List<PeripheralDevice> getPeripheralDevices() {
		return peripheralDevices;
	}

	public void setPeripheralDevices(List<PeripheralDevice> peripheralDevices) {
		this.peripheralDevices = peripheralDevices;
	}

	public Memory getMemory() {
		return memory;
	}

	public void setMemory(Memory memory) {
		this.memory = memory;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getAgentInstallPath() {
		return agentInstallPath;
	}

	public void setAgentInstallPath(String agentInstallPath) {
		this.agentInstallPath = agentInstallPath;
	}

	public List<InstalledPackage> getInstalledPackages() {
		return installedPackages;
	}

	public void setInstalledPackages(List<InstalledPackage> installedPackages) {
		this.installedPackages = installedPackages;
	}

	public List<String> getUsers() {
		return users;
	}

	public void setUsers(List<String> users) {
		this.users = users;
	}

}


package tr.com.agem.alfa.messaging.message;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import tr.com.agem.alfa.dto.Bios;
import tr.com.agem.alfa.dto.Cpu;
import tr.com.agem.alfa.dto.Disk;
import tr.com.agem.alfa.dto.Gpu;
import tr.com.agem.alfa.dto.Memory;
import tr.com.agem.alfa.dto.Network;
import tr.com.agem.alfa.dto.PeripheralDevice;
import tr.com.agem.alfa.dto.Platform;
import tr.com.agem.alfa.dto.Process;

public class SysInfoResultMessage extends AgentBaseMessage {

	private static final long serialVersionUID = 1135297498132000603L;

	@NotEmpty
	private String from;
	@NotEmpty
	private String agentInstallPath;
	private Disk disk;
	private Network network;
	private List<Process> processes = null;
	@NotNull
	private Bios bios;
	@NotNull
	private Cpu cpu;
	@NotNull
	private Platform platform;
	private Gpu gpu;
	private List<PeripheralDevice> peripheralDevices = null;
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

}

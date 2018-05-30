
package tr.com.agem.alfa.agent.sysinfo;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GpuDevice implements Serializable {

	private final static long serialVersionUID = 3642930991760174615L;

	private String subsystem;
	private String kernel;
	private String memory;
	@JsonProperty("driver_date")
	private String driverDate;
	@JsonProperty("driver_version")
	private String driverVersion;

	public String getSubsystem() {
		return subsystem;
	}

	public void setSubsystem(String subsystem) {
		this.subsystem = subsystem;
	}

	public String getKernel() {
		return kernel;
	}

	public void setKernel(String kernel) {
		this.kernel = kernel;
	}

	public String getMemory() {
		return memory;
	}

	public void setMemory(String memory) {
		this.memory = memory;
	}

	public String getDriverDate() {
		return driverDate;
	}

	public void setDriverDate(String driverDate) {
		this.driverDate = driverDate;
	}

	public String getDriverVersion() {
		return driverVersion;
	}

	public void setDriverVersion(String driverVersion) {
		this.driverVersion = driverVersion;
	}

}

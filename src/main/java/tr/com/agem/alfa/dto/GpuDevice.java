
package tr.com.agem.alfa.dto;

import java.io.Serializable;

public class GpuDevice implements Serializable {

	private final static long serialVersionUID = 3642930991760174615L;

	private String subsystem;
	private String kernel;
	private String memory;

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

}

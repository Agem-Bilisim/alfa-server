package tr.com.agem.alfa.form;

import org.hibernate.validator.constraints.NotEmpty;

public class GpuForm extends BaseForm {

	private static final long serialVersionUID = 3323842484079222128L;

	@NotEmpty
	private String subsystem;

	@NotEmpty
	private String kernel;

	@NotEmpty
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

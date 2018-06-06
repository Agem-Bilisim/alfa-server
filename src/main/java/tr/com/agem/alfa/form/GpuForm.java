package tr.com.agem.alfa.form;

import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

import tr.com.agem.alfa.model.Agent;

public class GpuForm extends BaseForm {

	private static final long serialVersionUID = 3323842484079222128L;

	@NotEmpty
	private String subsystem;

	@NotEmpty
	private String kernel;

	@NotEmpty
	private String memory;
	
	private List<Agent> agents;

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

	public List<Agent> getAgents() {
		return agents;
	}

	public void setAgents(List<Agent> agents) {
		this.agents = agents;
	}

}

package tr.com.agem.alfa.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "c_agent_gpu")
public class Gpu extends BaseModel {

	private static final long serialVersionUID = -5967722775213127483L;

	@Column(name = "SUBSYSTEM", nullable = false, length = 100)
	private String subsystem;

	@Column(name = "KERNEL", nullable = false, length = 100)
	private String kernel;

	@Column(name = "MEMORY", nullable = false, length = 20)
	private String memory;

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "gpus")
	private Set<Agent> agents = new HashSet<Agent>(0);

	public Gpu() {
	}

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

	public Set<Agent> getAgents() {
		return agents;
	}

	public void setAgents(Set<Agent> agents) {
		this.agents = agents;
	}
	
	public void addAgent(Agent agent) {
		if (this.agents == null) {
			this.agents = new HashSet<Agent>();
		}
		this.agents.add(agent);
	}

}

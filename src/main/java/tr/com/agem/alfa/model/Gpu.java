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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((kernel == null) ? 0 : kernel.hashCode());
		result = prime * result + ((memory == null) ? 0 : memory.hashCode());
		result = prime * result + ((subsystem == null) ? 0 : subsystem.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Gpu other = (Gpu) obj;
		if (kernel == null) {
			if (other.kernel != null) return false;
		} else if (!kernel.equals(other.kernel)) return false;
		if (memory == null) {
			if (other.memory != null) return false;
		} else if (!memory.equals(other.memory)) return false;
		if (subsystem == null) {
			if (other.subsystem != null) return false;
		} else if (!subsystem.equals(other.subsystem)) return false;
		return true;
	}

}

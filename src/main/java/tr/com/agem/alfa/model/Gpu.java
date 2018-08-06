package tr.com.agem.alfa.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author <a href="mailto:emre.akkaya@agem.com.tr">Emre Akkaya</a>
 */
@Entity
@Table(name = "c_agent_gpu", uniqueConstraints = { @UniqueConstraint(columnNames = { "SUBSYSTEM", "KERNEL", "MEMORY" }) })
public class Gpu extends BaseModel {

	private static final long serialVersionUID = -5967722775213127483L;

	@Column(name = "SUBSYSTEM", nullable = false, length = 100)
	private String subsystem;

	@Column(name = "KERNEL", nullable = false, length = 100)
	private String kernel;

	@Column(name = "MEMORY", nullable = false, length = 200)
	private String memory;

	@Column(name = "DRIVER_DATE", length = 100)
	private String driverDate;

	@Column(name = "DRIVER_VERSION", length = 100)
	private String driverVersion;

	@Column(name = "COMPATIBLE")
	private String compatible;
	
	@JsonIgnore
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
		agent.getGpus().add(this);
		this.agents.add(agent);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((driverDate == null) ? 0 : driverDate.hashCode());
		result = prime * result + ((driverVersion == null) ? 0 : driverVersion.hashCode());
		result = prime * result + ((kernel == null) ? 0 : kernel.hashCode());
		result = prime * result + ((subsystem == null) ? 0 : subsystem.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Gpu other = (Gpu) obj;
		if (driverDate == null) {
			if (other.driverDate != null) return false;
		} else if (!driverDate.equals(other.driverDate)) return false;
		if (driverVersion == null) {
			if (other.driverVersion != null) return false;
		} else if (!driverVersion.equals(other.driverVersion)) return false;
		if (kernel == null) {
			if (other.kernel != null) return false;
		} else if (!kernel.equals(other.kernel)) return false;
		if (subsystem == null) {
			if (other.subsystem != null) return false;
		} else if (!subsystem.equals(other.subsystem)) return false;
		return true;
	}

	public String getCompatible() {
		return compatible;
	}

	public void setCompatible(String compatible) {
		this.compatible = compatible;
	}

}

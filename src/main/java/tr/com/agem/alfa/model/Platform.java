package tr.com.agem.alfa.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "c_agent_platform", uniqueConstraints = { @UniqueConstraint(columnNames = { "SYSTEM", "PL_RELEASE" }) })
public class Platform extends BaseModel {

	private static final long serialVersionUID = 4952577557714888266L;

	@Column(name = "PL_RELEASE", nullable = false) // 'RELEASE' key word is reserved!
	private String release;

	@Column(name = "VERSION", nullable = false)
	private String version;

	@Column(name = "SYSTEM", nullable = false)
	private String system;

	@Column(name = "MACHINE", nullable = false)
	private String machine;

	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "platform")
	private Set<Agent> agents = new HashSet<Agent>(0);

	public String getRelease() {
		return release;
	}

	public void setRelease(String release) {
		this.release = release;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getSystem() {
		return system;
	}

	public void setSystem(String system) {
		this.system = system;
	}

	public String getMachine() {
		return machine;
	}

	public void setMachine(String machine) {
		this.machine = machine;
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
		result = prime * result + ((release == null) ? 0 : release.hashCode());
		result = prime * result + ((system == null) ? 0 : system.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Platform other = (Platform) obj;
		if (release == null) {
			if (other.release != null) return false;
		} else if (!release.equals(other.release)) return false;
		if (system == null) {
			if (other.system != null) return false;
		} else if (!system.equals(other.system)) return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see tr.com.agem.alfa.model.BaseModel#getCorrespondingForm()
	 */
	@Override
	public Object getCorrespondingForm() {
		return null;
	}

}

package tr.com.agem.alfa.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "c_agent_platform")
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

}

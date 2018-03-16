package tr.com.agem.alfa.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "c_agent_bios", uniqueConstraints = { @UniqueConstraint(columnNames = "VERSION"),
		@UniqueConstraint(columnNames = "VENDOR") })
public class Bios extends BaseModel {

	private static final long serialVersionUID = -6764863993208773539L;

	@Column(name = "VENDOR", nullable = false)
	private String vendor;

	@Column(name = "VERSION", nullable = false)
	private String version;

	@Column(name = "RELEASE_DATE")
	private String releaseDate;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "bios")
	private Set<Agent> agents = new HashSet<Agent>(0);

	public String getVendor() {
		return vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
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

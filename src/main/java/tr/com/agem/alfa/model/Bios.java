package tr.com.agem.alfa.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "c_agent_bios", uniqueConstraints = { @UniqueConstraint(columnNames = "VERSION"),
		@UniqueConstraint(columnNames = "VENDOR") })
public class Bios extends BaseModel {

	private static final long serialVersionUID = -6764863993208773539L;

	@Column(name = "VENDOR", nullable = false)
	private String vendor;

	@Column(name = "VERSION", nullable = false)
	private String version;

	@Temporal(TemporalType.DATE)
	@Column(name = "RELEASE_DATE")
	private Date releaseDate;

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

	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	public Set<Agent> getAgents() {
		return agents;
	}

	public void setAgents(Set<Agent> agents) {
		this.agents = agents;
	}

}

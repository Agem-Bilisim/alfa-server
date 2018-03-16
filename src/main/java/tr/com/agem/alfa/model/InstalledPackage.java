package tr.com.agem.alfa.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "c_agent_package")
public class InstalledPackage extends BaseModel {

	private static final long serialVersionUID = -4829346133428733432L;

	@Column(name = "NAME", length = 100)
	private String name;

	@Column(name = "VERSION", length = 100)
	private String version;

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "installedPackages")
	private Set<Agent> agents = new HashSet<Agent>(0);

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public void addAgent(Agent agent) {
		if (this.agents == null) {
			this.agents = new HashSet<Agent>();
		}
		this.agents.add(agent);
	}

}

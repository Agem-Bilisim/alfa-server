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
@Table(name = "c_agent_package", uniqueConstraints = { @UniqueConstraint(columnNames = { "NAME", "VERSION" }) })
public class InstalledPackage extends BaseModel {

	private static final long serialVersionUID = -4829346133428733432L;

	@Column(name = "NAME", length = 100, nullable = false)
	private String name;

	@Column(name = "VERSION", length = 100, nullable = false)
	private String version;

	@Column(name = "INSTITUTIONAL")
	private Boolean institutional; // Kurumsal

	@Column(name = "SHOW_IN_SURVEY")
	private Boolean showInSurvey;

	@Column(name = "COMPATIBLE")
	private String compatible;

	@JsonIgnore
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

	public Boolean getInstitutional() {
		return institutional;
	}

	public void setInstitutional(Boolean institutional) {
		this.institutional = institutional;
	}

	public Boolean getShowInSurvey() {
		return showInSurvey;
	}

	public void setShowInSurvey(Boolean showInSurvey) {
		this.showInSurvey = showInSurvey;
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
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((version == null) ? 0 : version.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		InstalledPackage other = (InstalledPackage) obj;
		if (name == null) {
			if (other.name != null) return false;
		} else if (!name.equals(other.name)) return false;
		if (version == null) {
			if (other.version != null) return false;
		} else if (!version.equals(other.version)) return false;
		return true;
	}

	public String getCompatible() {
		return compatible;
	}

	public void setCompatible(String compatible) {
		this.compatible = compatible;
	}

	
}

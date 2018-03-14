package tr.com.agem.alfa.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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

	@ManyToMany(cascade = { CascadeType.ALL })
	private Set<Agent> agents = new HashSet<Agent>();

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

}

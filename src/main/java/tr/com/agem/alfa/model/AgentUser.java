package tr.com.agem.alfa.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "c_agent_user")
public class AgentUser extends BaseModel {

	private static final long serialVersionUID = 6660316032136480916L;

	@Column(name = "NAME", nullable = false, length = 255, unique = true)
	private String name;

	@Column(name = "GROUPS", nullable = false, length = 255)
	private String commaSeparatedGroups;

	@JsonIgnore
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "users")
	private Set<Agent> agents = new HashSet<Agent>(0);

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCommaSeparatedGroups() {
		return commaSeparatedGroups;
	}

	public void setCommaSeparatedGroups(String commaSeparatedGroups) {
		this.commaSeparatedGroups = commaSeparatedGroups;
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

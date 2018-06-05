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

	@Column(name = "GROUPS", length = 255)
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		AgentUser other = (AgentUser) obj;
		if (name == null) {
			if (other.name != null) return false;
		} else if (!name.equals(other.name)) return false;
		return true;
	}

}

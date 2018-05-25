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
@Table(name = "c_agent_memory")
public class Memory extends BaseModel {

	private static final long serialVersionUID = -5117383050214392422L;

	@Column(name = "SPEED", nullable = false, length = 20)
	private String speed;

	@Column(name = "SIZE", nullable = false, length = 20)
	private String size;

	@Column(name = "TYPE", nullable = false, length = 10)
	private String type;

	@Column(name = "MANUFACTURER", nullable = false, length = 100)
	private String manufacturer;

	@JsonIgnore
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "memories")
	private Set<Agent> agents = new HashSet<Agent>(0);

	public Memory() {
	}

	public String getSpeed() {
		return speed;
	}

	public void setSpeed(String speed) {
		this.speed = speed;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
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
		result = prime * result + ((manufacturer == null) ? 0 : manufacturer.hashCode());
		result = prime * result + ((size == null) ? 0 : size.hashCode());
		result = prime * result + ((speed == null) ? 0 : speed.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Memory other = (Memory) obj;
		if (manufacturer == null) {
			if (other.manufacturer != null) return false;
		} else if (!manufacturer.equals(other.manufacturer)) return false;
		if (size == null) {
			if (other.size != null) return false;
		} else if (!size.equals(other.size)) return false;
		if (speed == null) {
			if (other.speed != null) return false;
		} else if (!speed.equals(other.speed)) return false;
		if (type == null) {
			if (other.type != null) return false;
		} else if (!type.equals(other.type)) return false;
		return true;
	}

}

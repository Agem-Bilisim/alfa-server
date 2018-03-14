package tr.com.agem.alfa.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

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

}

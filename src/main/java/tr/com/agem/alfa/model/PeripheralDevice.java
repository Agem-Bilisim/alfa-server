package tr.com.agem.alfa.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author <a href="mailto:emre.akkaya@agem.com.tr">Emre Akkaya</a>
 */
@Entity
@Table(name = "c_agent_peripheral")
public class PeripheralDevice extends BaseModel {

	private static final long serialVersionUID = -5929581631043566633L;

	@Column(name = "TAG", nullable = false, length = 100, unique = true)
	private String tag;

	@Column(name = "SHOW_IN_SURVEY")
	private Boolean showInSurvey;

	@Column(name = "COMPATIBLE")
	private String compatible;

	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "peripheralDevice", cascade = CascadeType.REMOVE)
	private Set<AgentPeripheralDevice> agentPeripheralDevices = new HashSet<AgentPeripheralDevice>(0);

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public Boolean getShowInSurvey() {
		return showInSurvey;
	}

	public void setShowInSurvey(Boolean showInSurvey) {
		this.showInSurvey = showInSurvey;
	}

	public Set<AgentPeripheralDevice> getAgentPeripheralDevices() {
		return agentPeripheralDevices;
	}

	public void setAgentPeripheralDevices(Set<AgentPeripheralDevice> agentPeripheralDevices) {
		this.agentPeripheralDevices = agentPeripheralDevices;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((tag == null) ? 0 : tag.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		PeripheralDevice other = (PeripheralDevice) obj;
		if (tag == null) {
			if (other.tag != null) return false;
		} else if (!tag.equals(other.tag)) return false;
		return true;
	}

	public String getCompatible() {
		return compatible;
	}

	public void setCompatible(String compatible) {
		this.compatible = compatible;
	}

}

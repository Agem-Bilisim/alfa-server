package tr.com.agem.alfa.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "c_agent_peripheral")
public class PeripheralDevice extends BaseModel {

	private static final long serialVersionUID = -5929581631043566633L;

	@Column(name = "TAG", nullable = false, length = 100)
	private String tag;

	@Column(name = "SHOW_IN_SURVEY")
	private Boolean showInSurvey;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.peripheralDevice")
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

}

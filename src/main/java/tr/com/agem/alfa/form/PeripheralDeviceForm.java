package tr.com.agem.alfa.form;

import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

import tr.com.agem.alfa.model.Agent;

/**
 * @author <a href="mailto:caner.feyzullahoglu@agem.com.tr">Caner Feyzullahoğlu</a>
 */
public class PeripheralDeviceForm extends BaseForm {

	private static final long serialVersionUID = 5368610239577313929L;

	@NotEmpty
	private String tag;

	private Boolean showInSurvey;

	private List<Agent> agents;

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

	public List<Agent> getAgents() {
		return agents;
	}

	public void setAgents(List<Agent> agents) {
		this.agents = agents;
	}

}

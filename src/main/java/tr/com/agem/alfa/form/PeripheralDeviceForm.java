package tr.com.agem.alfa.form;

import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

import tr.com.agem.alfa.model.Agent;
import tr.com.agem.alfa.model.enums.ProblemReferenceType;
import tr.com.agem.alfa.util.SelectboxBuilder.OptionFormConvertable;

/**
 * @author <a href="mailto:caner.feyzullahoglu@agem.com.tr">Caner
 *         Feyzullahoğlu</a>
 */
public class PeripheralDeviceForm extends BaseForm implements OptionFormConvertable {

	private static final long serialVersionUID = 5368610239577313929L;

	@NotEmpty
	private String tag;

	private Boolean showInSurvey;

	@NotEmpty
	private String deviceId;

	@NotEmpty
	private String devicePath;

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

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getDevicePath() {
		return devicePath;
	}

	public void setDevicePath(String devicePath) {
		this.devicePath = devicePath;
	}

	@Override
	public String getOptionText() {
		return this.tag;
	}

	@Override
	public String getOptionValue() {
		return ProblemReferenceType.PERIPHERAL.getId() + "-" + this.getId();
	}

}

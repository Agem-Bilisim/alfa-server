package tr.com.agem.alfa.form;

import org.hibernate.validator.constraints.NotEmpty;

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

	private String compatible;
	
	@NotEmpty
	private String deviceId;

	@NotEmpty
	private String devicePath;

	private Long[] agentIds;

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

	public Long[] getAgentIds() {
		return agentIds;
	}

	public void setAgentIds(Long[] agentIds) {
		this.agentIds = agentIds;
	}

	@Override
	public String getOptionText() {
		return this.tag;
	}

	@Override
	public String getOptionValue() {
		return ProblemReferenceType.PERIPHERAL.getId() + "-" + this.getId();
	}

	public String getCompatible() {
		return compatible;
	}

	public void setCompatible(String compatible) {
		this.compatible = compatible;
	}
}

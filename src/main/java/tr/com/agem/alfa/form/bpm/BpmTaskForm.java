package tr.com.agem.alfa.form.bpm;

import java.io.Serializable;
import java.util.List;

/**
 * @author <a href="mailto:ali.ozeren@agem.com.tr">Ali Ozkan Ozeren</a>
 */
public class BpmTaskForm implements Serializable {

	private static final long serialVersionUID = -4212106777364053291L;
	
	private Long processId;
	
	private String processName;

	private String relatedComponent;

	private Long relatedComponentId;
	
	private List<String> formString;

	public String getRelatedComponent() {
		return relatedComponent;
	}

	public void setRelatedComponent(String relatedComponent) {
		this.relatedComponent = relatedComponent;
	}

	public Long getRelatedComponentId() {
		return relatedComponentId;
	}

	public void setRelatedComponentId(Long relatedComponentId) {
		this.relatedComponentId = relatedComponentId;
	}

	public List<String> getFormString() {
		return formString;
	}

	public void setFormString(List<String> formString) {
		this.formString = formString;
	}

	public Long getProcessId() {
		return processId;
	}

	public void setProcessId(Long processId) {
		this.processId = processId;
	}

	public String getProcessName() {
		return processName;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}


}

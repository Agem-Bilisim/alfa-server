package tr.com.agem.alfa.form.bpm;

import java.util.List;

import tr.com.agem.alfa.form.BaseForm;

/**
 * @author <a href="mailto:ali.ozeren@agem.com.tr">Ali Ozkan Ozeren</a>
 */
public class BpmTaskForm extends BaseForm {

	private static final long serialVersionUID = -4212112777364053291L;
	
	private String taskId;

	private String taskDescription;

    private List<String> formString;

    
	public String getTaskDescription() {
		return taskDescription;
	}

	public void setTaskDescription(String taskDescription) {
		this.taskDescription = taskDescription;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public List<String> getFormString() {
		return formString;
	}

	public void setFormString(List<String> formString) {
		this.formString = formString;
	}
	

}

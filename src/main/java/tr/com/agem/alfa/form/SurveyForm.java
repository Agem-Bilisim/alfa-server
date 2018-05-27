package tr.com.agem.alfa.form;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * 
 * @author emre
 *
 */
public class SurveyForm extends BaseForm {

	private static final long serialVersionUID = 1726030152113654799L;

	@NotEmpty
	private String label;

	@NotEmpty
	private String description;

	@NotEmpty
	private String surveyJson;

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSurveyJson() {
		return surveyJson;
	}

	public void setSurveyJson(String surveyJson) {
		this.surveyJson = surveyJson;
	}

}

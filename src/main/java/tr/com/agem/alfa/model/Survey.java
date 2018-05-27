package tr.com.agem.alfa.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 * 
 * @author emre
 *
 */
@Entity
@Table(name = "c_survey")
public class Survey extends BaseModel {

	private static final long serialVersionUID = 7919070392446825134L;

	@Column(name = "LABEL", nullable = false, unique = true)
	private String label;

	@Column(name = "DESCRIPTION", length = 500)
	private String description;

	@Lob
	@Column(name = "SURVEY_JSON", nullable = false)
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

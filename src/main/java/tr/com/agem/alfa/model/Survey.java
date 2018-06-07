package tr.com.agem.alfa.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import tr.com.agem.alfa.model.enums.SurveyType;

/**
 * @author <a href="mailto:emre.akkaya@agem.com.tr">Emre Akkaya</a>
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

	@Column(name = "SURVEY_TYPE")
	private Integer surveyType;

	@OneToMany(mappedBy = "survey", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<SurveyResult> surveyResults = new HashSet<SurveyResult>(0);

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

	public Integer getSurveyType() {
		return surveyType;
	}

	public SurveyType getLdapTypeEnum() {
		return SurveyType.getType(surveyType);
	}

	public void setSurveyType(Integer surveyType) {
		this.surveyType = surveyType;
	}

	public void setSurveyType(SurveyType surveyType) {
		if (surveyType == null) {
			this.surveyType = null;
		} else {
			this.surveyType = surveyType.getId();
		}
	}

	public Set<SurveyResult> getSurveyResults() {
		return surveyResults;
	}

	public void setSurveyResults(Set<SurveyResult> surveyResults) {
		this.surveyResults = surveyResults;
	}

}

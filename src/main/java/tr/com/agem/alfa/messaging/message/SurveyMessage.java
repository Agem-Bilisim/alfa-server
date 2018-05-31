package tr.com.agem.alfa.messaging.message;

import java.util.LinkedHashMap;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author <a href="mailto:emre.akkaya@agem.com.tr">Emre Akkaya</a>
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SurveyMessage extends ServerBaseMessage {

	private static final long serialVersionUID = -371528372579193448L;

	private String to;

	private LinkedHashMap<String, Object> survey;

	private Long surveyId;

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public LinkedHashMap<String, Object> getSurvey() {
		return survey;
	}

	public void setSurvey(LinkedHashMap<String, Object> survey) {
		this.survey = survey;
	}

	public Long getSurveyId() {
		return surveyId;
	}

	public void setSurveyId(Long surveyId) {
		this.surveyId = surveyId;
	}

}

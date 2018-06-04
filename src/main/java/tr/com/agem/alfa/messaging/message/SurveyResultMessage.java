package tr.com.agem.alfa.messaging.message;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author <a href="mailto:emre.akkaya@agem.com.tr">Emre Akkaya</a>
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SurveyResultMessage extends AgentBaseMessage {

	private static final long serialVersionUID = -3525058722764455050L;

	@NotEmpty
	private String from;

	@NotNull
	private String result;

	@NotNull
	@JsonProperty("survey_id")
	private Long surveyId;

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public Long getSurveyId() {
		return surveyId;
	}

	public void setSurveyId(Long surveyId) {
		this.surveyId = surveyId;
	}

}

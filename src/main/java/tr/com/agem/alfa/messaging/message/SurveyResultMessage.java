package tr.com.agem.alfa.messaging.message;

import java.util.LinkedHashMap;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author <a href="mailto:emre.akkaya@agem.com.tr">Emre Akkaya</a>
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SurveyResultMessage extends AgentBaseMessage {

	private static final long serialVersionUID = -3525058722764455050L;

	@NotEmpty
	private String from;

	@NotNull
	private LinkedHashMap<String, Object> result;

	@NotNull
	private Long surveyId;

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public LinkedHashMap<String, Object> getResult() {
		return result;
	}

	public void setResult(LinkedHashMap<String, Object> result) {
		this.result = result;
	}

	public Long getSurveyId() {
		return surveyId;
	}

	public void setSurveyId(Long surveyId) {
		this.surveyId = surveyId;
	}

}

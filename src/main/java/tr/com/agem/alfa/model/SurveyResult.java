package tr.com.agem.alfa.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author <a href="mailto:emre.akkaya@agem.com.tr">Emre Akkaya</a>
 */
@Entity
@Table(name = "c_survey_result")
public class SurveyResult extends BaseModel {

	private static final long serialVersionUID = 1881803300273251270L;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "AGENT_ID", nullable = false)
	private Agent agent;

	@ManyToOne
	@JoinColumn(name = "SURVEY_ID", nullable = false)
	private Survey survey;

	@Lob
	@Column(name = "SURVEY_RESULT", nullable = false)
	private byte[] result;

	public Agent getAgent() {
		return agent;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	public Survey getSurvey() {
		return survey;
	}

	public void setSurvey(Survey survey) {
		this.survey = survey;
	}

	public byte[] getResult() {
		return result;
	}

	public void setResult(byte[] result) {
		this.result = result;
	}

}

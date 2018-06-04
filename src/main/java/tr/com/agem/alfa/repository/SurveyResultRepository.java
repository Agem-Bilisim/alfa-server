package tr.com.agem.alfa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tr.com.agem.alfa.model.SurveyResult;

/**
 * @author <a href="mailto:emre.akkaya@agem.com.tr">Emre Akkaya</a>
 */
public interface SurveyResultRepository extends JpaRepository<SurveyResult, Long> {

	SurveyResult findFirstByAgentMessagingIdAndSurveyIdOrderByCreatedDateDesc(String messagingId, Long surveyId);

}

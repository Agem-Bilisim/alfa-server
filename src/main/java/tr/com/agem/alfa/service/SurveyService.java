package tr.com.agem.alfa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import tr.com.agem.alfa.model.Survey;
import tr.com.agem.alfa.model.SurveyResult;
import tr.com.agem.alfa.repository.SurveyRepository;
import tr.com.agem.alfa.repository.SurveyResultRepository;

/**
 * @author <a href="mailto:emre.akkaya@agem.com.tr">Emre Akkaya</a>
 */
@Component
@Transactional
public class SurveyService {

	private final SurveyRepository surveyRepository;
	private final SurveyResultRepository surveyResultRepository;

	@Autowired
	public SurveyService(SurveyRepository surveyRepository, SurveyResultRepository surveyResultRepository) {
		this.surveyRepository = surveyRepository;
		this.surveyResultRepository = surveyResultRepository;
	}

	public Survey getSurvey(Long id) {
		Assert.notNull(id, "ID must not be null");
		return this.surveyRepository.findOne(id);
	}

	public Page<Survey> getSurveys(Pageable pageable, String search) {
		Assert.notNull(pageable, "Pageable must not be null.");
		if (search != null && !search.isEmpty()) {
			return this.surveyRepository.findByLabelContainingOrDescriptionContainingAllIgnoringCase(search, pageable);
		}
		return this.surveyRepository.findAll(pageable);
	}

	public void saveSurvey(Survey survey) {
		Assert.notNull(survey, "Survey must not be null.");
		Survey s = null;
		if (survey.getId() != null && (s = surveyRepository.findOne(survey.getId())) != null) {
			// Update
			s.setLabel(survey.getLabel());
			s.setDescription(survey.getDescription());
			s.setSurveyJson(survey.getSurveyJson());
			this.surveyRepository.save(s);
			return;
		}
		// Create
		this.surveyRepository.save(survey);
	}

	public void saveResult(SurveyResult result) {
		Assert.notNull(result, "Survey result must not be null.");
		surveyResultRepository.save(result);
	}
	
	public SurveyResult getLatestSurveyResult(String messagingId, Long surveyId) {
		return this.surveyResultRepository.findFirstByAgentMessagingIdAndSurveyIdOrderByCreatedDateDesc(messagingId, surveyId);
	}

	public void deleteSurvey(Long id) {
		Assert.notNull(id, "ID must not be null.");
		this.surveyRepository.delete(id);
	}

}

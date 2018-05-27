package tr.com.agem.alfa.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import tr.com.agem.alfa.model.Survey;

public interface SurveyService {

	Survey getSurvey(Long id);

	Page<Survey> getSurveys(Pageable pageable, String search);

	void saveSurvey(Survey survey);

}

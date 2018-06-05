package tr.com.agem.alfa.messaging.factory;

import java.io.IOException;
import java.util.LinkedHashMap;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import tr.com.agem.alfa.messaging.message.ServerBaseMessage;
import tr.com.agem.alfa.messaging.message.SurveyMessage;
import tr.com.agem.alfa.messaging.message.URLRedirectionMessage;
import tr.com.agem.alfa.model.Education;
import tr.com.agem.alfa.model.Survey;

/**
 * @author <a href="mailto:emre.akkaya@agem.com.tr">Emre Akkaya</a>
 */
public class MessageFactory {

	public static ServerBaseMessage newSurveyMessage(String recipient, Survey survey)
			throws JsonParseException, JsonMappingException, IOException {
		SurveyMessage msg = new SurveyMessage();
		msg.setTo(recipient);
		msg.setSurveyId(survey.getId());
		msg.setSurvey(new ObjectMapper().readValue(survey.getSurveyJson(),
				new TypeReference<LinkedHashMap<String, Object>>() {
				}));
		return msg;
	}

	public static ServerBaseMessage newURLRedirectionMessage(String recipient, Education education) {
		URLRedirectionMessage msg = new URLRedirectionMessage();
		msg.setTo(recipient);
		msg.setUrl(education.getUrl());
		return msg;
	}

}

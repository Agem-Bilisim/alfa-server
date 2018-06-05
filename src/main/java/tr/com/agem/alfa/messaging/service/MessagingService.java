package tr.com.agem.alfa.messaging.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import tr.com.agem.alfa.exception.AlfaException;
import tr.com.agem.alfa.messaging.client.BaseMessagingClient;
import tr.com.agem.alfa.messaging.message.ServerBaseMessage;
import tr.com.agem.alfa.messaging.message.SurveyMessage;
import tr.com.agem.alfa.messaging.message.URLRedirectionMessage;
import tr.com.agem.alfa.model.Agent;
import tr.com.agem.alfa.repository.AgentRepository;

/**
 * @author <a href="mailto:emre.akkaya@agem.com.tr">Emre Akkaya</a>
 */
@Service
public class MessagingService {

	private final BaseMessagingClient messagingClient;
	private final AgentRepository agentRepository;

	@Value("${sys.agent.collect-sysinfo-url}")
	private String COLLECT_SYSINFO;

	@Value("${sys.agent.create-survey-url}")
	private String CREATE_SURVEY;

	@Value("${sys.agent.url-redirect-url}")
	private String URL_REDIRECT;

	@Value("${sys.agent.port}")
	private String AGENT_PORT;
	
	@Value("${sys.agent.test-url}")
	private String TEST_URL;

	@Autowired
	public MessagingService(BaseMessagingClient messagingClient, AgentRepository agentRepository) {
		this.messagingClient = messagingClient;
		this.agentRepository = agentRepository;
	}

	public void send(ServerBaseMessage message) throws Exception {
		Assert.notNull(message, "Message must not be null.");
		Agent agent = agentRepository.getAgentByMessagingId(message.getTo());
		for (String ipAddress : getIpAddresses(agent.getIpAddresses())) {
			messagingClient.sendMessage("http://" + ipAddress + ":" + AGENT_PORT, getCorrespondingURL(message), message);
			break;
		}
	}
	
	public boolean isOnline(Agent agent) {
		Assert.notNull(agent, "Agent must not be null.");
		for (String ipAddress : getIpAddresses(agent.getIpAddresses())) {
			try {
				messagingClient.sendMessage("http://" + ipAddress + ":" + AGENT_PORT, TEST_URL);
				return true;
			} catch (Exception e) {
			}
		}
		return false;
	}

	/**
	 * Return all IP addresses except for localhost
	 * 
	 * @param ipAddresses
	 * @return
	 */
	private List<String> getIpAddresses(String ipAddresses) {
		if (ipAddresses == null || ipAddresses.isEmpty()) {
			throw new AlfaException("IP address cannot be found for agent!");
		}
		List<String> _tmp = new ArrayList<String>();
		String[] tokens = ipAddresses.trim().split(",");
		for (String token : tokens) {
			if (!token.trim().equals("127.0.0.1") && !token.trim().equals("localhost")) {
				_tmp.add(token.trim());
			}
		}
		return _tmp;
	}

	private String getCorrespondingURL(ServerBaseMessage message) {
		if (message instanceof SurveyMessage)
			return CREATE_SURVEY;
		else if (message instanceof URLRedirectionMessage) return URL_REDIRECT;
		return COLLECT_SYSINFO;
	}

}

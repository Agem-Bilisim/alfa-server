package tr.com.agem.alfa.service;

import tr.com.agem.alfa.model.Agent;

public interface AgentService {

	Agent getAgentByMessagingId(String messagingId);

	Agent saveOrUpdate(Agent agent);

}

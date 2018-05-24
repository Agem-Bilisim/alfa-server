package tr.com.agem.alfa.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import tr.com.agem.alfa.model.Agent;

public interface AgentService {

	Agent getAgentByMessagingId(String messagingId);

	Agent saveOrUpdate(Agent agent);
	
	Page<Agent> getAgents(Pageable pageable, String search);

}

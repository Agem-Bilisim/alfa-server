package tr.com.agem.alfa.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import tr.com.agem.alfa.model.Agent;
import tr.com.agem.alfa.repository.AgentRepository;

@Component("agentService")
@Transactional
public class AgentServiceImpl implements AgentService {

	@PersistenceContext
	private EntityManager em;

	@Autowired
	private AgentRepository agentRepository;

	@Override
	public Agent saveOrUpdate(Agent agent) {
		Assert.notNull(agent, "Agent must not be null.");
		if (agent.getId() != null) {
			return this.em.merge(agent);
		}
		return this.agentRepository.save(agent);
	}

	@Override
	public Agent getAgentByMessagingId(String messagingId) {
		Assert.notNull(messagingId, "Messaging ID must not be null.");
		return this.agentRepository.getAgentByMessagingId(messagingId);
	}

}

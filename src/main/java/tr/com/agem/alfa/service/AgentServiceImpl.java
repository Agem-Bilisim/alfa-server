package tr.com.agem.alfa.service;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import tr.com.agem.alfa.model.Agent;
import tr.com.agem.alfa.repository.AgentRepository;

@Component("agentService")
@Transactional
public class AgentServiceImpl implements AgentService {

	private final AgentRepository agentRepository;

	public AgentServiceImpl(AgentRepository agentRepository) {
		super();
		this.agentRepository = agentRepository;
	}

	@Override
	public Agent createOrUpdate(Agent agent) {
		Assert.notNull(agent, "Agent must not be null");
		return this.agentRepository.save(agent);
	}

}

package tr.com.agem.alfa.service;

import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import tr.com.agem.alfa.model.Agent;
import tr.com.agem.alfa.model.AgentCpu;
import tr.com.agem.alfa.model.AgentPeripheralDevice;
import tr.com.agem.alfa.model.AgentRunningProcess;
import tr.com.agem.alfa.model.Tag;
import tr.com.agem.alfa.repository.AgentRepository;
import tr.com.agem.alfa.repository.TagRepository;

/**
 * @author <a href="mailto:emre.akkaya@agem.com.tr">Emre Akkaya</a>
 */
@Component
@Transactional
public class AgentService {

	@PersistenceContext
	private EntityManager em;

	private final AgentRepository agentRepository;
	private final TagRepository tagRepository;

	@Autowired
	public AgentService(AgentRepository agentRepository, TagRepository tagRepository) {
		this.agentRepository = agentRepository;
		this.tagRepository = tagRepository;
	}

	public Agent saveOrUpdate(Agent agent, boolean updateCrossTables) {
		Assert.notNull(agent, "Agent must not be null.");
		if (agent.getId() != null) {
			if (updateCrossTables) {
				// Remove processes
				if (agent.getAgentRunningProcesses() != null) {
					Iterator<AgentRunningProcess> it = agent.getAgentRunningProcesses().iterator();
					while (it.hasNext()) {
						AgentRunningProcess _p = it.next();
						if (_p.getId() != null) {
							this.em.remove(_p);
							it.remove();
						}
					}
				}
				// Remove CPUs
				if (agent.getAgentCpus() != null) {
					Iterator<AgentCpu> it = agent.getAgentCpus().iterator();
					while (it.hasNext()) {
						AgentCpu _c = it.next();
						if (_c.getId() != null) {
							this.em.remove(_c);
							it.remove();
						}
					}
				}
				// Remove peripherals
				if (agent.getAgentPeripheralDevices() != null) {
					Iterator<AgentPeripheralDevice> it = agent.getAgentPeripheralDevices().iterator();
					while (it.hasNext()) {
						AgentPeripheralDevice _p = it.next();
						if (_p.getId() != null) {
							this.em.remove(_p);
							it.remove();
						}
					}
				}
			}
			return this.em.merge(agent);
		}
		return this.agentRepository.save(agent);
	}

	public Agent getAgentByMessagingId(String messagingId) {
		Assert.notNull(messagingId, "Messaging ID must not be null.");
		return this.agentRepository.getAgentByMessagingId(messagingId);
	}

	public Page<Agent> getAgents(Pageable pageable, String search) {
		Assert.notNull(pageable, "Pageable must not be null.");
		if (search != null && !search.isEmpty()) {
			// TODO
			return null;
		}
		return this.agentRepository.findAll(pageable);
	}

	public List<Agent> getAgents() {
		return this.agentRepository.findAll();
	}

	public Agent getAgent(Long id) {
		Assert.notNull(id, "Agent ID must not be null.");
		return this.agentRepository.findOne(id);
	}

	public List<Tag> getTags() {
		return this.tagRepository.findAll();
	}

}

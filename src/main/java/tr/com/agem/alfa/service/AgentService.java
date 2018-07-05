package tr.com.agem.alfa.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import tr.com.agem.alfa.model.Agent;
import tr.com.agem.alfa.model.AgentUser;
import tr.com.agem.alfa.model.InstalledPackage;
import tr.com.agem.alfa.model.Tag;
import tr.com.agem.alfa.repository.AgentRepository;
import tr.com.agem.alfa.repository.AgentUserRepository;
import tr.com.agem.alfa.repository.TagRepository;

/**
 * @author <a href="mailto:emre.akkaya@agem.com.tr">Emre Akkaya</a>
 */
@Component
@Transactional(rollbackFor = Exception.class)
public class AgentService {

	@PersistenceContext
	private EntityManager em;

	private final AgentRepository agentRepository;
	private final TagRepository tagRepository;
	private final AgentUserRepository agentUserRepository;

	@Autowired
	public AgentService(AgentRepository agentRepository, TagRepository tagRepository,
			AgentUserRepository agentUserRepository) {
		this.agentRepository = agentRepository;
		this.tagRepository = tagRepository;
		this.agentUserRepository = agentUserRepository;
	}

	public Agent saveOrUpdate(Agent agent, boolean updateCrossTables) {
		Assert.notNull(agent, "Agent must not be null.");
		if (agent.getId() != null) {
			if (updateCrossTables) {
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
			return this.agentRepository
					.findByHostNameContainingOrIpAddressesContainingOrPlatformSystemContainingAllIgnoringCase(search,
							search, search, pageable);
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

	public void saveTags(Agent agent, List<Tag> tags) {
		Query query = this.em.createNativeQuery("DELETE FROM c_agent_tag_agent WHERE agent_id = :agentId");
		query.setParameter("agentId", agent.getId());
		query.executeUpdate();
		if (tags != null) {
			for (Tag tag : tags) {
				Tag _tag = this.tagRepository.findByName(tag.getName());
				Long id = _tag == null ? id = this.tagRepository.save(tag).getId() : _tag.getId();
				Query query2 = this.em.createNativeQuery(
						"INSERT INTO c_agent_tag_agent (agent_id, tag_id) VALUES (:agentId, :tagId)");
				query2.setParameter("agentId", agent.getId());
				query2.setParameter("tagId", id);
				query2.executeUpdate();
			}
		}
	}

	public AgentUser getAgentUser(String name) {
		return this.agentUserRepository.findByNameIgnoreCase(name);
	}

	@SuppressWarnings("unchecked")
	public Agent getAgentDetail(Long id, boolean fetchChildren) {
		Assert.notNull(id, "Agent ID must not be null.");
		Query query = em.createNativeQuery("SELECT TYPE, HOST_NAME, IP_ADDRESSES, MAC_ADDRESSES FROM c_agent WHERE ID = :id");
		query.setParameter("id", id);
		Agent agent = new Agent((Object[]) query.getSingleResult());
		em.detach(agent); // So that, it wont query children!
		if (fetchChildren && agent != null) {
			// Users
			// @formatter:off
			Query query2 = em.createNativeQuery(
					"SELECT au.NAME, au.GROUPS FROM c_agent_user au "
					+ "INNER JOIN c_agent_user_agent aua ON (au.id = aua.user_id AND aua.agent_id = :agentId)");
			// @formatter:on
			query2.setParameter("agentId", id);
			List<Object[]> users = query2.getResultList();
			for (Object[] user : users) {
				AgentUser aUser = new AgentUser();
				aUser.setName(user[0].toString());
				aUser.setCommaSeparatedGroups(user[1] != null ? user[1].toString() : null);
				agent.addUser(aUser);
			}
			// Packages
			// @formatter:off
			Query query3 = em.createNativeQuery(
					"SELECT ap.NAME, ap.VERSION FROM c_agent_package ap "
					+ "INNER JOIN c_agent_package_agent apa ON (ap.id = apa.installed_package_id AND apa.agent_id = :agentId)");
			// @formatter:on
			query3.setParameter("agentId", id);
			List<Object[]> packages = query3.getResultList();
			for (Object[] pkg : packages) {
				InstalledPackage aPackage = new InstalledPackage();
				aPackage.setName(pkg[0].toString());
				aPackage.setVersion(pkg[1] != null ? pkg[1].toString() : null);
				agent.addInstalledPackage(aPackage);
			}
		}
		return agent;
	}

	public Agent getAgentByMessagingIdOrMacAddresses(String messagingId, List<String> macAddresses) {
		Assert.notNull(messagingId, "Messaging ID must not be null.");
		Agent agent = this.agentRepository.getAgentByMessagingId(messagingId);
		if (agent == null) {
			// Cannot find by messaging ID, try to find by one of the MAC addresses...
			if (macAddresses != null) {
				for (String macAddr : macAddresses) {
					agent = this.agentRepository.getOneAgentByMacAddressesContainingIgnoreCase(macAddr);
					if (agent != null) {
						return agent;
					}
				}
			}
		}
		return agent;
	}

}

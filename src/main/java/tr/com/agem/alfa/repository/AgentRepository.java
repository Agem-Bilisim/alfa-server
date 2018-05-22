package tr.com.agem.alfa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tr.com.agem.alfa.model.Agent;

public interface AgentRepository extends JpaRepository<Agent, Long> {

	Agent getAgentByMessagingId(String messagingId);

}

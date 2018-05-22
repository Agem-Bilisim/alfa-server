package tr.com.agem.alfa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import tr.com.agem.alfa.model.Agent;

/**
 * @author <a href="mailto:emre.akkaya@agem.com.tr">Emre Akkaya</a>
 */
public interface AgentRepository extends JpaRepository<Agent, Long> {

	Agent getAgentByMessagingId(String messagingId);

	Page<Agent> findByHostNameContainingOrIpAddressesContainingAllIgnoringCase(String hostName, String ipAddresses,
			Pageable pageable);

}

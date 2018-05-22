package tr.com.agem.alfa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import tr.com.agem.alfa.model.Agent;
import tr.com.agem.alfa.model.AgentUser;

/**
 * @author <a href="mailto:emre.akkaya@agem.com.tr">Emre Akkaya</a>
 */
public interface AgentUserRepository extends JpaRepository<AgentUser, Long> {

	AgentUser findByNameIgnoreCase(String name);

	@Query("SELECT u from AgentUser u INNER JOIN u.agents a WHERE a.id = ?1 ORDER BY u.name DESC")
	Page<AgentUser> findByAgent(Agent agent, Pageable pageable);

	Page<AgentUser> findByCommaSeparatedGroupsContainingIgnoreCase(String commaSeparatedGroups, Pageable pageable);

}

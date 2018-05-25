package tr.com.agem.alfa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import tr.com.agem.alfa.model.AgentUser;

/**
 * @author <a href="mailto:emre.akkaya@agem.com.tr">Emre Akkaya</a>
 */
public interface AgentUserRepository extends JpaRepository<AgentUser, Long> {

	AgentUser findByNameIgnoreCase(String name);

	Page<AgentUser> findByCommaSeparatedGroupsContainingIgnoreCase(String commaSeparatedGroups, Pageable pageable);

}

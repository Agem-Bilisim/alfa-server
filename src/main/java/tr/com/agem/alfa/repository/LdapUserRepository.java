package tr.com.agem.alfa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import tr.com.agem.alfa.model.LdapUser;

/**
 * @author <a href="mailto:emre.akkaya@agem.com.tr">Emre Akkaya</a>
 */
public interface LdapUserRepository extends JpaRepository<LdapUser, Long> {

	@Query("SELECT u from LdapUser u INNER JOIN u.ldapIntegration i WHERE i.id = ?1 ORDER BY u.dn DESC")
	Page<LdapUser> findByIntegrationId(Long integrationId, Pageable pageable);

}

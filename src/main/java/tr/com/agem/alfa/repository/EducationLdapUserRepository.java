package tr.com.agem.alfa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import tr.com.agem.alfa.model.EducationLdapUser;

/**
 * @author <a href="mailto:emre.akkaya@agem.com.tr">Emre Akkaya</a>
 */
public interface EducationLdapUserRepository extends JpaRepository<EducationLdapUser, Long> {
	
	List<EducationLdapUser> findByEducationId(Long id);

}

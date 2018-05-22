package tr.com.agem.alfa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import tr.com.agem.alfa.model.Agent;
import tr.com.agem.alfa.model.InstalledPackage;

/**
 * @author <a href="mailto:emre.akkaya@agem.com.tr">Emre Akkaya</a>
 */
public interface PackageRepository extends JpaRepository<InstalledPackage, Long> {

	InstalledPackage findByNameAndVersionAllIgnoringCase(String name, String version);

	@Query("SELECT i from InstalledPackage i INNER JOIN i.agents a WHERE a.id = ?1 ORDER BY i.name, i.version DESC")
	Page<InstalledPackage> findByAgent(Agent agent, Pageable pageable);

	Page<InstalledPackage> findByNameContainingAndVersionContainingAllIgnoringCase(String name, String version,
			Pageable pageable);

}

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

	
	Page<InstalledPackage> findByNameContainingOrVersionContainingAllIgnoringCase(String name, String version,
			Pageable pageable);

	
	@Query("SELECT i from InstalledPackage i WHERE upper(i.name) LIKE concat('%',?2 ,'%') and " +
			"     ((?1 = 'E' and coalesce(i.compatible,'H') = 'E' and  not exists (select p.id from Problem p inner join p.references r " +
			"                    where coalesce(p.solved,0) = 0 and r.referenceType =7  and r.referenceId = i.id )) " +
			"		or  " +
			"       (?1 = 'H' and (coalesce(i.compatible,'H') = 'H' or exists (select p.id from Problem p inner join p.references r " +
			"                    where coalesce(p.solved,0) = 0 and r.referenceType =7  and r.referenceId = i.id ))))")
	Page<InstalledPackage> findByCompatibleAndNameContainingAllIgnoringCase(String compatible, String name, Pageable pageable);

	
	
	@Query("SELECT i from InstalledPackage i WHERE " +
			"     ((?1 = 'E' and coalesce(i.compatible,'H') = 'E' and  not exists (select p.id from Problem p inner join p.references r " +
			"                    where coalesce(p.solved,0) = 0 and r.referenceType =7  and r.referenceId = i.id )) " +
			"		or  " +
			"       (?1 = 'H' and (coalesce(i.compatible,'H') = 'H' or exists (select p.id from Problem p inner join p.references r " +
			"                    where coalesce(p.solved,0) = 0 and r.referenceType =7  and r.referenceId = i.id ))))")
	Page<InstalledPackage> findByCompatible(String compatible, Pageable pageable);

}

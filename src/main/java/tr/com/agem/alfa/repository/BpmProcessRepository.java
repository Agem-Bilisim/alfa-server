package tr.com.agem.alfa.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import tr.com.agem.alfa.model.bpm.BpmProcess;


/**
 * @author <a href="mailto:ali.ozeren@agem.com.tr">Ali Ozkan Ozeren</a>
 *
 */
public interface BpmProcessRepository extends JpaRepository<BpmProcess, Long> {

	BpmProcess findByNameAndVersionAllIgnoringCase(String name, String version);
	
	Page<BpmProcess> findByNameContainingAndVersionContainingAllIgnoringCase(String name, String version,
			Pageable pageable);

	@Query("SELECT p FROM BpmProcess p WHERE p.processDeploymentId is not null")
	List<BpmProcess> findAllDeployed();

}

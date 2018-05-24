package tr.com.agem.alfa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import tr.com.agem.alfa.model.bpm.BpmProcess;


/**
 * @author <a href="mailto:ali.ozeren@agem.com.tr">Ali Ozkan Ozeren</a>
 *
 */
public interface BpmProcessRepository extends JpaRepository<BpmProcess, Long> {

	BpmProcess findByNameAndVersionAllIgnoringCase(String name, String version);
	
	Page<BpmProcess> findByNameContainingAndVersionContainingAllIgnoringCase(String name, String version,
			Pageable pageable);

}

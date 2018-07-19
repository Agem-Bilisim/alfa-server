package tr.com.agem.alfa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import tr.com.agem.alfa.model.Disk;

/**
 * @author <a href="mailto:emre.akkaya@agem.com.tr">Emre Akkaya</a>
 */
public interface DiskRepository extends JpaRepository<Disk, Long> {

	Disk findByProductAndVersion(String product, String version);

	Page<Disk> findByDescriptionContainingAllIgnoringCase(String search, Pageable pageable);

}

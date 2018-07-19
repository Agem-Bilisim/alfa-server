package tr.com.agem.alfa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import tr.com.agem.alfa.model.Memory;

/**
 * @author <a href="mailto:emre.akkaya@agem.com.tr">Emre Akkaya</a>
 */
public interface MemoryRepository extends JpaRepository<Memory, Long> {

	Page<Memory> findByManufacturerContainingAllIgnoringCase(String search, Pageable pageable);

}

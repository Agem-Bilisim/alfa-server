package tr.com.agem.alfa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import tr.com.agem.alfa.model.Cpu;

/**
 * @author <a href="mailto:emre.akkaya@agem.com.tr">Emre Akkaya</a>
 */
public interface CpuRepository extends JpaRepository<Cpu, Long> {

	Page<Cpu> findByBrandContainingAllIgnoringCase(String brand, Pageable pageable);

}

package tr.com.agem.alfa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import tr.com.agem.alfa.model.Agent;
import tr.com.agem.alfa.model.Gpu;

/**
 * @author <a href="mailto:emre.akkaya@agem.com.tr">Emre Akkaya</a>
 */
public interface GpuRepository extends JpaRepository<Gpu, Long> {

	@Query("SELECT i from Gpu i INNER JOIN i.agents a WHERE a.id = ?1 ORDER BY i.kernel, i.memory DESC")
	Page<Gpu> findByAgent(Agent agent, Pageable pageable);

	Page<Gpu> findByKernelContainingOrSubsystemContainingOrMemoryAllIgnoringCase(String kernel, String subsystem,
			String memory, Pageable pageable);

}

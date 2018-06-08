package tr.com.agem.alfa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tr.com.agem.alfa.model.PeripheralDevice;

/**
 * @author <a href="mailto:emre.akkaya@agem.com.tr">Emre Akkaya</a>
 */
public interface PeripheralRepository extends JpaRepository<PeripheralDevice, Long> {

	PeripheralDevice findByTag(String tag);

}

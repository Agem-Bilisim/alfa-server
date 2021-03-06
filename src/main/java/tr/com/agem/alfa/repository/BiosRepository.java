package tr.com.agem.alfa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import tr.com.agem.alfa.model.Bios;

/**
 * @author <a href="mailto:emre.akkaya@agem.com.tr">Emre Akkaya</a>
 */
public interface BiosRepository extends JpaRepository<Bios, Long> {

	Bios findByVersionAndVendor(String version, String vendor);

	Page<Bios> findByVendorContainingAllIgnoringCase(String search, Pageable pageable);

}

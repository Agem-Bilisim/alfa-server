package tr.com.agem.alfa.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import tr.com.agem.alfa.model.Agent;
import tr.com.agem.alfa.model.InstalledPackage;

/**
 * @author <a href="mailto:emre.akkaya@agem.com.tr">Emre Akkaya</a>
 */
public interface SoftwareService {
	
	InstalledPackage getPackage(Long id);

	InstalledPackage getPackage(String name, String version);

	Page<InstalledPackage> getPackages(Agent agent, Pageable pageable);
	
	Page<InstalledPackage> getPackages(Pageable pageable, String search);

	void savePackage(InstalledPackage packageEntity);

	void deletePackage(Long id);

}

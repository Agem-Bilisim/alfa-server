package tr.com.agem.alfa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import tr.com.agem.alfa.model.Agent;
import tr.com.agem.alfa.model.InstalledPackage;
import tr.com.agem.alfa.repository.PackageRepository;

/**
 * @author <a href="mailto:emre.akkaya@agem.com.tr">Emre Akkaya</a>
 */
@Component("packageService")
@Transactional
public class PackageServiceImpl implements PackageService {

	private final PackageRepository packageRepository;

	@Autowired
	public PackageServiceImpl(PackageRepository packageRepository) {
		this.packageRepository = packageRepository;
	}

	@Override
	public InstalledPackage getPackage(Long id) {
		Assert.notNull(id, "ID must not be null");
		return this.packageRepository.findOne(id);
	}

	@Override
	public InstalledPackage getPackage(String name, String version) {
		Assert.notNull(name, "Name must not be null");
		Assert.notNull(version, "Version must not be null");
		return this.packageRepository.findByNameAndVersionAllIgnoringCase(name, version);
	}

	@Override
	public Page<InstalledPackage> getPackages(Agent agent, Pageable pageable) {
		Assert.notNull(agent, "Agent must not be null.");
		return this.packageRepository.findByAgent(agent, pageable);
	}

	@Override
	public Page<InstalledPackage> getPackages(Pageable pageable, String search) {
		Assert.notNull(pageable, "Pageable must not be null.");
		if (search != null && !search.isEmpty()) {
			return this.packageRepository.findByNameContainingAndVersionContainingAllIgnoringCase(search, search,
					pageable);
		}
		return this.packageRepository.findAll(pageable);
	}

	@Override
	public void save(InstalledPackage _package) {
		Assert.notNull(_package, "Package must not be null.");
		InstalledPackage p = null;
		if (_package.getId() != null && (p = packageRepository.findOne(_package.getId())) != null) {
			// Update
			p.setName(_package.getName());
			p.setVersion(_package.getVersion());
			this.packageRepository.save(p);
			return;
		}
		// Create
		this.packageRepository.save(_package);
	}

	@Override
	public void delete(Long id) {
		Assert.notNull(id, "ID must not be null.");
		this.packageRepository.delete(id);
	}

}

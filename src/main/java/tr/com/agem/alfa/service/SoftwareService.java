package tr.com.agem.alfa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import tr.com.agem.alfa.model.Agent;
import tr.com.agem.alfa.model.InstalledPackage;
import tr.com.agem.alfa.model.RunningProcess;
import tr.com.agem.alfa.repository.PackageRepository;
import tr.com.agem.alfa.repository.ProcessRepository;

/**
 * @author <a href="mailto:emre.akkaya@agem.com.tr">Emre Akkaya</a>
 */
@Component
@Transactional
public class SoftwareService {

	private final PackageRepository packageRepository;
	private final ProcessRepository processRepository;

	@Autowired
	public SoftwareService(PackageRepository packageRepository, ProcessRepository processRepository) {
		this.packageRepository = packageRepository;
		this.processRepository = processRepository;
	}

	public InstalledPackage getPackage(Long id) {
		Assert.notNull(id, "ID must not be null");
		return this.packageRepository.findOne(id);
	}

	public InstalledPackage getPackage(String name, String version) {
		Assert.notNull(name, "Name must not be null");
		Assert.notNull(version, "Version must not be null");
		return this.packageRepository.findByNameAndVersionAllIgnoringCase(name, version);
	}

	public Page<InstalledPackage> getPackages(Agent agent, Pageable pageable) {
		Assert.notNull(agent, "Agent must not be null.");
		return this.packageRepository.findByAgent(agent, pageable);
	}

	public Page<InstalledPackage> getPackages(Pageable pageable, String search) {
		Assert.notNull(pageable, "Pageable must not be null.");
		if (search != null && !search.isEmpty()) {
			return this.packageRepository.findByNameContainingOrVersionContainingAllIgnoringCase(search, search,
					pageable);
		}
		return this.packageRepository.findAll(pageable);
	}

	public void savePackage(InstalledPackage _package) {
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

	public void deletePackage(Long id) {
		Assert.notNull(id, "ID must not be null.");
		this.packageRepository.delete(id);
	}

	public void saveProcess(RunningProcess process) {
		Assert.notNull(process, "Process must not be null.");
		RunningProcess p = null;
		if (process.getId() != null && (p = processRepository.findOne(process.getId())) != null) {
			// Update
			p.setName(process.getName());
			this.processRepository.save(p);
			return;
		}
		// Create
		this.processRepository.save(process);
	}

	public RunningProcess getProcess(Long id) {
		Assert.notNull(id, "ID must not be null");
		return this.processRepository.findOne(id);
	}

	public Page<RunningProcess> getProcesses(Pageable pageable, String search) {
		Assert.notNull(pageable, "Pageable must not be null.");
		if (search != null && !search.isEmpty()) {
			return this.processRepository.findByNameContainingIgnoreCase(search, pageable);
		}
		return this.processRepository.findAll(pageable);
	}

	public void deleteProcess(Long id) {
		Assert.notNull(id, "ID must not be null.");
		this.processRepository.delete(id);
	}

	public List<InstalledPackage> getPackages() {
		return this.packageRepository.findAll();
	}

	public List<RunningProcess> getProcesses() {
		return this.processRepository.findAll();
	}

	public List<InstalledPackage> getSurveyablePackages() {
		InstalledPackage p = new InstalledPackage();
		p.setShowInSurvey(true);
		return this.packageRepository.findAll(Example.of(p));
	}

	public RunningProcess getProcess(String name) {
		return this.processRepository.findByNameIgnoreCase(name);
	}

}

package tr.com.agem.alfa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import tr.com.agem.alfa.model.bpm.BpmProcess;
import tr.com.agem.alfa.repository.BpmProcessRepository;


/**
 * @author <a href="mailto:ali.ozeren@agem.com.tr">Ali Ozkan Ozeren</a>
 *
 */
@Component("bpmProcessService")
@Transactional
public class BpmProcessServiceImpl implements BpmProcessService {

	private final BpmProcessRepository bpmProcessRepository;

	@Autowired
	public BpmProcessServiceImpl(BpmProcessRepository packageRepository) {
		this.bpmProcessRepository = packageRepository;
	}

	@Override
	public BpmProcess getBpmProcess(Long id) {
		Assert.notNull(id, "ID must not be null");
		return this.bpmProcessRepository.findOne(id);
	}

	@Override
	public BpmProcess getBpmProcess(String name, String version) {
		Assert.notNull(name, "Name must not be null");
		Assert.notNull(version, "Version must not be null");
		return this.bpmProcessRepository.findByNameAndVersionAllIgnoringCase(name, version);
	}

	@Override
	public Page<BpmProcess> getBpmProcesses(Pageable pageable, String search) {
		Assert.notNull(pageable, "Pageable must not be null.");
		if (search != null && !search.isEmpty()) {
			return this.bpmProcessRepository.findByNameContainingAndVersionContainingAllIgnoringCase(search, search, pageable);
		}
		return this.bpmProcessRepository.findAll(pageable);
	}

	@Override
	public void save(BpmProcess _bpmProcess) {
		Assert.notNull(_bpmProcess, "BpmProcess must not be null.");
		BpmProcess p = null;
		if (_bpmProcess.getId() != null && (p = bpmProcessRepository.findOne(_bpmProcess.getId())) != null) {
			// Update
			p.setName(_bpmProcess.getName());
			p.setVersion(_bpmProcess.getVersion());
			p.setProcessDeploymentId(_bpmProcess.getProcessDeploymentId());
			this.bpmProcessRepository.save(p);
			return;
		}
		// Create
		this.bpmProcessRepository.save(_bpmProcess);
	}

	@Override
	public void delete(Long id) {
		Assert.notNull(id, "ID must not be null.");
		this.bpmProcessRepository.delete(id);
	}

}

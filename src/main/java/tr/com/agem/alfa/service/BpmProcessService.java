package tr.com.agem.alfa.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import tr.com.agem.alfa.model.bpm.BpmProcess;

public interface BpmProcessService {
	
	BpmProcess getBpmProcess(Long id);

	BpmProcess getBpmProcess(String name, String version);

	Page<BpmProcess> getBpmProcesses(Pageable pageable, String search);
	
	void save(BpmProcess bpmProcessEntity);

	void delete(Long id);

}

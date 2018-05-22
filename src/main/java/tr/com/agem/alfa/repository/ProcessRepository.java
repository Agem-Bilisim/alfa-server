package tr.com.agem.alfa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import tr.com.agem.alfa.model.Agent;
import tr.com.agem.alfa.model.RunningProcess;

public interface ProcessRepository extends JpaRepository<RunningProcess, Long> {

	RunningProcess findByNameIgnoreCase(String name);
	
	@Query("SELECT r from RunningProcess r INNER JOIN r.agentRunningProcesses a WHERE a.id = ?1 ORDER BY r.name DESC")
	Page<RunningProcess> findByAgent(Agent agent, Pageable pageable);
	
	Page<RunningProcess> findByNameContainingIgnoreCase(String name, Pageable pageable);
	
}

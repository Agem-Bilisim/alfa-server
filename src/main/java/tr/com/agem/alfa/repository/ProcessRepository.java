package tr.com.agem.alfa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tr.com.agem.alfa.model.RunningProcess;

public interface ProcessRepository extends JpaRepository<RunningProcess, Long> {

}

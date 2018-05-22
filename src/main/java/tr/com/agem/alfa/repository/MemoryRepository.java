package tr.com.agem.alfa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tr.com.agem.alfa.model.Memory;

public interface MemoryRepository extends JpaRepository<Memory, Long> {

}

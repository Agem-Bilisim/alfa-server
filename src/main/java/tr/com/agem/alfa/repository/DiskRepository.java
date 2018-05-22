package tr.com.agem.alfa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tr.com.agem.alfa.model.Disk;

public interface DiskRepository extends JpaRepository<Disk, Long> {

}

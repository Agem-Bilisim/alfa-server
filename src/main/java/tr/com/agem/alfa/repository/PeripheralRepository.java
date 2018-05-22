package tr.com.agem.alfa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tr.com.agem.alfa.model.PeripheralDevice;

public interface PeripheralRepository extends JpaRepository<PeripheralDevice, Long> {

}

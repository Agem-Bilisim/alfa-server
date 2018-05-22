package tr.com.agem.alfa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tr.com.agem.alfa.model.NetworkInterface;

public interface InetRepository extends JpaRepository<NetworkInterface, Long> {

}

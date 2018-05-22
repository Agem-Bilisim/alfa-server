package tr.com.agem.alfa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tr.com.agem.alfa.model.NetworkInterface;

/**
 * @author <a href="mailto:emre.akkaya@agem.com.tr">Emre Akkaya</a>
 */
public interface InetRepository extends JpaRepository<NetworkInterface, Long> {

}
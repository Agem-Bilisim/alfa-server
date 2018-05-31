package tr.com.agem.alfa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import tr.com.agem.alfa.model.SysRole;

/**
 * @author <a href="mailto:emre.akkaya@agem.com.tr">Emre Akkaya</a>
 */
public interface SysRoleRepository extends JpaRepository<SysRole, Long> {
	
	List<SysRole> findByNameContainingAllIgnoringCase(String search);

}

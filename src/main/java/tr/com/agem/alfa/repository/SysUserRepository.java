package tr.com.agem.alfa.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import tr.com.agem.alfa.model.SysUser;

/**
 * @author <a href="mailto:emre.akkaya@agem.com.tr">Emre Akkaya</a>
 */
public interface SysUserRepository extends JpaRepository<SysUser, Long> {

	Page<SysUser> findByUserNameContainingOrEmailContainingAllIgnoringCase(String search, Pageable pageable);

	SysUser findByEmail(String email);

	SysUser findByUserName(String userName);
	
	@Query("SELECT u FROM c_user u JOIN c_role r ON (r.id = u.role_id) WHERE r.name LIKE CONCAT('%',:rolename,'%')")
	List<SysUser> findByRoleName(@Param("rolename") String roleName);

}

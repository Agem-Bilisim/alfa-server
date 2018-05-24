package tr.com.agem.alfa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import tr.com.agem.alfa.model.SysUser;

/**
 * @author <a href="mailto:emre.akkaya@agem.com.tr">Emre Akkaya</a>
 */
public interface SysUserRepository extends JpaRepository<SysUser, Long> {

	SysUser findByUserNameOrEmail(String userName, String email);

	Page<SysUser> findByUserNameContainingOrEmailContainingAllIgnoringCase(String search, Pageable pageable);

}

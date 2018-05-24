package tr.com.agem.alfa.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import tr.com.agem.alfa.model.SysRole;
import tr.com.agem.alfa.model.SysUser;

public interface SysUserService {

	SysUser getUserByEmail(String email);

	List<SysRole> getRoles();

	SysUser getUser(Long id);

	void saveUser(SysUser userEntity);

	Page<SysUser> getUsers(Pageable pageable, String search);

	SysUser getUserByUserName(String userName);

}

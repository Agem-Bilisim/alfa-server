package tr.com.agem.alfa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import tr.com.agem.alfa.model.SysRole;
import tr.com.agem.alfa.model.SysUser;
import tr.com.agem.alfa.repository.SysRoleRepository;
import tr.com.agem.alfa.repository.SysUserRepository;

@Component("sysUserService")
@Transactional
public class SysUserServiceImpl implements SysUserService {

	private final SysUserRepository sysUserRepository;
	private final SysRoleRepository sysRoleRepository;

	@Autowired
	public SysUserServiceImpl(SysUserRepository userRepository, SysRoleRepository sysRoleRepository) {
		this.sysUserRepository = userRepository;
		this.sysRoleRepository = sysRoleRepository;
	}

	@Override
	public SysUser getUserByEmail(String email) {
		Assert.notNull(email, "Email must not be null");
		return this.sysUserRepository.findByUserNameOrEmail(null, email);
	}

	@Override
	public SysUser getUserByUserName(String userName) {
		Assert.notNull(userName, "Username must not be null");
		return this.sysUserRepository.findByUserNameOrEmail(userName, null);
	}

	@Override
	public SysUser getUser(Long id) {
		Assert.notNull(id, "ID must not be null");
		return this.sysUserRepository.findOne(id);
	}

	@Override
	public Page<SysUser> getUsers(Pageable pageable, String search) {
		Assert.notNull(pageable, "Pageable must not be null.");
		if (search != null && !search.isEmpty()) {
			return this.sysUserRepository.findByUserNameContainingOrEmailContainingAllIgnoringCase(search, pageable);
		}
		return this.sysUserRepository.findAll(pageable);
	}

	@Override
	public List<SysRole> getRoles() {
		return this.sysRoleRepository.findAll();
	}

	@Override
	public void saveUser(SysUser user) {
		Assert.notNull(user, "User must not be null.");
		SysUser u = null;
		if (user.getId() != null && (u = sysUserRepository.findOne(user.getId())) != null) {
			// Update
			u.setFirstName(user.getFirstName());
			u.setLastName(user.getLastName());
			u.setPasswordHash(user.getPasswordHash());
			u.setDisabled(user.getDisabled());
			u.setRole(user.getRole());
			this.sysUserRepository.save(u);
			return;
		}
		// Create
		this.sysUserRepository.save(user);
	}

}

package tr.com.agem.alfa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import tr.com.agem.alfa.model.User;
import tr.com.agem.alfa.repository.UserRepository;

@Component("userService")
@Transactional
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;

	@Autowired
	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public User findByUserNameOrEmail(String username) {
		Assert.notNull(username, "Username must not be null.");
		return this.userRepository.findByUserNameOrEmail(username, username);
	}

}

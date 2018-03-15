package tr.com.agem.alfa.service;

import tr.com.agem.alfa.model.User;

public interface UserService {

	User findByUserNameOrEmail(String username);

}

package tr.com.agem.alfa.repository;

import org.springframework.data.repository.CrudRepository;

import tr.com.agem.alfa.model.User;

public interface UserRepository extends CrudRepository<User, Long> {

	User findByUserNameOrEmail(String username, String email);

}

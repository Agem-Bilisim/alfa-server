package tr.com.agem.alfa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tr.com.agem.alfa.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByUserNameOrEmail(String username, String email);

}

package tr.com.agem.alfa.model;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

/**
 * 
 * @author <a href="mailto:emre.akkaya@agem.com.tr">Emre Akkaya</a>
 *
 */
public class CurrentUser extends org.springframework.security.core.userdetails.User {

	private static final long serialVersionUID = -4536767597668835359L;

	private User user;

	public CurrentUser(User user, Collection<? extends GrantedAuthority> authorities) {
		super(user.getUserName(), user.getPasswordHash(), true, true, true, true, authorities);
		this.user = user;
	}

	public Long getId() {
		return user.getId();
	}

	public String getRole() {
		return user.getRole().getName();
	}
	
	public String getUsername(){
		return user.getUserName();
	}

	@Override
	public String toString() {
		return "CurrentUser [user=" + user + "] " + super.toString();
	}

}

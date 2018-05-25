package tr.com.agem.alfa.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import tr.com.agem.alfa.model.LdapIntegration;
import tr.com.agem.alfa.model.LdapUser;

/**
 * @author <a href="mailto:emre.akkaya@agem.com.tr">Emre Akkaya</a>
 */
public interface LdapService {

	LdapUser getUser(Long userId);

	Page<LdapUser> getUsers(Pageable pageable, Long integrationId, String search);

	List<LdapIntegration> getIntegrations();

	void save(LdapUser user);

	LdapIntegration getIntegration(Long id);

	Page<LdapIntegration> getIntegrations(Pageable pageable, String search);

	void save(LdapIntegration integration);

}

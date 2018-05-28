package tr.com.agem.alfa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import tr.com.agem.alfa.model.LdapIntegration;
import tr.com.agem.alfa.model.LdapUser;
import tr.com.agem.alfa.repository.LdapIntegrationRepository;
import tr.com.agem.alfa.repository.LdapUserRepository;

/**
 * @author <a href="mailto:emre.akkaya@agem.com.tr">Emre Akkaya</a>
 */
@Component("ldapService")
@Transactional
public class LdapServiceImpl implements LdapService {

	private final LdapIntegrationRepository ldapIntegrationRepository;
	private final LdapUserRepository ldapUserRepository;

	@Autowired
	public LdapServiceImpl(LdapIntegrationRepository ldapIntegrationRepository, LdapUserRepository ldapUserRepository) {
		this.ldapIntegrationRepository = ldapIntegrationRepository;
		this.ldapUserRepository = ldapUserRepository;
	}

	@Override
	public LdapUser getUser(Long id) {
		Assert.notNull(id, "ID must not be null");
		return this.ldapUserRepository.findOne(id);
	}

	@Override
	public Page<LdapUser> getUsers(Pageable pageable, Long integrationId, String search) {
		Assert.notNull(pageable, "Pageable must not be null.");
		Assert.notNull(integrationId, "Integration must not be null.");
		// TODO search?
		return this.ldapUserRepository.findByIntegrationId(integrationId, pageable);
	}

	@Override
	public List<LdapIntegration> getIntegrations() {
		return this.ldapIntegrationRepository.findAll();
	}

	@Override
	public void save(LdapUser user) {
		Assert.notNull(user, "User must not be null.");
		LdapUser u = null;
		if (user.getId() != null && (u = ldapUserRepository.findOne(user.getId())) != null) {
			// Update
			u.setDn(user.getDn());
			u.setName(user.getName());
			u.setUserIdentifier(user.getUserIdentifier());
			this.ldapUserRepository.save(u);
			return;
		}
		// Create
		this.ldapUserRepository.save(user);
	}

	@Override
	public LdapIntegration getIntegration(Long id) {
		Assert.notNull(id, "ID must not be null");
		return this.ldapIntegrationRepository.findOne(id);
	}

	@Override
	public Page<LdapIntegration> getIntegrations(Pageable pageable, String search) {
		Assert.notNull(pageable, "Pageable must not be null.");
		if (search != null && !search.isEmpty()) {
			return this.ldapIntegrationRepository.findByIpAddressContainingIgnoreCase(search, pageable);
		}
		return this.ldapIntegrationRepository.findAll(pageable);
	}

	@Override
	public void save(LdapIntegration integration) {
		Assert.notNull(integration, "Integration must not be null.");
		LdapIntegration i = null;
		if (integration.getId() != null && (i = ldapIntegrationRepository.findOne(integration.getId())) != null) {
			// Update
			this.ldapIntegrationRepository.save(i);
			return;
		}
		// Create
		this.ldapIntegrationRepository.save(integration);
	}

	@Override
	public void deleteUsers(Long id) {
		Assert.notNull(id, "ID must not be null");
		this.ldapUserRepository.deleteByLdapIntegrationId(id);
	}

}

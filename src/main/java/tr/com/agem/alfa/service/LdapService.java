package tr.com.agem.alfa.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

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
@Component
@Transactional
public class LdapService {
	
	@PersistenceContext
	private EntityManager em;

	private final LdapIntegrationRepository ldapIntegrationRepository;
	private final LdapUserRepository ldapUserRepository;

	@Autowired
	public LdapService(LdapIntegrationRepository ldapIntegrationRepository, LdapUserRepository ldapUserRepository) {
		this.ldapIntegrationRepository = ldapIntegrationRepository;
		this.ldapUserRepository = ldapUserRepository;
	}

	public LdapUser getUser(Long id) {
		Assert.notNull(id, "ID must not be null");
		return this.ldapUserRepository.findOne(id);
	}

	public Page<LdapUser> getUsers(Pageable pageable, Long integrationId, String search) {
		Assert.notNull(pageable, "Pageable must not be null.");
		Assert.notNull(integrationId, "Integration must not be null.");
		// TODO search?
		return this.ldapUserRepository.findByIntegrationId(integrationId, pageable);
	}

	public List<LdapIntegration> getIntegrations() {
		return this.ldapIntegrationRepository.findAll();
	}

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

	public LdapIntegration getIntegration(Long id) {
		Assert.notNull(id, "ID must not be null");
		return this.ldapIntegrationRepository.findOne(id);
	}

	public Page<LdapIntegration> getIntegrations(Pageable pageable, String search) {
		Assert.notNull(pageable, "Pageable must not be null.");
		if (search != null && !search.isEmpty()) {
			return this.ldapIntegrationRepository.findByIpAddressContainingIgnoreCase(search, pageable);
		}
		return this.ldapIntegrationRepository.findAll(pageable);
	}

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

	public void deleteUsers(Long id) {
		Assert.notNull(id, "ID must not be null");
		this.ldapUserRepository.deleteByLdapIntegrationId(id);
	}

	// TODO use HQL equivalent of 'sql' to prevent second query (findOne)
	// also do not use id! we should just use lmsUserId
	public LdapUser getUserByLmsIdOrEmail(Long lmsUserId, String email) {
		Assert.notNull(lmsUserId, "ID must not be null");
		String sql = "select u.id\n" + 
				"from c_ldap_user u left outer join c_ldap_user_attribute a\n" + 
				"    on (u.id = a.ldap_user_id and a.name in ('userPrincipalName', 'email', 'mail'))\n" + 
				"where u.id = :lmsUserId or u.lms_user_id = :lmsUserId or a.value = :email\n" + 
				"limit 0,1";
		Query query = em.createNativeQuery(sql);
		query.setParameter("lmsUserId", lmsUserId);
		query.setParameter("email", email);
		Object result = query.getSingleResult();
		return result == null ? null : this.ldapUserRepository.findOne(new Long(result.toString()));
	}

}

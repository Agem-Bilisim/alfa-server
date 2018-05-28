package tr.com.agem.alfa.ldap;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Strings.isNullOrEmpty;

import java.security.GeneralSecurityException;
import java.util.Date;
import java.util.List;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.DirContext;
import javax.naming.directory.SearchControls;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ldap.core.ContextMapper;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import tr.com.agem.alfa.ldap.util.LdapUtils;
import tr.com.agem.alfa.model.LdapIntegration;
import tr.com.agem.alfa.model.LdapUser;
import tr.com.agem.alfa.model.LdapUserAttribute;
import tr.com.agem.alfa.security.EncryptionService;
import tr.com.agem.alfa.service.LdapService;
import tr.com.agem.alfa.util.CommonUtils;

/**
 * @author <a href="mailto:emre.akkaya@agem.com.tr">Emre Akkaya</a>
 */
@Service
public class LdapSyncService {

	private static final Logger log = LoggerFactory.getLogger(LdapSyncService.class);

	private final LdapService ldapService;
	private final EncryptionService encryptionService;

	public LdapSyncService(LdapService ldapService, EncryptionService encryptionService) {
		this.ldapService = ldapService;
		this.encryptionService = encryptionService;
	}

	@Scheduled(cron = "${sys.ldap.sync-cron}")
	public void sync() {
		log.debug("Checking LDAP integrations...");
		List<LdapIntegration> integrations = ldapService.getIntegrations();
		boolean hasIntegrations = integrations != null && !integrations.isEmpty();
		if (!hasIntegrations) return;
		log.debug("Found {} LDAP integrations. Synchronizing...", hasIntegrations ? integrations.size() : 0);
		for (final LdapIntegration intgr : integrations) {
			try {
				doSync(intgr, "SYSTEM");
			} catch (GeneralSecurityException e) {
				log.error(e.getMessage(), e);
			} catch (NamingException e) {
				log.error(e.getMessage(), e);
			}
		}
	}

	/**
	 * @param intgr
	 * @param string
	 * @throws GeneralSecurityException 
	 * @throws NamingException 
	 */
	public void doSync(final LdapIntegration intgr, String principal) throws GeneralSecurityException, NamingException {
		LdapTemplate ldapTemplate = LdapUtils.initLdapTemplate(intgr, encryptionService);

		// Build filter string and determine search context!
		String filter = !isNullOrEmpty(intgr.getSearchFilter()) ? intgr.getSearchFilter()
				: toSearchFilter(intgr.getUserDnPattern(), intgr.getUserIdentifierAttribute());
		String searchContext = !isNullOrEmpty(intgr.getUserDnPattern()) ? toSearchContext(intgr.getUserDnPattern())
				: (isNullOrEmpty(intgr.getSearchContexts()) ? getBaseDN(ldapTemplate)
						: intgr.getSearchContexts());

		Date now = new Date();
		List<LdapUser> users = ldapTemplate.search(searchContext, filter, SearchControls.SUBTREE_SCOPE, null,
				new ContextMapper<LdapUser>() {
					@Override
					public LdapUser mapFromContext(Object ctx) throws NamingException {
						DirContextAdapter context = (DirContextAdapter) ctx;
						// User
						LdapUser user = new LdapUser();
						user.setDn(context.getDn().toString());
						user.setUserIdentifier(context.getStringAttribute(intgr.getUserIdentifierAttribute()));
						user.setName(context.getStringAttribute("cn"));
						user.setLdapIntegration(intgr);
						user.setCreatedDate(now);
						user.setCreatedBy(principal);
						user.setLastModifiedDate(now);
						user.setLastModifiedBy(principal);
						// Attributes
						NamingEnumeration<? extends Attribute> attrs = context.getAttributes().getAll();
						while (attrs.hasMore()) {
							Attribute attr = attrs.next();
							LdapUserAttribute uAttr = new LdapUserAttribute();
							uAttr.setName(attr.getID());
							uAttr.setValue(attr.get().toString());
							uAttr.setCreatedDate(now);
							uAttr.setCreatedBy(principal);
							uAttr.setLastModifiedDate(now);
							uAttr.setLastModifiedBy(principal);
							user.addAttribute(uAttr);
						}
						return user;
					}
				});

		if (users != null) {
			log.info("Found user in the LDAP server: {}", intgr.getIpAddress());
			ldapService.deleteUsers(intgr.getId());
			for (LdapUser user : users) {
				try {
					log.debug("Saving LDAP user with identifier: {}", user.getUserIdentifier());
					ldapService.save(user);
				} catch(Exception e) {
				}
			}
		}
	}

	/**
	 * @param dnPattern
	 * @return
	 */
	private String toSearchContext(String dnPattern) {
		checkArgument(dnPattern != null, "DN pattern cannot be null.");
		return dnPattern.substring(dnPattern.indexOf(",") + 1);
	}

	/**
	 * @param dnPattern
	 * @param uidAttributeFromIntegration
	 * @return
	 */
	private String toSearchFilter(String dnPattern, String uidAttributeFromIntegration) {
		checkArgument(dnPattern != null, "DN pattern cannot be null.");
		checkArgument(uidAttributeFromIntegration != null, "UID attribute cannot be null.");
		String uidAttributeFromDn = dnPattern.split(",")[0].split("=")[0];
		return String.format("(&(objectClass=*)(%s=*)%s)", uidAttributeFromDn,
				!uidAttributeFromDn.equalsIgnoreCase(uidAttributeFromIntegration)
						? String.format("(%s=*)", uidAttributeFromIntegration)
						: "");
	}

	/**
	 * @param ldapTemplate
	 * @return
	 * @throws NamingException
	 */
	private String getBaseDN(LdapTemplate ldapTemplate) throws NamingException {
		DirContext context = ldapTemplate.getContextSource().getReadWriteContext();
		NamingEnumeration<? extends Attribute> attrs = context.getAttributes("", new String[] { "namingContext" })
				.getAll();
		String baseDn = "";
		while (attrs.hasMore()) {
			Attribute attr = attrs.next();
			baseDn = CommonUtils.join(CommonUtils.COMMA_DELIM, baseDn, attr.get().toString());
		}
		return baseDn;
	}

}

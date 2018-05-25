package tr.com.agem.alfa.ldap.util;

import static com.google.common.base.Strings.isNullOrEmpty;

import java.security.GeneralSecurityException;

import javax.naming.NamingException;
import javax.naming.directory.DirContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.DefaultDirObjectFactory;
import org.springframework.ldap.core.support.LdapContextSource;

import tr.com.agem.alfa.model.LdapIntegration;
import tr.com.agem.alfa.model.enums.LdapEncryptionType;
import tr.com.agem.alfa.security.EncryptionService;

/**
 * @author <a href="mailto:emre.akkaya@agem.com.tr">Emre Akkaya</a>
 */
public class LdapUtils {

	private static final Logger log = LoggerFactory.getLogger(LdapUtils.class);

	/**
	 * @param config
	 * @param encryptionService
	 * @return
	 */
	public static boolean testConnection(LdapIntegration config, EncryptionService encryptionService) {
		DirContext context = null;
		try {
			LdapTemplate template = initLdapTemplate(config, encryptionService);
			context = template.getContextSource().getReadWriteContext();
			return context != null;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			try {
				if (context != null) {
					context.close();
				}
			} catch (NamingException e) {
			}
		}
		return false;
	}

	/**
	 * This is basically opens the 'connection' to the LDAP server on which further
	 * operations can be performed.
	 * 
	 * @param config
	 * @param encryptionService
	 * @return
	 * @throws GeneralSecurityException
	 */
	public static LdapTemplate initLdapTemplate(LdapIntegration config, EncryptionService encryptionService)
			throws GeneralSecurityException {
		LdapContextSource ctx = new LdapContextSource();

		ctx.setUrl(buildUrl(config));
		ctx.setDirObjectFactory(DefaultDirObjectFactory.class);
		ctx.setPooled(true);
		String password = null;
		if (!isNullOrEmpty(config.getSearchDn())) {
			password = encryptionService.decode(config.getEncryptedSearchPassword());
			ctx.setUserDn(config.getSearchDn());
			ctx.setPassword(password);
		}
		ctx.afterPropertiesSet();

		LdapTemplate template = new LdapTemplate(ctx);
		log.info("LDAP template initialized.", template.toString());
		return template;
	}

	private static String buildUrl(LdapIntegration config) {
		StringBuilder url = new StringBuilder();
		url.append(config.getEncryptionType() == LdapEncryptionType.NONE ? "ldap://" : "ldaps://");
		url.append(config.getIpAddress()).append(":").append(config.getPort()).append("/");
		return url.toString();
	}

}

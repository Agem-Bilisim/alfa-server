package tr.com.agem.alfa.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import tr.com.agem.alfa.model.enums.LdapEncryptionType;
import tr.com.agem.alfa.model.enums.LdapType;

/**
 * LDAP/AD integration model for storing integration/connection related
 * configurations.
 * 
 * @author <a href="mailto:emre.akkaya@agem.com.tr">Emre Akkaya</a>
 */
@Entity
@Table(name = "c_ldap_integration", uniqueConstraints = { @UniqueConstraint(columnNames = { "IP_ADDRESS", "PORT" }) })
public class LdapIntegration extends BaseModel {

	private static final long serialVersionUID = -5425600069166674484L;

	@Column(name = "IP_ADDRESS", nullable = false)
	private String ipAddress;

	@Column(name = "PORT", nullable = false)
	private Integer port;

	@Column(name = "LDAP_TYPE", nullable = false)
	private Integer ldapType;

	/**
	 * Number of seconds to wait for the server to respond.
	 */
	@Column(name = "TIMEOUT", nullable = false)
	private Integer timeout;

	@Column(name = "ENCRYPTION_TYPE", nullable = false)
	private Integer encryptionType;

	/**
	 * Whether or not to validate the server's certificate when connecting.
	 */
	@Column(name = "VALIDATE_SERVER_CERT")
	private Boolean validateServerCert;

	/**
	 * File containing the LDAP server public certificate. If this is left blank and
	 * validate server certificate is selected, the server's certificate is verified
	 * against the local certificate store.
	 */
	@Column(name = "SSL_CERT_FILE_PATH")
	private String sslCertFilePath;

	/**
	 * Credentials to use when searching for users. Leave field blank for anonymous
	 * search.
	 */
	@Column(name = "SEARCH_DN")
	private String searchDn;

	/**
	 * Credentials to use when searching for users. Leave field blank for anonymous
	 * search.
	 */
	@Column(name = "ENCRYPTED_SEARCH_PASSWORD")
	private String encryptedSearchPassword;

	/**
	 * The pattern to use when creating a DN from a user name. %u can be used to
	 * replace username. Default: cn=%u,dc=agem,dc=com,dc=tr
	 */
	@Column(name = "USER_DN_PATTERN")
	private String userDnPattern;

	/**
	 * Whether or not to search a set of LDAP trees for the DN rather than using the
	 * user DN pattern.
	 */
	@Column(name = "SEARCH_FOR_DN")
	private Boolean searchForDn;

	/**
	 * The filter to use when searching. %u is replaced by username.
	 */
	@Column(name = "SEARCH_FILTER")
	private String searchFilter;

	/**
	 * A comma separated list of one or more DN(s) that indicates roots of LDAP
	 * subtrees that will be searched.
	 */
	@Column(name = "SEARCH_CONTEXTS")
	private String searchContexts;

	/**
	 * When selected, empty passwords are used to bind attempts. Otherwise an empty
	 * password causes authentication to immediately fail.
	 */
	@Column(name = "ALLOW_EMPTY_PASSWORDS")
	private Boolean allowEmptyPasswords;

	/**
	 * If agent machines uses an LDAP authentication to authenticate/authorize users
	 * before login, then there should also be an LDAP attribute to match these
	 * users to their LDAP entries (which is usually 'uid' or 'tckno'). This is also
	 * used to handle recurrent C_LDAP_USER records where multiple LDAP integrations
	 * may provide same users.
	 */
	@Column(name = "USER_IDENTIFIER_ATTRIBUTE")
	private String userIdentifierAttribute;

	@OneToMany(mappedBy = "ldapIntegration", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<LdapUser> users = new HashSet<LdapUser>(0);

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	@Transient
	public LdapType getLdapType() {
		return LdapType.getType(ldapType);
	}

	@Transient
	public void setLdapType(LdapType ldapType) {
		if (ldapType == null) {
			this.ldapType = null;
		} else {
			this.ldapType = ldapType.getId();
		}
	}

	public Integer getTimeout() {
		return timeout;
	}

	public void setTimeout(Integer timeout) {
		this.timeout = timeout;
	}

	@Transient
	public LdapEncryptionType getEncryptionType() {
		return LdapEncryptionType.getType(encryptionType);
	}

	@Transient
	public void setEncryptionType(LdapEncryptionType encryptionType) {
		if (encryptionType == null) {
			this.encryptionType = null;
		} else {
			this.encryptionType = encryptionType.getId();
		}
	}

	public Boolean getValidateServerCert() {
		return validateServerCert;
	}

	public void setValidateServerCert(Boolean validateServerCert) {
		this.validateServerCert = validateServerCert;
	}

	public String getSslCertFilePath() {
		return sslCertFilePath;
	}

	public void setSslCertFilePath(String sslCertFilePath) {
		this.sslCertFilePath = sslCertFilePath;
	}

	public String getSearchDn() {
		return searchDn;
	}

	public void setSearchDn(String searchDn) {
		this.searchDn = searchDn;
	}

	public String getEncryptedSearchPassword() {
		return encryptedSearchPassword;
	}

	public void setEncryptedSearchPassword(String encryptedSearchPassword) {
		this.encryptedSearchPassword = encryptedSearchPassword;
	}

	public String getUserDnPattern() {
		return userDnPattern;
	}

	public void setUserDnPattern(String userDnPattern) {
		this.userDnPattern = userDnPattern;
	}

	public Boolean getSearchForDn() {
		return searchForDn;
	}

	public void setSearchForDn(Boolean searchForDn) {
		this.searchForDn = searchForDn;
	}

	public String getSearchFilter() {
		return searchFilter;
	}

	public void setSearchFilter(String searchFilter) {
		this.searchFilter = searchFilter;
	}

	public String getSearchContexts() {
		return searchContexts;
	}

	public void setSearchContexts(String searchContexts) {
		this.searchContexts = searchContexts;
	}

	public Boolean getAllowEmptyPasswords() {
		return allowEmptyPasswords;
	}

	public void setAllowEmptyPasswords(Boolean allowEmptyPasswords) {
		this.allowEmptyPasswords = allowEmptyPasswords;
	}

	public String getUserIdentifierAttribute() {
		return userIdentifierAttribute;
	}

	public void setUserIdentifierAttribute(String userIdentifierAttribute) {
		this.userIdentifierAttribute = userIdentifierAttribute;
	}

	public void setLdapType(Integer ldapType) {
		this.ldapType = ldapType;
	}

	public void setEncryptionType(Integer encryptionType) {
		this.encryptionType = encryptionType;
	}

	public Set<LdapUser> getUsers() {
		return users;
	}

	public void setUsers(Set<LdapUser> users) {
		this.users = users;
	}

}

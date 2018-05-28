package tr.com.agem.alfa.form;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import tr.com.agem.alfa.model.enums.LdapEncryptionType;
import tr.com.agem.alfa.model.enums.LdapType;

/**
 * @author <a href="mailto:emre.akkaya@agem.com.tr">Emre Akkaya</a>
 */
public class LdapIntegrationForm extends BaseForm {

	private static final long serialVersionUID = -7377890604450756174L;

	@NotEmpty
	private String ipAddress;

	@NotNull
	private Integer port = 389;

	@NotNull
	private Integer ldapType = LdapType.OPENLDAP.getId();

	@NotNull
	private Integer timeout = 120;

	@NotNull
	private Integer encryptionType = LdapEncryptionType.NONE.getId();

	private Boolean validateServerCert;

	private String sslCertFilePath;

	private String searchDn;

	@NotEmpty
	private String password;

	@NotEmpty
	private String passwordRepeated;

	private String userDnPattern = "cn=%u,dc=agem,dc=com,dc=tr";

	private Boolean searchForDn;

	private String searchFilter;

	private String searchContexts;

	private Boolean allowEmptyPasswords;

	@NotEmpty
	private String userIdentifierAttribute;

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

	public Integer getLdapType() {
		return ldapType;
	}

	public void setLdapType(Integer ldapType) {
		this.ldapType = ldapType;
	}

	public Integer getTimeout() {
		return timeout;
	}

	public void setTimeout(Integer timeout) {
		this.timeout = timeout;
	}

	public Integer getEncryptionType() {
		return encryptionType;
	}

	public void setEncryptionType(Integer encryptionType) {
		this.encryptionType = encryptionType;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordRepeated() {
		return passwordRepeated;
	}

	public void setPasswordRepeated(String passwordRepeated) {
		this.passwordRepeated = passwordRepeated;
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

	@Override
	public String toString() {
		return "LdapIntegrationForm [ipAddress=" + ipAddress + ", port=" + port + ", ldapType=" + ldapType
				+ ", timeout=" + timeout + ", encryptionType=" + encryptionType + ", validateServerCert="
				+ validateServerCert + ", sslCertFilePath=" + sslCertFilePath + ", searchDn=" + searchDn + ", password="
				+ password + ", passwordRepeated=" + passwordRepeated + ", userDnPattern=" + userDnPattern
				+ ", searchForDn=" + searchForDn + ", searchFilter=" + searchFilter + ", searchContexts="
				+ searchContexts + ", allowEmptyPasswords=" + allowEmptyPasswords + ", userIdentifierAttribute="
				+ userIdentifierAttribute + "]";
	}

}

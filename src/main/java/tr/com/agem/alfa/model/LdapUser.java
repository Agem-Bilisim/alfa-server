package tr.com.agem.alfa.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @author <a href="mailto:emre.akkaya@agem.com.tr">Emre Akkaya</a>
 */
@Entity
@Table(name = "c_ldap_user")
public class LdapUser extends BaseModel {

	private static final long serialVersionUID = -2726826799602462857L;

	/**
	 * User DN may change time to time and this field does not necessarily reflect
	 * the real DN value. So always use user identifier to find/filter users instead
	 * of this!
	 */
	@Column(name = "DN", nullable = false)
	private String dn;

	/**
	 * Unique identifier to match LDAP entry with local user Usually this is TCK no
	 * or some other identification value.
	 */
	@Column(name = "USER_IDENTIFIER", nullable = false)
	private String userIdentifier;

	@Column(name = "NAME", nullable = false)
	private String name;

	@ManyToOne
	@JoinColumn(name = "LDAP_INTEGRATION_ID", nullable = false)
	private LdapIntegration ldapIntegration;

	@OneToMany(mappedBy = "ldapUser", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<LdapUserAttribute> attributes = new HashSet<LdapUserAttribute>(0);

	public String getDn() {
		return dn;
	}

	public void setDn(String dn) {
		this.dn = dn;
	}

	public String getUserIdentifier() {
		return userIdentifier;
	}

	public void setUserIdentifier(String userIdentifier) {
		this.userIdentifier = userIdentifier;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LdapIntegration getLdapIntegration() {
		return ldapIntegration;
	}

	public void setLdapIntegration(LdapIntegration ldapIntegration) {
		this.ldapIntegration = ldapIntegration;
	}

	public Set<LdapUserAttribute> getAttributes() {
		return attributes;
	}

	public void setAttributes(Set<LdapUserAttribute> attributes) {
		this.attributes = attributes;
	}

	public void addAttribute(LdapUserAttribute attr) {
		attr.setLdapUser(this);
		attributes.add(attr);
	}

}

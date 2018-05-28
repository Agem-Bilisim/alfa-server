package tr.com.agem.alfa.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * @author <a href="mailto:emre.akkaya@agem.com.tr">Emre Akkaya</a>
 */
@Entity
@Table(name = "c_ldap_user_attribute", uniqueConstraints = { @UniqueConstraint(columnNames = { "LDAP_USER_ID", "NAME", "VALUE" }) })
public class LdapUserAttribute extends BaseModel {

	private static final long serialVersionUID = -7661468503413681022L;

	@Column(name = "NAME", nullable = false)
	private String name;

	@Column(name = "VALUE", nullable = false)
	private String value;

	@ManyToOne
	@JoinColumn(name = "LDAP_USER_ID", nullable = false)
	private LdapUser ldapUser;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public LdapUser getLdapUser() {
		return ldapUser;
	}

	public void setLdapUser(LdapUser ldapUser) {
		this.ldapUser = ldapUser;
	}

}

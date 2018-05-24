package tr.com.agem.alfa.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author <a href="mailto:emre.akkaya@agem.com.tr">Emre Akkaya</a>
 */
@Entity
@Table(name = "c_user")
public class SysUser extends BaseModel {

	private static final long serialVersionUID = 8155712510767070264L;

	@Column(name = "USER_NAME", nullable = false, unique = true)
	private String userName;

	@Column(name = "EMAIL", nullable = false, unique = true)
	private String email;

	@Column(name = "FIRST_NAME")
	private String firstName;

	@Column(name = "LAST_NAME")
	private String lastName;

	@Column(name = "PASSWORD_HASH", nullable = false)
	private String passwordHash;

	@Column(name = "DISABLED")
	private Boolean disabled;

	@ManyToOne
	@JoinColumn(name = "ROLE_ID", nullable = false)
	private SysRole role;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	public Boolean getDisabled() {
		return disabled;
	}

	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
	}

	public SysRole getRole() {
		return role;
	}
	
	public String getRoleString() {
		return role.getName();
	}

	public void setRole(SysRole role) {
		this.role = role;
	}

	/* (non-Javadoc)
	 * @see tr.com.agem.alfa.model.BaseModel#getCorrespondingForm()
	 */
	@Override
	public Object getCorrespondingForm() {
		return null;
	}

}

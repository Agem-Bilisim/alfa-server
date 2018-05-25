package tr.com.agem.alfa.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author <a href="mailto:emre.akkaya@agem.com.tr">Emre Akkaya</a>
 */
@Entity
@Table(name = "c_role")
public class SysRole extends BaseModel {

	private static final long serialVersionUID = 255243218266449046L;

	@Column(name = "NAME", nullable = false, updatable = false)
	private String name;

	@JsonIgnore
	@OneToMany(mappedBy = "role")
	private Set<SysUser> users = new HashSet<SysUser>(0);

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<SysUser> getUsers() {
		return users;
	}

	public void setUsers(Set<SysUser> users) {
		this.users = users;
	}

}

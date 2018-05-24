package tr.com.agem.alfa.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @author <a href="mailto:emre.akkaya@agem.com.tr">Emre Akkaya</a>
 */
@Entity
@Table(name = "c_role")
public class Role extends BaseModel {

	private static final long serialVersionUID = 255243218266449046L;

	@Column(name = "NAME", nullable = false)
	private String name;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "role")
	private Set<User> users = new HashSet<User>(0);

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	/* (non-Javadoc)
	 * @see tr.com.agem.alfa.model.BaseModel#getCorrespondingForm()
	 */
	@Override
	public Object getCorrespondingForm() {
		return null;
	}

}

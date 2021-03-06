package tr.com.agem.alfa.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * @author <a href="mailto:emre.akkaya@agem.com.tr">Emre Akkaya</a>
 */
@Entity
@Table(name = "c_education")
public class Education extends BaseModel {

	private static final long serialVersionUID = 2731322421302346918L;

	@Column(name = "LABEL", nullable = false, unique = true)
	private String label;

	@Column(name = "DESCRIPTION", length = 500)
	private String description;

	@Column(name = "URL", length = 500)
	private String url;

	@Column(name = "LMS_EDUCATION_ID")
	private Long lmsEducationId;

	@OneToMany(mappedBy = "education", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE })
	private Set<EducationLdapUser> educationUsers = new HashSet<EducationLdapUser>(0);

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Set<EducationLdapUser> getEducationUsers() {
		return educationUsers;
	}

	public void setEducationUsers(Set<EducationLdapUser> educationUsers) {
		this.educationUsers = educationUsers;
	}

	public Long getLmsEducationId() {
		return lmsEducationId;
	}

	public void setLmsEducationId(Long lmsEducationId) {
		this.lmsEducationId = lmsEducationId;
	}

	public void addEducationUser(EducationLdapUser elu) {
		if (this.educationUsers == null) {
			this.educationUsers = new HashSet<EducationLdapUser>();
		}
		elu.setEducation(this);
		this.educationUsers.add(elu);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((label == null) ? 0 : label.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Education other = (Education) obj;
		if (label == null) {
			if (other.label != null)
				return false;
		} else if (!label.equals(other.label))
			return false;
		return true;
	}

}

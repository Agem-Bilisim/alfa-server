package tr.com.agem.alfa.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnore;

import tr.com.agem.alfa.model.enums.EducationStatus;

/**
 * @author <a href="mailto:emre.akkaya@agem.com.tr">Emre Akkaya</a>
 */
@Entity
@Table(name = "c_education_user_education", uniqueConstraints = { @UniqueConstraint(columnNames = { "EDUCATION_ID", "LDAP_USER_ID" }) })
public class EducationLdapUser implements Serializable {

	private static final long serialVersionUID = 8653912712002079235L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "EDUCATION_USER_EDUCATION_ID", unique = true, nullable = false, updatable = false)
	private Long id;

	@JsonIgnore
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "EDUCATION_ID")
	private Education education;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "LDAP_USER_ID")
	private LdapUser ldapUser;

	@Column(name = "STATUS")
	private Integer status;

	@Column(name = "DURATION", length = 50)
	private String duration;

	@Column(name = "EXAM_SCORE", length = 50)
	private String examScore;

	@Column(name = "EXAM_STATUS", length = 500)
	private String examStatus;

	public EducationLdapUser() {
	}

	public EducationLdapUser(Education education, LdapUser ldapUser, Integer status, String duration, String examScore,
			String examStatus) {
		this.education = education;
		this.ldapUser = ldapUser;
		this.status = status;
		this.duration = duration;
		this.examScore = examScore;
		this.examStatus = examStatus;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Education getEducation() {
		return education;
	}

	public void setEducation(Education education) {
		this.education = education;
	}

	public LdapUser getLdapUser() {
		return ldapUser;
	}

	public void setLdapUser(LdapUser ldapUser) {
		this.ldapUser = ldapUser;
	}

	public EducationStatus getStatus() {
		return EducationStatus.getType(status);
	}

	public String getStatusLabel() {
		return status != null ? EducationStatus.getLabel(status)
				: EducationStatus.getLabel(EducationStatus.NOT_STARTED.getId());
	}

	public void setStatus(EducationStatus status) {
		if (status == null) {
			this.status = null;
		} else {
			this.status = status.getId();
		}
	}

	public String getDuration() {
		return duration != null ? duration : "00:00:00";
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getExamScore() {
		return examScore != null ? examScore : "0";
	}

	public void setExamScore(String examScore) {
		this.examScore = examScore;
	}

	public String getExamStatus() {
		return examStatus;
	}

	public void setExamStatus(String examStatus) {
		this.examStatus = examStatus;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((education == null) ? 0 : education.hashCode());
		result = prime * result + ((ldapUser == null) ? 0 : ldapUser.hashCode());
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
		EducationLdapUser other = (EducationLdapUser) obj;
		if (education == null) {
			if (other.education != null)
				return false;
		} else if (!education.equals(other.education))
			return false;
		if (ldapUser == null) {
			if (other.ldapUser != null)
				return false;
		} else if (!ldapUser.equals(other.ldapUser))
			return false;
		return true;
	}

}

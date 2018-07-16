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

import tr.com.agem.alfa.model.enums.EducationStatus;

/**
 * @author <a href="mailto:emre.akkaya@agem.com.tr">Emre Akkaya</a>
 */
@Entity
@Table(name = "c_education_user_education")
public class EducationLdapUser implements Serializable {

	private static final long serialVersionUID = 8653912712002079235L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "EDUCATION_USER_EDUCATION_ID", unique = true, nullable = false, updatable = false)
	private Long id;

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
		return EducationStatus.getLabel(status);
	}

	public void setStatus(EducationStatus status) {
		if (status == null) {
			this.status = null;
		} else {
			this.status = status.getId();
		}
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getExamScore() {
		return examScore;
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

}

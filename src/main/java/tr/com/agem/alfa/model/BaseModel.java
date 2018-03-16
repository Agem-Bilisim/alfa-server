package tr.com.agem.alfa.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author emre
 */
@MappedSuperclass
public abstract class BaseModel implements Serializable {

	private static final long serialVersionUID = -3892645791411537782L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID", unique = true, nullable = false)
	private Long id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED_DATE", nullable = false)
	private Date createdDate;

	@Column(name = "CREATED_BY", nullable = false)
	private String createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LAST_MODIFIED_DATE")
	private Date lastModifiedDate;

	@Column(name = "LAST_MODIFIED_BY")
	private String lastModifiedBy;

	@PrePersist
	public void prePersist() {
		if (createdDate == null) {
			createdDate = new Date();
		}
		if (createdBy == null) {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			createdBy = (String) authentication.getPrincipal();
		}
	}

	@PreUpdate
	public void preUpdate() {
		if (lastModifiedDate == null) {
			lastModifiedDate = new Date();
		}
		if (lastModifiedBy == null) {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			lastModifiedBy = (String) authentication.getPrincipal();
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}
	
}

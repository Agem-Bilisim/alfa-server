package tr.com.agem.alfa.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import tr.com.agem.alfa.model.enums.ProblemReferenceType;

/**
 * @author <a href="mailto:emre.akkaya@agem.com.tr">Emre Akkaya</a>
 */
@Entity
@Table(name = "c_problem_reference", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "REFERENCE_TYPE", "REFERENCE_ID", "PROBLEM_ID" }) })
public class ProblemReference extends BaseModel {

	private static final long serialVersionUID = -8327141280072177865L;

	@Column(name = "REFERENCE_TYPE", nullable = false)
	private Integer referenceType;

	@Column(name = "REFERENCE_ID", nullable = false)
	private Long referenceId;

	@ManyToOne
	@JoinColumn(name = "PROBLEM_ID", nullable = false)
	private Problem problem;

	public ProblemReferenceType getReferenceType() {
		return ProblemReferenceType.getType(referenceType);
	}

	public void setReferenceType(ProblemReferenceType type) {
		if (type == null) {
			this.referenceType = null;
		} else {
			this.referenceType = type.getId();
		}
	}

	public Long getReferenceId() {
		return referenceId;
	}

	public void setReferenceId(Long referenceId) {
		this.referenceId = referenceId;
	}

	public Problem getProblem() {
		return problem;
	}

	public void setProblem(Problem problem) {
		this.problem = problem;
	}

}

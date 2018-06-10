package tr.com.agem.alfa.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "c_problem")
public class Problem extends BaseModel {

	private static final long serialVersionUID = 1983036977266067697L;

	@Column(name = "LABEL", nullable = false)
	private String label;

	@Lob
	@Column(name = "DESCRIPTION", nullable = false)
	private String description;

	@Column(name = "SOLVED")
	private Boolean solved;

	@Column(name = "SOLUTION_DATE")
	private Date solutionDate;

	@Column(name = "ESTIMATED_SOLUTION_DATE")
	private Date estimatedSolutionDate;

	@Column(name = "WORK_START_DATE")
	private Date workStartDate;

	@OneToMany(mappedBy = "problem", cascade = CascadeType.ALL)
	private Set<ProblemReference> references = new HashSet<ProblemReference>(0);

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

	public Boolean getSolved() {
		return solved;
	}

	public void setSolved(Boolean solved) {
		this.solved = solved;
	}

	public Set<ProblemReference> getReferences() {
		return references;
	}

	public void setReferences(Set<ProblemReference> references) {
		this.references = references;
	}

	public Date getSolutionDate() {
		return solutionDate;
	}

	public void setSolutionDate(Date solutionDate) {
		this.solutionDate = solutionDate;
	}

	public Date getEstimatedSolutionDate() {
		return estimatedSolutionDate;
	}

	public void setEstimatedSolutionDate(Date estimatedSolutionDate) {
		this.estimatedSolutionDate = estimatedSolutionDate;
	}

	public Date getWorkStartDate() {
		return workStartDate;
	}

	public void setWorkStartDate(Date workStartDate) {
		this.workStartDate = workStartDate;
	}

}

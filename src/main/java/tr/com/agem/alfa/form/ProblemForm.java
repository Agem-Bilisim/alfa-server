package tr.com.agem.alfa.form;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @author <a href="mailto:emre.akkaya@agem.com.tr">Emre Akkaya</a>
 */
public class ProblemForm extends BaseForm {

	private static final long serialVersionUID = 4462834258513669498L;

	@NotEmpty
	private String label;

	@NotEmpty
	private String description = " ";

	private Boolean solved;

	@DateTimeFormat(pattern = "dd.MM.yyyy")
	private Date estimatedSolutionDate;

	@DateTimeFormat(pattern = "dd.MM.yyyy")
	private Date workStartDate;

	@DateTimeFormat(pattern = "dd.MM.yyyy")
	private Date solutionDate;

	private String relatedPeripherals;

	private String relatedHardwares;

	private String relatedSoftwares;

	private String[] strReferences;

	private Set<ProblemReferenceForm> references = new HashSet<ProblemReferenceForm>(0);

	public ProblemForm() {
	}

	public ProblemForm(Long id, Date createdDate, String createdBy, Date lastModifiedDate, String lastModifiedBy,
			String label, String description, Boolean solved, Date estimatedSolutionDate, Date workStartDate,
			Date solutionDate, String relatedPeripherals, String relatedHardwares, String relatedSoftwares) {
		this.setId(id);
		this.setCreatedDate(createdDate);
		this.setCreatedBy(createdBy);
		this.setLastModifiedDate(lastModifiedDate);
		this.setLastModifiedBy(lastModifiedBy);
		this.label = label;
		this.description = description;
		this.solved = solved;
		this.estimatedSolutionDate = estimatedSolutionDate;
		this.workStartDate = workStartDate;
		this.solutionDate = solutionDate;
		this.relatedPeripherals = relatedPeripherals;
		this.relatedHardwares = relatedHardwares;
		this.relatedSoftwares = relatedSoftwares;
	}

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

	public Set<ProblemReferenceForm> getReferences() {
		return references;
	}

	public void setReferences(Set<ProblemReferenceForm> references) {
		if (references != null) {
			List<String> _references = new ArrayList<String>();
			for (ProblemReferenceForm ref : references) {
				_references.add(ref.getReferenceType().getId() + "-" + ref.getReferenceId());
			}
			this.strReferences = _references.toArray(new String[] {});
		}
		this.references = references;
	}

	public String[] getStrReferences() {
		return strReferences;
	}

	public void setStrReferences(String[] strReferences) {
		if (strReferences != null) {
			List<ProblemReferenceForm> _references = new ArrayList<ProblemReferenceForm>();
			for (String ref : strReferences) {
				_references.add(new ProblemReferenceForm(ref));
			}
			this.references = new HashSet<>(_references);
		}
		this.strReferences = strReferences;
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

	public String getRelatedPeripherals() {
		return relatedPeripherals;
	}

	public void setRelatedPeripherals(String relatedPeripherals) {
		this.relatedPeripherals = relatedPeripherals;
	}

	public String getRelatedHardwares() {
		return relatedHardwares;
	}

	public void setRelatedHardwares(String relatedHardwares) {
		this.relatedHardwares = relatedHardwares;
	}

	public String getRelatedSoftwares() {
		return relatedSoftwares;
	}

	public void setRelatedSoftwares(String relatedSoftwares) {
		this.relatedSoftwares = relatedSoftwares;
	}

	public Date getSolutionDate() {
		return solutionDate;
	}

	public void setSolutionDate(Date solutionDate) {
		this.solutionDate = solutionDate;
	}

}

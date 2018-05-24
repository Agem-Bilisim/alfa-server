package tr.com.agem.alfa.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import tr.com.agem.alfa.form.ProblemForm;

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

	@OneToMany(mappedBy = "problem", cascade = CascadeType.ALL, orphanRemoval = true)
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

	/* (non-Javadoc)
	 * @see tr.com.agem.alfa.model.BaseModel#getCorrespondingForm()
	 */
	@Override
	public Object getCorrespondingForm() {
		return new ProblemForm();
	}

}

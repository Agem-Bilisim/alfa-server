package tr.com.agem.alfa.form;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author <a href="mailto:emre.akkaya@agem.com.tr">Emre Akkaya</a>
 */
public class ProblemForm extends BaseForm {

	private static final long serialVersionUID = 4462834258513669498L;

	@NotEmpty
	private String label;

	@NotEmpty
	private String description;

	private Boolean solved;

	private String[] strReferences;

	private Set<ProblemReferenceForm> references = new HashSet<ProblemReferenceForm>(0);

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

}

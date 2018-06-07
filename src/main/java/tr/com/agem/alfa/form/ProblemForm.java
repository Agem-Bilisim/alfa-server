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
		this.references = references;
	}

	public String[] getStrReferences() {
		if ((strReferences == null || strReferences.length == 0) && !this.references.isEmpty()) {
			List<String> _references = new ArrayList<String>();
			for (ProblemReferenceForm ref : references) {
				_references.add(ref.getReferenceType().getId() + "-" + ref.getId());
			}
			this.strReferences = _references.toArray(new String[] {});
		}
		return strReferences;
	}

	public void setStrReferences(String[] strReferences) {
		this.strReferences = strReferences;
		if (strReferences != null) {
			for (String ref : strReferences) {
				this.references.add(new ProblemReferenceForm(ref));
			}
		}
	}

}

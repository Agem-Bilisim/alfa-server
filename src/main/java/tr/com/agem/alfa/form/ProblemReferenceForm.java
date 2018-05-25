package tr.com.agem.alfa.form;

import tr.com.agem.alfa.model.enums.ProblemReferenceType;

/**
 * @author <a href="mailto:emre.akkaya@agem.com.tr">Emre Akkaya</a>
 */
public class ProblemReferenceForm extends BaseForm {

	private static final long serialVersionUID = 5134376063789885303L;

	private Integer referenceType;

	private Long referenceId;

	public ProblemReferenceForm() {
	}

	public ProblemReferenceForm(String ref) {
		if (ref == null || ref.isEmpty()) return;
		String[] parts = ref.split("-");
		setReferenceId(Long.parseLong(parts[1]));
		setReferenceType(ProblemReferenceType.getType(parts[0]));
	}

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

}

package tr.com.agem.alfa.form;

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

	// TODO iliskili kayitlar
	
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

}

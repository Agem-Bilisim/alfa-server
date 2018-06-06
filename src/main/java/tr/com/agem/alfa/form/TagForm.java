package tr.com.agem.alfa.form;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author <a href="mailto:emre.akkaya@agem.com.tr">Emre Akkaya</a>
 */
public class TagForm extends BaseForm {

	private static final long serialVersionUID = 3887521280929916142L;

	@NotEmpty
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}

}

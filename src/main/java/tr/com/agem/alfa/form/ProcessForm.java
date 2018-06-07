package tr.com.agem.alfa.form;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author <a href="mailto:emre.akkaya@agem.com.tr">Emre Akkaya</a>
 */
public class ProcessForm extends BaseForm {

	private static final long serialVersionUID = 1646328371851827316L;

	@NotEmpty
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}

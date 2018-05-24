package tr.com.agem.alfa.form;

import org.hibernate.validator.constraints.NotEmpty;

import tr.com.agem.alfa.model.SysRole;

/**
 * @author <a href="mailto:emre.akkaya@agem.com.tr">Emre Akkaya</a>
 */
public class SysRoleForm extends BaseForm {

	private static final long serialVersionUID = 3891320051878622117L;

	@NotEmpty
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/* (non-Javadoc)
	 * @see tr.com.agem.alfa.form.BaseForm#getCorrespondingModel()
	 */
	@Override
	public Object getCorrespondingModel() {
		return new SysRole();
	}

}

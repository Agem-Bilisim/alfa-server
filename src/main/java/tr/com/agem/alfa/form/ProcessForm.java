package tr.com.agem.alfa.form;

import org.hibernate.validator.constraints.NotEmpty;

import tr.com.agem.alfa.model.RunningProcess;
import tr.com.agem.alfa.model.enums.ProblemReferenceType;
import tr.com.agem.alfa.util.SelectboxBuilder.OptionFormConvertable;

/**
 * @author <a href="mailto:emre.akkaya@agem.com.tr">Emre Akkaya</a>
 */
public class ProcessForm extends BaseForm implements OptionFormConvertable {

	private static final long serialVersionUID = 1646328371851827316L;

	@NotEmpty
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getOptionText() {
		return this.name;
	}

	@Override
	public String getOptionValue() {
		return ProblemReferenceType.PROCESS.toString() + "-" + this.getId();
	}

	/* (non-Javadoc)
	 * @see tr.com.agem.alfa.form.BaseForm#getCorrespondingModel()
	 */
	@Override
	public Object getCorrespondingModel() {
		return new RunningProcess();
	}

}

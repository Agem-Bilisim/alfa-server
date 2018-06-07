package tr.com.agem.alfa.form;

import org.hibernate.validator.constraints.NotEmpty;

import tr.com.agem.alfa.model.enums.ProblemReferenceType;
import tr.com.agem.alfa.util.SelectboxBuilder.OptionFormConvertable;

/**
 * @author <a href="mailto:emre.akkaya@agem.com.tr">Emre Akkaya</a>
 */
public class PackageForm extends BaseForm implements OptionFormConvertable {

	private static final long serialVersionUID = 8825161815758128735L;

	@NotEmpty
	private String name;

	@NotEmpty
	private String version;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	@Override
	public String getOptionText() {
		return this.name + " " + this.version;
	}

	@Override
	public String getOptionValue() {
		return ProblemReferenceType.PACKAGE.getId() + "-" + this.getId();
	}

}

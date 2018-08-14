package tr.com.agem.alfa.form;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author <a href="mailto:emre.akkaya@agem.com.tr">Emre Akkaya</a>
 */
public class EducationForm extends BaseForm {

	private static final long serialVersionUID = 1378440036781090620L;

	@NotEmpty
	@JsonProperty("urun_adi")
	private String label;

	@NotEmpty
	private String description;

	@NotEmpty
	private String url;

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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}

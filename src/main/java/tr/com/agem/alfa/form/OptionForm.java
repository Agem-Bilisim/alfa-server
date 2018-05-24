package tr.com.agem.alfa.form;

/**
 * @author <a href="mailto:emre.akkaya@agem.com.tr">Emre Akkaya</a>
 */
public class OptionForm extends BaseForm {

	private static final long serialVersionUID = 2992777487321881924L;

	private String text;

	private String value;

	public OptionForm(String text, String value) {
		this.text = text;
		this.value = value;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	/* (non-Javadoc)
	 * @see tr.com.agem.alfa.form.BaseForm#getCorrespondingModel()
	 */
	@Override
	public Object getCorrespondingModel() {
		return null;
	}

}

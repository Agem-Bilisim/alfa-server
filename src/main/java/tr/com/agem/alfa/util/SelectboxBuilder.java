package tr.com.agem.alfa.util;

import java.util.ArrayList;
import java.util.List;

import tr.com.agem.alfa.form.OptionForm;

public class SelectboxBuilder {

	private List<OptionForm> options;

	private SelectboxBuilder() {
		this.options = new ArrayList<OptionForm>();
	}

	public static SelectboxBuilder newSelectbox() {
		return new SelectboxBuilder();
	}

	public SelectboxBuilder addOpts(List<? extends OptionForm> options) {
		if (options != null) this.options.addAll(options);
		return this;
	}

	public SelectboxBuilder add(List<? extends OptionFormConvertable> convertables) {
		if (convertables != null && !convertables.isEmpty()) {
			for (OptionFormConvertable convertable : convertables) {
				this.options.add(new OptionForm(convertable.getOptionText(), convertable.getOptionValue()));
			}
		}
		return this;
	}

	public List<OptionForm> build() {
		return this.options;
	}

	public interface OptionFormConvertable {
		String getOptionText();

		String getOptionValue();
	}

}

package tr.com.agem.alfa.form;

import java.io.Serializable;

public class PlanForm implements Serializable {

	private static final long serialVersionUID = 7351302012260021638L;

	private TagForm[] tags;

	public TagForm[] getTags() {
		return tags;
	}

	public void setTags(TagForm[] tags) {
		this.tags = tags;
	}
}

package tr.com.agem.alfa.form;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

/**
 * @author <a href="mailto:emre.akkaya@agem.com.tr">Emre Akkaya</a>
 */
public class AgentForm extends BaseForm {

	private static final long serialVersionUID = -2640937649058731051L;

	private Set<TagForm> tags = new HashSet<TagForm>(0);

	public String getTagsStr() {
		return tags != null && !tags.isEmpty()
				? StringUtils.join(tags, ",")
				: "";
	}
	
	public Set<TagForm> getTags() {
		return tags;
	}

	public void setTags(Set<TagForm> tags) {
		this.tags = tags;
	}

}

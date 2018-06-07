package tr.com.agem.alfa.form;

import org.hibernate.validator.constraints.NotEmpty;

public class BiosForm extends BaseForm {

	private static final long serialVersionUID = 5108730072495560762L;

	@NotEmpty
	private String vendor;

	@NotEmpty
	private String version;

	private String releaseDate;
	
	private Long[] agentIds;

	public String getVendor() {
		return vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}

	public Long[] getAgentIds() {
		return agentIds;
	}

	public void setAgentIds(Long[] agentIds) {
		this.agentIds = agentIds;
	}

}

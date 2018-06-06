package tr.com.agem.alfa.form;

import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

import tr.com.agem.alfa.model.Agent;

public class BiosForm extends BaseForm {

	private static final long serialVersionUID = 5108730072495560762L;

	@NotEmpty
	private String vendor;

	@NotEmpty
	private String version;

	private String releaseDate;
	
	private List<Agent> agents;

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

	public List<Agent> getAgents() {
		return agents;
	}

	public void setAgents(List<Agent> agents) {
		this.agents = agents;
	}

}

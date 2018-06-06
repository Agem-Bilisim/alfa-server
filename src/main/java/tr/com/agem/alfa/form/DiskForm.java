package tr.com.agem.alfa.form;

import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

import tr.com.agem.alfa.model.Agent;

public class DiskForm extends BaseForm {

	private static final long serialVersionUID = 5737317571161335134L;

	private String vendor;

	private String description;

	@NotEmpty
	private String version;

	@NotEmpty
	private String product;

	private String serial;
	
	private List<Agent> agents;

	public String getVendor() {
		return vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}

	public List<Agent> getAgents() {
		return agents;
	}

	public void setAgents(List<Agent> agents) {
		this.agents = agents;
	}

}

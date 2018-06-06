package tr.com.agem.alfa.form;

import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

import tr.com.agem.alfa.model.Agent;

public class NetworkInterfaceForm extends BaseForm {
	
	private static final long serialVersionUID = 4426299401066324106L;

	private String vendor;

	@NotEmpty
	private String version;

	@NotEmpty
	private String product;

	private String capabilities;
	
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

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getCapabilities() {
		return capabilities;
	}

	public void setCapabilities(String capabilities) {
		this.capabilities = capabilities;
	}

	public List<Agent> getAgents() {
		return agents;
	}

	public void setAgents(List<Agent> agents) {
		this.agents = agents;
	}
}

package tr.com.agem.alfa.form;

import org.hibernate.validator.constraints.NotEmpty;

import tr.com.agem.alfa.model.enums.ProblemReferenceType;
import tr.com.agem.alfa.util.SelectboxBuilder.OptionFormConvertable;

public class NetworkInterfaceForm extends BaseForm implements OptionFormConvertable {
	
	private static final long serialVersionUID = 4426299401066324106L;

	private String vendor;

	@NotEmpty
	private String version;

	@NotEmpty
	private String product;

	private String capabilities;
	
	private String compatible;
	
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

	public Long[] getAgentIds() {
		return agentIds;
	}

	public void setAgentIds(Long[] agentIds) {
		this.agentIds = agentIds;
	}
	
	@Override
	public String getOptionText() {
		return "Ethernet: " + this.vendor + " " + this.version;
	}

	@Override
	public String getOptionValue() {
		return ProblemReferenceType.INET.getId() + "-" + this.getId();
	}

	public String getCompatible() {
		return compatible;
	}

	public void setCompatible(String compatible) {
		this.compatible = compatible;
	}

}

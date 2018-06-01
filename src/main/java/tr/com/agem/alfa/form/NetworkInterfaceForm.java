package tr.com.agem.alfa.form;

import org.hibernate.validator.constraints.NotEmpty;

public class NetworkInterfaceForm extends BaseForm {
	
	private static final long serialVersionUID = 4426299401066324106L;

	private String vendor;

	@NotEmpty
	private String version;

	@NotEmpty
	private String product;

	private String capabilities;

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
}

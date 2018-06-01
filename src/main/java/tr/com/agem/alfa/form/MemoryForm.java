package tr.com.agem.alfa.form;

import org.hibernate.validator.constraints.NotEmpty;

public class MemoryForm extends BaseForm {

	private static final long serialVersionUID = -6041613156998307113L;

	@NotEmpty
	private String speed;

	@NotEmpty
	private String size;

	@NotEmpty
	private String type;

	@NotEmpty
	private String manufacturer;

	public String getSpeed() {
		return speed;
	}

	public void setSpeed(String speed) {
		this.speed = speed;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

}

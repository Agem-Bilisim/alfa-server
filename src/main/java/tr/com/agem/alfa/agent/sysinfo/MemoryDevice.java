
package tr.com.agem.alfa.agent.sysinfo;

import java.io.Serializable;

public class MemoryDevice implements Serializable {

	private final static long serialVersionUID = 3447345418217527443L;

	private String speed;
	private String size;
	private String type;
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


package tr.com.agem.alfa.dto;

import java.io.Serializable;
import java.util.List;

public class Network implements Serializable {

	private static final long serialVersionUID = -7522997601681145638L;

	private List<Device> devices = null;
	private List<String> macAddresses = null;
	private List<String> ipAddresses = null;
	private List<InterfaceAddress> interfaceAddresses = null;

	public List<Device> getDevices() {
		return devices;
	}

	public void setDevices(List<Device> devices) {
		this.devices = devices;
	}

	public List<String> getMacAddresses() {
		return macAddresses;
	}

	public void setMacAddresses(List<String> macAddresses) {
		this.macAddresses = macAddresses;
	}

	public List<String> getIpAddresses() {
		return ipAddresses;
	}

	public void setIpAddresses(List<String> ipAddresses) {
		this.ipAddresses = ipAddresses;
	}

	public List<InterfaceAddress> getInterfaceAddresses() {
		return interfaceAddresses;
	}

	public void setInterfaceAddresses(List<InterfaceAddress> interfaceAddresses) {
		this.interfaceAddresses = interfaceAddresses;
	}

}

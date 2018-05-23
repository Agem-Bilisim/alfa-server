package tr.com.agem.alfa.agent.sysinfo;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Network implements Serializable {

	private static final long serialVersionUID = -7522997601681145638L;

	private List<Device> devices = null;
	@JsonProperty("mac_addresses")
	private List<String> macAddresses = null;
	@JsonProperty("ip_addresses")
	private List<String> ipAddresses = null;
	@JsonProperty("interface_addresses")
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

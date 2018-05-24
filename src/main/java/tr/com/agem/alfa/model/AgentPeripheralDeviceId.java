package tr.com.agem.alfa.model;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Embeddable
public class AgentPeripheralDeviceId implements Serializable {

	private static final long serialVersionUID = 1927236167006978858L;
	
	@ManyToOne
	private Agent agent;

	@ManyToOne
	private PeripheralDevice peripheralDevice;

	public Agent getAgent() {
		return agent;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	public PeripheralDevice getPeripheralDevice() {
		return peripheralDevice;
	}

	public void setPeripheralDevice(PeripheralDevice peripheralDevice) {
		this.peripheralDevice = peripheralDevice;
	}

	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		AgentPeripheralDeviceId that = (AgentPeripheralDeviceId) o;

		if (agent != null ? !agent.equals(that.agent) : that.agent != null) return false;
		if (peripheralDevice != null ? !peripheralDevice.equals(that.peripheralDevice) : that.peripheralDevice != null)
			return false;

		return true;
	}

	public int hashCode() {
		int result;
		result = (agent != null ? agent.hashCode() : 0);
		result = 31 * result + (peripheralDevice != null ? peripheralDevice.hashCode() : 0);
		return result;
	}

}

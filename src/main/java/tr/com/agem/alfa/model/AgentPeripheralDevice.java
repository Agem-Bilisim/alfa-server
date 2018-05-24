package tr.com.agem.alfa.model;

import java.io.Serializable;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "c_agent_peripheral_agent")
@AssociationOverrides({ @AssociationOverride(name = "pk.agent", joinColumns = @JoinColumn(name = "AGENT_ID")),
		@AssociationOverride(name = "pk.peripheral", joinColumns = @JoinColumn(name = "PERIPHERAL_ID")) })
public class AgentPeripheralDevice implements Serializable {

	private static final long serialVersionUID = 3225471957126758413L;

	@JsonIgnore
	@EmbeddedId
	private AgentPeripheralDeviceId pk = new AgentPeripheralDeviceId();

	@Column(name = "DEVICE_ID", nullable = false, length = 100)
	String deviceId;

	@Column(name = "DEVICE_PATH", nullable = false, length = 100)
	String devicePath;

	public AgentPeripheralDeviceId getPk() {
		return pk;
	}

	public void setPk(AgentPeripheralDeviceId pk) {
		this.pk = pk;
	}

	@Transient
	public Agent getAgent() {
		return getPk().getAgent();
	}

	public void setAgent(Agent agent) {
		getPk().setAgent(agent);
	}

	@Transient
	public PeripheralDevice getPeripheralDevice() {
		return getPk().getPeripheralDevice();
	}

	public void setPeripheralDevice(PeripheralDevice peripheralDevice) {
		getPk().setPeripheralDevice(peripheralDevice);
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getDevicePath() {
		return devicePath;
	}

	public void setDevicePath(String devicePath) {
		this.devicePath = devicePath;
	}

	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		AgentPeripheralDevice that = (AgentPeripheralDevice) o;

		if (getPk() != null ? !getPk().equals(that.getPk()) : that.getPk() != null) return false;

		return true;
	}

	public int hashCode() {
		return (getPk() != null ? getPk().hashCode() : 0);
	}

}

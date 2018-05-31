package tr.com.agem.alfa.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author <a href="mailto:emre.akkaya@agem.com.tr">Emre Akkaya</a>
 */
@Entity
@Table(name = "c_agent_peripheral_agent")
public class AgentPeripheralDevice implements Serializable {

	private static final long serialVersionUID = 3225471957126758413L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "AGENT_PERIPHERAL_AGENT_ID", unique = true, nullable = false, updatable = false)
	private Long id;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "AGENT_ID")
	private Agent agent;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "PERIPHERAL_ID")
	private PeripheralDevice peripheralDevice;

	@Column(name = "DEVICE_ID", nullable = false, length = 100)
	String deviceId;

	@Column(name = "DEVICE_PATH", nullable = false, length = 100)
	String devicePath;

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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

}

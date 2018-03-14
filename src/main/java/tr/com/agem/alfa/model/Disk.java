package tr.com.agem.alfa.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "c_agent_disk", uniqueConstraints = { @UniqueConstraint(columnNames = "PRODUCT"),
		@UniqueConstraint(columnNames = "VERSION") })
public class Disk extends BaseModel {

	private static final long serialVersionUID = -3332758640758446379L;

	@Column(name = "VENDOR")
	private String vendor;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "VERSION", nullable = false)
	private String version;

	@Column(name = "PRODUCT", nullable = false)
	private String product;

	@Column(name = "SERIAL")
	private String serial;

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "disks")
	private Set<Agent> agents = new HashSet<Agent>(0);

	public String getVendor() {
		return vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}

	public Set<Agent> getAgents() {
		return agents;
	}

	public void setAgents(Set<Agent> agents) {
		this.agents = agents;
	}

}

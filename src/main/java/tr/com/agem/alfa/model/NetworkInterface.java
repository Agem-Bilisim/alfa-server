package tr.com.agem.alfa.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "c_agent_inet")
public class NetworkInterface extends BaseModel {

	private static final long serialVersionUID = 2290582206476792971L;

	@Column(name = "VENDOR")
	private String vendor;

	@Column(name = "VERSION", nullable = false)
	private String version;

	@Column(name = "PRODUCT", nullable = false)
	private String product;

	@Lob
	@Column(name = "CAPABILITIES")
	private String capabilities;

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "networkInterfaces")
	private Set<Agent> agents = new HashSet<Agent>(0);

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

	public Set<Agent> getAgents() {
		return agents;
	}

	public void setAgents(Set<Agent> agents) {
		this.agents = agents;
	}

	public void addAgent(Agent agent) {
		if (this.agents == null) {
			this.agents = new HashSet<Agent>();
		}
		this.agents.add(agent);
	}

}

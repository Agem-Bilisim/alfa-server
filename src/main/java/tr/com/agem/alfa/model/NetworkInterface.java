package tr.com.agem.alfa.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

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

	@JsonIgnore
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
		agent.getNetworkInterfaces().add(this);
		this.agents.add(agent);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((product == null) ? 0 : product.hashCode());
		result = prime * result + ((vendor == null) ? 0 : vendor.hashCode());
		result = prime * result + ((version == null) ? 0 : version.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		NetworkInterface other = (NetworkInterface) obj;
		if (product == null) {
			if (other.product != null) return false;
		} else if (!product.equals(other.product)) return false;
		if (vendor == null) {
			if (other.vendor != null) return false;
		} else if (!vendor.equals(other.vendor)) return false;
		if (version == null) {
			if (other.version != null) return false;
		} else if (!version.equals(other.version)) return false;
		return true;
	}

}

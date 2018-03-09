package tr.com.agem.alfa.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "c_agent")
public class Agent extends BaseModel {

	private static final long serialVersionUID = 8396192263006131366L;

	private Boolean deleted;

	private String installPath;

	private String hostName;

	private String ipAddresses;

	private String macAddresses;

	private Date lastInstallationDate;

	@ManyToMany(cascade = { CascadeType.ALL })
	@JoinTable(name = "c_agent_bios_agent", joinColumns = { @JoinColumn(name = "agent_id") }, inverseJoinColumns = {
			@JoinColumn(name = "bios_id") })
	private Set<Bios> bioses = new HashSet<Bios>();

	@ManyToMany(cascade = { CascadeType.ALL })
	@JoinTable(name = "c_agent_platform_agent", joinColumns = { @JoinColumn(name = "agent_id") }, inverseJoinColumns = {
			@JoinColumn(name = "platform_id") })
	private Set<Platform> platforms = new HashSet<Platform>();

	@ManyToMany(cascade = { CascadeType.ALL })
	@JoinTable(name = "c_agent_package_agent", joinColumns = { @JoinColumn(name = "agent_id") }, inverseJoinColumns = {
			@JoinColumn(name = "package_id") })
	private Set<InstalledPackages> packages = new HashSet<InstalledPackages>();

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public String getInstallPath() {
		return installPath;
	}

	public void setInstallPath(String installPath) {
		this.installPath = installPath;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public String getIpAddresses() {
		return ipAddresses;
	}

	public void setIpAddresses(String ipAddresses) {
		this.ipAddresses = ipAddresses;
	}

	public String getMacAddresses() {
		return macAddresses;
	}

	public void setMacAddresses(String macAddresses) {
		this.macAddresses = macAddresses;
	}

	public Date getLastInstallationDate() {
		return lastInstallationDate;
	}

	public void setLastInstallationDate(Date lastInstallationDate) {
		this.lastInstallationDate = lastInstallationDate;
	}

	public Set<Bios> getBioses() {
		return bioses;
	}

	public void setBioses(Set<Bios> bioses) {
		this.bioses = bioses;
	}

	public Set<Platform> getPlatforms() {
		return platforms;
	}

	public void setPlatforms(Set<Platform> platforms) {
		this.platforms = platforms;
	}

	public Set<InstalledPackages> getPackages() {
		return packages;
	}

	public void setPackages(Set<InstalledPackages> packages) {
		this.packages = packages;
	}

}

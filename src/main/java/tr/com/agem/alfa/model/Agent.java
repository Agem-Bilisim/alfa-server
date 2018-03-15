package tr.com.agem.alfa.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "c_agent")
public class Agent extends BaseModel {

	private static final long serialVersionUID = 8396192263006131366L;

	@Column(name = "TYPE", nullable = false)
	private Integer type;

	@Column(name = "MESSAGING_ID", nullable = false, unique = true)
	private String messagingId;

	@Column(name = "DELETED")
	private Boolean deleted;

	@Column(name = "INSTALL_PATH", nullable = false)
	private String installPath;

	@Column(name = "HOST_NAME", nullable = false)
	private String hostName; // platform.node

	@Column(name = "IP_ADDRESSES", nullable = false)
	private String ipAddresses; // network.ip_addresses

	@Column(name = "MAC_ADDRESSES", nullable = false)
	private String macAddresses; // network.mac_addresses

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LAST_INSTALLATION_DATE", nullable = false)
	private Date lastInstallationDate;

	@Column(name = "SYS_INFO")
	private byte[] sysinfo;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "c_agent_disk_agent", joinColumns = {
			@JoinColumn(name = "AGENT_ID", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "DISK_ID", nullable = false, updatable = false) })
	private Set<Disk> disks = new HashSet<Disk>(0);

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "c_agent_inet_agent", joinColumns = {
			@JoinColumn(name = "AGENT_ID", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "NETWORK_INTERFACE_ID", nullable = false, updatable = false) })
	private Set<NetworkInterface> networkInterfaces = new HashSet<NetworkInterface>(0);

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "c_agent_package_agent", joinColumns = {
			@JoinColumn(name = "AGENT_ID", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "INSTALLED_PACKAGE_ID", nullable = false, updatable = false) })
	private Set<InstalledPackage> installedPackages = new HashSet<InstalledPackage>(0);

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "c_agent_user_agent", joinColumns = {
			@JoinColumn(name = "AGENT_ID", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "USER_ID", nullable = false, updatable = false) })
	private Set<AgentUser> users = new HashSet<AgentUser>(0);

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "c_agent_memory_agent", joinColumns = {
			@JoinColumn(name = "AGENT_ID", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "MEMORY_ID", nullable = false, updatable = false) })
	private Set<Memory> memories = new HashSet<Memory>(0);

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "c_agent_gpu_agent", joinColumns = {
			@JoinColumn(name = "AGENT_ID", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "GPU_ID", nullable = false, updatable = false) })
	private Set<Gpu> gpus = new HashSet<Gpu>(0);

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.agent", cascade = CascadeType.ALL)
	private Set<AgentRunningProcess> agentRunningProcesses = new HashSet<AgentRunningProcess>(0);

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.agent", cascade = CascadeType.ALL)
	private Set<AgentCpu> agentCpus = new HashSet<AgentCpu>(0);

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.agent", cascade = CascadeType.ALL)
	private Set<AgentPeripheralDevice> agentPeripheralDevices = new HashSet<AgentPeripheralDevice>(0);

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BIOS_ID", nullable = false)
	private Bios bios;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PLATFORM_ID", nullable = false)
	private Platform platform;

	public Agent() {
	}

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

	public byte[] getSysinfo() {
		return sysinfo;
	}

	public void setSysinfo(byte[] sysinfo) {
		this.sysinfo = sysinfo;
	}

	public Set<Disk> getDisks() {
		return disks;
	}

	public void setDisks(Set<Disk> disks) {
		this.disks = disks;
	}

	public Bios getBios() {
		return bios;
	}

	public void setBios(Bios bios) {
		this.bios = bios;
	}

	public Platform getPlatform() {
		return platform;
	}

	public void setPlatform(Platform platform) {
		this.platform = platform;
	}

	public Set<NetworkInterface> getNetworkInterfaces() {
		return networkInterfaces;
	}

	public void setNetworkInterfaces(Set<NetworkInterface> networkInterfaces) {
		this.networkInterfaces = networkInterfaces;
	}

	public Set<AgentRunningProcess> getAgentRunningProcesses() {
		return agentRunningProcesses;
	}

	public void setAgentRunningProcesses(Set<AgentRunningProcess> agentRunningProcesses) {
		this.agentRunningProcesses = agentRunningProcesses;
	}

	public Set<InstalledPackage> getInstalledPackages() {
		return installedPackages;
	}

	public void setInstalledPackages(Set<InstalledPackage> installedPackages) {
		this.installedPackages = installedPackages;
	}

	public Set<AgentCpu> getAgentCpus() {
		return agentCpus;
	}

	public void setAgentCpus(Set<AgentCpu> agentCpus) {
		this.agentCpus = agentCpus;
	}

	public Set<AgentUser> getUsers() {
		return users;
	}

	public void setUsers(Set<AgentUser> users) {
		this.users = users;
	}

	public Set<AgentPeripheralDevice> getAgentPeripheralDevices() {
		return agentPeripheralDevices;
	}

	public void setAgentPeripheralDevices(Set<AgentPeripheralDevice> agentPeripheralDevices) {
		this.agentPeripheralDevices = agentPeripheralDevices;
	}

	public Set<Memory> getMemories() {
		return memories;
	}

	public void setMemories(Set<Memory> memories) {
		this.memories = memories;
	}

	public Set<Gpu> getGpus() {
		return gpus;
	}

	public void setGpus(Set<Gpu> gpus) {
		this.gpus = gpus;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getMessagingId() {
		return messagingId;
	}

	public void setMessagingId(String messagingId) {
		this.messagingId = messagingId;
	}

	public void addDisk(Disk disk) {
		if (this.disks == null) {
			this.disks = new HashSet<Disk>();
		}
		this.disks.add(disk);
	}

	public void addNetworkInterface(NetworkInterface inet) {
		if (this.networkInterfaces == null) {
			this.networkInterfaces = new HashSet<NetworkInterface>();
		}
		this.networkInterfaces.add(inet);
	}

	public void addInstalledPackage(InstalledPackage _package) {
		if (this.installedPackages == null) {
			this.installedPackages = new HashSet<InstalledPackage>();
		}
		this.installedPackages.add(_package);
	}

	public void addMemory(Memory mem) {
		if (this.memories == null) {
			this.memories = new HashSet<Memory>();
		}
		this.memories.add(mem);
	}

	public void addGpu(Gpu gpu) {
		if (this.gpus == null) {
			this.gpus = new HashSet<Gpu>();
		}
		this.gpus.add(gpu);
	}

}

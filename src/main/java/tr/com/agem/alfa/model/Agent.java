package tr.com.agem.alfa.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;

import tr.com.agem.alfa.model.enums.AgentType;

/**
 * @author <a href="mailto:emre.akkaya@agem.com.tr">Emre Akkaya</a>
 */
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

	@Column(name = "MAC_ADDRESSES", nullable = false, unique = true)
	private String macAddresses; // network.mac_addresses

	// windows'dan linux'e geçiş tarihi
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LAST_INSTALLATION_DATE")
	private Date lastInstallationDate;

	@JsonIgnore
	@Lob
	@Column(name = "SYS_INFO")
	private byte[] sysinfo;

	@ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "c_agent_disk_agent", joinColumns = {
			@JoinColumn(name = "AGENT_ID", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "DISK_ID", nullable = false, updatable = false) })
	private Set<Disk> disks = new HashSet<Disk>(0);

	@ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "c_agent_inet_agent", joinColumns = {
			@JoinColumn(name = "AGENT_ID", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "NETWORK_INTERFACE_ID", nullable = false, updatable = false) })
	private Set<NetworkInterface> networkInterfaces = new HashSet<NetworkInterface>(0);

	@ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "c_agent_tag_agent", joinColumns = {
			@JoinColumn(name = "AGENT_ID", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "TAG_ID", nullable = false, updatable = false) })
	private Set<Tag> tags = new HashSet<Tag>(0);

	@ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "c_agent_package_agent", joinColumns = {
			@JoinColumn(name = "AGENT_ID", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "INSTALLED_PACKAGE_ID", nullable = false, updatable = false) })
	private Set<InstalledPackage> installedPackages = new HashSet<InstalledPackage>(0);

	@ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "c_agent_user_agent", joinColumns = {
			@JoinColumn(name = "AGENT_ID", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "USER_ID", nullable = false, updatable = false) })
	private Set<AgentUser> users = new HashSet<AgentUser>(0);

	@ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "c_agent_memory_agent", joinColumns = {
			@JoinColumn(name = "AGENT_ID", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "MEMORY_ID", nullable = false, updatable = false) })
	private Set<Memory> memories = new HashSet<Memory>(0);

	@ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "c_agent_gpu_agent", joinColumns = {
			@JoinColumn(name = "AGENT_ID", nullable = false, updatable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "GPU_ID", nullable = false, updatable = false) })
	private Set<Gpu> gpus = new HashSet<Gpu>(0);

	@OneToMany(mappedBy = "agent", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE })
	private Set<AgentRunningProcess> agentRunningProcesses = new HashSet<AgentRunningProcess>(0);

	@OneToMany(mappedBy = "agent", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE })
	private Set<AgentCpu> agentCpus = new HashSet<AgentCpu>(0);

	@OneToMany(mappedBy = "agent", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE })
	private Set<AgentPeripheralDevice> agentPeripheralDevices = new HashSet<AgentPeripheralDevice>(0);

	@ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "BIOS_ID", nullable = false)
	private Bios bios;

	@ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "PLATFORM_ID", nullable = false)
	private Platform platform;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "survey")
	private Set<SurveyResult> surveyResults = new HashSet<SurveyResult>(0);

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
		this.bios.addAgent(this);
	}

	public Platform getPlatform() {
		return platform;
	}

	public void setPlatform(Platform platform) {
		this.platform = platform;
		this.platform.addAgent(this);
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

	public Set<Tag> getTags() {
		return tags;
	}

	public void setTags(Set<Tag> tags) {
		this.tags = tags;
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

	public AgentType getType() {
		return AgentType.getType(type);
	}

	public String getTypeLabel() {
		return AgentType.getLabel(type);
	}

	public void setType(AgentType type) {
		if (type == null) {
			this.type = null;
		} else {
			this.type = type.getId();
		}
	}

	public String getMessagingId() {
		return messagingId;
	}

	public void setMessagingId(String messagingId) {
		this.messagingId = messagingId;
	}

	public Set<SurveyResult> getSurveyResults() {
		return surveyResults;
	}

	public void setSurveyResults(Set<SurveyResult> surveyResults) {
		this.surveyResults = surveyResults;
	}

	public String getTagsStr() {
		return tags != null && !tags.isEmpty()
				? (StringUtils.join(tags, ",").length() > 80 ? StringUtils.join(tags, ",").substring(0, 80) + "..."
						: StringUtils.join(tags, ","))
				: "";
	}

	public void addDisk(Disk disk) {
		if (this.disks == null) {
			this.disks = new HashSet<Disk>();
		}
		disk.addAgent(this);
		this.disks.add(disk);
	}

	public void addNetworkInterface(NetworkInterface inet) {
		if (this.networkInterfaces == null) {
			this.networkInterfaces = new HashSet<NetworkInterface>();
		}
		inet.addAgent(this);
		this.networkInterfaces.add(inet);
	}

	public void addInstalledPackage(InstalledPackage _package) {
		if (this.installedPackages == null) {
			this.installedPackages = new HashSet<InstalledPackage>();
		}
		_package.addAgent(this);
		this.installedPackages.add(_package);
	}

	public void addMemory(Memory mem) {
		if (this.memories == null) {
			this.memories = new HashSet<Memory>();
		}
		mem.addAgent(this);
		this.memories.add(mem);
	}

	public void addGpu(Gpu gpu) {
		if (this.gpus == null) {
			this.gpus = new HashSet<Gpu>();
		}
		gpu.addAgent(this);
		this.gpus.add(gpu);
	}

	public void addUser(AgentUser user) {
		if (this.users == null) {
			this.users = new HashSet<AgentUser>();
		}
		user.addAgent(this);
		this.users.add(user);
	}

	public void addTag(Tag tag) {
		if (this.tags == null) {
			this.tags = new HashSet<Tag>();
		}
		tag.addAgent(this);
		this.tags.add(tag);
	}

}

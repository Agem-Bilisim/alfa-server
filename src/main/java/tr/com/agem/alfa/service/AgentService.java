package tr.com.agem.alfa.service;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import tr.com.agem.alfa.model.Agent;
import tr.com.agem.alfa.model.AgentCpu;
import tr.com.agem.alfa.model.AgentPeripheralDevice;
import tr.com.agem.alfa.model.AgentRunningProcess;
import tr.com.agem.alfa.model.AgentUser;
import tr.com.agem.alfa.model.CurrentUser;
import tr.com.agem.alfa.model.Disk;
import tr.com.agem.alfa.model.Gpu;
import tr.com.agem.alfa.model.InstalledPackage;
import tr.com.agem.alfa.model.Memory;
import tr.com.agem.alfa.model.NetworkInterface;
import tr.com.agem.alfa.model.Tag;
import tr.com.agem.alfa.model.enums.AgentType;
import tr.com.agem.alfa.repository.AgentRepository;
import tr.com.agem.alfa.repository.AgentUserRepository;
import tr.com.agem.alfa.repository.TagRepository;

/**
 * @author <a href="mailto:emre.akkaya@agem.com.tr">Emre Akkaya</a>
 */
@Component
public class AgentService {

	@PersistenceContext
	private EntityManager em;

	private final AgentRepository agentRepository;
	private final TagRepository tagRepository;
	private final AgentUserRepository agentUserRepository;

	@Autowired
	public AgentService(AgentRepository agentRepository, TagRepository tagRepository,
			AgentUserRepository agentUserRepository) {
		this.agentRepository = agentRepository;
		this.tagRepository = tagRepository;
		this.agentUserRepository = agentUserRepository;
	}

	@Transactional(noRollbackFor=javax.persistence.NoResultException.class)
	public void saveOrUpdate(Agent agent) {
		Assert.notNull(agent, "Agent must not be null.");
		Date now = new Date();
		String lastModifiedBy = "SYSTEM";
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Long agentId = agent.getId() != null ? agent.getId() : null;
		Long biosId = null;
		Long platformId = null;
		if (authentication.getPrincipal() instanceof CurrentUser) {
			CurrentUser user = (CurrentUser) authentication.getPrincipal();
			checkNotNull(user, "Current user not found.");
			lastModifiedBy = (String) user.getUsername();
		}
		//
		// BIOS
		//
		int i = 1;
		int res = this.em.createNativeQuery(
				"UPDATE c_agent_bios SET "
				+ "release_date = ?, last_modified_by = ?, last_modified_date = ? "
				+ "WHERE version = ? AND vendor = ?")
		.setParameter(i++, agent.getBios().getReleaseDate())
		.setParameter(i++, lastModifiedBy)
		.setParameter(i++, now, TemporalType.TIMESTAMP)
		.setParameter(i++, agent.getBios().getVersion())
		.setParameter(i++, agent.getBios().getVendor())
		.executeUpdate();
		this.em.flush();
		if (res == 1) {
			int k = 1;
			biosId = Long.parseLong(this.em.createNativeQuery(
					"SELECT id FROM c_agent_bios WHERE vendor = ? AND version = ?")
					.setParameter(k++, agent.getBios().getVendor())
					.setParameter(k++, agent.getBios().getVersion())
					.getSingleResult().toString());
		} else {
			i = 1;
			this.em.createNativeQuery(
					"INSERT INTO c_agent_bios "
					+ "(created_date, created_by, "
					+ "vendor, version, release_date) VALUES (?, ?, ?, ?, ?)")
			.setParameter(i++, now, TemporalType.TIMESTAMP)
			.setParameter(i++, lastModifiedBy)
			.setParameter(i++, agent.getBios().getVendor())
			.setParameter(i++, agent.getBios().getVersion())
			.setParameter(i++, agent.getBios().getReleaseDate())
			.executeUpdate();
			this.em.flush();
			biosId = Long.parseLong(this.em.createNativeQuery("SELECT LAST_INSERT_ID()").getSingleResult().toString());
		}
		//
		// Platform
		//
		i = 1;
		res = this.em.createNativeQuery(
				"UPDATE c_agent_platform SET "
				+ "version = ?, machine = ?, "
				+ "last_modified_by = ?, last_modified_date = ? "
				+ "WHERE system = ? AND pl_release = ?")
		.setParameter(i++, agent.getPlatform().getVersion())
		.setParameter(i++, agent.getPlatform().getMachine())
		.setParameter(i++, lastModifiedBy)
		.setParameter(i++, now, TemporalType.TIMESTAMP)
		.setParameter(i++, agent.getPlatform().getSystem())
		.setParameter(i++, agent.getPlatform().getRelease())
		.executeUpdate();			
		this.em.flush();
		if (res == 1) {
			int k = 1;
			platformId = Long.parseLong(this.em.createNativeQuery(
					"SELECT id FROM c_agent_platform WHERE system = ? AND pl_release = ?")
					.setParameter(k++, agent.getPlatform().getSystem())
					.setParameter(k++, agent.getPlatform().getRelease())
					.getSingleResult().toString());
		} else {
			i = 1;
			this.em.createNativeQuery(
					"INSERT INTO c_agent_platform "
					+ "(created_date, created_by, "
					+ "machine, pl_release, system, version) "
					+ "VALUES (?, ?, ?, ?, ?, ?)")
			.setParameter(i++, now, TemporalType.TIMESTAMP)
			.setParameter(i++, lastModifiedBy)
			.setParameter(i++, agent.getPlatform().getMachine())
			.setParameter(i++, agent.getPlatform().getRelease())
			.setParameter(i++, agent.getPlatform().getSystem())
			.setParameter(i++, agent.getPlatform().getVersion())
			.executeUpdate();
			this.em.flush();
			platformId = Long.parseLong(this.em.createNativeQuery("SELECT LAST_INSERT_ID()").getSingleResult().toString());			
		}
		//
		// Agent
		//
		if (agent.getId() != null) {
			// Update agent
			i = 1;
			this.em.createNativeQuery(
					"UPDATE c_agent SET "
					+ "deleted = ?, host_name = ?, install_path = ?, "
					+ "ip_addresses = ?, last_installation_date = ?, "
					+ "mac_addresses = ?, sys_info = ?, `type` = ?, "
					+ "last_modified_by = ?, last_modified_date = ?, "
					+ "bios_id = ?, platform_id = ? WHERE id = ?")
			.setParameter(i++, false)
			.setParameter(i++, agent.getHostName())
			.setParameter(i++, agent.getInstallPath())
			.setParameter(i++, agent.getIpAddresses())
			.setParameter(i++, agent.getLastInstallationDate(), TemporalType.TIMESTAMP)
			.setParameter(i++, agent.getMacAddresses())
			.setParameter(i++, agent.getSysinfo())
			.setParameter(i++, agent.getType().getId())
			.setParameter(i++, lastModifiedBy)
			.setParameter(i++, now, TemporalType.TIMESTAMP)
			.setParameter(i++, biosId)
			.setParameter(i++, platformId)
			.setParameter(i++, agent.getId())
			.executeUpdate();
			this.em.flush();
		} else {
			// New agent
			i = 1;
			this.em.createNativeQuery(
					"INSERT INTO c_agent " 
					+ "(created_by, created_date, host_name, install_path, "
					+ "ip_addresses, mac_addresses, messaging_id, sys_info, `type`, bios_id, platform_id) " 
					+ "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")
			.setParameter(i++, lastModifiedBy)
			.setParameter(i++, now, TemporalType.TIMESTAMP)
			.setParameter(i++, agent.getHostName())
			.setParameter(i++, agent.getInstallPath())
			.setParameter(i++, agent.getIpAddresses())
			.setParameter(i++, agent.getMacAddresses())
			.setParameter(i++, agent.getMessagingId())
			.setParameter(i++, agent.getSysinfo())
			.setParameter(i++, agent.getType().getId())
			.setParameter(i++, biosId)
			.setParameter(i++, platformId)
			.executeUpdate();
			this.em.flush();
			agentId = Long.parseLong(this.em.createNativeQuery("SELECT LAST_INSERT_ID()").getSingleResult().toString());
		}
		//
		// Disk
		//
		this.em.createNativeQuery(
				"DELETE FROM c_agent_disk_agent WHERE agent_id = :id")
				.setParameter("id", agentId)
				.executeUpdate();
		this.em.flush();
		if (agent.getDisks() != null) {
			for (Disk disk : agent.getDisks()) {
				i = 1;
				Long diskId = null;
				try {
					diskId = Long.parseLong(this.em.createNativeQuery(
							"SELECT id FROM c_agent_disk WHERE product = ? AND version = ?")
							.setParameter(i++, disk.getProduct())
							.setParameter(i++, disk.getVersion())
							.getSingleResult().toString());
				} catch (Exception e) {
				}
				if (diskId == null) {
					i = 1;
					this.em.createNativeQuery(
							"INSERT INTO c_agent_disk "
							+ "(created_by, created_date, "
							+ "description, product, serial, "
							+ "vendor, version) VALUES "
							+ "(?, ?, ?, ?, ?, ?, ?)")
					.setParameter(i++, lastModifiedBy)
					.setParameter(i++, now, TemporalType.TIMESTAMP)
					.setParameter(i++, disk.getDescription())
					.setParameter(i++, disk.getProduct())
					.setParameter(i++, disk.getSerial())
					.setParameter(i++, disk.getVendor())
					.setParameter(i++, disk.getVersion())
					.executeUpdate();
					this.em.flush();
					diskId = Long.parseLong(this.em.createNativeQuery("SELECT LAST_INSERT_ID()").getSingleResult().toString());	
				}
				// New agent-disk
				int j = 1;
				this.em.createNativeQuery(
						"INSERT INTO c_agent_disk_agent (agent_id, disk_id) VALUES(?, ?)")
				.setParameter(j++, agentId)
				.setParameter(j++, diskId)
				.executeUpdate();
			}
		}
		//
		// Network interface
		//
		this.em.createNativeQuery(
				"DELETE FROM c_agent_inet_agent WHERE agent_id = :id")
				.setParameter("id", agentId)
				.executeUpdate();
		this.em.flush();
		if (agent.getNetworkInterfaces() != null) {
			for (NetworkInterface inet : agent.getNetworkInterfaces()) {
				i = 1;
				Long inetId = null;
				try {
					inetId = Long.parseLong(this.em.createNativeQuery(
							"SELECT id FROM c_agent_inet WHERE vendor = ? AND product = ? AND version = ?")
							.setParameter(i++, inet.getVendor())
							.setParameter(i++, inet.getProduct())
							.setParameter(i++, inet.getVersion())
							.getSingleResult().toString());					
				} catch (Exception e) {
				}
				if (inetId == null) {
					i = 1;
					this.em.createNativeQuery(
							"INSERT INTO c_agent_inet "
							+ "(created_by, created_date, capabilities, "
							+ "product, vendor, version) "
							+ "VALUES(?, ?, ?, ?, ?, ?)")
					.setParameter(i++, lastModifiedBy)
					.setParameter(i++, now, TemporalType.TIMESTAMP)
					.setParameter(i++, inet.getCapabilities())
					.setParameter(i++, inet.getProduct())
					.setParameter(i++, inet.getVendor())
					.setParameter(i++, inet.getVersion())
					.executeUpdate();
					this.em.flush();
					inetId = Long.parseLong(this.em.createNativeQuery("SELECT LAST_INSERT_ID()").getSingleResult().toString());					
				}
				// New agent-inet
				int j = 1;
				this.em.createNativeQuery("INSERT INTO c_agent_inet_agent (agent_id, network_interface_id) VALUES(?, ?)")
				.setParameter(j++, agentId)
				.setParameter(j++, inetId)
				.executeUpdate();
			}
		}
		//
		// Installed packages/programs
		//
		this.em.createNativeQuery(
				"DELETE FROM c_agent_package_agent WHERE agent_id = :id")
				.setParameter("id", agentId)
				.executeUpdate();
		this.em.flush();
		if (agent.getInstalledPackages() != null) {
			for (InstalledPackage pckg : agent.getInstalledPackages()) {
				i = 1;
				Long packageId = null;
				try {
					packageId = Long.parseLong(this.em.createNativeQuery(
							"SELECT id FROM c_agent_package WHERE name = ? AND version = ?")
							.setParameter(i++, pckg.getName())
							.setParameter(i++, pckg.getVersion())
							.getSingleResult().toString());
				} catch (Exception e) {
				}
				if (packageId == null) {
					i = 1;
					this.em.createNativeQuery(
							"INSERT INTO c_agent_package (created_by, created_date, name, version) "
							+ "VALUES(?, ?, ?, ?)")
					.setParameter(i++, lastModifiedBy)
					.setParameter(i++, now, TemporalType.TIMESTAMP)
					.setParameter(i++, pckg.getName())
					.setParameter(i++, pckg.getVersion())
					.executeUpdate();
					this.em.flush();
					packageId = Long.parseLong(this.em.createNativeQuery("SELECT LAST_INSERT_ID()").getSingleResult().toString());
				}
				// New agent-package
				int j = 1;
				this.em.createNativeQuery("INSERT INTO c_agent_package_agent (agent_id, installed_package_id) VALUES(?, ?)")
				.setParameter(j++, agentId)
				.setParameter(j++, packageId)
				.executeUpdate();
			}
		}
		// Memories
		this.em.createNativeQuery(
				"DELETE FROM c_agent_memory_agent WHERE agent_id = :id")
				.setParameter("id", agentId)
				.executeUpdate();
		this.em.flush();
		if (agent.getMemories() != null) {
			for (Memory mem : agent.getMemories()) {
				i = 1;
				Long memoryId = null;
				try {
					memoryId = Long.parseLong(this.em.createNativeQuery(
							"SELECT id FROM c_agent_memory WHERE "
							+ "speed = ? AND size = ? AND type = ? AND manufacturer = ?")
							.setParameter(i++, mem.getSpeed())
							.setParameter(i++, mem.getSize())
							.setParameter(i++, mem.getType())
							.setParameter(i++, mem.getManufacturer())
							.getSingleResult().toString());
				} catch (Exception e) {
				}
				if (memoryId == null) {
					i = 1;
					this.em.createNativeQuery(
							"INSERT INTO c_agent_memory (created_by, created_date, "
							+ "manufacturer, `size`, speed, `type`) "
							+ "VALUES(?, ?, ?, ?, ?, ?)")
					.setParameter(i++, lastModifiedBy)
					.setParameter(i++, now, TemporalType.TIMESTAMP)
					.setParameter(i++, mem.getManufacturer())
					.setParameter(i++, mem.getSize())
					.setParameter(i++, mem.getSpeed())
					.setParameter(i++, mem.getType())
					.executeUpdate();
					this.em.flush();
					memoryId = Long.parseLong(this.em.createNativeQuery("SELECT LAST_INSERT_ID()").getSingleResult().toString());
				}
				// New agent-memory
				int j = 1;
				this.em.createNativeQuery("INSERT INTO c_agent_memory_agent (agent_id, memory_id) VALUES(?, ?)")
				.setParameter(j++, agentId)
				.setParameter(j++, memoryId)
				.executeUpdate();
			}
		}
		// GPU
		this.em.createNativeQuery(
				"DELETE FROM c_agent_gpu_agent WHERE agent_id = :id")
				.setParameter("id", agentId)
				.executeUpdate();
		this.em.flush();
		if (agent.getGpus() != null) {
			for (Gpu gpu : agent.getGpus()) {
				i = 1;
				Long gpuId = null;
				try {
					gpuId = Long.parseLong(this.em.createNativeQuery(
							"SELECT id FROM c_agent_gpu WHERE "
							+ "subsystem = ? AND kernel = ? AND memory = ?")
							.setParameter(i++, gpu.getSubsystem())
							.setParameter(i++, gpu.getKernel())
							.setParameter(i++, gpu.getMemory())
							.getSingleResult().toString());
				} catch (Exception e) {
				}
				if (gpuId == null) {
					i = 1;
					this.em.createNativeQuery(
							"INSERT INTO c_agent_gpu (created_by, created_date, "
							+ "kernel, memory, subsystem, driver_date, driver_version) "
							+ "VALUES(?, ?, ?, ?, ?, ?, ?)")
					.setParameter(i++, lastModifiedBy)
					.setParameter(i++, now, TemporalType.TIMESTAMP)
					.setParameter(i++, gpu.getKernel())
					.setParameter(i++, gpu.getMemory())
					.setParameter(i++, gpu.getSubsystem())
					.setParameter(i++, gpu.getDriverDate())
					.setParameter(i++, gpu.getDriverVersion())
					.executeUpdate();
					this.em.flush();
					gpuId = Long.parseLong(this.em.createNativeQuery("SELECT LAST_INSERT_ID()").getSingleResult().toString());					
				}
				// New agent-gpu
				int j = 1;
				this.em.createNativeQuery("INSERT INTO c_agent_gpu_agent (agent_id, gpu_id) VALUES(?, ?)")
				.setParameter(j++, agentId)
				.setParameter(j++, gpuId)
				.executeUpdate();
			}
		}
		// Users
		this.em.createNativeQuery(
				"DELETE FROM c_agent_user_agent WHERE agent_id = :id")
				.setParameter("id", agentId)
				.executeUpdate();
		this.em.flush();
		if (agent.getUsers() != null) {
			for (AgentUser user : agent.getUsers()) {
				i = 1;
				Long userId = null;
				try {
					userId = Long.parseLong(this.em.createNativeQuery(
							"SELECT id FROM c_agent_user WHERE name = ?")
							.setParameter(i++, user.getName())
							.getSingleResult().toString());
				} catch (Exception e) {
				}
				if (userId == null) {
					i = 1;
					this.em.createNativeQuery(
							"INSERT INTO c_agent_user (created_by, created_date, groups, name) VALUES(?, ?, ?, ?)")
					.setParameter(i++, lastModifiedBy)
					.setParameter(i++, now, TemporalType.TIMESTAMP)
					.setParameter(i++, user.getCommaSeparatedGroups())
					.setParameter(i++, user.getName())
					.executeUpdate();
					this.em.flush();
					userId = Long.parseLong(this.em.createNativeQuery("SELECT LAST_INSERT_ID()").getSingleResult().toString());
				}
				// New agent-user
				int j = 1;
				this.em.createNativeQuery("INSERT INTO c_agent_user_agent (agent_id, user_id) VALUES(?, ?)")
				.setParameter(j++, agentId)
				.setParameter(j++, userId)
				.executeUpdate();
			}
		}
		// Processes
		this.em.createNativeQuery(
				"DELETE FROM c_agent_process_agent WHERE agent_id = :id")
				.setParameter("id", agentId)
				.executeUpdate();
		this.em.flush();
		if (agent.getAgentRunningProcesses() != null) {
			for (AgentRunningProcess process : agent.getAgentRunningProcesses()) {
				i = 1;
				Long processId = null;
				try {
					processId = Long.parseLong(this.em.createNativeQuery(
							"SELECT id FROM c_agent_process WHERE name = ?")
							.setParameter(i++, process.getRunningProcess().getName())
							.getSingleResult().toString());
				} catch (Exception e) {
				}
				if (processId == null) {
					i = 1;
					this.em.createNativeQuery(
							"INSERT INTO c_agent_process (created_by, created_date, name) VALUES(?, ?, ?)")
					.setParameter(i++, lastModifiedBy)
					.setParameter(i++, now, TemporalType.TIMESTAMP)
					.setParameter(i++, process.getRunningProcess().getName())
					.executeUpdate();
					this.em.flush();
					processId = Long.parseLong(this.em.createNativeQuery("SELECT LAST_INSERT_ID()").getSingleResult().toString());					
				}
				// New agent-prcess
				int j = 1;
				this.em.createNativeQuery(
						"INSERT INTO c_agent_process_agent (cpu_percent, cpu_times, "
						+ "memory_info, pid, username, agent_id, process_id) VALUES(?, ?, ?, ?, ?, ?, ?)")
				.setParameter(j++, process.getCpuPercent())
				.setParameter(j++, process.getCpuTimes())
				.setParameter(j++, process.getMemoryInfo())
				.setParameter(j++, process.getPid())
				.setParameter(j++, process.getUsername())
				.setParameter(j++, agentId)
				.setParameter(j++, processId)
				.executeUpdate();
			}
		}
		// CPU
		this.em.createNativeQuery(
				"DELETE FROM c_agent_cpu_agent WHERE agent_id = :id")
				.setParameter("id", agentId)
				.executeUpdate();
		this.em.flush();
		if (agent.getAgentCpus() != null) {
			for (AgentCpu cpu : agent.getAgentCpus()) {
				i = 1;
				Long cpuId = null;
				try {
					cpuId = Long.parseLong(this.em.createNativeQuery(
							"SELECT id FROM c_agent_cpu WHERE brand = ? and processor = ?")
							.setParameter(i++, cpu.getCpu().getBrand())
							.setParameter(i++, cpu.getCpu().getProcessor())
							.getSingleResult().toString());
				} catch (Exception e) {
				}
				if (cpuId == null) {
					i = 1;
					this.em.createNativeQuery(
							"INSERT INTO c_agent_cpu (created_by, created_date, arch, bits, brand, count, "
							+ "extended_family, family, hz_advertised, l2_cache_associativity, l2_cache_line_size, "
							+ "l2_cache_size, logical_core_count, model, processor, pyhsical_core_count, raw_arch_string, "
							+ "vendor_id) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")
					.setParameter(i++, lastModifiedBy)
					.setParameter(i++, now, TemporalType.TIMESTAMP)
					.setParameter(i++, cpu.getCpu().getArch())
					.setParameter(i++, cpu.getCpu().getBits())
					.setParameter(i++, cpu.getCpu().getBrand())
					.setParameter(i++, cpu.getCpu().getCount())
					.setParameter(i++, cpu.getCpu().getExtendedFamily())
					.setParameter(i++, cpu.getCpu().getFamily())
					.setParameter(i++, cpu.getCpu().getHzAdvertised())
					.setParameter(i++, cpu.getCpu().getL2CacheAssociativity())
					.setParameter(i++, cpu.getCpu().getL2CacheLineSize())
					.setParameter(i++, cpu.getCpu().getL2CacheSize())
					.setParameter(i++, cpu.getCpu().getLogicalCoreCount())
					.setParameter(i++, cpu.getCpu().getModel())
					.setParameter(i++, cpu.getCpu().getProcessor())
					.setParameter(i++, cpu.getCpu().getPyhsicalCoreCount())
					.setParameter(i++, cpu.getCpu().getRawArchString())
					.setParameter(i++, cpu.getCpu().getVendorId())
					.executeUpdate();
					this.em.flush();
					cpuId = Long.parseLong(this.em.createNativeQuery("SELECT LAST_INSERT_ID()").getSingleResult().toString());
				}
				// New agent-cpu
				int j = 1;
				this.em.createNativeQuery(
						"INSERT INTO c_agent_cpu_agent (cpu_times, flags, stats, hz_actual, "
						+ "agent_id, cpu_id) VALUES(?, ?, ?, ?, ?, ?)")
				.setParameter(j++, cpu.getCommaSeparatedCpuTimes())
				.setParameter(j++, cpu.getCommaSeparatedFlags())
				.setParameter(j++, cpu.getCommaSeparatedStats())
				.setParameter(j++, cpu.getHzActual())
				.setParameter(j++, agentId)
				.setParameter(j++, cpuId)
				.executeUpdate();
			}
		}
		// Peripheral devices
		this.em.createNativeQuery(
				"DELETE FROM c_agent_peripheral_agent WHERE agent_id = :id")
				.setParameter("id", agentId)
				.executeUpdate();
		this.em.flush();
		if (agent.getAgentPeripheralDevices() != null) {
			for (AgentPeripheralDevice peripheral : agent.getAgentPeripheralDevices()) {
				i = 1;
				Long peripheralId = null;
				try {
					peripheralId = Long.parseLong(this.em.createNativeQuery(
							"SELECT id FROM c_agent_peripheral WHERE tag = ?")
							.setParameter(i++, peripheral.getPeripheralDevice().getTag())
							.getSingleResult().toString());
				} catch (Exception e) {
				}
				if (peripheralId == null) {
					i = 1;
					this.em.createNativeQuery(
							"INSERT INTO c_agent_peripheral (created_by, created_date, tag) "
							+ "VALUES(?, ?, ?)")
					.setParameter(i++, lastModifiedBy)
					.setParameter(i++, now, TemporalType.TIMESTAMP)
					.setParameter(i++, peripheral.getPeripheralDevice().getTag())
					.executeUpdate();
					this.em.flush();
					peripheralId = Long.parseLong(this.em.createNativeQuery("SELECT LAST_INSERT_ID()").getSingleResult().toString());
				}
				// New agent-peripheral
				int j = 1;
				this.em.createNativeQuery(
						"INSERT INTO c_agent_peripheral_agent (device_id, device_path, agent_id, peripheral_id) "
						+ "VALUES(?, ?, ?, ?)")
				.setParameter(j++, peripheral.getDeviceId())
				.setParameter(j++, peripheral.getDevicePath())
				.setParameter(j++, agentId)
				.setParameter(j++, peripheralId)
				.executeUpdate();
			}
		}
		System.out.println("SUCCESS");
	}

	public Agent getAgentByMessagingId(String messagingId) {
		Assert.notNull(messagingId, "Messaging ID must not be null.");
		return this.agentRepository.getAgentByMessagingId(messagingId);
	}

	public Page<Agent> getAgents(Pageable pageable, String search) {
		Assert.notNull(pageable, "Pageable must not be null.");
		if (search != null && !search.isEmpty()) {
			return this.agentRepository
					.findByHostNameContainingOrIpAddressesContainingOrPlatformSystemContainingAllIgnoringCase(search,
							search, search, pageable);
		}
		return this.agentRepository.findAll(pageable);
	}

	public List<Agent> getAgents() {
		return this.agentRepository.findAll();
	}

	public Agent getAgent(Long id) {
		Assert.notNull(id, "Agent ID must not be null.");
		return this.agentRepository.findOne(id);
	}

	public List<Tag> getTags() {
		return this.tagRepository.findAll();
	}

	@Transactional(rollbackFor = Exception.class)
	public void saveTags(Agent agent, List<Tag> tags) {
		Query query = this.em.createNativeQuery("DELETE FROM c_agent_tag_agent WHERE agent_id = :agentId");
		query.setParameter("agentId", agent.getId());
		query.executeUpdate();
		if (tags != null) {
			for (Tag tag : tags) {
				Tag _tag = this.tagRepository.findByName(tag.getName());
				Long id = _tag == null ? id = this.tagRepository.save(tag).getId() : _tag.getId();
				Query query2 = this.em.createNativeQuery(
						"INSERT INTO c_agent_tag_agent (agent_id, tag_id) VALUES (:agentId, :tagId)");
				query2.setParameter("agentId", agent.getId());
				query2.setParameter("tagId", id);
				query2.executeUpdate();
			}
		}
	}

	public AgentUser getAgentUser(String name) {
		return this.agentUserRepository.findByNameIgnoreCase(name);
	}

	@SuppressWarnings("unchecked")
	public Agent getAgentDetail(Long id, boolean fetchChildren) {
		Assert.notNull(id, "Agent ID must not be null.");
		Query query = em.createNativeQuery("SELECT TYPE, HOST_NAME, IP_ADDRESSES, MAC_ADDRESSES FROM c_agent WHERE ID = :id");
		query.setParameter("id", id);
		Agent agent = new Agent((Object[]) query.getSingleResult());
		em.detach(agent); // So that, it wont query children!
		if (fetchChildren && agent != null) {
			// Users
			// @formatter:off
			Query query2 = em.createNativeQuery(
					"SELECT au.NAME, au.GROUPS FROM c_agent_user au "
					+ "INNER JOIN c_agent_user_agent aua ON (au.id = aua.user_id AND aua.agent_id = :agentId)");
			// @formatter:on
			query2.setParameter("agentId", id);
			List<Object[]> users = query2.getResultList();
			for (Object[] user : users) {
				AgentUser aUser = new AgentUser();
				aUser.setName(user[0].toString());
				aUser.setCommaSeparatedGroups(user[1] != null ? user[1].toString() : null);
				agent.addUser(aUser);
			}
			// Packages
			// @formatter:off
			Query query3 = em.createNativeQuery(
					"SELECT ap.NAME, ap.VERSION FROM c_agent_package ap "
					+ "INNER JOIN c_agent_package_agent apa ON (ap.id = apa.installed_package_id AND apa.agent_id = :agentId)");
			// @formatter:on
			query3.setParameter("agentId", id);
			List<Object[]> packages = query3.getResultList();
			for (Object[] pkg : packages) {
				InstalledPackage aPackage = new InstalledPackage();
				aPackage.setName(pkg[0].toString());
				aPackage.setVersion(pkg[1] != null ? pkg[1].toString() : null);
				agent.addInstalledPackage(aPackage);
			}
		}
		return agent;
	}

	public Agent getAgentByMessagingIdOrMacAddresses(String messagingId, List<String> macAddresses) {
		Assert.notNull(messagingId, "Messaging ID must not be null.");
		try {
			Object[] res = (Object[]) this.em.createNativeQuery(
					"SELECT id, type FROM c_agent WHERE messaging_id = ?")
					.setParameter(1, messagingId)
					.getSingleResult();
			if (res != null && res.length == 2) {
				Agent agent = new Agent();
				agent.setId(Long.parseLong(res[0].toString()));
				agent.setType(AgentType.getType(Integer.parseInt(res[1].toString())));
				return agent;
			}
		} catch (Exception e)  {
		}
		if (macAddresses != null) {
			for (String macAddr : macAddresses) {
				try {
					Object[] res = (Object[]) this.em.createNativeQuery(
							"SELECT id, type FROM c_agent WHERE mac_addresses LIKE CONCAT('%', ?, '%')")
							.setParameter(1, macAddr)
							.getSingleResult();
					if (res != null && res.length == 2) {
						Agent agent = new Agent();
						agent.setId(Long.parseLong(res[0].toString()));
						agent.setType(AgentType.getType(Integer.parseInt(res[1].toString())));
						return agent;
					}
				} catch (Exception e) {
				}
			}
		}
		return null;
	}

}

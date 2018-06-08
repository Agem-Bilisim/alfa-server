package tr.com.agem.alfa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import tr.com.agem.alfa.model.Bios;
import tr.com.agem.alfa.model.Cpu;
import tr.com.agem.alfa.model.Disk;
import tr.com.agem.alfa.model.Gpu;
import tr.com.agem.alfa.model.Memory;
import tr.com.agem.alfa.model.NetworkInterface;
import tr.com.agem.alfa.repository.BiosRepository;
import tr.com.agem.alfa.repository.CpuRepository;
import tr.com.agem.alfa.repository.DiskRepository;
import tr.com.agem.alfa.repository.GpuRepository;
import tr.com.agem.alfa.repository.InetRepository;
import tr.com.agem.alfa.repository.MemoryRepository;

/**
 * @author <a href="mailto:emre.akkaya@agem.com.tr">Emre Akkaya</a>
 */
@Component
@Transactional
public class HardwareService {

	private final BiosRepository biosRepository;
	private final CpuRepository cpuRepository;
	private final DiskRepository diskRepository;
	private final GpuRepository gpuRepository;
	private final InetRepository inetRepository;
	private final MemoryRepository memoryRepository;

	@Autowired
	public HardwareService(BiosRepository biosRepository, CpuRepository cpuRepository, DiskRepository diskRepository,
			GpuRepository gpuRepository, InetRepository inetRepository, MemoryRepository memoryRepository) {
		this.biosRepository = biosRepository;
		this.cpuRepository = cpuRepository;
		this.diskRepository = diskRepository;
		this.gpuRepository = gpuRepository;
		this.inetRepository = inetRepository;
		this.memoryRepository = memoryRepository;
	}

	public Page<Cpu> getCpus(Pageable pageable, String search) {
		Assert.notNull(pageable, "Pageable must not be null.");
		if (search != null && !search.isEmpty()) {
			// TODO
			return null;
		}
		return this.cpuRepository.findAll(pageable);
	}

	public Page<Gpu> getGpus(Pageable pageable, String search) {
		Assert.notNull(pageable, "Pageable must not be null.");
		if (search != null && !search.isEmpty()) {
			// TODO
			return null;
		}
		return this.gpuRepository.findAll(pageable);
	}

	public Page<Disk> getDisks(Pageable pageable, String search) {
		Assert.notNull(pageable, "Pageable must not be null.");
		if (search != null && !search.isEmpty()) {
			// TODO
			return null;
		}
		return this.diskRepository.findAll(pageable);
	}

	public Page<Memory> getMemories(Pageable pageable, String search) {
		Assert.notNull(pageable, "Pageable must not be null.");
		if (search != null && !search.isEmpty()) {
			// TODO
			return null;
		}
		return this.memoryRepository.findAll(pageable);
	}

	public Page<Bios> getBioss(Pageable pageable, String search) {
		Assert.notNull(pageable, "Pageable must not be null.");
		if (search != null && !search.isEmpty()) {
			// TODO
			return null;
		}
		return this.biosRepository.findAll(pageable);
	}

	public Page<NetworkInterface> getNetworkInterfaces(Pageable pageable, String search) {
		Assert.notNull(pageable, "Pageable must not be null.");
		if (search != null && !search.isEmpty()) {
			// TODO
			return null;
		}
		return this.inetRepository.findAll(pageable);
	}

	public List<Disk> getDisks() {
		return this.diskRepository.findAll();
	}

	public List<Memory> getMemories() {
		return this.memoryRepository.findAll();
	}

	public List<Bios> getBioss() {
		return this.biosRepository.findAll();
	}

	public List<NetworkInterface> getNetworkInterfaces() {
		return this.inetRepository.findAll();
	}

	public Cpu saveCpu(Cpu cpu) {
		Assert.notNull(cpu, "Cpu must not be null.");
		Cpu c = null;
		if (cpu.getId() != null && (c = cpuRepository.findOne(cpu.getId())) != null) {
			// Update
			c.setL2CacheSize(cpu.getL2CacheSize());
			c.setBits(cpu.getBits());
			c.setExtendedFamily(cpu.getExtendedFamily());
			c.setL2CacheAssociativity(cpu.getL2CacheAssociativity());
			c.setL2CacheLineSize(cpu.getL2CacheLineSize());
			c.setHzAdvertised(cpu.getHzAdvertised());
			c.setProcessor(cpu.getProcessor());
			c.setPyhsicalCoreCount(cpu.getPyhsicalCoreCount());
			c.setLogicalCoreCount(cpu.getLogicalCoreCount());
			c.setBrand(cpu.getBrand());
			c.setRawArchString(cpu.getRawArchString());
			return this.cpuRepository.save(c);
		}
		// Create
		return this.cpuRepository.save(cpu);
	}

	public Gpu saveGpu(Gpu gpu) {
		Assert.notNull(gpu, "Gpu must not be null.");
		Gpu g = null;
		if (gpu.getId() != null && (g = gpuRepository.findOne(gpu.getId())) != null) {
			// Update
			g.setSubsystem(gpu.getSubsystem());
			g.setKernel(gpu.getKernel());
			g.setMemory(gpu.getMemory());
			g.setDriverDate(gpu.getDriverDate());
			g.setDriverVersion(gpu.getDriverVersion());
			g.setAgents(gpu.getAgents());
			return this.gpuRepository.saveAndFlush(g);
		}
		// Create
		return this.gpuRepository.save(gpu);
	}

	public Disk saveDisk(Disk disk) {
		Assert.notNull(disk, "Disk must not be null.");
		Disk d = null;
		if (disk.getId() != null && (d = diskRepository.findOne(disk.getId())) != null) {
			// Update
			d.setVendor(disk.getVendor());
			d.setDescription(disk.getDescription());
			d.setVersion(disk.getVersion());
			d.setProduct(disk.getProduct());
			d.setSerial(disk.getSerial());
			return this.diskRepository.save(d);
		}
		// Create
		return this.diskRepository.save(disk);
	}

	public Memory saveMemory(Memory memory) {
		Assert.notNull(memory, "Memory must not be null.");
		Memory m = null;
		if (memory.getId() != null && (m = memoryRepository.findOne(memory.getId())) != null) {
			// Update
			m.setSpeed(memory.getSpeed());
			m.setSize(memory.getSize());
			m.setType(memory.getType());
			m.setManufacturer(memory.getManufacturer());
			return this.memoryRepository.save(m);
		}
		// Create
		return this.memoryRepository.save(memory);
	}

	public Bios saveBios(Bios bios) {
		Assert.notNull(bios, "Bios must not be null.");
		Bios b = null;
		if (bios.getId() != null && (b = biosRepository.findOne(bios.getId())) != null) {
			// Update
			b.setVendor(bios.getVendor());
			b.setVersion(bios.getVersion());
			b.setReleaseDate(bios.getReleaseDate());
			return this.biosRepository.save(b);
		}
		// Create
		return this.biosRepository.save(bios);
	}

	public NetworkInterface saveNetworkInterface(NetworkInterface networkInterface) {
		Assert.notNull(networkInterface, "NetworkInterface must not be null.");
		NetworkInterface ni = null;
		if (networkInterface.getId() != null && (ni = inetRepository.findOne(networkInterface.getId())) != null) {
			// Update
			ni.setVendor(networkInterface.getVendor());
			ni.setVersion(networkInterface.getVersion());
			ni.setProduct(networkInterface.getProduct());
			ni.setCapabilities(networkInterface.getCapabilities());
			return this.inetRepository.save(ni);
		}
		// Create
		return this.inetRepository.save(networkInterface);
	}

	public Cpu getCpu(Long id) {
		Assert.notNull(id, "ID must not be null");
		return this.cpuRepository.findOne(id);
	}

	public Gpu getGpu(Long id) {
		Assert.notNull(id, "ID must not be null");
		return this.gpuRepository.findOne(id);
	}

	public Disk getDisk(Long id) {
		Assert.notNull(id, "ID must not be null");
		return this.diskRepository.findOne(id);
	}

	public List<Gpu> getGpus() {
		return this.gpuRepository.findAll();
	}

	public Memory getMemory(Long id) {
		Assert.notNull(id, "ID must not be null");
		return this.memoryRepository.findOne(id);
	}

	public Bios getBios(Long id) {
		Assert.notNull(id, "ID must not be null");
		return this.biosRepository.findOne(id);
	}

	public NetworkInterface getNetworkInterface(Long id) {
		Assert.notNull(id, "ID must not be null");
		return this.inetRepository.findOne(id);
	}
	
}

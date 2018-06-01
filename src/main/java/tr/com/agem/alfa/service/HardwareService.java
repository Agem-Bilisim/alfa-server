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
import tr.com.agem.alfa.repository.PeripheralRepository;

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
	private final PeripheralRepository peripheralRepository;

	@Autowired
	public HardwareService(BiosRepository biosRepository, CpuRepository cpuRepository,
			DiskRepository diskRepository, GpuRepository gpuRepository, InetRepository inetRepository,
			MemoryRepository memoryRepository, PeripheralRepository peripheralRepository) {
		this.biosRepository = biosRepository;
		this.cpuRepository = cpuRepository;
		this.diskRepository = diskRepository;
		this.gpuRepository = gpuRepository;
		this.inetRepository = inetRepository;
		this.memoryRepository = memoryRepository;
		this.peripheralRepository = peripheralRepository;
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

}

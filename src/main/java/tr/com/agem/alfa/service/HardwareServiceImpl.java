package tr.com.agem.alfa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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
@Component("hardwareService")
@Transactional
public class HardwareServiceImpl implements HardwareService {

	private final BiosRepository biosRepository;
	private final CpuRepository cpuRepository;
	private final DiskRepository diskRepository;
	private final GpuRepository gpuRepository;
	private final InetRepository inetRepository;
	private final MemoryRepository memoryRepository;
	private final PeripheralRepository peripheralRepository;

	@Autowired
	public HardwareServiceImpl(BiosRepository biosRepository, CpuRepository cpuRepository,
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

}

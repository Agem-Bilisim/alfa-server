package tr.com.agem.alfa.controller;

import static com.google.common.base.Preconditions.checkNotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import tr.com.agem.alfa.model.Bios;
import tr.com.agem.alfa.model.Cpu;
import tr.com.agem.alfa.model.Disk;
import tr.com.agem.alfa.model.Gpu;
import tr.com.agem.alfa.model.Memory;
import tr.com.agem.alfa.model.NetworkInterface;
import tr.com.agem.alfa.service.HardwareService;

/**
 * @author <a href="mailto:emre.akkaya@agem.com.tr">Emre Akkaya</a>
 */
@Controller
public class HardwareController {

	private static final Logger log = LoggerFactory.getLogger(HardwareController.class);

	private final HardwareService hardwareService;

	@Value("${sys.page-size}")
	private Integer sysPageSize;

	@Autowired
	public HardwareController(HardwareService hardwareService) {
		this.hardwareService = hardwareService;
	}

	@GetMapping("/hardware/list")
	public String getListPage() {
		return "hardware/list";
	}
	
	@GetMapping("/hardware/cpu/list-paginated")
	public ResponseEntity<?> handleCpuList(@RequestParam(value = "search", required = false) String search,
			Pageable pageable) {
		RestResponseBody result = new RestResponseBody();
		try {
			Page<Cpu> cpus = hardwareService.getCpus(pageable, search);
			result.add("cpus", checkNotNull(cpus, "CPUs not found."));
		} catch (Exception e) {
			log.error("Exception occurred when trying to find CPUs, assuming invalid parameters", e);
			result.setMessage(e.getMessage());
			return ResponseEntity.badRequest().body(result);
		}
		return ResponseEntity.ok(result);
	}

	@GetMapping("/hardware/gpu/list-paginated")
	public ResponseEntity<?> handleGpuList(@RequestParam(value = "search", required = false) String search,
			Pageable pageable) {
		RestResponseBody result = new RestResponseBody();
		try {
			Page<Gpu> gpus = hardwareService.getGpus(pageable, search);
			result.add("gpus", checkNotNull(gpus, "GPUs not found."));
		} catch (Exception e) {
			log.error("Exception occurred when trying to find GPUs, assuming invalid parameters", e);
			result.setMessage(e.getMessage());
			return ResponseEntity.badRequest().body(result);
		}
		return ResponseEntity.ok(result);
	}

	@GetMapping("/hardware/disk/list-paginated")
	public ResponseEntity<?> handleDiskList(@RequestParam(value = "search", required = false) String search,
			Pageable pageable) {
		RestResponseBody result = new RestResponseBody();
		try {
			Page<Disk> disks = hardwareService.getDisks(pageable, search);
			result.add("disks", checkNotNull(disks, "Disks not found."));
		} catch (Exception e) {
			log.error("Exception occurred when trying to find disks, assuming invalid parameters", e);
			result.setMessage(e.getMessage());
			return ResponseEntity.badRequest().body(result);
		}
		return ResponseEntity.ok(result);
	}

	@GetMapping("/hardware/memory/list-paginated")
	public ResponseEntity<?> handleMemoryList(@RequestParam(value = "search", required = false) String search,
			Pageable pageable) {
		RestResponseBody result = new RestResponseBody();
		try {
			Page<Memory> memories = hardwareService.getMemories(pageable, search);
			result.add("memories", checkNotNull(memories, "Memories not found."));
		} catch (Exception e) {
			log.error("Exception occurred when trying to find memories, assuming invalid parameters", e);
			result.setMessage(e.getMessage());
			return ResponseEntity.badRequest().body(result);
		}
		return ResponseEntity.ok(result);
	}
	
	@GetMapping("/hardware/bios/list-paginated")
	public ResponseEntity<?> handleBiosList(@RequestParam(value = "search", required = false) String search,
			Pageable pageable) {
		RestResponseBody result = new RestResponseBody();
		try {
			Page<Bios> bioss = hardwareService.getBioss(pageable, search);
			result.add("bioss", checkNotNull(bioss, "BIOSs not found."));
		} catch (Exception e) {
			log.error("Exception occurred when trying to find BIOSs, assuming invalid parameters", e);
			result.setMessage(e.getMessage());
			return ResponseEntity.badRequest().body(result);
		}
		return ResponseEntity.ok(result);
	}

	@GetMapping("/hardware/networkInterface/list-paginated")
	public ResponseEntity<?> handleNetworkList(@RequestParam(value = "search", required = false) String search,
			Pageable pageable) {
		RestResponseBody result = new RestResponseBody();
		try {
			Page<NetworkInterface> networkInterfaces = hardwareService.getNetworkInterfaces(pageable, search);
			result.add("networkInterfaces", checkNotNull(networkInterfaces, "Network interfaces not found."));
		} catch (Exception e) {
			log.error("Exception occurred when trying to find network interfaces, assuming invalid parameters", e);
			result.setMessage(e.getMessage());
			return ResponseEntity.badRequest().body(result);
		}
		return ResponseEntity.ok(result);
	}

}

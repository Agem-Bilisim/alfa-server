package tr.com.agem.alfa.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

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
		log.debug("Getting hardware list page");
		return "hardware/list";
	}

}

package tr.com.agem.alfa.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author <a href="mailto:emre.akkaya@agem.com.tr">Emre Akkaya</a>
 */
@PreAuthorize("hasRole('ROLE_ADMIN')")
@Controller
public class MonitorController {

	private static final Logger log = LoggerFactory.getLogger(MonitorController.class);


	@Autowired
	public MonitorController() {
	}
	
	@GetMapping("/monitor/main")
	public String mainPage() { 
		log.debug("Monitor page");
		return "monitor/main";
	}


}

package tr.com.agem.alfa.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

	private static final Logger log = LoggerFactory.getLogger(HomeController.class);

	@GetMapping(path = { "/", "index" })
	public String getIndexPage() {
		log.info("Getting home page");
		return "redirect:/home";
	}

	@GetMapping("/home")
	public String getHomePage() {
		log.info("Getting home page");
		return "home";
	}

}

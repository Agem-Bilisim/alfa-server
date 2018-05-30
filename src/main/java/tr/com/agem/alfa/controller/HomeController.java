package tr.com.agem.alfa.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author <a href="mailto:emre.akkaya@agem.com.tr">Emre Akkaya</a>
 */
@Controller
public class HomeController {

	@GetMapping(path = { "/", "index" })
	public String getIndexPage() {
		return "redirect:/home";
	}

	@GetMapping("/home")
	public String getHomePage() {
		return "home";
	}

}

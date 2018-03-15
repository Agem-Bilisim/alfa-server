package tr.com.agem.alfa.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 * @author <a href="mailto:emre.akkaya@agem.com.tr">Emre Akkaya</a>
 *
 */
@Controller
public class LoginController {

	private static final Logger log = LoggerFactory.getLogger(LoginController.class);

	@GetMapping("/login")
	public ModelAndView getLoginPage(@RequestParam(required=false) String error, @RequestParam(required=false) String logout) {
		log.info("Getting login page, error={}, logout={}", error, logout);
		ModelAndView modelAndView = new ModelAndView("login");
		if (error != null) {
			modelAndView.addObject("error", error);
		}
		if (logout != null) {
			modelAndView.addObject("logout", logout);
		}
		return modelAndView;
	}

}

package tr.com.agem.alfa.controller;

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

	@GetMapping("/login")
	public ModelAndView getLoginPage(@RequestParam(required=false) String error, @RequestParam(required=false) String logout) {
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

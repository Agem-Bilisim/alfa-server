package tr.com.agem.alfa.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * Global exception handler for controllers.
 * 
 * @author <a href="mailto:emre.akkaya@agem.com.tr">Emre Akkaya</a>
 */
@ControllerAdvice
public class ExceptionHandlerControllerAdvice {

	private static final Logger log = LoggerFactory.getLogger(ExceptionHandlerControllerAdvice.class);

	@ExceptionHandler(Throwable.class)
	public ModelAndView handleError500(HttpServletRequest request, HttpServletResponse response, Exception e) {
		log.error("Request: {} raised {}", new Object[] { request.getRequestURL(), e.getMessage() });
		return response.isCommitted() ? null : new ModelAndView("error/500");
	}

	@ExceptionHandler(NoHandlerFoundException.class)
	public ModelAndView handleError404(HttpServletRequest request, Exception e) {
		log.error("Request: {} raised {}", new Object[] { request.getRequestURL(), e.getMessage() });
		return new ModelAndView("error/404");
	}

	@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Malformed JSON request. Check API docs")
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public void handleException(HttpServletRequest request, Exception e) {
		log.error("Request: {} raised {}", new Object[] { request.getRequestURL(), e.getMessage() });
	}

}

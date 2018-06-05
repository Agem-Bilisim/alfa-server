/**
 * 
 */
package tr.com.agem.alfa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * @author <a href="mailto:ali.ozeren@agem.com.tr">Ali Ozkan Ozeren</a>
 *
 */
@Component
public class SpringTools {

	private static SpringTools instance = null;
	
	private final ApplicationContext context;

	
	@Autowired
	public SpringTools(ApplicationContext context) {
		this.context = context;
		instance = this;
	}

	
	public ApplicationContext getContext() {
		return context;
	}

	public static SpringTools getInstance() {
		return instance;
	}
	
}

package tr.com.agem.alfa.bpmn.inputs;

import java.util.HashMap;
import java.util.Map;

import tr.com.agem.alfa.bpmn.utils.AlfaFormToMapConverterInterface;

/**
 * @author <a href="mailto:ali.ozeren@agem.com.tr">Ali Ozkan Ozeren</a>
 *
 */
public class AlfaBpmnForm2MapConverter implements AlfaFormToMapConverterInterface {

	
	/* (non-Javadoc)
	 * @see tr.com.agem.alfa.bpmn.utils.AlfaFormToMapConverterInterface#formToMap(java.lang.Object)
	 */
	@Override
	public Map<String, String> formToMap(Object parameters) {
		
		Map<String,String> vars = new HashMap<String, String>();
		
		@SuppressWarnings("unchecked")
		Map<String,String> params = (Map<String, String>) parameters;
		
		for (String key : params.keySet()) {
			if (key.startsWith("pact_")) {
				vars.put(key.replaceFirst("pact_", ""), params.get(key));
			}
		}
		
		return vars;
	}

}

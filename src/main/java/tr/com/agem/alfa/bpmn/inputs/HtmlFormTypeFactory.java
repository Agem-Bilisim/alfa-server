package tr.com.agem.alfa.bpmn.inputs;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import tr.com.agem.alfa.bpmn.types.AbstractCheckboxFormType;
import tr.com.agem.alfa.bpmn.types.AbstractComboboxFormType;
import tr.com.agem.alfa.bpmn.types.AbstractCurrencyFormType;
import tr.com.agem.alfa.bpmn.types.AbstractDateFormType;
import tr.com.agem.alfa.bpmn.types.AbstractIntegerFormType;
import tr.com.agem.alfa.bpmn.types.AbstractNumericFormType;
import tr.com.agem.alfa.bpmn.types.AbstractPasswordFormType;
import tr.com.agem.alfa.bpmn.types.AbstractPercentageFormType;
import tr.com.agem.alfa.bpmn.types.AbstractTextboxFormType;
import tr.com.agem.alfa.bpmn.types.ActivitiFormTypeInterface;
import tr.com.agem.alfa.bpmn.types.AlfaFormTypeException;
import tr.com.agem.alfa.bpmn.types.FormTypeFactoryInterface;
import tr.com.agem.alfa.bpmn.utils.Utils;


/**
 * @author <a href="mailto:ali.ozeren@agem.com.tr">Ali Ozkan Ozeren</a>
 *
 */
public class HtmlFormTypeFactory implements FormTypeFactoryInterface
{
	
	private final static Logger logger = Logger.getLogger(HtmlFormTypeFactory.class);
	
	private static final Map<String, Class<?>> formTypes;
	static {
		formTypes= new HashMap<String, Class<?>>();
		
		// activiti-explorer form types
		formTypes.put("boolean", HtmlCheckboxFormType.class);
		formTypes.put("enum", HtmlSelectFormType.class);
		formTypes.put("string", HtmlTextboxFormType.class);
		formTypes.put("long", HtmlDecimalFormType.class);
		
		formTypes.put(AbstractComboboxFormType.NAME, HtmlSelectFormType.class);
		formTypes.put(AbstractIntegerFormType.NAME, HtmlIntegerFormType.class);
		formTypes.put(AbstractNumericFormType.NAME, HtmlNumericFormType.class);
		formTypes.put(AbstractCurrencyFormType.NAME, HtmlCurrencyFormType.class);
		formTypes.put(AbstractPercentageFormType.NAME, HtmlPercentageFormType.class);
		formTypes.put(AbstractTextboxFormType.NAME, HtmlTextboxFormType.class);
		formTypes.put(AbstractDateFormType.NAME, HtmlDateFormType.class);
		formTypes.put(AbstractPasswordFormType.NAME, HtmlPasswordFormType.class);
		formTypes.put(AbstractCheckboxFormType.NAME, HtmlCheckboxFormType.class);
	}
	
	public ActivitiFormTypeInterface getFormTypeInstance(String name)
	{
		if (formTypes.containsKey(name)) {
			try {
				return (ActivitiFormTypeInterface) formTypes.get(name).newInstance();
			} catch (Exception e) {
				Utils.getInstance().logError(logger, e);
			} 
		}
		
		throw new AlfaFormTypeException("Error in creating object with the type named " + name );
	}

}

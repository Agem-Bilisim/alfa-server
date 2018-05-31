package tr.com.agem.alfa.bpmn.inputs;

import tr.com.agem.alfa.bpmn.types.CustomFormTypes;

/**
 * @author <a href="mailto:ali.ozeren@agem.com.tr">Ali Ozkan Ozeren</a>
 *
 */
public class HtmlCustomFormTypes extends CustomFormTypes 
{

	public HtmlCustomFormTypes()
	{
		super.setFactory(new HtmlFormTypeFactory());
	}
}
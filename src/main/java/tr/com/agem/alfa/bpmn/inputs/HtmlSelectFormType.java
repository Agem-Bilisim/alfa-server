package tr.com.agem.alfa.bpmn.inputs;

import org.activiti.engine.form.FormProperty;

import tr.com.agem.alfa.bpmn.types.AbstractComboboxFormType;

/**
 * @author <a href="mailto:ali.ozeren@agem.com.tr">Ali Ozkan Ozeren</a>
 *
 */
public class HtmlSelectFormType extends AbstractComboboxFormType  
{
	private static final long serialVersionUID = 3868249214623992954L;

	public String renderInputForType(FormProperty property) 
	{
		StringBuffer str = new StringBuffer("<div class=\"form-group\"> ");

		str.append("<label>");
		str.append(super.getLabel());
		str.append("</label>");
		str.append("<select class=\"form-control select2\" id=\"");
		str.append(super.getId());
		str.append("\" name=\"");
		str.append(super.getId());
		str.append("\" ");
		str.append(HTMLInputUtils.getInstance().prepareAttributes(this, super.getMap(), "options"));
		str.append(" >");
		
		for ( String key : super.getOptions().keySet()) {
			str.append("<option value='")
				.append(key)
				.append("'");
			if (property.getValue() != null && property.getValue().equals(key)) {
				str.append(" selected ");
			}
				
			str.append(" > ")
				.append(super.getOptions().get(key))
				.append("</option>");
		}
		str.append("</select>");

		str.append("</div>");
		
		return str.toString();
		
	}
	
}

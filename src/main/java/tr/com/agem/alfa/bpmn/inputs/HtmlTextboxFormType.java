package tr.com.agem.alfa.bpmn.inputs;

import org.activiti.engine.form.FormProperty;
import org.apache.commons.lang3.StringEscapeUtils;

import tr.com.agem.alfa.bpmn.types.AbstractTextboxFormType;

/**
 * @author <a href="mailto:ali.ozeren@agem.com.tr">Ali Ozkan Ozeren</a>
 *
 */
public class HtmlTextboxFormType extends AbstractTextboxFormType  
{

	private static final long serialVersionUID = 3868249214623992954L;

	public String renderInputForType(FormProperty property) 
	{
	
		StringBuffer str = new StringBuffer("<div class=\"form-group\"> ");

		str.append("<label for=\"");
		str.append("pact_").append(super.getId());
		str.append("\">");
		str.append(super.getLabel());
		str.append("</label>");
		str.append("<input type=\"text\" class=\"form-control\" name=\"");
		str.append("pact_").append(super.getId());
		str.append("\" id=\"");
		str.append("pact_").append(super.getId());
		str.append("\" ");

		if (property.getValue() != null) {
			str.append(" value=\"").append(StringEscapeUtils.escapeHtml4(property.getValue())).append("\" ");
		}
		
		str.append(HTMLInputUtils.getInstance().prepareAttributes(this, super.getMap()));

		str.append(" />");
		str.append("</div>");
		
		return str.toString();
		
	}

}

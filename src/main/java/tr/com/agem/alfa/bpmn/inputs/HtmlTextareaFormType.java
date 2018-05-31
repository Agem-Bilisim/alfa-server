package tr.com.agem.alfa.bpmn.inputs;

import org.activiti.engine.form.FormProperty;
import org.apache.commons.lang3.StringEscapeUtils;

import tr.com.agem.alfa.bpmn.types.AbstractTextareaFormType;

/**
 * @author <a href="mailto:ali.ozeren@agem.com.tr">Ali Ozkan Ozeren</a>
 *
 */
public class HtmlTextareaFormType extends AbstractTextareaFormType  
{

	private static final long serialVersionUID = 3868249211623992954L;

	public String renderInputForType(FormProperty property) 
	{
		
		StringBuffer str = new StringBuffer("<div class=\"form-group\"> ");

		str.append("<label for=\"");
		str.append(super.getId());
		str.append("\">");
		str.append(super.getLabel());
		str.append("</label>");
		str.append("<textarea class=\"form-control\" name=\"");
		str.append(super.getId());
		str.append("\" id=\"");
		str.append(super.getId());
		str.append("\" ");

		str.append(HTMLInputUtils.getInstance().prepareAttributes(this, super.getMap()));
		
		

		str.append(">");
		if (property.getValue() != null) {
			str.append(StringEscapeUtils.escapeHtml4(property.getValue()));
		}
		str.append("</textarea>");
		str.append("</div>");
		
		return str.toString();
		
	}

}

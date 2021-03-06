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
		str.append("pact_").append(super.getId());
		str.append("\">");
		str.append(super.getLabel());
		str.append("</label>");
		str.append("<textarea class=\"form-control\" name=\"");
		str.append("pact_").append(super.getId());
		str.append("\" id=\"");
		str.append("pact_").append(super.getId());
		str.append("\" ");
		
		if (super.getRows() != 0) {
			str.append(" rows= \"").append(super.getRows()).append("\" ");
		}
		if (super.getCols() != 0) {
			str.append(" cols= \"").append(super.getCols()).append("\" ");
		}

		str.append(HTMLInputUtils.getInstance().prepareAttributes(this, super.getMap(), "rows","cols"));
		
		

		str.append(">");
		if (property.getValue() != null) {
			str.append(StringEscapeUtils.escapeHtml4(property.getValue()));
		}
		str.append("</textarea>");
		str.append("</div>");
		
		return str.toString();
		
	}

}

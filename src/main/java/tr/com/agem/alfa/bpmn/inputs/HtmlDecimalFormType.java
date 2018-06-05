package tr.com.agem.alfa.bpmn.inputs;

import org.activiti.engine.form.FormProperty;

import tr.com.agem.alfa.bpmn.types.AbstractDecimalFormType;

/**
 * @author <a href="mailto:ali.ozeren@agem.com.tr">Ali Ozkan Ozeren</a>
 *
 */
public class HtmlDecimalFormType extends AbstractDecimalFormType  
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
		str.append("<input type=\"text\" class=\"form-control numeric-input\" name=\"");
		str.append("pact_").append(super.getId());
		str.append("\" id=\"");
		str.append("pact_").append(super.getId());
		str.append("\" ");

		if (property.getValue() != null) {
			str.append(" value=\"").append(property.getValue()).append("\" ");
		}
		
		if (super.getMap().get("data-inputmask") == null) {
			str.append(" data-inputmask = \" 'alias': 'decimal', 'autoGroup':'true' ");
			if (super.getMap().get("min") != null) {
				str.append(" 'min' : '").append(super.getMap().get("min")).append("' ");
			}
			if (super.getMap().get("max") != null) {
				str.append(" 'max' : '").append(super.getMap().get("max")).append("' ");
			}
			str.append(" \" ");
		}
		
		str.append(HTMLInputUtils.getInstance().prepareAttributes(this, super.getMap()));

		str.append(" />");
		str.append("</div>");
		
		return str.toString();

	}

}

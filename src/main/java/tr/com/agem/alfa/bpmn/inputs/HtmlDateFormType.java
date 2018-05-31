package tr.com.agem.alfa.bpmn.inputs;


import org.activiti.engine.form.FormProperty;

import tr.com.agem.alfa.bpmn.types.AbstractDateFormType;

/**
 * @author <a href="mailto:ali.ozeren@agem.com.tr">Ali Ozkan Ozeren</a>
 *
 */
public class HtmlDateFormType extends AbstractDateFormType 
{
	private static final long serialVersionUID = 3868249214623992114L;

	public Object renderInputForType(FormProperty property) {

		StringBuffer str = new StringBuffer("<div class=\"form-group\"> ");

		str.append("<label for=\"");
		str.append(super.getId());
		str.append("\">");
		str.append(super.getLabel());
		str.append("</label>");
		str.append("<div class=\"input-group date\">");	
		str.append("<input type=\"text\" class=\"form-control date-input pull-right\" name=\"");
		str.append(super.getId());
		str.append("\" id=\"");
		str.append(super.getId());
		str.append("\" ");
		str.append(" data-date-format=\"");
		if (super.getDatePattern() == null) {
			str.append("dd/mm/yyyy");
		} else {
			str.append(super.getDatePattern());
		}
		str.append("\"");

		if (property.getValue() != null) {
			str.append(" value=\"").append(property.getValue()).append("\" ");
		}

		str.append(HTMLInputUtils.getInstance().prepareAttributes(this, super.getMap(),"data-date-format"));

		str.append(" /><div class=\"input-group-addon\"><i class=\"fa fa-calendar\"></i></div></div>");
		str.append("</div>");

		return str.toString();
	}
}

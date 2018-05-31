package tr.com.agem.alfa.bpmn.inputs;

import org.activiti.engine.form.FormProperty;

import tr.com.agem.alfa.bpmn.types.AbstractCheckboxFormType;

/**
 * @author <a href="mailto:ali.ozeren@agem.com.tr">Ali Ozkan Ozeren</a>
 * 
 */
public class HtmlCheckboxFormType extends AbstractCheckboxFormType  
{

	private static final long serialVersionUID = 3868249214623992954L;

	public String renderInputForType(FormProperty property) 
	{
		
		StringBuffer str = new StringBuffer("<div class=\"form-group\"> ");

		str.append("<div class=\"checkbox\">");
		str.append("<label style=\"padding-left:0px;\">");
		str.append("<input type=\"checkbox\" class=\"minimal\" name=\"");
		str.append(super.getId());
		str.append("\" id=\"");
		str.append(super.getId());
		str.append("\" ");
		if ( super.getCheckedValue() != null) {
			str.append(" value=\"");
			str.append(super.getCheckedValue());
			str.append("\" ");
			
			if (super.getCheckedValue().equals(property.getValue())) {
				str.append(" checked=\"checked\" ");
			}
		}
		str.append(">");
		str.append("<input type=\"hidden\" name=\"_");
		str.append(super.getId());
		str.append("\" value=\"on\" />");
		str.append(super.getLabel());
		str.append("</label>");		
		str.append("</div>");
		str.append("</div>");
		
		return str.toString();
	}

}

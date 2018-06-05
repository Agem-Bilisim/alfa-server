package tr.com.agem.alfa.bpmn.inputs;

import java.util.List;

import org.activiti.engine.form.FormProperty;

import tr.com.agem.alfa.bpmn.types.AbstractComboboxFormType;
import tr.com.agem.alfa.model.SysRole;
import tr.com.agem.alfa.service.SpringTools;
import tr.com.agem.alfa.service.SysUserService;

/**
 * @author <a href="mailto:ali.ozeren@agem.com.tr">Ali Ozkan Ozeren</a>
 *
 */
public class HtmlRoleFormType extends AbstractComboboxFormType  
{
	private static final long serialVersionUID = 3868212314623992954L;
	
	public static final String NAME = "role";

	private SysUserService sysUserService = (SysUserService) SpringTools.getInstance().getContext().getBean("sysUserService");
	
	
	public String getName() 
	{
		return  HtmlRoleFormType.NAME;
	}	

	public String renderInputForType(FormProperty property) 
	{
		
		StringBuffer str = new StringBuffer("<div class=\"form-group\"> ");

		str.append("<label>");
		str.append(super.getLabel());
		str.append("</label>");
		str.append("<select class=\"form-control select2\" id=\"");
		str.append("pact_").append(super.getId());
		str.append("\" name=\"");
		str.append("pact_").append(super.getId());
		str.append("\" ");
		str.append(HTMLInputUtils.getInstance().prepareAttributes(this, super.getMap(), "options"));
		str.append(" >");
		
		List<SysRole> roles;
		if (super.getMap().get("filter") != null ) {
			roles = sysUserService.getRolesNameContaining(super.getMap().get("filter"));
		} else {
			roles = sysUserService.getRoles();
		}
		
		if (roles != null) {
			for ( SysRole role : roles) {
				str.append("<option value='")
				.append(role.getId())
				.append("'");
				if (property.getValue() != null && property.getValue().equals(role.getId())) {
					str.append(" selected ");
				}
				
				str.append(" > ")
				.append(role.getName())
				.append("</option>");
			}
		}
		str.append("</select>");

		str.append("</div>");
		
		return str.toString();
		
	}
	
}

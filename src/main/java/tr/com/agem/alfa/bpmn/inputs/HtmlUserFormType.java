package tr.com.agem.alfa.bpmn.inputs;

import java.util.List;

import org.activiti.engine.form.FormProperty;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import tr.com.agem.alfa.bpmn.types.AbstractComboboxFormType;
import tr.com.agem.alfa.model.SysUser;
import tr.com.agem.alfa.service.SpringTools;
import tr.com.agem.alfa.service.SysUserService;

/**
 * @author <a href="mailto:ali.ozeren@agem.com.tr">Ali Ozkan Ozeren</a>
 *
 */
public class HtmlUserFormType extends AbstractComboboxFormType  
{
	private static final long serialVersionUID = 3868212314623992954L;
	
	public static final String NAME = "user";


	@Autowired
	private SysUserService sysUserService = (SysUserService) SpringTools.getInstance().getContext().getBean("sysUserService");
	
	
	public String getName() 
	{
		return  HtmlUserFormType.NAME;
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
		str.append(HTMLInputUtils.getInstance().prepareAttributes(this, super.getMap(), "options","role"));
		str.append(" >");
		
		List<SysUser> users;
		String role = super.getMap().get("role");
		if (role != null ) {
			if (StringUtils.isNumeric(role)) {
				users = sysUserService.getUsersByRoleId(Long.parseLong(role));
			} else {
				users = sysUserService.getUsersByRoleName(role);
			}
		} else {
			users = sysUserService.getUsersByRoleId(null);
		}
		
		if (users != null) {
			for ( SysUser user : users) {
				str.append("<option value='")
				.append(user.getUserName())
				.append("'");
				if (property.getValue() != null && property.getValue().equals(user.getId())) {
					str.append(" selected ");
				}
				
				str.append(" > ")
				.append(user.getFirstName()).append(" ").append(user.getLastName())
				.append("(").append(user.getUserName()).append(")")
				.append("</option>");
			}
		}
		str.append("</select>");

		str.append("</div>");
		
		return str.toString();
		
	}
	
}

package tr.com.agem.alfa.form;

/**
 * @author <a href="mailto:emre.akkaya@agem.com.tr">Emre Akkaya</a>
 */
public class LdapUserForm extends BaseForm {

	private static final long serialVersionUID = -9119884634348219480L;

	private String userIdentifier;

	private String name;

	public String getUserIdentifier() {
		return userIdentifier;
	}

	public void setUserIdentifier(String userIdentifier) {
		this.userIdentifier = userIdentifier;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}

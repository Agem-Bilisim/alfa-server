package tr.com.agem.alfa.form;

import tr.com.agem.alfa.locale.MessageSourceDecorator.MessageSourceDecoratable;
import tr.com.agem.alfa.model.enums.EducationStatus;

/**
 * @author <a href="mailto:emre.akkaya@agem.com.tr">Emre Akkaya</a>
 */
public class EducationLdapUserForm extends BaseForm implements MessageSourceDecoratable {

	private static final long serialVersionUID = -7482586334867883956L;

	private LdapUserForm ldapUser;

	private Integer status;

	private String statusLabel;

	private String duration;

	private String examScore;

	public LdapUserForm getLdapUser() {
		return ldapUser;
	}

	public void setLdapUser(LdapUserForm ldapUser) {
		this.ldapUser = ldapUser;
	}

	public EducationStatus getStatus() {
		return EducationStatus.getType(status);
	}

	public void setStatus(EducationStatus status) {
		if (status == null) {
			this.status = null;
		} else {
			this.status = status.getId();
		}
	}

	public String getStatusLabel() {
		return statusLabel;
	}

	public void setStatusLabel(String statusLabel) {
		this.statusLabel = statusLabel;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getExamScore() {
		return examScore;
	}

	public void setExamScore(String examScore) {
		this.examScore = examScore;
	}

	@Override
	public String getMessageConvention() {
		return "education-status.%s";
	}

}

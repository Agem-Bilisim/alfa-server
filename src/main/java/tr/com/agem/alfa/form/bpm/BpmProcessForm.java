package tr.com.agem.alfa.form.bpm;

import java.util.Date;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;

import tr.com.agem.alfa.form.BaseForm;

/**
 * @author <a href="mailto:ali.ozeren@agem.com.tr">Ali Ozkan Ozeren</a>
 */
public class BpmProcessForm extends BaseForm {

	private static final long serialVersionUID = -4212106777364053291L;

	@NotEmpty
	private String name;

	@NotEmpty
	private String version;

	private String processDeploymentId;
	
	private Date test;

	MultipartFile file;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	@JsonIgnore
	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	public String getProcessDeploymentId() {
		return processDeploymentId;
	}

	public void setProcessDeploymentId(String processDeploymentId) {
		this.processDeploymentId = processDeploymentId;
	}

	public Date getTest() {
		return test;
	}

	public void setTest(Date test) {
		this.test = test;
	}

}

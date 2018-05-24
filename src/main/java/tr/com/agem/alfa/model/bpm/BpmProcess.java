package tr.com.agem.alfa.model.bpm;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import tr.com.agem.alfa.form.bpm.BpmProcessForm;
import tr.com.agem.alfa.model.BaseModel;

@Entity
@Table(name = "c_bpm_process", uniqueConstraints = { @UniqueConstraint(columnNames = { "NAME", "VERSION" }) })
public class BpmProcess extends BaseModel {

	private static final long serialVersionUID = 4744508001062587676L;

	@Column(name = "NAME", length = 100)
	private String name;

	@Column(name = "VERSION", length = 100)
	private String version;

	@Column(name = "PROCESS_DEF_ID", length = 50)
	private String processDefId;
	
    @Lob @Basic(fetch = FetchType.LAZY)
    @Column(name="content", updatable=false)
    private byte[] content;
    
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

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}

	public String getProcessDefId() {
		return processDefId;
	}

	public void setProcessDefId(String processDefId) {
		this.processDefId = processDefId;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((version == null) ? 0 : version.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		BpmProcess other = (BpmProcess) obj;
		if (name == null) {
			if (other.name != null) return false;
		} else if (!name.equals(other.name)) return false;
		if (version == null) {
			if (other.version != null) return false;
		} else if (!version.equals(other.version)) return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see tr.com.agem.alfa.model.BaseModel#getCorrespondingForm()
	 */
	@Override
	public Object getCorrespondingForm() {
		return new BpmProcessForm();
	}


}

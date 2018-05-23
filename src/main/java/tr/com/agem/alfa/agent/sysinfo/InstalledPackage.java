package tr.com.agem.alfa.agent.sysinfo;

import java.io.Serializable;

public class InstalledPackage implements Serializable {

	private static final long serialVersionUID = 8825161815758128735L;

	private String name;

	private String version;

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

}

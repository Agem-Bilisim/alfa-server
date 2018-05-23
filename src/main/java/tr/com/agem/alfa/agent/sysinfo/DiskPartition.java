package tr.com.agem.alfa.agent.sysinfo;

import java.io.Serializable;

public class DiskPartition implements Serializable {

	private static final long serialVersionUID = 2633151831335406107L;

	private String mountpoint;
	private String opts;
	private String device;
	private String fstype;

	public String getDevice() {
		return device;
	}

	public void setDevice(String device) {
		this.device = device;
	}

	public String getMountpoint() {
		return mountpoint;
	}

	public void setMountpoint(String mountpoint) {
		this.mountpoint = mountpoint;
	}

	public String getFstype() {
		return fstype;
	}

	public void setFstype(String fstype) {
		this.fstype = fstype;
	}

	public String getOpts() {
		return opts;
	}

	public void setOpts(String opts) {
		this.opts = opts;
	}

}

package tr.com.agem.alfa.dto;

import java.io.Serializable;

public class Addr implements Serializable {

	private static final long serialVersionUID = -5064842610756852275L;

	private String address;
	private String ptp;
	private Integer family;
	private String netmask;
	private String broadcast;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPtp() {
		return ptp;
	}

	public void setPtp(String ptp) {
		this.ptp = ptp;
	}

	public Integer getFamily() {
		return family;
	}

	public void setFamily(Integer family) {
		this.family = family;
	}

	public String getNetmask() {
		return netmask;
	}

	public void setNetmask(String netmask) {
		this.netmask = netmask;
	}

	public String getBroadcast() {
		return broadcast;
	}

	public void setBroadcast(String broadcast) {
		this.broadcast = broadcast;
	}

}

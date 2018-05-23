package tr.com.agem.alfa.agent.sysinfo;

import java.io.Serializable;
import java.util.List;

public class InterfaceAddress implements Serializable {

	private static final long serialVersionUID = 5271269275431736480L;

	private String inet;
	private List<Addr> addr = null;

	public String getInet() {
		return inet;
	}

	public void setInet(String inet) {
		this.inet = inet;
	}

	public List<Addr> getAddr() {
		return addr;
	}

	public void setAddr(List<Addr> addr) {
		this.addr = addr;
	}

}

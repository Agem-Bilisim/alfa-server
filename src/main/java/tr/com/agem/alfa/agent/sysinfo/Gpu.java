
package tr.com.agem.alfa.agent.sysinfo;

import java.io.Serializable;
import java.util.List;

public class Gpu implements Serializable
{

    private List<GpuDevice> devices = null;
    private final static long serialVersionUID = 8313394611520334066L;

    public List<GpuDevice> getDevices() {
        return devices;
    }

    public void setDevices(List<GpuDevice> devices) {
        this.devices = devices;
    }

}

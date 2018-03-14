
package tr.com.agem.alfa.dto;

import java.io.Serializable;
import java.util.List;

public class Gpu implements Serializable
{

    private List<Device__> devices = null;
    private final static long serialVersionUID = 8313394611520334066L;

    public List<Device__> getDevices() {
        return devices;
    }

    public void setDevices(List<Device__> devices) {
        this.devices = devices;
    }

}

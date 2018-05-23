
package tr.com.agem.alfa.agent.sysinfo;

import java.io.Serializable;

public class PeripheralDevice implements Serializable
{

    private String tag;
    private String id;
    private String device;
    private final static long serialVersionUID = -4563297726464431151L;

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

}

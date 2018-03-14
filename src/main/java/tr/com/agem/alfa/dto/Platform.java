
package tr.com.agem.alfa.dto;

import java.io.Serializable;

public class Platform implements Serializable
{

    private String release;
    private String node;
    private String machine;
    private String system;
    private String version;
    private final static long serialVersionUID = -14583977183625829L;

    public String getRelease() {
        return release;
    }

    public void setRelease(String release) {
        this.release = release;
    }

    public String getNode() {
        return node;
    }

    public void setNode(String node) {
        this.node = node;
    }

    public String getMachine() {
        return machine;
    }

    public void setMachine(String machine) {
        this.machine = machine;
    }

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

}


package tr.com.agem.alfa.dto;

import java.io.Serializable;

public class Child implements Serializable
{

    private String id;
    private String physid;
    private String logicalname;
    private String dev;
    private String _class;
    private Boolean claimed;
    private final static long serialVersionUID = 8819537772703364403L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhysid() {
        return physid;
    }

    public void setPhysid(String physid) {
        this.physid = physid;
    }

    public String getLogicalname() {
        return logicalname;
    }

    public void setLogicalname(String logicalname) {
        this.logicalname = logicalname;
    }

    public String getDev() {
        return dev;
    }

    public void setDev(String dev) {
        this.dev = dev;
    }

    public String getClass_() {
        return _class;
    }

    public void setClass_(String _class) {
        this._class = _class;
    }

    public Boolean getClaimed() {
        return claimed;
    }

    public void setClaimed(Boolean claimed) {
        this.claimed = claimed;
    }

}


package tr.com.agem.alfa.agent.sysinfo;

import java.io.Serializable;

public class Bios implements Serializable
{

    private String vendor;
    private String version;
    private String releaseDate;
    private final static long serialVersionUID = -5082513597134247261L;

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

}

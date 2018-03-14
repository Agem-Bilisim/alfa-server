
package tr.com.agem.alfa.dto;

import java.io.Serializable;

public class Configuration implements Serializable
{

    private String sectorsize;
    private String logicalsectorsize;
    private String ansiversion;
    private String guid;
    private final static long serialVersionUID = 2407936254666714723L;

    public String getSectorsize() {
        return sectorsize;
    }

    public void setSectorsize(String sectorsize) {
        this.sectorsize = sectorsize;
    }

    public String getLogicalsectorsize() {
        return logicalsectorsize;
    }

    public void setLogicalsectorsize(String logicalsectorsize) {
        this.logicalsectorsize = logicalsectorsize;
    }

    public String getAnsiversion() {
        return ansiversion;
    }

    public void setAnsiversion(String ansiversion) {
        this.ansiversion = ansiversion;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

}

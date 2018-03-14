
package tr.com.agem.alfa.dto;

import java.io.Serializable;
import java.util.List;

public class Device implements Serializable
{

    private String vendor;
    private String id;
    private String physid;
    private String handle;
    private String _class;
    private Capabilities capabilities;
    private String description;
    private Configuration configuration;
    private String businfo;
    private List<Child> children = null;
    private String version;
    private String logicalname;
    private String dev;
    private String product;
    private Boolean claimed;
    private Integer size;
    private String serial;
    private String units;
    private final static long serialVersionUID = -1558536484133907408L;

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

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

    public String getHandle() {
        return handle;
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }

    public String getClass_() {
        return _class;
    }

    public void setClass_(String _class) {
        this._class = _class;
    }

    public Capabilities getCapabilities() {
        return capabilities;
    }

    public void setCapabilities(Capabilities capabilities) {
        this.capabilities = capabilities;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    public String getBusinfo() {
        return businfo;
    }

    public void setBusinfo(String businfo) {
        this.businfo = businfo;
    }

    public List<Child> getChildren() {
        return children;
    }

    public void setChildren(List<Child> children) {
        this.children = children;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
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

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public Boolean getClaimed() {
        return claimed;
    }

    public void setClaimed(Boolean claimed) {
        this.claimed = claimed;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

}

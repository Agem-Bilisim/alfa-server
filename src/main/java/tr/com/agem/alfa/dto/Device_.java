
package tr.com.agem.alfa.dto;

import java.io.Serializable;

public class Device_ implements Serializable
{

    private String vendor;
    private Integer size;
    private String id;
    private Integer capacity;
    private String physid;
    private String handle;
    private Integer clock;
    private Integer width;
    private String _class;
    private Capabilities_ capabilities;
    private String description;
    private Configuration_ configuration;
    private String businfo;
    private String product;
    private String logicalname;
    private String serial;
    private String units;
    private String version;
    private Boolean claimed;
    private final static long serialVersionUID = -8460894718694776582L;

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
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

    public Integer getClock() {
        return clock;
    }

    public void setClock(Integer clock) {
        this.clock = clock;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public String getClass_() {
        return _class;
    }

    public void setClass_(String _class) {
        this._class = _class;
    }

    public Capabilities_ getCapabilities() {
        return capabilities;
    }

    public void setCapabilities(Capabilities_ capabilities) {
        this.capabilities = capabilities;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Configuration_ getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration_ configuration) {
        this.configuration = configuration;
    }

    public String getBusinfo() {
        return businfo;
    }

    public void setBusinfo(String businfo) {
        this.businfo = businfo;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getLogicalname() {
        return logicalname;
    }

    public void setLogicalname(String logicalname) {
        this.logicalname = logicalname;
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

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Boolean getClaimed() {
        return claimed;
    }

    public void setClaimed(Boolean claimed) {
        this.claimed = claimed;
    }

}

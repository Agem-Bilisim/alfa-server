package tr.com.agem.alfa.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "c_agent_cpu")
public class Cpu extends BaseModel {

	private static final long serialVersionUID = 7537194714323092263L;
	
	private String l2CacheSize;

	private String bits;

	private String vendorId;

	private String extendedFamily;

	private String l2CacheAssociativity;

	private String l2CacheLineSize;

	private String hzAdvertised;

	private String processor;

	private Integer pyhsicalCoreCount;

	private Integer logicalCoreCount;

	private String brand;

	private String rawArchString;

	private Integer count;

	private String family;

	private String arch;

	private String model;

	public String getBits() {
		return bits;
	}

	public void setBits(String bits) {
		this.bits = bits;
	}

	public String getL2CacheSize() {
		return l2CacheSize;
	}

	public void setL2CacheSize(String l2CacheSize) {
		this.l2CacheSize = l2CacheSize;
	}

	public String getVendorId() {
		return vendorId;
	}

	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}

	public String getExtendedFamily() {
		return extendedFamily;
	}

	public void setExtendedFamily(String extendedFamily) {
		this.extendedFamily = extendedFamily;
	}

	public String getL2CacheAssociativity() {
		return l2CacheAssociativity;
	}

	public void setL2CacheAssociativity(String l2CacheAssociativity) {
		this.l2CacheAssociativity = l2CacheAssociativity;
	}

	public String getL2CacheLineSize() {
		return l2CacheLineSize;
	}

	public void setL2CacheLineSize(String l2CacheLineSize) {
		this.l2CacheLineSize = l2CacheLineSize;
	}

	public String getHzAdvertised() {
		return hzAdvertised;
	}

	public void setHzAdvertised(String hzAdvertised) {
		this.hzAdvertised = hzAdvertised;
	}

	public String getProcessor() {
		return processor;
	}

	public void setProcessor(String processor) {
		this.processor = processor;
	}

	public Integer getPyhsicalCoreCount() {
		return pyhsicalCoreCount;
	}

	public void setPyhsicalCoreCount(Integer pyhsicalCoreCount) {
		this.pyhsicalCoreCount = pyhsicalCoreCount;
	}

	public Integer getLogicalCoreCount() {
		return logicalCoreCount;
	}

	public void setLogicalCoreCount(Integer logicalCoreCount) {
		this.logicalCoreCount = logicalCoreCount;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getRawArchString() {
		return rawArchString;
	}

	public void setRawArchString(String rawArchString) {
		this.rawArchString = rawArchString;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public String getFamily() {
		return family;
	}

	public void setFamily(String family) {
		this.family = family;
	}

	public String getArch() {
		return arch;
	}

	public void setArch(String arch) {
		this.arch = arch;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

}

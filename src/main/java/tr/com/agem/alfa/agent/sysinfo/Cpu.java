
package tr.com.agem.alfa.agent.sysinfo;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Cpu implements Serializable {

	private final static long serialVersionUID = 2285250068081656743L;

	@JsonProperty("hz_advertised")
	private String hzAdvertised;

	private String processor;

	@JsonProperty("raw_arch_string")
	private String rawArchString;

	private Integer model;

	private List<Long> stats = null;

	@JsonProperty("cpu-times")
	private List<Float> cpuTimes = null;

	private String arch;

	private Integer extendedModel;

	private List<String> flags = null;

	private String l2CacheAssociativity;

	private Integer bits;

	@JsonProperty("cpuinfo_version")
	private List<Integer> cpuinfoVersion = null;

	private Integer l2CacheLineSize;

	@JsonProperty("hz_advertised_raw")
	private List<String> hzAdvertisedRaw = null;

	private Integer family;

	@JsonProperty("logical-core-count")
	private Integer logicalCoreCount;

	private String l2CacheSize;

	private Integer count;

	private Integer stepping;

	private String brand;

	@JsonProperty("hz_actual")
	private String hzActual;

	private Integer extendedFamily;

	@JsonProperty("vendor_id")
	private String vendorId;

	@JsonProperty("pyhsical-core-count")
	private Integer pyhsicalCoreCount;

	@JsonProperty("hz_actual_raw")
	private List<String> hzActualRaw = null;

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

	public String getRawArchString() {
		return rawArchString;
	}

	public void setRawArchString(String rawArchString) {
		this.rawArchString = rawArchString;
	}

	public Integer getModel() {
		return model;
	}

	public void setModel(Integer model) {
		this.model = model;
	}

	public List<Long> getStats() {
		return stats;
	}

	public void setStats(List<Long> stats) {
		this.stats = stats;
	}

	public List<Float> getCpuTimes() {
		return cpuTimes;
	}

	public void setCpuTimes(List<Float> cpuTimes) {
		this.cpuTimes = cpuTimes;
	}

	public String getArch() {
		return arch;
	}

	public void setArch(String arch) {
		this.arch = arch;
	}

	public Integer getExtendedModel() {
		return extendedModel;
	}

	public void setExtendedModel(Integer extendedModel) {
		this.extendedModel = extendedModel;
	}

	public List<String> getFlags() {
		return flags;
	}

	public void setFlags(List<String> flags) {
		this.flags = flags;
	}

	public String getL2CacheAssociativity() {
		return l2CacheAssociativity;
	}

	public void setL2CacheAssociativity(String l2CacheAssociativity) {
		this.l2CacheAssociativity = l2CacheAssociativity;
	}

	public Integer getBits() {
		return bits;
	}

	public void setBits(Integer bits) {
		this.bits = bits;
	}

	public List<Integer> getCpuinfoVersion() {
		return cpuinfoVersion;
	}

	public void setCpuinfoVersion(List<Integer> cpuinfoVersion) {
		this.cpuinfoVersion = cpuinfoVersion;
	}

	public Integer getL2CacheLineSize() {
		return l2CacheLineSize;
	}

	public void setL2CacheLineSize(Integer l2CacheLineSize) {
		this.l2CacheLineSize = l2CacheLineSize;
	}

	public List<String> getHzAdvertisedRaw() {
		return hzAdvertisedRaw;
	}

	public void setHzAdvertisedRaw(List<String> hzAdvertisedRaw) {
		this.hzAdvertisedRaw = hzAdvertisedRaw;
	}

	public Integer getFamily() {
		return family;
	}

	public void setFamily(Integer family) {
		this.family = family;
	}

	public Integer getLogicalCoreCount() {
		return logicalCoreCount;
	}

	public void setLogicalCoreCount(Integer logicalCoreCount) {
		this.logicalCoreCount = logicalCoreCount;
	}

	public String getL2CacheSize() {
		return l2CacheSize;
	}

	public void setL2CacheSize(String l2CacheSize) {
		this.l2CacheSize = l2CacheSize;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Integer getStepping() {
		return stepping;
	}

	public void setStepping(Integer stepping) {
		this.stepping = stepping;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getHzActual() {
		return hzActual;
	}

	public void setHzActual(String hzActual) {
		this.hzActual = hzActual;
	}

	public Integer getExtendedFamily() {
		return extendedFamily;
	}

	public void setExtendedFamily(Integer extendedFamily) {
		this.extendedFamily = extendedFamily;
	}

	public String getVendorId() {
		return vendorId;
	}

	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}

	public Integer getPyhsicalCoreCount() {
		return pyhsicalCoreCount;
	}

	public void setPyhsicalCoreCount(Integer pyhsicalCoreCount) {
		this.pyhsicalCoreCount = pyhsicalCoreCount;
	}

	public List<String> getHzActualRaw() {
		return hzActualRaw;
	}

	public void setHzActualRaw(List<String> hzActualRaw) {
		this.hzActualRaw = hzActualRaw;
	}

}

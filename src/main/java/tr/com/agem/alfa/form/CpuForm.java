package tr.com.agem.alfa.form;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author <a href="mailto:caner.feyzullahoglu@agem.com.tr">Caner Feyzullahoğlu</a>
 */
public class CpuForm extends BaseForm {

	private static final long serialVersionUID = 4476046731296682194L;

	@NotEmpty
	private String brand;

	@NotEmpty
	private String hzAdvertised;

	@NotNull
	private Integer logicalCoreCount;

	@NotEmpty
	private String processor;

	@NotNull
	private Integer pyhsicalCoreCount;
	
	private String arch;

	private String bits;

	private Integer count;
	
	private String extendedFamily;

	private String family;
	
	private String l2CacheAssociativity;

	private String l2CacheLineSize;
	
	private String l2CacheSize;

	private String model;

	private String rawArchString;
	
	private String cpuTimes;

	private String flags;

	private String stats;

	private String hzActual;
	
	private Long[] agentIds;

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getHzAdvertised() {
		return hzAdvertised;
	}

	public void setHzAdvertised(String hzAdvertised) {
		this.hzAdvertised = hzAdvertised;
	}

	public Integer getLogicalCoreCount() {
		return logicalCoreCount;
	}

	public void setLogicalCoreCount(Integer logicalCoreCount) {
		this.logicalCoreCount = logicalCoreCount;
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

	public String getArch() {
		return arch;
	}

	public void setArch(String arch) {
		this.arch = arch;
	}

	public String getBits() {
		return bits;
	}

	public void setBits(String bits) {
		this.bits = bits;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public String getExtendedFamily() {
		return extendedFamily;
	}

	public void setExtendedFamily(String extendedFamily) {
		this.extendedFamily = extendedFamily;
	}

	public String getFamily() {
		return family;
	}

	public void setFamily(String family) {
		this.family = family;
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

	public String getL2CacheSize() {
		return l2CacheSize;
	}

	public void setL2CacheSize(String l2CacheSize) {
		this.l2CacheSize = l2CacheSize;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getRawArchString() {
		return rawArchString;
	}

	public void setRawArchString(String rawArchString) {
		this.rawArchString = rawArchString;
	}

	public String getVendorId() {
		return vendorId;
	}

	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}

	private String vendorId;

	public Long[] getAgentIds() {
		return agentIds;
	}

	public void setAgentIds(Long[] agentIds) {
		this.agentIds = agentIds;
	}

	public String getCpuTimes() {
		return cpuTimes;
	}

	public void setCpuTimes(String cpuTimes) {
		this.cpuTimes = cpuTimes;
	}

	public String getFlags() {
		return flags;
	}

	public void setFlags(String flags) {
		this.flags = flags;
	}

	public String getStats() {
		return stats;
	}

	public void setStats(String stats) {
		this.stats = stats;
	}

	public String getHzActual() {
		return hzActual;
	}

	public void setHzActual(String hzActual) {
		this.hzActual = hzActual;
	}
}

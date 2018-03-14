
package tr.com.agem.alfa.dto;

import java.io.Serializable;
import java.util.List;

public class Disk implements Serializable {

	private final static long serialVersionUID = 1226800383991623511L;

	private List<DiskPartition> diskPartitions = null;
	private List<Device> devices = null;
	private DiskUsage diskUsage;

	public List<DiskPartition> getDiskPartitions() {
		return diskPartitions;
	}

	public void setDiskPartitions(List<DiskPartition> diskPartitions) {
		this.diskPartitions = diskPartitions;
	}

	public List<Device> getDevices() {
		return devices;
	}

	public void setDevices(List<Device> devices) {
		this.devices = devices;
	}

	public DiskUsage getDiskUsage() {
		return diskUsage;
	}

	public void setDiskUsage(DiskUsage diskUsage) {
		this.diskUsage = diskUsage;
	}

}


package tr.com.agem.alfa.dto;

import java.io.Serializable;
import java.util.List;

public class Process implements Serializable
{

    private String name;
    private List<Float> cpuTimes = null;
    private Integer pid;
    private Float cpuPercent;
    private String username;
    private List<Integer> memoryInfo = null;
    private final static long serialVersionUID = 1217440266443348229L;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Float> getCpuTimes() {
        return cpuTimes;
    }

    public void setCpuTimes(List<Float> cpuTimes) {
        this.cpuTimes = cpuTimes;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Float getCpuPercent() {
        return cpuPercent;
    }

    public void setCpuPercent(Float cpuPercent) {
        this.cpuPercent = cpuPercent;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Integer> getMemoryInfo() {
        return memoryInfo;
    }

    public void setMemoryInfo(List<Integer> memoryInfo) {
        this.memoryInfo = memoryInfo;
    }

}

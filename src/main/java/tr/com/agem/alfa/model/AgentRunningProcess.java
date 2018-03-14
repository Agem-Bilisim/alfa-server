package tr.com.agem.alfa.model;

import java.io.Serializable;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Cross-table for c_agent and c_agent_running_process
 * 
 * @author emre
 */
@Entity
@Table(name = "c_agent_process_agent")
@AssociationOverrides({ @AssociationOverride(name = "pk.agent", joinColumns = @JoinColumn(name = "AGENT_ID")),
		@AssociationOverride(name = "pk.runningProcess", joinColumns = @JoinColumn(name = "RUNNING_PROCESS_ID")) })
public class AgentRunningProcess implements Serializable {

	private static final long serialVersionUID = 6844159583081268836L;

	@EmbeddedId
	private AgentRunningProcessId pk = new AgentRunningProcessId();

	@Column(name = "CPU_TIMES", length = 100)
	private String cpuTimes;

	@Column(name = "PID", nullable = false, length = 100)
	private String pid;

	@Column(name = "CPU_PERCENT", length = 100)
	private String cpuPercent;

	@Column(name = "USERNAME", nullable = false, length = 100)
	private String username;

	@Column(name = "MEMORY_INFO", length = 100)
	private String memoryInfo;

	public AgentRunningProcessId getPk() {
		return pk;
	}

	@Transient
	public Agent getAgent() {
		return getPk().getAgent();
	}

	public void setAgent(Agent agent) {
		getPk().setAgent(agent);
	}

	@Transient
	public RunningProcess getRunningProcess() {
		return getPk().getRunningProcess();
	}

	public void setRunningProcess(RunningProcess runningProcess) {
		getPk().setRunningProcess(runningProcess);
	}

	public void setPk(AgentRunningProcessId pk) {
		this.pk = pk;
	}

	public String getCpuTimes() {
		return cpuTimes;
	}

	public void setCpuTimes(String cpuTimes) {
		this.cpuTimes = cpuTimes;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getCpuPercent() {
		return cpuPercent;
	}

	public void setCpuPercent(String cpuPercent) {
		this.cpuPercent = cpuPercent;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getMemoryInfo() {
		return memoryInfo;
	}

	public void setMemoryInfo(String memoryInfo) {
		this.memoryInfo = memoryInfo;
	}

	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		AgentRunningProcess that = (AgentRunningProcess) o;

		if (getPk() != null ? !getPk().equals(that.getPk()) : that.getPk() != null) return false;

		return true;
	}

	public int hashCode() {
		return (getPk() != null ? getPk().hashCode() : 0);
	}

}

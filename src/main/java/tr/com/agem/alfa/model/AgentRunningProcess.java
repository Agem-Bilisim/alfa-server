package tr.com.agem.alfa.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Cross-table for c_agent and c_agent_running_process
 * 
 * @author emre
 */
@Entity
@Table(name = "c_agent_process_agent")
public class AgentRunningProcess implements Serializable {

	private static final long serialVersionUID = 6844159583081268836L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "AGENT_PROCESS_AGENT_ID", unique = true, nullable = false, updatable = false)
	private Long id;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "AGENT_ID")
	private Agent agent;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "PROCESS_ID")
	private RunningProcess runningProcess;

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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Agent getAgent() {
		return agent;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	public RunningProcess getRunningProcess() {
		return runningProcess;
	}

	public void setRunningProcess(RunningProcess runningProcess) {
		this.runningProcess = runningProcess;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((runningProcess == null) ? 0 : runningProcess.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		AgentRunningProcess other = (AgentRunningProcess) obj;
		if (runningProcess == null) {
			if (other.runningProcess != null) return false;
		} else if (!runningProcess.equals(other.runningProcess)) return false;
		return true;
	}

}

package tr.com.agem.alfa.model;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Embeddable
public class AgentRunningProcessId implements Serializable {

	private static final long serialVersionUID = -3288761088782020117L;

	@ManyToOne
	private Agent agent;

	@ManyToOne
	private RunningProcess runningProcess;

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

	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		AgentRunningProcessId that = (AgentRunningProcessId) o;

		if (agent != null ? !agent.equals(that.agent) : that.agent != null) return false;
		if (runningProcess != null ? !runningProcess.equals(that.runningProcess) : that.runningProcess != null)
			return false;

		return true;
	}

	public int hashCode() {
		int result;
		result = (agent != null ? agent.hashCode() : 0);
		result = 31 * result + (runningProcess != null ? runningProcess.hashCode() : 0);
		return result;
	}

}

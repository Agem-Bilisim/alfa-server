package tr.com.agem.alfa.model;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

/**
 * Cross-table for c_agent and c_agent_cpu
 * 
 * @author emre
 */
@Embeddable
public class AgentCpuId implements Serializable {

	private static final long serialVersionUID = -4811296310416705368L;

	@ManyToOne
	private Agent agent;

	@ManyToOne
	private Cpu cpu;

	public Agent getAgent() {
		return agent;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	public Cpu getCpu() {
		return cpu;
	}

	public void setCpu(Cpu cpu) {
		this.cpu = cpu;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		AgentCpuId that = (AgentCpuId) o;

		if (agent != null ? !agent.equals(that.agent) : that.agent != null) return false;
		if (cpu != null ? !cpu.equals(that.cpu) : that.cpu != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result;
		result = (agent != null ? agent.hashCode() : 0);
		result = 31 * result + (cpu != null ? cpu.hashCode() : 0);
		return result;
	}

}

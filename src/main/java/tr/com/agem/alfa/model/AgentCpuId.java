package tr.com.agem.alfa.model;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@Embeddable
public class AgentCpuId implements Serializable {

	private static final long serialVersionUID = -4811296310416705368L;

	private Agent agent;

	private Cpu cpu;

	@ManyToOne
	public Agent getAgent() {
		return agent;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	@ManyToOne
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
        if (cpu != null ? !cpu.equals(that.cpu) : that.cpu != null)
            return false;

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

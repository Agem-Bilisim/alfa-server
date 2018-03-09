package tr.com.agem.alfa.model;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name = "c_agent_cpu")
public class AgentCpu implements Serializable {

	private static final long serialVersionUID = 7337356336364195558L;

	private AgentCpuId pk = new AgentCpuId();

	private String hzActual;

	private String commaSeparatedCpuTimes;

	private String commaSeparatedStats;

	private String commaSeparatedFlags;

	public AgentCpu() {
	}

	@EmbeddedId
	public AgentCpuId getPk() {
		return pk;
	}

	public void setPk(AgentCpuId pk) {
		this.pk = pk;
	}

	@Transient
	public Agent getAgent() {
		return getPk().getAgent();
	}

	public void setAgent(Agent agent) {
		getPk().setAgent(agent);
	}

	@Transient
	public Cpu getCpu() {
		return getPk().getCpu();
	}

	public void setCpu(Cpu cpu) {
		getPk().setCpu(cpu);
	}

	public String getHzActual() {
		return hzActual;
	}

	public void setHzActual(String hzActual) {
		this.hzActual = hzActual;
	}

	public String getCommaSeparatedCpuTimes() {
		return commaSeparatedCpuTimes;
	}

	public void setCommaSeparatedCpuTimes(String commaSeparatedCpuTimes) {
		this.commaSeparatedCpuTimes = commaSeparatedCpuTimes;
	}

	public String getCommaSeparatedStats() {
		return commaSeparatedStats;
	}

	public void setCommaSeparatedStats(String commaSeparatedStats) {
		this.commaSeparatedStats = commaSeparatedStats;
	}

	public String getCommaSeparatedFlags() {
		return commaSeparatedFlags;
	}

	public void setCommaSeparatedFlags(String commaSeparatedFlags) {
		this.commaSeparatedFlags = commaSeparatedFlags;
	}

	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		AgentCpu that = (AgentCpu) o;

		if (getPk() != null ? !getPk().equals(that.getPk()) : that.getPk() != null) return false;

		return true;
	}

	public int hashCode() {
		return (getPk() != null ? getPk().hashCode() : 0);
	}

}

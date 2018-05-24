package tr.com.agem.alfa.model;

import java.io.Serializable;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "c_agent_cpu_agent")
@AssociationOverrides({ @AssociationOverride(name = "pk.agent", joinColumns = @JoinColumn(name = "AGENT_ID")),
		@AssociationOverride(name = "pk.cpu", joinColumns = @JoinColumn(name = "CPU_ID")) })
public class AgentCpu implements Serializable {

	private static final long serialVersionUID = 7337356336364195558L;

	@JsonIgnore
	@EmbeddedId
	private AgentCpuId pk = new AgentCpuId();

	@Column(name = "HZ_ACTUAL", length = 100)
	private String hzActual;

	@Column(name = "CPU_TIMES", length = 500)
	private String commaSeparatedCpuTimes;

	@Column(name = "STATS", length = 500)
	private String commaSeparatedStats;

	@Column(name = "FLAGS", length = 500)
	private String commaSeparatedFlags;

	public AgentCpu() {
	}

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

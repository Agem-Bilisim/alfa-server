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
 * @author <a href="mailto:emre.akkaya@agem.com.tr">Emre Akkaya</a>
 */
@Entity
@Table(name = "c_agent_cpu_agent")
public class AgentCpu implements Serializable {

	private static final long serialVersionUID = 7337356336364195558L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "AGENT_CPU_AGENT_ID", unique = true, nullable = false, updatable = false)
	private Long id;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "AGENT_ID")
	private Agent agent;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "CPU_ID")
	private Cpu cpu;

	@Column(name = "HZ_ACTUAL", length = 100)
	private String hzActual;

	@Column(name = "CPU_TIMES", length = 500)
	private String commaSeparatedCpuTimes;

	@Column(name = "STATS", length = 500)
	private String commaSeparatedStats;

	@Column(name = "FLAGS", length = 500)
	private String commaSeparatedFlags;

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

	public Cpu getCpu() {
		return cpu;
	}

	public void setCpu(Cpu cpu) {
		this.cpu = cpu;
	}

}

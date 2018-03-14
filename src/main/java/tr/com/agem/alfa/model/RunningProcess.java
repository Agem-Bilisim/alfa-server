package tr.com.agem.alfa.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "c_agent_process")
public class RunningProcess extends BaseModel {

	private static final long serialVersionUID = 2751855136492532472L;

	@Column(name = "NAME", nullable = false, length = 100)
	private String name;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.runningProcess")
	private Set<AgentRunningProcess> agentRunningProcesses = new HashSet<AgentRunningProcess>(0);

}

package tr.com.agem.alfa.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import tr.com.agem.alfa.form.ProcessForm;

@Entity
@Table(name = "c_agent_process")
public class RunningProcess extends BaseModel {

	private static final long serialVersionUID = 2751855136492532472L;

	@Column(name = "NAME", nullable = false, length = 100, unique = true)
	private String name;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.runningProcess")
	private Set<AgentRunningProcess> agentRunningProcesses = new HashSet<AgentRunningProcess>(0);

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<AgentRunningProcess> getAgentRunningProcesses() {
		return agentRunningProcesses;
	}

	public void setAgentRunningProcesses(Set<AgentRunningProcess> agentRunningProcesses) {
		this.agentRunningProcesses = agentRunningProcesses;
	}

	/* (non-Javadoc)
	 * @see tr.com.agem.alfa.model.BaseModel#getCorrespondingForm()
	 */
	@Override
	public Object getCorrespondingForm() {
		return new ProcessForm();
	}

}

package tr.com.agem.alfa.form;

import org.hibernate.validator.constraints.NotEmpty;

import tr.com.agem.alfa.model.enums.ProblemReferenceType;
import tr.com.agem.alfa.util.SelectboxBuilder.OptionFormConvertable;

/**
 * @author <a href="mailto:emre.akkaya@agem.com.tr">Emre Akkaya</a>
 */
public class GpuForm extends BaseForm implements OptionFormConvertable {

	private static final long serialVersionUID = 3323842484079222128L;

	@NotEmpty
	private String subsystem;

	@NotEmpty
	private String kernel;

	@NotEmpty
	private String memory;

	private String driverDate;

	private String driverVersion;

	private Long[] agentIds;

	public String getSubsystem() {
		return subsystem;
	}

	public void setSubsystem(String subsystem) {
		this.subsystem = subsystem;
	}

	public String getKernel() {
		return kernel;
	}

	public void setKernel(String kernel) {
		this.kernel = kernel;
	}

	public String getMemory() {
		return memory;
	}

	public void setMemory(String memory) {
		this.memory = memory;
	}

	public Long[] getAgentIds() {
		return agentIds;
	}

	public void setAgentIds(Long[] agentIds) {
		this.agentIds = agentIds;
	}

	public String getDriverDate() {
		return driverDate;
	}

	public void setDriverDate(String driverDate) {
		this.driverDate = driverDate;
	}

	public String getDriverVersion() {
		return driverVersion;
	}

	public void setDriverVersion(String driverVersion) {
		this.driverVersion = driverVersion;
	}

	@Override
	public String getOptionText() {
		return this.subsystem + (this.driverVersion != null ? " Sürücü:" + this.driverVersion : "");
	}

	@Override
	public String getOptionValue() {
		return ProblemReferenceType.GPU.getId() + "-" + this.getId();
	}

}

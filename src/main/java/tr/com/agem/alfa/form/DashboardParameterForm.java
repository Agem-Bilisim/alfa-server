package tr.com.agem.alfa.form;

import java.io.Serializable;

/**
 * @author <a href="mailto:emre.akkaya@agem.com.tr">Emre Akkaya</a>
 */
public class DashboardParameterForm implements Serializable {

	private static final long serialVersionUID = -7476522008067329922L;

	private String startDate;
	private String endDate;

	public DashboardParameterForm() {
	}

	public DashboardParameterForm(String startDate, String endDate) {
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

}

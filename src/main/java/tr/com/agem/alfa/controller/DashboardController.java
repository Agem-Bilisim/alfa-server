package tr.com.agem.alfa.controller;

import java.io.Serializable;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import tr.com.agem.alfa.model.Problem;
import tr.com.agem.alfa.service.DashboardService;

/**
 * @author <a href="mailto:emre.akkaya@agem.com.tr">Emre Akkaya</a>
 */
@Controller
public class DashboardController {

	private static final Logger log = LoggerFactory.getLogger(DashboardController.class);

	private final DashboardService dashboardService;

	@Autowired
	public DashboardController(DashboardService dashboardService) {
		this.dashboardService = dashboardService;
	}
	
	@GetMapping("/dashboard/monthly-problem")
	public ResponseEntity<?> getMonthlyProblem(@Valid @RequestBody DashboardParameterForm form,
			BindingResult bindingResult) {
		RestResponseBody result = new RestResponseBody();
		if (bindingResult.hasErrors()) {
			String error = ControllerUtils.toErrorMessage(bindingResult);
			log.error(error);
			result.setMessage(error);
			return ResponseEntity.badRequest().body(result);
		}
		try {
			result.add("continuing", dashboardService.getMonthlyContinuingProblems(form.getStartDate(), form.getEndDate()));
			result.add("newly_opened", dashboardService.getMonthlyNewlyOpenedProblems(form.getStartDate(), form.getEndDate()));
			result.add("solved", dashboardService.getMonthlySolvedProblems(form.getStartDate(), form.getEndDate()));
		} catch (Exception e) {
			log.error("Exception occurred when trying to get dashboard", e);
			result.setMessage(e.getMessage());
			return ResponseEntity.badRequest().body(result);
		}
		return ResponseEntity.ok(result);
	}
	
	@GetMapping("/dashboard/monthly-migration")
	public ResponseEntity<?> getMonthlyMigration(@Valid @RequestBody DashboardParameterForm form,
			BindingResult bindingResult) {
		RestResponseBody result = new RestResponseBody();
		if (bindingResult.hasErrors()) {
			String error = ControllerUtils.toErrorMessage(bindingResult);
			log.error(error);
			result.setMessage(error);
			return ResponseEntity.badRequest().body(result);
		}
		try {
			result.add("migrated", dashboardService.getMonthlyMigrated(form.getStartDate(), form.getEndDate()));
			result.add("planned", dashboardService.getMonthlyPlanned(form.getStartDate(), form.getEndDate()));
		} catch (Exception e) {
			log.error("Exception occurred when trying to get dashboard", e);
			result.setMessage(e.getMessage());
			return ResponseEntity.badRequest().body(result);
		}
		return ResponseEntity.ok(result);
	}

	@GetMapping("/dashboard/monthly-cumulative-migration")
	public ResponseEntity<?> getMonthlyCumulativeMigration(@Valid @RequestBody DashboardParameterForm form,
			BindingResult bindingResult) {
		RestResponseBody result = new RestResponseBody();
		if (bindingResult.hasErrors()) {
			String error = ControllerUtils.toErrorMessage(bindingResult);
			log.error(error);
			result.setMessage(error);
			return ResponseEntity.badRequest().body(result);
		}
		try {
			result.add("migrated", dashboardService.getMonthlyCumulativeMigrated(form.getStartDate(), form.getEndDate()));
			result.add("planned", dashboardService.getMonthlyCumulativePlanned(form.getStartDate(), form.getEndDate()));
		} catch (Exception e) {
			log.error("Exception occurred when trying to get dashboard", e);
			result.setMessage(e.getMessage());
			return ResponseEntity.badRequest().body(result);
		}
		return ResponseEntity.ok(result);
	}

	@PostMapping("/dashboard")
	public ResponseEntity<?> getDashboard() {
		RestResponseBody result = new RestResponseBody();
		try {
			DashboardData data = new DashboardData();
			data.setAgentCount(this.dashboardService.getAgentCount());
			data.setUserCount(this.dashboardService.getUserCount());
			data.setProblemCount(this.dashboardService.getUnResolvedProblemCount());
			data.setLastProblems(this.dashboardService.getLastProblems());

			// TODO other statistics, chart data etc...

			result.add("dashboard-data", data);
		} catch (Exception e) {
			log.error("Exception occurred when trying to get dashboard", e);
			result.setMessage(e.getMessage());
			return ResponseEntity.badRequest().body(result);
		}
		return ResponseEntity.ok(result);
	}

	public class DashboardParameterForm implements Serializable {

		private static final long serialVersionUID = -7476522008067329922L;

		private String startDate;
		private String endDate;

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

	public class DashboardData implements Serializable {

		private static final long serialVersionUID = -5936326047063857590L;

		private long agentCount;
		private long userCount;
		private long problemCount;
		private List<Problem> lastProblems;

		// TODO

		public long getAgentCount() {
			return agentCount;
		}

		public void setAgentCount(long agentCount) {
			this.agentCount = agentCount;
		}

		public long getUserCount() {
			return userCount;
		}

		public void setUserCount(long userCount) {
			this.userCount = userCount;
		}

		public long getProblemCount() {
			return problemCount;
		}

		public void setProblemCount(long problemCount) {
			this.problemCount = problemCount;
		}

		public List<Problem> getLastProblems() {
			return lastProblems;
		}

		public void setLastProblems(List<Problem> lastProblems) {
			this.lastProblems = lastProblems;
		}

	}

}

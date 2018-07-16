package tr.com.agem.alfa.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import tr.com.agem.alfa.form.DashboardParameterForm;
import tr.com.agem.alfa.model.enums.AgentType;
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

	@PostMapping("/dashboard/monthly-problem")
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
			result.add("continuing",
					dashboardService.getMonthlyContinuingProblems(form.getStartDate(), form.getEndDate()));
			result.add("newly_opened",
					dashboardService.getMonthlyNewlyOpenedProblems(form.getStartDate(), form.getEndDate()));
			result.add("solved", dashboardService.getMonthlySolvedProblems(form.getStartDate(), form.getEndDate()));
		} catch (Exception e) {
			log.error("Exception occurred when trying to get dashboard", e);
			result.setMessage(e.getMessage());
			return ResponseEntity.badRequest().body(result);
		}
		return ResponseEntity.ok(result);
	}

	@PostMapping("/dashboard/monthly-migration")
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

	@PostMapping("/dashboard/monthly-cumulative-migration")
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
			result.add("migrated",
					dashboardService.getMonthlyCumulativeMigrated(form.getStartDate(), form.getEndDate()));
			result.add("planned", dashboardService.getMonthlyCumulativePlanned(form.getStartDate(), form.getEndDate()));
		} catch (Exception e) {
			log.error("Exception occurred when trying to get dashboard", e);
			result.setMessage(e.getMessage());
			return ResponseEntity.badRequest().body(result);
		}
		return ResponseEntity.ok(result);
	}

	@PostMapping("/dashboard/summary")
	public ResponseEntity<?> getDashboard() {
		RestResponseBody result = new RestResponseBody();
		try {
			result.add("win-agent-count", this.dashboardService.getAgentCount(AgentType.WINDOWS_BASED));
			result.add("debian-agent-count", this.dashboardService.getAgentCount(AgentType.DEBIAN_BASED));
			result.add("user-count", this.dashboardService.getUserCount());
			result.add("unresolved-problem-count", this.dashboardService.getUnResolvedProblemCount());
		} catch (Exception e) {
			log.error("Exception occurred when trying to get dashboard", e);
			result.setMessage(e.getMessage());
			return ResponseEntity.badRequest().body(result);
		}
		return ResponseEntity.ok(result);
	}

}

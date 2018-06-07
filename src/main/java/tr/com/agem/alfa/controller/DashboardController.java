package tr.com.agem.alfa.controller;

import java.io.Serializable;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import tr.com.agem.alfa.model.Problem;
import tr.com.agem.alfa.service.DashboardService;

/**
 * @author <a href="mailto:emre.akkaya@agem.com.tr">Emre Akkaya</a>
 */
@Controller
public class DashboardController {

	private static final Logger log = LoggerFactory.getLogger(DashboardController.class);

	private final DashboardService analysisService;

	@Autowired
	public DashboardController(DashboardService analysisService) {
		this.analysisService = analysisService;
	}

	@PostMapping("/dashboard")
	public ResponseEntity<?> getDashboard() {
		RestResponseBody result = new RestResponseBody();
		try {
			DashboardData data = new DashboardData();
			data.setAgentCount(this.analysisService.getAgentCount());
			data.setUserCount(this.analysisService.getUserCount());
			data.setProblemCount(this.analysisService.getUnResolvedProblemCount());
			data.setLastProblems(this.analysisService.getLastProblems());

			// TODO other statistics, chart data etc...

			result.add("dashboard-data", data);
		} catch (Exception e) {
			log.error("Exception occurred when trying to get dashboard", e);
			result.setMessage(e.getMessage());
			return ResponseEntity.badRequest().body(result);
		}
		return ResponseEntity.ok(result);
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

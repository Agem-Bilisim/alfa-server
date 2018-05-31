package tr.com.agem.alfa.controller;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import tr.com.agem.alfa.bpmn.AlfaBpmnFormService;
import tr.com.agem.alfa.bpmn.AlfaBpmnProcessEngine;
import tr.com.agem.alfa.model.CurrentUser;


@Controller
public class BpmTaskController {

	private static final Logger log = LoggerFactory.getLogger(BpmTaskController.class);


	@RequestMapping("/task/start/{processDeploymentId}")
	public ModelAndView startProcess(@PathVariable String processDeploymentId) {
		
		checkNotNull(processDeploymentId, "Invalid process id");
		
		@SuppressWarnings("unchecked")
		List<String> form = (List<String>) AlfaBpmnFormService.getInstance().getRenderedStartFormByProcessId(processDeploymentId);
		
		return new ModelAndView("task/start", "form", form);
	}

	@RequestMapping("/task/list")
	public ResponseEntity<?>  listTasks(Authentication authentication) {
		
		RestResponseBody result = new RestResponseBody();
		
		try {
			CurrentUser user = (CurrentUser) authentication.getPrincipal();
			List<String> role = new ArrayList<String>();
			role.add(user.getRole());
			
			List<Task> tasks = AlfaBpmnProcessEngine.getInstance().getTasksInvolved(user.getUsername(), role);
			result.add("tasks", checkNotNull(tasks, "Aktif görev yok..."));
		} catch (Exception e) {
			log.error("Exception occurred when trying to list user tasks", e);
			result.setMessage(e.getMessage());
			return ResponseEntity.badRequest().body(result);
		}
		
		return ResponseEntity.ok(result);
	}

	@RequestMapping("/task/get/{taskId}")
	public ModelAndView getTask(@PathVariable Long taskId) {
		checkNotNull(taskId, "Task not found...");
		
		@SuppressWarnings("unchecked")
		List<String> form = (List<String>) AlfaBpmnFormService.getInstance().getRenderedTaskForm(taskId.toString());
		
		return new ModelAndView("task/get", "form", form);
	}

	@RequestMapping("/task/complete/{taskId}")
	public String completeTask(@PathVariable Long taskId) {
		
		return "redirect:/bpm-process/list";
	}

}

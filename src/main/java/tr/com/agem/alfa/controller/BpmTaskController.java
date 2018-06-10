package tr.com.agem.alfa.controller;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import tr.com.agem.alfa.bpmn.AlfaBpmnFormService;
import tr.com.agem.alfa.bpmn.AlfaBpmnProcessEngine;
import tr.com.agem.alfa.bpmn.inputs.AlfaBpmnForm2MapConverter;
import tr.com.agem.alfa.form.bpm.BpmTaskForm;
import tr.com.agem.alfa.form.bpm.BpmTaskStartForm;
import tr.com.agem.alfa.model.CurrentUser;
import tr.com.agem.alfa.model.bpm.BpmProcess;
import tr.com.agem.alfa.service.BpmProcessService;


/**
 * @author <a href="mailto:ali.ozeren@agem.com.tr">Ali Ozkan Ozeren</a>
 *
 */
@Controller
public class BpmTaskController 
{

	private static final Logger log = LoggerFactory.getLogger(BpmTaskController.class);

	private final BpmProcessService bpmProcessService;
	
	@Autowired
	public BpmTaskController(BpmProcessService bpmProcessService) 
	{
		this.bpmProcessService = bpmProcessService;
	}

	@RequestMapping("/bpm-task/startForm/{processId}")
	public ModelAndView getProcessStartForm(@PathVariable Long processId, @RequestParam(required=false, value="relatedComponent") String component, 
			@RequestParam(required = false, value="relatedComponentId") Long componentId) 
	{
		checkNotNull(processId, "Invalid process id");
		
		BpmProcess bpmn = bpmProcessService.getBpmProcess(processId);
		
		checkNotNull(bpmn, "Cannot find process");
		checkNotNull(bpmn.getProcessDeploymentId(), "Process has not been deployed");
		
		
		@SuppressWarnings("unchecked")
		List<String> formString = (List<String>) AlfaBpmnFormService.getInstance().getRenderedStartFormByProcessId(bpmn.getProcessDeploymentId());
		
		BpmTaskStartForm form= new BpmTaskStartForm();
		form.setRelatedComponent(component);
		form.setRelatedComponentId(componentId);
		form.setFormString(formString);
		form.setProcessId(bpmn.getId());
		form.setProcessName(bpmn.getName());
		
		return new ModelAndView("bpm-task/start", "form", form);
	}

	@RequestMapping("/bpm-task/start/{processId}")
	public String startProcess(@PathVariable Long processId, @RequestParam Map<String, String> queryMap) 
	{
		checkNotNull(processId, "Invalid process id");
		
		BpmProcess bpmn = bpmProcessService.getBpmProcess(processId);
		
		checkNotNull(bpmn, "Cannot find process");
		checkNotNull(bpmn.getProcessDeploymentId(), "Process has not been deployed");
		
		AlfaBpmnFormService.getInstance().submitStartFormDataByProcessId(bpmn.getProcessDeploymentId(),
				new AlfaBpmnForm2MapConverter(), queryMap );

		log.debug("The bpm process is started: {} ", bpmn.getProcessDeploymentId());
		
		return "redirect:/bpm-process/" + processId;
	}
	
	@RequestMapping("/bpm-task/list")
	public ResponseEntity<?>  listTasks(Authentication authentication) 
	{
		RestResponseBody result = new RestResponseBody();
		
		try {
			CurrentUser user = (CurrentUser) authentication.getPrincipal();
			List<String> role = new ArrayList<String>();
			role.add(user.getRole());
			
			List<BpmTaskForm> list = new ArrayList<BpmTaskForm>();
			
			List<Task> tasks = AlfaBpmnProcessEngine.getInstance().getTasksInvolved(user.getUsername(), role);
			if (tasks != null) {
				for (Task t : tasks) {
					BpmTaskForm f = new BpmTaskForm();
					f.setTaskId(t.getId());
					f.setTaskDescription(t.getDescription());
					f.setCreatedDate(t.getCreateTime());
					list.add(f);
				}
			}
			
			tasks = AlfaBpmnProcessEngine.getInstance().getTasksInvolved(user.getId().toString(), null);
			if (tasks != null) {
				for (Task t : tasks) {
					BpmTaskForm f = new BpmTaskForm();
					f.setTaskId(t.getId());
					f.setTaskDescription(t.getDescription());
					f.setCreatedDate(t.getCreateTime());
					list.add(f);
				}
			}			
			
			result.add("tasks",  new PageImpl<BpmTaskForm>(list));
			
		} catch (Exception e) {
			log.error("Exception occurred when trying to list user tasks", e);
			result.setMessage(e.getMessage());
			return ResponseEntity.badRequest().body(result);
		}
		
		return ResponseEntity.ok(result);
	}
	
	@RequestMapping("/bpm-task/list-all")
	public String getListPage() {
		return "bpm-task/list";
	}	

	@RequestMapping("/bpm-task/get/{taskId}")
	public ModelAndView getTask(@PathVariable Long taskId) 
	{
		checkNotNull(taskId, "Task not found...");
		
		Task task = AlfaBpmnProcessEngine.getInstance().getTask(taskId.toString());

		@SuppressWarnings("unchecked")
		List<String> formString = (List<String>) AlfaBpmnFormService.getInstance().getRenderedTaskForm(taskId.toString());
		BpmTaskForm form= new BpmTaskForm();
		form.setFormString(formString);
		form.setTaskId(task.getId());
		form.setTaskDescription(task.getDescription());
		
		return new ModelAndView("bpm-task/edit", "form", form);
	}

	@RequestMapping("/bpm-task/complete/{taskId}")
	public String completeTask(@PathVariable Long taskId, @RequestParam Map<String, String> queryMap) {
		
		checkNotNull(taskId, "Task not found...");
		
		AlfaBpmnFormService.getInstance().submitTaskFormData(taskId.toString(), new AlfaBpmnForm2MapConverter(), queryMap);
		
		log.debug("The bpm task is completed: {} ", taskId);
		
		
		return "redirect:/bpm-task/list-all";
	}

}

package tr.com.agem.alfa.controller;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.activiti.engine.repository.ProcessDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import tr.com.agem.alfa.bpmn.AlfaBpmnProcessEngine;
import tr.com.agem.alfa.form.bpm.BpmProcessForm;
import tr.com.agem.alfa.model.CurrentUser;
import tr.com.agem.alfa.model.bpm.BpmProcess;
import tr.com.agem.alfa.service.BpmProcessService;


@Controller
public class ProcessConfigController {

	private static final Logger log = LoggerFactory.getLogger(ProcessConfigController.class);

	private final BpmProcessService bpmProcessService;

	@Value("${sys.page-size}")
	private Integer sysPageSize;

	@Autowired
	public ProcessConfigController(BpmProcessService bpmProcessService) {
		this.bpmProcessService = bpmProcessService;
	}

	@GetMapping("/bpm-process/create")
	public ModelAndView getCreatePage() {
		log.debug("Getting previously saved bpm process create form");
		return new ModelAndView("bpm-process/create", "form", new BpmProcessForm());
	}

	@PostMapping("/bpm-process/create")
	public String handleCreate(@Valid @ModelAttribute("form") BpmProcessForm form, BindingResult bindingResult,
			Authentication authentication) {
		log.debug("Processing create:{}, bindingResult:{}", form, bindingResult);
		if (bindingResult.hasErrors()) {
			// failed validation
			return "bpm-process/create";
		}
		try {
			CurrentUser user = (CurrentUser) authentication.getPrincipal();
			checkNotNull(user, "Current user not found.");
			bpmProcessService.save(toBpmProcessEntity(form, user.getUsername()));
		} catch (Exception e) {
			log.error("Exception occurred when trying to save the bpm process, assuming duplicate name-version", e);
			bindingResult.reject("name.version.exists", "Hata oluştu. Aynı süreç adı ve sürümüyle birden fazla kayıt olamaz.");
			return "bpm-process/create";
		}
		// everything fine redirect to list
		return "redirect:/bpm-process/list";
	}

	@GetMapping("/bpm-process/{id}")
	public ModelAndView getProcess(@PathVariable Long id) {
		log.debug("Getting page for the bpm process:{}", id);
		BpmProcess _bpmProcess = bpmProcessService.getBpmProcess(id);
		checkNotNull(_bpmProcess, String.format("Process:%d not found.", id));
		return new ModelAndView("bpm-process/edit", "form", toBpmProcessForm(_bpmProcess));
	}

	@GetMapping("/bpm-process/deploy/{id}")
	public ModelAndView deploy(@PathVariable Long id, Authentication authentication) {

		log.debug("Deploying the bpm process : {}", id);
		
		BpmProcess _bpmProcess = bpmProcessService.getBpmProcess(id);
		
		checkNotNull(_bpmProcess, String.format("Process:%d not found.", id));
		
		ProcessDefinition deployment = AlfaBpmnProcessEngine.getInstance().deployModel( _bpmProcess.getName(), new String(_bpmProcess.getContent()));

		CurrentUser user = (CurrentUser) authentication.getPrincipal();
		_bpmProcess.setProcessDeploymentId(deployment.getDeploymentId());
		_bpmProcess.setLastModifiedBy(user.getUsername());
		_bpmProcess.setLastModifiedDate(new Date());
		
		bpmProcessService.save(_bpmProcess);

		log.debug("The bpm process is deployed: {} ", id);
		
		
		return new ModelAndView("bpm-process/edit", "form", toBpmProcessForm(_bpmProcess));
	}
	

	@PostMapping("/bpm-process/{id}")
	public String handleUpdate(@PathVariable Long id, @Valid @ModelAttribute("form") BpmProcessForm form,
			BindingResult bindingResult, Authentication authentication) {
		log.debug("Processing update:{}, bindingResult:{}", form, bindingResult);
		if (bindingResult.hasErrors()) {
			// failed validation
			return "bpm-process/edit";
		}
		try {
			CurrentUser user = (CurrentUser) authentication.getPrincipal();
			checkNotNull(user, "Current user not found.");
			form.setId(id);
			bpmProcessService.save(toBpmProcessEntity(form, user.getUsername()));
		} catch (Exception e) {
			log.warn("Exception occurred when trying to save the bpm process, duplicate name and version", e);
			bindingResult.reject("name.version.exists", "Hata oluştu. Aynı süreç adı ve sürümüyle birden fazla kayıt olamaz.");
			return "bpm-process/edit";
		}
		// everything fine redirect to list
		return "redirect:/bpm-process/list";
	}
	
	@GetMapping("/bpm-process/list")
	public String getListPage() {
		log.debug("Getting installed bpm process list page");
		return "bpm-process/list";
	}

	@GetMapping("/bpm-process/list-paginated")
	public ResponseEntity<?> handleList(@RequestParam(value = "search", required = false) String search,
			Pageable pageable) {
		log.info("Getting bpm process page with page number:{} and size: {}", pageable.getPageNumber(),
				pageable.getPageSize());
		RestResponseBody result = new RestResponseBody();
		try {
			Page<BpmProcess> processes = bpmProcessService.getBpmProcesses(pageable, search);
			result.add("processes", checkNotNull(processes, "Process not found."));
		} catch (Exception e) {
			log.error("Exception occurred when trying to find bpm processs, assuming invalid parameters", e);
			result.setMessage(e.getMessage());
			return ResponseEntity.badRequest().body(result);
		}
		return ResponseEntity.ok(result);
	}

	@GetMapping(value="/bpm-process/image/{processDeploymentId}", produces = "image/png")
	@ResponseBody
	public ResponseEntity<byte[]> image(HttpServletRequest request, HttpServletResponse response, @PathVariable String processDeploymentId) 
	{
		 final HttpHeaders httpHeaders= new HttpHeaders();
		 httpHeaders.setContentType(MediaType.IMAGE_PNG);

		try {
			InputStream data = AlfaBpmnProcessEngine.getInstance().getProcessDiagramById(processDeploymentId);
			
			log.debug("image is retrieved for process : " + processDeploymentId);
			
			ByteArrayOutputStream bos=new ByteArrayOutputStream();
			int b;
			byte[] buffer = new byte[1024];
			while((b=data.read(buffer))!=-1){
				bos.write(buffer,0,b);
			}
			byte[] fileBytes=bos.toByteArray();
			data.close();
			bos.close();
			
			 return new ResponseEntity<byte[]>(fileBytes, httpHeaders, HttpStatus.OK);
			 
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ResponseEntity<byte[]>(new byte[]{}, httpHeaders, HttpStatus.NOT_FOUND);
	}
	
	private BpmProcess toBpmProcessEntity(BpmProcessForm form, String username) {
		
		BpmProcess entity = new BpmProcess();
		
		MultipartFile multipartFile = form.getFile();
        
		entity.setId(form.getId());
		entity.setName(form.getName());
		entity.setVersion(form.getVersion());
		entity.setProcessDeploymentId(form.getProcessDeploymentId());
		
		if (form.getId() == null && form.getFile() != null) {
			try {
				entity.setContent(multipartFile.getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
        
		Date date = new Date();
		entity.setCreatedBy(username);
		entity.setCreatedDate(date);
		entity.setLastModifiedBy(username);
		entity.setLastModifiedDate(date);
		
		return entity;
	}

	private BpmProcessForm toBpmProcessForm(BpmProcess entity) 
	{
		BpmProcessForm form = new BpmProcessForm();
		form.setId(entity.getId());
		form.setName(entity.getName());
		form.setVersion(entity.getVersion());
		form.setProcessDeploymentId(entity.getProcessDeploymentId());
		form.setCreatedBy(entity.getCreatedBy());
		form.setCreatedDate(entity.getCreatedDate());
		form.setLastModifiedBy(entity.getLastModifiedBy());
		form.setLastModifiedDate(entity.getLastModifiedDate());
		return form;
	}	
}

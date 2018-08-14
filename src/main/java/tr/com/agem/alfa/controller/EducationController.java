package tr.com.agem.alfa.controller;

import static com.google.common.base.Preconditions.checkNotNull;
import tr.com.agem.alfa.client.*;

import java.io.IOException;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import tr.com.agem.alfa.exception.AlfaException;
import tr.com.agem.alfa.form.EducationForm;
import tr.com.agem.alfa.form.EducationLdapUserForm;
import tr.com.agem.alfa.form.EducationReportForm;
import tr.com.agem.alfa.form.EducationUserForm;
import tr.com.agem.alfa.form.LdapUserForm;
import tr.com.agem.alfa.lms.LmsUser;
import tr.com.agem.alfa.locale.MessageSourceDecorator;
import tr.com.agem.alfa.mapper.SysMapper;
import tr.com.agem.alfa.messaging.factory.MessageFactory;
import tr.com.agem.alfa.messaging.message.ServerBaseMessage;
import tr.com.agem.alfa.messaging.service.MessagingService;
import tr.com.agem.alfa.model.CurrentUser;
import tr.com.agem.alfa.model.Education;
import tr.com.agem.alfa.model.EducationLdapUser;
import tr.com.agem.alfa.model.LdapUser;
import tr.com.agem.alfa.model.enums.EducationStatus;
import tr.com.agem.alfa.service.AgentService;
import tr.com.agem.alfa.service.EducationService;
import tr.com.agem.alfa.service.LdapService;

/**
 * @author <a href="mailto:emre.akkaya@agem.com.tr">Emre Akkaya</a>
 */
@Controller
public class EducationController {

	private static final Logger log = LoggerFactory.getLogger(EducationController.class);

	private final EducationService educationService;
	private final LdapService ldapService;
	private final AgentService agentService;
	private final MessagingService messagingService;
	private final MessageSourceDecorator messageSourceDecorator;
	private final SysMapper mapper;
	
	@PersistenceContext
	private EntityManager em;

	@Value("${sys.page-size}")
	private Integer sysPageSize;

	@Autowired
	public EducationController(EducationService educationService, LdapService ldapService, AgentService agentService,
			MessagingService messagingService, MessageSourceDecorator messageSourceDecorator, SysMapper mapper) {
		this.educationService = educationService;
		this.ldapService = ldapService;
		this.agentService = agentService;
		this.messagingService = messagingService;
		this.messageSourceDecorator = messageSourceDecorator;
		this.mapper = mapper;
	}

	@GetMapping("/education/create")
	public String getCreatePage(Model model) {
		model.addAttribute("form", new EducationForm());
		return "education/create";
	}

	@PostMapping("/education/create")
	public String handleEducationCreate(@Valid @ModelAttribute("form") EducationForm form, BindingResult bindingResult,
			Model model, Authentication authentication) {
		if (bindingResult.hasErrors()) {
			// failed validation
			return "education/create";
		}
		try {
			CurrentUser user = (CurrentUser) authentication.getPrincipal();
			checkNotNull(user, "Current user not found.");
			educationService.saveEducation(toEducationEntity(form, user.getUsername()));
		} catch (Exception e) {
			log.error("Exception occurred when trying to save the education, assuming invalid parameters", e);
			bindingResult.reject("unexpected.error", "Beklenmeyen hata oluştu.");
			return "education/create";
		}
		// everything fine redirect to list
		return "redirect:/education/list";
	}

	@PostMapping("/education/{educationId}/send/{messagingId}")
	public ResponseEntity<?> handleURLSend(@PathVariable(name = "educationId") Long educationId,
			@PathVariable(name = "messagingId") String messagingId) {
		RestResponseBody result = new RestResponseBody();
		try {
			Education education = educationService.getEducation(educationId);
			checkNotNull(education, String.format("Education:%d not found.", educationId));
			ServerBaseMessage message = MessageFactory.newURLRedirectionMessage(messagingId, education);
			messagingService.send(message);
		} catch (Exception e) {
			log.error("Exception occurred when trying to send URL, assuming invalid parameters", e);
			result.setMessage(e.getMessage());
			return ResponseEntity.badRequest().body(result);
		}
		return ResponseEntity.ok(result);
	}

	@GetMapping("/education/{id}")
	public String getEducation(@PathVariable Long id, Model model) {
		Education education = educationService.getEducation(id);
		checkNotNull(education, String.format("Education:%d not found.", id));
		model.addAttribute("form", mapper.toEducationForm(education));
		return "education/edit";
	}

	@PostMapping("/education/{id}")
	public String handleEducationUpdate(@PathVariable Long id, @Valid @ModelAttribute("form") EducationForm form,
			BindingResult bindingResult, Model model, Authentication authentication) {
		if (bindingResult.hasErrors()) {
			// failed validation
			return "education/edit";
		}
		try {
			CurrentUser user = (CurrentUser) authentication.getPrincipal();
			checkNotNull(user, "Current user not found.");
			educationService.saveEducation(toEducationEntity(form, user.getUsername()));
		} catch (Exception e) {
			log.warn("Exception occurred when trying to save the education, invalid parameters.", e);
			bindingResult.reject("unexpected.error", "Beklenmeyen hata oluştu.");
			return "education/edit";
		}
		// everything fine redirect to list
		return "redirect:/education/list";
	}

	@GetMapping("/education/list")
	public String getListPage(Model model) {
		model.addAttribute("agents", agentService.getAgents());
		return "education/list";
	}

	@GetMapping("/education/list-paginated")
	public ResponseEntity<?> handleEducationList(@RequestParam(value = "search", required = false) String search,
			Pageable pageable) {
		RestResponseBody result = new RestResponseBody();
		try {
			Page<Education> educations = educationService.getEducations(pageable, search);
			result.add("educations", checkNotNull(educations, "Educations not found."));
		} catch (Exception e) {
			log.error("Exception occurred when trying to find educations, assuming invalid parameters", e);
			result.setMessage(e.getMessage());
			return ResponseEntity.badRequest().body(result);
		}
		return ResponseEntity.ok(result);
	}

	@PostMapping("/education/{id}/delete")
	public ResponseEntity<?> handleDelete(@PathVariable Long id) {
		RestResponseBody result = new RestResponseBody();
		try {
			educationService.deleteEducation(checkNotNull(id, "ID not found."));
		} catch (Exception e) {
			log.error("Exception occurred when trying to delete education, assuming invalid parameters", e);
			result.setMessage(e.getMessage());
			return ResponseEntity.badRequest().body(result);
		}
		return ResponseEntity.ok(result);
	}

	@Transactional(noRollbackFor = Exception.class)
	@GetMapping("/education/snyc")
	public ResponseEntity<?> handleLmsSync(Authentication authentication) {
		RestResponseBody result = new RestResponseBody();
		//LMS User
		List<EducationUserForm> userListLms = new ArrayList<EducationUserForm>();
		List<EducationUserForm> userListAlfa = new ArrayList<EducationUserForm>();
		try {
			ObjectMapper mapper = new ObjectMapper();
			userListLms = mapper.readValue(RestClient.getJsonData("http://localhost:8081/alfalms/apiusers", ""),
																					new TypeReference<List<EducationUserForm>>() {});
			if (userListLms == null) throw new AlfaException("LMS sunucuya erişilemedi.");
		}catch(Exception e) {
			e.printStackTrace();
		}
		//ALFA USER ATTRIBUTE
		List<Object []> userAttribute = new ArrayList<Object []>();
		try {
			String userAttributeQuery = "select value, ldap_user_id from c_ldap_user_attribute where name in ('userPrincipalName', 'eposta', 'email', 'mail', 'posta')";
			Query query = em.createNativeQuery(userAttributeQuery);
			userAttribute = query.getResultList();
			
		}catch(Exception e) {
			e.printStackTrace();
		}		
		//Education
		List<EducationForm> educationListLms = new ArrayList<EducationForm>() ;
		List<String> educationAlfa = new ArrayList<String>();
		int statu = 0; // Aynı urun adına sahip ise statu = 1 --> UPDATE  -- status = 0 --> INSERT
		int counter = 0;
		try {
			ObjectMapper mapper = new ObjectMapper();
			educationListLms = mapper.readValue(RestClient.getJsonData("http://localhost:8081/alfalms/apieducations", ""),
																						new TypeReference<List<EducationForm>>() {});
			if (educationListLms == null) throw new AlfaException("LMS sunucuya erişilemedi.");
		}catch(Exception e) {
			e.printStackTrace();
		}		
		try {			
			String educationAlfaQuery = "SELECT e.label FROM c_education e";
			Query query = em.createNativeQuery(educationAlfaQuery);
			@SuppressWarnings("unchecked")
			List<Object> result1 = (List<Object>) query.getResultList(); 
			for( Object r : result1) {
				educationAlfa.add((String)r);
			}
		}catch ( Exception e) {
			e.printStackTrace();
		}
		for(EducationForm educationLms : educationListLms) {
			statu = 0;
			counter = 0;
			while(statu == 0 && counter<educationAlfa.size())
			{
				String label = educationLms.getLabel();
				String baslik = educationAlfa.get(counter);
				if(label.equalsIgnoreCase(baslik)) {
					statu = 1;
				}
				else counter++;
			}
			if(statu == 1) {
				try {			
					String updateEducationSql = "UPDATE c_education "
							+ "SET lms_education_id = :eId"
							+ " WHERE label = :label "; 
					Query query = em.createNativeQuery(updateEducationSql);
					query.setParameter("eId",educationLms.getId());
					query.setParameter("label", educationLms.getLabel());
					query.executeUpdate();
			}catch(Exception e1) {
				e1.printStackTrace();
			}	
			}else {
				try {
					Date date = new Date();
					DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
					String educationSql = "INSERT INTO c_education (label,description,url,lms_education_id,created_by,created_date)"
							+ " VALUES (:label,' ',' ',:lmsId,'admin',:date)";
					Query query = em.createNativeQuery(educationSql);
					query.setParameter("label", educationLms.getLabel()); 
					query.setParameter("lmsId", educationLms.getId());
					query.setParameter("date",df.format(date));
					query.executeUpdate();
					
				}catch(Exception e1) {
					e1.printStackTrace();
				}
			}
		}				
		//EducationReport 
		
		List<EducationReportForm> educationReportListLms = new ArrayList<EducationReportForm>() ;
		List<Integer> educationReportAlfa = new ArrayList<Integer>();
		
		int statuReport = 0; // Aynı education_id 'ye sahip ise statu = 1 --> UPDATE  | status = 0 --> INSERT
		int counterReport = 0;
		
		try {
			ObjectMapper mapper = new ObjectMapper();
			educationReportListLms =mapper.readValue(RestClient.getJsonData("http://localhost:8081/alfalms/apieducationreports", ""),
					new TypeReference<List<EducationReportForm>>() {});
			if ( educationReportListLms == null) throw new AlfaException("LMS Sunucuya erişilemedi.");
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		try {
			String educationReportAlfaQuery = "SELECT e.education_user_education_id FROM c_education_user_education e";
			Query query = em.createNativeQuery(educationReportAlfaQuery);
			@SuppressWarnings("unchecked")
			List<Integer> result1 = (List<Integer>) query.getResultList(); 
			educationReportAlfa = result1 ;
			
		}catch(Exception e) {
			e.printStackTrace();
			
		}
		
		for(EducationReportForm educationReportLms : educationReportListLms) {			
			statuReport = 0;
			counterReport = 0;
			long education_id = -1 ; //default
			long ldap_user_id = -1 ; 
			
			//Foreign key educationId parametresini çekme 
			try {
				BigInteger bi ;
				String eIdParamQuery = "SELECT ce.id FROM c_education ce where ce.lms_education_id = :erEducationId limit 1";
				Query query = em.createNativeQuery(eIdParamQuery);
				query.setParameter("erEducationId",educationReportLms.getEducationID());
				bi =  new BigInteger(query.getSingleResult()+"");
				education_id = (long)bi.longValue();
				
				
			}catch(Exception e) {
				e.printStackTrace();
			}
				
			
			String emailLms = educationReportLms.getUserEmail();
			String tempMail ;
			BigInteger bi;
			for (Object[] valueAndId : userAttribute) {
				tempMail = valueAndId[0].toString();
				if( tempMail.equals(emailLms)) {
					bi = new BigInteger(valueAndId[1]+"");
					ldap_user_id = (long)bi.longValue();
					break;
				}
			}
			
			if(ldap_user_id != -1) {
				
				while(statuReport == 0 && counterReport<educationReportAlfa.size())
				{ 
					//bigint to long
					BigInteger bi2 ;
					bi2 = new BigInteger(educationReportAlfa.get(counterReport)+"");
					long erIdAlfa = (long)bi2.longValue();				
					long erId = educationReportLms.getID();	
					if(erIdAlfa == erId) {
						statuReport = 1;
					}
					else counterReport++;
				}
				if(statuReport == 1) {
					try {					
						String updateEducationReportSql = "UPDATE c_education_user_education "
								+ "SET  status = :status, "
								+ "duration = :duration, exam_score = :examScore, exam_status = :examStatus, education_id = :eId" + 
								",ldap_user_id = :uId "
								+ " WHERE education_user_education_id = :erId  ";
						Query query1 = em.createNativeQuery(updateEducationReportSql);
						query1.setParameter("erId", educationReportLms.getID());
						query1.setParameter("eId", education_id);
						query1.setParameter("uId", ldap_user_id);
						query1.setParameter("status", educationReportLms.getDurumu());
						query1.setParameter("duration", educationReportLms.getSure());
						query1.setParameter("examScore", educationReportLms.getSinavPuani());
						query1.setParameter("examStatus", educationReportLms.getSinavDurumu());
						query1.executeUpdate();
				}catch(Exception e1) {
					e1.printStackTrace();
				}	
				}else {
					try {
						String educationReportSql = "INSERT INTO c_education_user_education (education_user_education_id,status,duration,exam_score,exam_status,education_id,ldap_user_id) "
								+ "VALUES ( :erId, :status, :duration, :examScore, :examStatus , :eId, :uId)";
						Query query1 = em.createNativeQuery(educationReportSql);
						query1.setParameter("erId", educationReportLms.getID());
						query1.setParameter("status", educationReportLms.getDurumu());
						query1.setParameter("duration", educationReportLms.getSure());
						query1.setParameter("examScore", educationReportLms.getSinavPuani());
						query1.setParameter("examStatus", educationReportLms.getSinavDurumu());
						query1.setParameter("eId", education_id);
						query1.setParameter("uId",ldap_user_id);
						query1.executeUpdate();
						
					}catch(Exception e1) {
						e1.printStackTrace();
					}
				}				
			}			
		}		
		
		result.setMessage(String.format("LMS eğitim kayıtları başarıyla senkronize edildi."));
		return ResponseEntity.ok(result);	
	}

	@GetMapping("/education/{educationId}/users")
	public @ResponseBody ResponseEntity<?> getEducationUsersPage(@PathVariable Long educationId) {
		log.info("Getting details for education with education id:{}", educationId);
		RestResponseBody result = new RestResponseBody();
		try {
			List<EducationLdapUserForm> forms = mapper.toEducationLdapUserFormList(educationService.getEducationUsers(educationId));
			result.add("users", messageSourceDecorator.decorate("status", "statusLabel", forms));
		} catch (Exception e) {
			log.error("Exception occurred when trying to find education", e);
			result.setMessage(e.getMessage());
			return ResponseEntity.badRequest().body(result);
		}
		return ResponseEntity.ok(result);
	}

	private Education toEducationEntity(EducationForm form, String username) {
		Education entity = mapper.toEducationEntity(form);
		Date date = new Date();
		entity.setCreatedBy(username);
		entity.setCreatedDate(date);
		entity.setLastModifiedBy(username);
		entity.setLastModifiedDate(date);
		return entity;
	}

}

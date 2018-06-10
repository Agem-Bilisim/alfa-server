/**
 * 
 */
package tr.com.agem.alfa.form.bpm.listeners;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.delegate.Expression;
import org.springframework.beans.factory.annotation.Autowired;

import tr.com.agem.alfa.model.Problem;
import tr.com.agem.alfa.service.ProblemService;
import tr.com.agem.alfa.service.SpringTools;

/**
 * @author <a href="mailto:ali.ozeren@agem.com.tr">Ali Ozkan Ozeren</a>
 *
 */
public class ProblemUpdate implements ExecutionListener {

	
	private static final long serialVersionUID = 4712401691315158203L;

	@Autowired
	private ProblemService problemService = (ProblemService) SpringTools.getInstance().getContext().getBean("problemService");
	
	private Expression startDateVar = null;
	private Expression estimatedDateVar = null;
	
	private SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");

	/* (non-Javadoc)
	 * @see org.activiti.engine.delegate.ExecutionListener#notify(org.activiti.engine.delegate.DelegateExecution)
	 */
	@Override
	public void notify(DelegateExecution execution) {
			Map<String, Object> vars = execution.getVariables();
		
		if ("Sorun".equals(vars.get("component")) && vars.get("componentId") != null) {
			Object startDate= null;
			try {
				startDate= startDateVar.getValue(null);
			} catch (Exception e) {
				startDate = null;
			}
			
			Object estimatedDate= null;
			try {
				estimatedDate= estimatedDateVar.getValue(null);
			} catch (Exception e) {
				estimatedDate = null;
			}
			
			Long id = Long.parseLong(vars.get("componentId").toString());
			
			Problem problem = problemService.getProblem(id);
			
			if (startDate != null) {
				try {
					if (problem.getWorkStartDate() == null) {
						problem.setWorkStartDate(sf.parse((String)vars.get(startDate)));
					}
				} catch (ParseException e) {
				}
			}
			if (estimatedDate != null) {
				try {
					if (problem.getEstimatedSolutionDate() == null) {
						problem.setEstimatedSolutionDate(sf.parse((String)vars.get(estimatedDate)));
					}
				} catch (ParseException e) {
				}
			}
			problemService.saveProblem(problem);
			
		}		
	}

}

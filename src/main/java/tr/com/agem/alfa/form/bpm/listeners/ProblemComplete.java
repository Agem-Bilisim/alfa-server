/**
 * 
 */
package tr.com.agem.alfa.form.bpm.listeners;

import java.util.Date;
import java.util.Map;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;

import tr.com.agem.alfa.model.Problem;
import tr.com.agem.alfa.service.ProblemService;
import tr.com.agem.alfa.service.SpringTools;

/**
 * @author <a href="mailto:ali.ozeren@agem.com.tr">Ali Ozkan Ozeren</a>
 *
 */
public class ProblemComplete implements ExecutionListener {

	
	private static final long serialVersionUID = 4712401231315158203L;

	@Autowired
	private ProblemService problemService = (ProblemService) SpringTools.getInstance().getContext().getBean("problemService");
	
	/* (non-Javadoc)
	 * @see org.activiti.engine.delegate.ExecutionListener#notify(org.activiti.engine.delegate.DelegateExecution)
	 */
	@Override
	public void notify(DelegateExecution execution) {
		
		Map<String, Object> vars = execution.getVariables();
		
		if ("Sorun".equals(vars.get("component")) && vars.get("componentId") != null) {

			Long id = Long.parseLong(vars.get("componentId").toString());
			
			Problem problem = problemService.getProblem(id);
			
			if (problem.getSolutionDate() == null) {
				problem.setSolutionDate(new Date());
				problem.setSolved(true);
			}
			
			problemService.saveProblem(problem);
			
		}		
	}

}

package tr.com.agem.alfa.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import tr.com.agem.alfa.model.Problem;
import tr.com.agem.alfa.repository.ProblemRepository;

/**
 * @author <a href="mailto:emre.akkaya@agem.com.tr">Emre Akkaya</a>
 */
@Component
@Transactional
public class ProblemService {

	// @formatter:off
	private static final String LIST_PROBLEMS_WITH_RELATED_ITEMS = 
			"SELECT\n" + 
			"  p.id,\n" + 
			"  p.created_date,\n" + 
			"  p.created_by,\n" + 
			"  p.last_modified_date,\n" + 
			"  p.last_modified_by,\n" + 
			"  p.label,\n" + 
			"  p.description,\n" + 
			"  p.solved,\n" + 
			"  p.estimated_solution_date,\n" + 
			"  p.work_start_date,\n" + 
			"  p.solution_date,\n" + 
			"  (select group_concat(DISTINCT ap.tag SEPARATOR ', ')\n" + 
			"   from c_problem_reference r\n" + 
			"     inner join c_agent_peripheral ap on (ap.id = r.reference_id and r.reference_type = 2)\n" + 
			"   where r.problem_id = p.id) as related_peripherals,\n" + 
			"  (select group_concat(DISTINCT gpu.subsystem SEPARATOR ', ')\n" + 
			"   from c_problem_reference r\n" + 
			"     inner join c_agent_gpu gpu\n" + 
			"       on (gpu.id = r.reference_id and r.reference_type = 6 and trim(gpu.subsystem) is not null and\n" + 
			"           trim(gpu.subsystem) <> '')\n" + 
			"   where r.problem_id = p.id) as related_hardwares,\n" + 
			"  (select group_concat(DISTINCT pc.name SEPARATOR ', ')\n" + 
			"   from c_problem_reference r\n" + 
			"     inner join c_agent_package pc on (pc.id = r.reference_id and r.reference_type = 7)\n" + 
			"   where r.problem_id = p.id) as related_softwares\n" + 
			"from c_problem p";
	// @formatter:on

	@PersistenceContext
	private EntityManager em;

	private final ProblemRepository problemRepository;

	@Autowired
	public ProblemService(ProblemRepository problemRepository) {
		this.problemRepository = problemRepository;
	}

	public Page<Problem> getProblems(Pageable pageable, String search, List<Integer> _referenceTypes) {
		Assert.notNull(pageable, "Pageable must not be null.");
		if (search != null && !search.isEmpty()) {
			return this.problemRepository.findDistinctByLabelContainingOrDescriptionContainingAllIgnoringCase(search,
					search, pageable);
		} else if (_referenceTypes != null && !_referenceTypes.isEmpty()) {
			return this.problemRepository.findDistinctByReferencesReferenceTypeIn(_referenceTypes, pageable);
		}
		return this.problemRepository.findAll(pageable);
	}
	
	public void saveProblem(Problem problem) {
		Assert.notNull(problem, "Problem must not be null.");
		// Update
		if (problem.getId() != null) {
			// Delete previous reference records
			Query query = em.createNativeQuery("DELETE FROM c_problem_reference WHERE problem_id = :problemId");
			query.setParameter("problemId", problem.getId());
			query.executeUpdate();
			// Update problem
			Problem p = problemRepository.findOne(problem.getId());
			p.setLabel(problem.getLabel());
			p.setDescription(problem.getDescription());
			p.setSolved(problem.getSolved());
			p.setSolutionDate(problem.getSolutionDate());
			p.setEstimatedSolutionDate(problem.getEstimatedSolutionDate());
			p.setWorkStartDate(problem.getWorkStartDate());
			p.getReferences().addAll(problem.getReferences());
			this.em.merge(p);
			return;
		}
		// Create
		this.problemRepository.save(problem);
	}
	
	public Problem getProblem(Long id) {
		Assert.notNull(id, "ID must not be null");
		return this.problemRepository.findOne(id);
	}

	public void deleteProblem(Long id) {
		Assert.notNull(id, "ID must not be null");
		this.problemRepository.delete(id);
	}

	@SuppressWarnings("unchecked")
	public List<Object[]> getProblemsWithDetails() {
		return this.em.createNativeQuery(LIST_PROBLEMS_WITH_RELATED_ITEMS).getResultList();
	}

}

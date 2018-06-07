package tr.com.agem.alfa.service;

import java.util.Iterator;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import tr.com.agem.alfa.model.Problem;
import tr.com.agem.alfa.model.ProblemReference;
import tr.com.agem.alfa.repository.ProblemRepository;

/**
 * @author <a href="mailto:emre.akkaya@agem.com.tr">Emre Akkaya</a>
 */
@Component
@Transactional
public class ProblemService {

	@PersistenceContext
	private EntityManager em;

	private final ProblemRepository problemRepository;

	@Autowired
	public ProblemService(ProblemRepository problemRepository) {
		this.problemRepository = problemRepository;
	}

	public Page<Problem> getProblems(Pageable pageable, String search, Integer referenceType) {
		Assert.notNull(pageable, "Pageable must not be null.");
		if (search != null && !search.isEmpty()) {
			return this.problemRepository.findDistinctByLabelContainingOrDescriptionContainingAllIgnoringCase(search,
					search, pageable);
		} else if (referenceType != null) {
			return this.problemRepository.findDistinctByReferencesReferenceType(referenceType, pageable);
		}
		return this.problemRepository.findAll(pageable);
	}

	public void saveProblem(Problem problem) {
		Assert.notNull(problem, "Problem must not be null.");
		Problem p = null;
		// Update
		if (problem.getId() != null && (p = problemRepository.findOne(problem.getId())) != null) {
			p.setLabel(problem.getLabel());
			p.setDescription(problem.getDescription());
			p.setSolved(problem.getSolved());
			// Remove previous references!
			if (p.getReferences() != null) {
				Iterator<ProblemReference> it = p.getReferences().iterator();
				while (it.hasNext()) {
					ProblemReference ref = it.next();
					this.em.remove(ref);
					it.remove();
				}
				this.em.flush();
			}
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

}

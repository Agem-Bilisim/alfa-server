package tr.com.agem.alfa.service;

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
@Component("problemService")
@Transactional
public class ProblemServiceImpl implements ProblemService {

	private final ProblemRepository problemRepository;

	@Autowired
	public ProblemServiceImpl(ProblemRepository problemRepository) {
		this.problemRepository = problemRepository;
	}

	@Override
	public Page<Problem> getProblems(Pageable pageable, String search, Integer referenceType) {
		Assert.notNull(pageable, "Pageable must not be null.");
		if (search != null && !search.isEmpty()) {
			return this.problemRepository.findByLabelContainingOrDescriptionContainingAllIgnoringCase(search, search, pageable);
		} else if (referenceType != null) {
			return this.problemRepository.findByReferencesReferenceType(referenceType, pageable);
		}
		return this.problemRepository.findAll(pageable);
	}
	
}

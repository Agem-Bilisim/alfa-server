package tr.com.agem.alfa.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import tr.com.agem.alfa.model.Problem;

/**
 * @author <a href="mailto:emre.akkaya@agem.com.tr">Emre Akkaya</a>
 */
public interface ProblemService {

	Page<Problem> getProblems(Pageable pageable, String search, Integer referenceType);

}

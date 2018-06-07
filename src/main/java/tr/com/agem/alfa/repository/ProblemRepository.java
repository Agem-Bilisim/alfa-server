package tr.com.agem.alfa.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import tr.com.agem.alfa.model.Problem;

/**
 * @author <a href="mailto:emre.akkaya@agem.com.tr">Emre Akkaya</a>
 */
public interface ProblemRepository extends JpaRepository<Problem, Long> {

	Page<Problem> findByLabelContainingOrDescriptionContainingAllIgnoringCase(String search, String search2,
			Pageable pageable);

	Page<Problem> findByReferencesReferenceType(Integer referenceType, Pageable pageable);

	List<Problem> findFirst5ByOrderByCreatedDateDesc();

}

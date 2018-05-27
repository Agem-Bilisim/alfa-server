package tr.com.agem.alfa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import tr.com.agem.alfa.model.Survey;

/**
 * 
 * @author <a href="mailto:emre.akkaya@agem.com.tr">Emre Akkaya</a>
 *
 */
public interface SurveyRepository extends JpaRepository<Survey, Long> {

	Page<Survey> findByLabelContainingOrDescriptionContainingAllIgnoringCase(String search, Pageable pageable);

}

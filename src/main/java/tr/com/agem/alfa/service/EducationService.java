package tr.com.agem.alfa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import tr.com.agem.alfa.model.Education;
import tr.com.agem.alfa.repository.EducationRepository;

/**
 * @author <a href="mailto:emre.akkaya@agem.com.tr">Emre Akkaya</a>
 */
@Component
@Transactional
public class EducationService {

	private final EducationRepository educationRepository;

	@Autowired
	public EducationService(EducationRepository educationRepository) {
		this.educationRepository = educationRepository;
	}

	public Education getEducation(Long id) {
		Assert.notNull(id, "ID must not be null");
		return this.educationRepository.findOne(id);
	}

	public void saveEducation(Education education) {
		Assert.notNull(education, "Education must not be null.");
		Education e = null;
		if (education.getId() != null && (e = this.educationRepository.findOne(education.getId())) != null) {
			// Update
			e.setLabel(education.getLabel());
			e.setDescription(education.getDescription());
			e.setUrl(education.getUrl());
			this.educationRepository.save(e);
			return;
		}
		// Create
		this.educationRepository.save(education);
	}

	public Page<Education> getEducations(Pageable pageable, String search) {
		Assert.notNull(pageable, "Pageable must not be null.");
		if (search != null && !search.isEmpty()) {
			return this.educationRepository.findByLabelContainingOrDescriptionContainingAllIgnoringCase(search,
					pageable);
		}
		return this.educationRepository.findAll(pageable);
	}

	public void deleteEducation(Long id) {
		Assert.notNull(id, "ID must not be null.");
		this.educationRepository.delete(id);
	}

}

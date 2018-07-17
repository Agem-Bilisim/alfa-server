package tr.com.agem.alfa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import tr.com.agem.alfa.model.Education;
import tr.com.agem.alfa.model.EducationLdapUser;
import tr.com.agem.alfa.repository.EducationLdapUserRepository;
import tr.com.agem.alfa.repository.EducationRepository;

/**
 * @author <a href="mailto:emre.akkaya@agem.com.tr">Emre Akkaya</a>
 */
@Component
@Transactional
public class EducationService {

	private final EducationRepository educationRepository;
	private final EducationLdapUserRepository educationLdapUserRepository;

	@Autowired
	public EducationService(EducationRepository educationRepository, EducationLdapUserRepository educationLdapUserRepository) {
		this.educationRepository = educationRepository;
		this.educationLdapUserRepository = educationLdapUserRepository;
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
			// Handle education users as well...
			if (education.getEducationUsers() != null) {
				for (EducationLdapUser elu : education.getEducationUsers()) {
					e.addEducationUser(elu);
				}
			}
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

	public List<Education> getEducations() {
		return this.educationRepository.findAll();
	}

	public void deleteEducation(Long id) {
		Assert.notNull(id, "ID must not be null.");
		this.educationRepository.delete(id);
	}

	public Education getEducationByLmsId(Long lmsEducationId) {
		Assert.notNull(lmsEducationId, "ID must not be null.");
		// TODO this should be LMS id
		return this.educationRepository.findById(lmsEducationId);
	}

	public List<EducationLdapUser> getEducationUsers(Long educationId) {
		Assert.notNull(educationId, "ID must not be null.");
		return this.educationLdapUserRepository.findByEducationId(educationId);
	}

}

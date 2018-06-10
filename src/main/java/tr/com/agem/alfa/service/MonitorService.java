package tr.com.agem.alfa.service;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import tr.com.agem.alfa.model.Tag;
import tr.com.agem.alfa.repository.TagRepository;

@Component
@Transactional(rollbackFor = Exception.class)
public class MonitorService {
	@PersistenceContext
	private EntityManager em;

	private final TagRepository tagRepository;

	@Autowired
	public MonitorService(TagRepository tagRepository) {
		super();
		this.tagRepository = tagRepository;
	}
	
	public Page<Tag> getTags(Pageable pageable, String search) {
		Assert.notNull(pageable, "Pageable must not be null.");
		if (search != null && !search.isEmpty()) {
			return this.tagRepository.findByNameContainingAllIgnoringCase(search,pageable);
		}
		return this.tagRepository.findAll(pageable);
	}

	public int updateTag(Tag tag) {
		Query update = em.createNativeQuery("update c_agent_tag set planned_migration_date = :planned_migration_date, last_modified_by = :last_modified_by, last_modified_date = :last_modified_date where id = :id");
		update.setParameter("planned_migration_date", tag.getPlannedMigrationDate());
		update.setParameter("last_modified_date", new Date());
		update.setParameter("last_modified_by", tag.getLastModifiedBy());
		update.setParameter("id", tag.getId());
		return update.executeUpdate();
	}



}

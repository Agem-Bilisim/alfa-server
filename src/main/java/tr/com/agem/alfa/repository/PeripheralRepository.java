package tr.com.agem.alfa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import tr.com.agem.alfa.model.PeripheralDevice;

/**
 * @author <a href="mailto:emre.akkaya@agem.com.tr">Emre Akkaya</a>
 */
public interface PeripheralRepository extends JpaRepository<PeripheralDevice, Long> {

	PeripheralDevice findByTag(String tag);

	@Query("SELECT i from PeripheralDevice i WHERE upper(i.tag) LIKE concat('%',?2 ,'%') and " +
			"     ((?1 = 'E' and coalesce(i.compatible,'H') = 'E' and  not exists (select p.id from Problem p inner join p.references r " +
			"                    where coalesce(p.solved,0) = 0 and r.referenceType =2  and r.referenceId = i.id )) " +
			"		or  " +
			"       (?1 = 'H' and (coalesce(i.compatible,'H') = 'H' or exists (select p.id from Problem p inner join p.references r " +
			"                    where coalesce(p.solved,0) = 0 and r.referenceType =2  and r.referenceId = i.id ))))")
	Page<PeripheralDevice> findByCompatibleAndTagContainingAllIgnoringCase(String compatible, String tag, Pageable pageable);

	@Query("SELECT i from PeripheralDevice i WHERE " +
			"     ((?1 = 'E' and coalesce(i.compatible,'H') = 'E' and  not exists (select p.id from Problem p inner join p.references r " +
			"                    where coalesce(p.solved,0) = 0 and r.referenceType =2  and r.referenceId = i.id)) " +
			"		or  " +
			"       (?1 = 'H' and (coalesce(i.compatible,'H') = 'H' or exists (select p.id from Problem p inner join p.references r " +
			"                    where coalesce(p.solved,0) = 0 and r.referenceType =2  and r.referenceId = i.id ))))")
	Page<PeripheralDevice> findByCompatible(String compatible, Pageable pageable);

}

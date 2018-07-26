package tr.com.agem.alfa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import tr.com.agem.alfa.model.CompatibleHardware;

/**
 * @author <a href="mailto:ali.ozeren@agem.com.tr">Ali Ozkan Ozeren</a>
 *
 */
public interface CompatibleHardwareRepository extends JpaRepository<CompatibleHardware, Long> {

	@Query(value="select t.id, t.name, t.product, t.version, t.type from " + 
	        " (select g.id as id, g.subsystem as name,  g.kernel as product, g.driver_version as version, 'GPU' as type, coalesce(g.compatible,'H') as compatible from c_agent_gpu g\n" + 
			"UNION\n" + 
			"select i.id, i.vendor, i.product, i.version, 'Network Interface', coalesce(i.compatible,'H') as compatible  from c_agent_inet i\n" + 
			") as t " +
			" where (:compatible = 'E' and compatible = 'E' and  not exists (select p.id from c_problem p inner join c_problem_reference r on (r.problem_id = p.id) " +
			"                    where coalesce(p.solved,0) = 0 and r.reference_type in (6,8) and r.reference_id = t.id )) " +
			"		or  " +
			"       (:compatible = 'H' and (compatible = 'H' or  exists (select p.id from c_problem p inner join c_problem_reference r on (r.problem_id = p.id) " +
					"                    where coalesce(p.solved,0) = 0 and r.reference_type in (6,8) and r.reference_id = t.id ))) " +			
			" ORDER BY t.name asc \n#pageable\n ",
			countQuery= "select count(1) from " +
			        " (select g.id as id, coalesce(g.compatible,'H') as compatible from c_agent_gpu g\n" + 
					"UNION\n" + 
					"select i.id, coalesce(i.compatible,'H') as compatible  from c_agent_inet i\n" + 
					") as t " +
					" where (:compatible = 'E' and compatible = 'E' and  not exists (select p.id from c_problem p inner join c_problem_reference r on (r.problem_id = p.id) " +
					"                    where coalesce(p.solved,0) = 0 and r.reference_type in (6,8) and r.reference_id = t.id )) " +
					"		or  " +
					"       (:compatible = 'H' and (compatible = 'H' or  exists (select p.id from c_problem p inner join c_problem_reference r on (r.problem_id = p.id) " +
					"                    where coalesce(p.solved,0) = 0 and r.reference_type in (6,8) and r.reference_id = t.id ))) ",
			nativeQuery = true)
	Page<CompatibleHardware> findByCompatible(@Param("compatible") String compatible, Pageable pageable);

	@Query(value="select t.id, t.name, t.product, t.version, t.type from " + 
	        " (select g.id as id, g.subsystem as name,  g.kernel as product, g.driver_version as version, 'GPU' as type, coalesce(g.compatible,'H') as compatible "+ 
			"     from c_agent_gpu g where upper(g.subsystem) like upper(concat('%',:name ,'%'))\n" + 
			"UNION\n" + 
			"select i.id, i.vendor, i.product, i.version, 'Network Interface', coalesce(i.compatible,'H') as compatible  "+ 
			"    from c_agent_inet i where upper(i.vendor) like upper(concat('%',:name ,'%'))\n" + 
			") as t " +
			" where (:compatible = 'E' and compatible = 'E' and  not exists (select p.id from c_problem p inner join c_problem_reference r on (r.problem_id = p.id) " +
			"                    where coalesce(p.solved,0) = 0 and r.reference_type in (6,8) and r.reference_id = t.id )) " +
			"		or  " +
			"       (:compatible = 'H' and (compatible = 'H' or  exists (select p.id from c_problem p inner join c_problem_reference r on (r.problem_id = p.id) " +
					"                    where coalesce(p.solved,0) = 0 and r.reference_type in (6,8) and r.reference_id = t.id ))) " +			
			" ORDER BY t.name asc \n#pageable\n ",
			countQuery= "select count(1) from " +
			        " (select g.id as id, coalesce(g.compatible,'H') as compatible from c_agent_gpu g where upper(i.vendor) like upper(concat('%',:name ,'%')) \n" + 
					"UNION\n" + 
					"select i.id, coalesce(i.compatible,'H') as compatible  from c_agent_inet i where upper(i.vendor) like upper(concat('%',:name ,'%'))\n" + 
					") as t " +
					" where (:compatible = 'E' and compatible = 'E' and  not exists (select p.id from c_problem p inner join c_problem_reference r on (r.problem_id = p.id) " +
					"                    where coalesce(p.solved,0) = 0 and r.reference_type in (6,8) and r.reference_id = t.id )) " +
					"		or  " +
					"       (:compatible = 'H' and (compatible = 'H' or  exists (select p.id from c_problem p inner join c_problem_reference r on (r.problem_id = p.id) " +
					"                    where coalesce(p.solved,0) = 0 and r.reference_type in (6,8) and r.reference_id = t.id ))) ",
			nativeQuery = true)
	Page<CompatibleHardware> findByCompatibleAndNameContainingAllIgnoringCase(@Param("compatible") String compatible, @Param("name") String name, Pageable pageable);


	
	
	
}

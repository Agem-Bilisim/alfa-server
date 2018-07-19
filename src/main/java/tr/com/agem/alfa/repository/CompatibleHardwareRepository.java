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

	@Query(value="select t.* from (select g.id as id, g.subsystem as name,  g.kernel as product, g.driver_version as version, 'GPU' as type from c_agent_gpu g\n" + 
			"where g.compatible = :compatible \n" + 
			"UNION\n" + 
			"select i.id, i.vendor, i.product, i.version, 'Network Interface'  from c_agent_inet i\n" + 
			"where i.compatible = :compatible) as t ORDER BY t.name asc \n#pageable\n ",
			countQuery= "select count(1) from (select g.id as id, g.subsystem as name, g.kernel  as product, g.driver_version as version, 'GPU' as type from c_agent_gpu g\n" + 
					"where g.compatible = :compatible \n" + 
					"UNION\n" + 
					"select i.id, i.vendor, i.product, i.version, 'Network Interface'  from c_agent_inet i\n" + 
					"where i.compatible = :compatible) as t",
			nativeQuery = true)
	Page<CompatibleHardware> findByCompatible(@Param("compatible") String compatible,Pageable pageable);


	@Query(value="select t.* from (select g.id as id, g.subsystem  as name,  g.kernel as product, g.driver_version as version, 'GPU' as type from c_agent_gpu g\n" + 
			"where g.compatible = :compatible and upper(g.subsystem) like upper(concat('%',:name ,'%'))\n" + 
			"UNION\n" + 
			"select i.id, i.vendor, i.product, i.version, 'Network Interface'  from c_agent_inet i\n" + 
			"where i.compatible = :compatible and upper(i.vendor) like upper(concat('%',:name ,'%'))) "
			+ " as t ORDER BY t.name asc \n#pageable\n ",
			countQuery= "select count(1) from (select g.id as id, g.subsystem as name, g.kernel as product, g.driver_version as version, 'GPU' as type from c_agent_gpu g\n" + 
					"where g.compatible = :compatible and upper(g.subsystem) like upper(concat('%',:name ,'%')) \n" + 
					"UNION\n" + 
					"select i.id, i.vendor, i.product, i.version, 'Network Interface'  from c_agent_inet i\n" + 
					"where i.compatible = :compatible and upper(i.vendor) like upper(concat('%',:name ,'%'))) as t",
			nativeQuery = true)
	Page<CompatibleHardware> findByCompatibleAndNameContainingAllIgnoringCase(@Param("compatible") String compatible, @Param("name") String name, Pageable pageable);


	
	
	
}

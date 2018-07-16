package tr.com.agem.alfa.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import tr.com.agem.alfa.model.Problem;
import tr.com.agem.alfa.model.enums.AgentType;
import tr.com.agem.alfa.repository.AgentRepository;
import tr.com.agem.alfa.repository.LdapUserRepository;
import tr.com.agem.alfa.repository.ProblemRepository;

/**
 * @author <a href="mailto:emre.akkaya@agem.com.tr">Emre Akkaya</a>
 */
@Component
@Transactional
public class DashboardService {

	@PersistenceContext
	private EntityManager em;

	private final AgentRepository agentRepository;
	private final LdapUserRepository ldapUserRepository;
	private final ProblemRepository problemRepository;

	@Autowired
	public DashboardService(AgentRepository agentRepository, LdapUserRepository ldapUserRepository,
			ProblemRepository problemRepository) {
		this.agentRepository = agentRepository;
		this.ldapUserRepository = ldapUserRepository;
		this.problemRepository = problemRepository;
	}

	public long getAgentCount(AgentType type) {
		return this.agentRepository.countByType(type == null ? AgentType.ALL.getId() : type.getId());
	}

	public long getUserCount() {
		return this.ldapUserRepository.count();
	}

	public long getUnResolvedProblemCount() {
		Problem p = new Problem();
		p.setSolved(true);
		return this.problemRepository.count(Example.of(p));
	}

	public List<Problem> getLastProblems() {
		return this.problemRepository.findFirst5ByOrderByCreatedDateDesc();
	}

	@SuppressWarnings("unchecked")
	public List<Integer> getMonthlyMigrated(String startDate, String endDate) {
		// @formatter:off
		String sql = "select\n" + 
				"  coalesce(count(a.id), 0)\n" + 
				"from\n" + 
				"  (\n" + 
				"    select STR_TO_DATE(:startDate, '%d-%m-%Y')\n" + 
				"           + INTERVAL m MONTH as m1\n" + 
				"    from\n" + 
				"      (\n" + 
				"        select @rownum \\:= @rownum + 1 as m\n" + 
				"        from\n" + 
				"          (select 1\n" + 
				"           union select 2\n" + 
				"           union select 3\n" + 
				"           union select 4) t1,\n" + 
				"          (select 1\n" + 
				"           union select 2\n" + 
				"           union select 3\n" + 
				"           union select 4) t2,\n" + 
				"          (select 1\n" + 
				"           union select 2\n" + 
				"           union select 3\n" + 
				"           union select 4) t3,\n" + 
				"          (select 1\n" + 
				"           union select 2\n" + 
				"           union select 3\n" + 
				"           union select 4) t4,\n" + 
				"          (select 1\n" + 
				"           union select 2\n" + 
				"           union select 3\n" + 
				"           union select 4) t5,\n" + 
				"          (select @rownum \\:= -1) t0\n" + 
				"      ) d1\n" + 
				"  ) d2\n" + 
				"  left outer join c_agent a on (DATE_FORMAT(a.last_installation_date, '%m-%Y') = DATE_FORMAT(d2.m1, '%m-%Y'))\n" + 
				"where d2.m1 <= STR_TO_DATE(:endDate, '%m-%Y')\n" + 
				"group by d2.m1\n" + 
				"order by d2.m1";
		// @formatter:on
		Query query = em.createNativeQuery(sql);
		query.setParameter("startDate", "01-" + startDate);
		query.setParameter("endDate", endDate);
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Integer> getMonthlyPlanned(String startDate, String endDate) {
		// @formatter:off
		String sql = "select\n" + 
				"  coalesce(count(d3.agent_id), 0)\n" + 
				"from\n" + 
				"  (\n" + 
				"    select STR_TO_DATE(:startDate, '%d-%m-%Y')\n" + 
				"           + INTERVAL m MONTH as m1\n" + 
				"    from\n" + 
				"      (\n" + 
				"        select @rownum \\:= @rownum + 1 as m\n" + 
				"        from\n" + 
				"          (select 1\n" + 
				"           union select 2\n" + 
				"           union select 3\n" + 
				"           union select 4) t1,\n" + 
				"          (select 1\n" + 
				"           union select 2\n" + 
				"           union select 3\n" + 
				"           union select 4) t2,\n" + 
				"          (select 1\n" + 
				"           union select 2\n" + 
				"           union select 3\n" + 
				"           union select 4) t3,\n" + 
				"          (select 1\n" + 
				"           union select 2\n" + 
				"           union select 3\n" + 
				"           union select 4) t4,\n" + 
				"          (select 1\n" + 
				"           union select 2\n" + 
				"           union select 3\n" + 
				"           union select 4) t5,\n" + 
				"          (select @rownum \\:= -1) t0\n" + 
				"      ) d1\n" + 
				"  ) d2\n" + 
				"  left outer join (\n" + 
				"                    select\n" + 
				"                      ta.agent_id,\n" + 
				"                      min(t.planned_migration_date) as planned_migration_date\n" + 
				"                    from c_agent_tag_agent ta\n" + 
				"                      inner join c_agent_tag t ON (ta.tag_id = t.id)\n" + 
				"                    group by ta.agent_id\n" + 
				"                  ) d3 on DATE_FORMAT(d3.planned_migration_date, '%m-%Y') = DATE_FORMAT(d2.m1, '%m-%Y')\n" + 
				"where d2.m1 <= STR_TO_DATE(:endDate, '%m-%Y')\n" + 
				"group by d2.m1\n" + 
				"order by d2.m1";
		// @formatter:on
		Query query = em.createNativeQuery(sql);
		query.setParameter("startDate", "01-" + startDate);
		query.setParameter("endDate", endDate);
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Integer> getMonthlyCumulativeMigrated(String startDate, String endDate) {
		// @formatter:off
		String sql = "select\n" + 
				"  coalesce(count(a.id), 0)\n" + 
				"from\n" + 
				"  (\n" + 
				"    select STR_TO_DATE(:startDate, '%d-%m-%Y')\n" + 
				"           + INTERVAL m MONTH as m1\n" + 
				"    from\n" + 
				"      (\n" + 
				"        select @rownum \\:= @rownum + 1 as m\n" + 
				"        from\n" + 
				"          (select 1\n" + 
				"           union select 2\n" + 
				"           union select 3\n" + 
				"           union select 4) t1,\n" + 
				"          (select 1\n" + 
				"           union select 2\n" + 
				"           union select 3\n" + 
				"           union select 4) t2,\n" + 
				"          (select 1\n" + 
				"           union select 2\n" + 
				"           union select 3\n" + 
				"           union select 4) t3,\n" + 
				"          (select 1\n" + 
				"           union select 2\n" + 
				"           union select 3\n" + 
				"           union select 4) t4,\n" + 
				"          (select 1\n" + 
				"           union select 2\n" + 
				"           union select 3\n" + 
				"           union select 4) t5,\n" + 
				"          (select @rownum \\:= -1) t0\n" + 
				"      ) d1\n" + 
				"  ) d2\n" + 
				"  left outer join c_agent a on (DATE_FORMAT(a.last_installation_date, '%m-%Y') < DATE_FORMAT(d2.m1, '%m-%Y'))\n" + 
				"where d2.m1 <= STR_TO_DATE(:endDate, '%m-%Y')\n" + 
				"group by d2.m1\n" + 
				"order by d2.m1";
		// @formatter:on
		Query query = em.createNativeQuery(sql);
		query.setParameter("startDate", "01-" + startDate);
		query.setParameter("endDate", endDate);
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Integer> getMonthlyCumulativePlanned(String startDate, String endDate) {
		// @formatter:off
		String sql = "select\n" + 
				"  coalesce(count(d3.agent_id), 0)\n" + 
				"from\n" + 
				"  (\n" + 
				"    select STR_TO_DATE(:startDate, '%d-%m-%Y')\n" + 
				"           + INTERVAL m MONTH as m1\n" + 
				"    from\n" + 
				"      (\n" + 
				"        select @rownum \\:= @rownum + 1 as m\n" + 
				"        from\n" + 
				"          (select 1\n" + 
				"           union select 2\n" + 
				"           union select 3\n" + 
				"           union select 4) t1,\n" + 
				"          (select 1\n" + 
				"           union select 2\n" + 
				"           union select 3\n" + 
				"           union select 4) t2,\n" + 
				"          (select 1\n" + 
				"           union select 2\n" + 
				"           union select 3\n" + 
				"           union select 4) t3,\n" + 
				"          (select 1\n" + 
				"           union select 2\n" + 
				"           union select 3\n" + 
				"           union select 4) t4,\n" + 
				"          (select 1\n" + 
				"           union select 2\n" + 
				"           union select 3\n" + 
				"           union select 4) t5,\n" + 
				"          (select @rownum \\:= -1) t0\n" + 
				"      ) d1\n" + 
				"  ) d2\n" + 
				"  left outer join (\n" + 
				"                    select\n" + 
				"                      ta.agent_id,\n" + 
				"                      min(t.planned_migration_date) as planned_migration_date\n" + 
				"                    from c_agent_tag_agent ta\n" + 
				"                      inner join c_agent_tag t ON (ta.tag_id = t.id)\n" + 
				"                    group by ta.agent_id\n" + 
				"                  ) d3 on DATE_FORMAT(d3.planned_migration_date, '%m-%Y') < DATE_FORMAT(d2.m1, '%m-%Y')\n" + 
				"where d2.m1 <= STR_TO_DATE(:endDate, '%m-%Y')\n" + 
				"group by d2.m1\n" + 
				"order by d2.m1";
		// @formatter:on
		Query query = em.createNativeQuery(sql);
		query.setParameter("startDate", "01-" + startDate);
		query.setParameter("endDate", endDate);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Integer> getMonthlyContinuingProblems(String startDate, String endDate) {
		// @formatter:off
		String sql = "select\n" + 
				"  (\n" + 
				"    select count(p.id)\n" + 
				"    from c_problem p\n" + 
				"    where p.created_date < d2.m1 and ((p.solved is False and p.solution_date is null) or\n" + 
				"          p.solution_date > d2.m1)\n" + 
				"  )\n" + 
				"from\n" + 
				"  (\n" + 
				"    select STR_TO_DATE(:startDate, '%d-%m-%Y')\n" + 
				"           + INTERVAL m MONTH as m1\n" + 
				"    from\n" + 
				"      (\n" + 
				"        select @rownum \\:= @rownum + 1 as m\n" + 
				"        from\n" + 
				"          (select 1\n" + 
				"           union select 2\n" + 
				"           union select 3\n" + 
				"           union select 4) t1,\n" + 
				"          (select 1\n" + 
				"           union select 2\n" + 
				"           union select 3\n" + 
				"           union select 4) t2,\n" + 
				"          (select 1\n" + 
				"           union select 2\n" + 
				"           union select 3\n" + 
				"           union select 4) t3,\n" + 
				"          (select 1\n" + 
				"           union select 2\n" + 
				"           union select 3\n" + 
				"           union select 4) t4,\n" + 
				"          (select 1\n" + 
				"           union select 2\n" + 
				"           union select 3\n" + 
				"           union select 4) t5,\n" + 
				"          (select @rownum \\:= -1) t0\n" + 
				"      ) d1\n" + 
				"  ) d2\n" + 
				"where d2.m1 <= STR_TO_DATE(:endDate, '%m-%Y')\n" + 
				"group by d2.m1\n" + 
				"order by d2.m1";
		// @formatter:on
		Query query = em.createNativeQuery(sql);
		query.setParameter("startDate", "01-" + startDate);
		query.setParameter("endDate", endDate);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Integer> getMonthlyNewlyOpenedProblems(String startDate, String endDate) {
		// @formatter:off
		String sql = "select\n" + 
				"  (\n" + 
				"    select count(p.id)\n" + 
				"    from c_problem p\n" + 
				"    where DATE_FORMAT(p.created_date, '%m-%Y') = DATE_FORMAT(d2.m1, '%m-%Y')\n" +
				"  )\n" + 
				"from\n" + 
				"  (\n" + 
				"    select STR_TO_DATE(:startDate, '%d-%m-%Y')\n" + 
				"           + INTERVAL m MONTH as m1\n" + 
				"    from\n" + 
				"      (\n" + 
				"        select @rownum \\:= @rownum + 1 as m\n" + 
				"        from\n" + 
				"          (select 1\n" + 
				"           union select 2\n" + 
				"           union select 3\n" + 
				"           union select 4) t1,\n" + 
				"          (select 1\n" + 
				"           union select 2\n" + 
				"           union select 3\n" + 
				"           union select 4) t2,\n" + 
				"          (select 1\n" + 
				"           union select 2\n" + 
				"           union select 3\n" + 
				"           union select 4) t3,\n" + 
				"          (select 1\n" + 
				"           union select 2\n" + 
				"           union select 3\n" + 
				"           union select 4) t4,\n" + 
				"          (select 1\n" + 
				"           union select 2\n" + 
				"           union select 3\n" + 
				"           union select 4) t5,\n" + 
				"          (select @rownum \\:= -1) t0\n" + 
				"      ) d1\n" + 
				"  ) d2\n" + 
				"where d2.m1 <= STR_TO_DATE(:endDate, '%m-%Y')\n" + 
				"group by d2.m1\n" + 
				"order by d2.m1";
		// @formatter:on
		Query query = em.createNativeQuery(sql);
		query.setParameter("startDate", "01-" + startDate);
		query.setParameter("endDate", endDate);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Integer> getMonthlySolvedProblems(String startDate, String endDate) {
		// @formatter:off
		String sql = "select\n" + 
				"  (\n" + 
				"    select count(p.id)\n" + 
				"    from c_problem p\n" + 
				"    where DATE_FORMAT(p.solution_date, '%m-%Y') = DATE_FORMAT(d2.m1, '%m-%Y')\n" +
				"  )\n" + 
				"from\n" + 
				"  (\n" + 
				"    select STR_TO_DATE(:startDate, '%d-%m-%Y')\n" + 
				"           + INTERVAL m MONTH as m1\n" + 
				"    from\n" + 
				"      (\n" + 
				"        select @rownum \\:= @rownum + 1 as m\n" + 
				"        from\n" + 
				"          (select 1\n" + 
				"           union select 2\n" + 
				"           union select 3\n" + 
				"           union select 4) t1,\n" + 
				"          (select 1\n" + 
				"           union select 2\n" + 
				"           union select 3\n" + 
				"           union select 4) t2,\n" + 
				"          (select 1\n" + 
				"           union select 2\n" + 
				"           union select 3\n" + 
				"           union select 4) t3,\n" + 
				"          (select 1\n" + 
				"           union select 2\n" + 
				"           union select 3\n" + 
				"           union select 4) t4,\n" + 
				"          (select 1\n" + 
				"           union select 2\n" + 
				"           union select 3\n" + 
				"           union select 4) t5,\n" + 
				"          (select @rownum \\:= -1) t0\n" + 
				"      ) d1\n" + 
				"  ) d2\n" + 
				"where d2.m1 <= STR_TO_DATE(:endDate, '%m-%Y')\n" + 
				"group by d2.m1\n" + 
				"order by d2.m1";
		// @formatter:on
		Query query = em.createNativeQuery(sql);
		query.setParameter("startDate", "01-" + startDate);
		query.setParameter("endDate", endDate);
		return query.getResultList();
	}

}

package tr.com.agem.alfa.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import tr.com.agem.alfa.model.Problem;
import tr.com.agem.alfa.repository.AgentRepository;
import tr.com.agem.alfa.repository.LdapUserRepository;
import tr.com.agem.alfa.repository.ProblemRepository;

/**
 * @author <a href="mailto:emre.akkaya@agem.com.tr">Emre Akkaya</a>
 */
@Component
@Transactional
public class DashboardService {

	// TODO EntityManager can be used to execute native SQL queries!
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

	// TODO other query methods:
	/*
	 * public List<Dummy> getDummyByFoo(Foo foo) { 
	 * 		return em.createNativeQuery("SELECT * FROM t_dummy WHERE foo = :param")
	 * 				.setParameter("param", foo.getParam())
	 * 				.getResultList(); 
	 * }
	 */

	public long getAgentCount() {
		return this.agentRepository.count();
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

}

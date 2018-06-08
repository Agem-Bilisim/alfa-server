package tr.com.agem.alfa.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import tr.com.agem.alfa.model.PeripheralDevice;
import tr.com.agem.alfa.repository.PeripheralRepository;

/**
 * @author <a href="mailto:emre.akkaya@agem.com.tr">Emre Akkaya</a>
 */
@Component
@Transactional(rollbackFor = Exception.class)
public class PeripheralService {

	@PersistenceContext
	private EntityManager em;

	private final PeripheralRepository peripheralRepository;

	@Autowired
	public PeripheralService(PeripheralRepository peripheralRepository) {
		this.peripheralRepository = peripheralRepository;
	}

	public PeripheralDevice savePeripheralDevice(PeripheralDevice peripheralDevice) {
		Assert.notNull(peripheralDevice, "PeripheralDevice must not be null.");
		PeripheralDevice pd = null;
		if (peripheralDevice.getId() != null && (pd = peripheralRepository.findOne(peripheralDevice.getId())) != null) {
			// Update
			pd.setTag(peripheralDevice.getTag());
			pd.setShowInSurvey(peripheralDevice.getShowInSurvey());
			return this.peripheralRepository.save(pd);
		}
		// Create
		return this.peripheralRepository.save(peripheralDevice);
	}

	public Page<PeripheralDevice> getPeripherals(Pageable pageable, String search) {
		Assert.notNull(pageable, "Pageable must not be null.");
		if (search != null && !search.isEmpty()) {
			// TODO
			return null;
		}
		return this.peripheralRepository.findAll(pageable);
	}

	public List<PeripheralDevice> getPeripherals() {
		return this.peripheralRepository.findAll();
	}

	public List<PeripheralDevice> getSurveyablePeripherals() {
		PeripheralDevice p = new PeripheralDevice();
		p.setShowInSurvey(true);
		return this.peripheralRepository.findAll(Example.of(p));
	}

	public void savePeripheralDevice(PeripheralDevice peripheralDevice, String deviceId, String devicePath,
			Long[] agentIds) {
		PeripheralDevice pd = this.savePeripheralDevice(peripheralDevice);
		// Delete previous cross-table records
		Query query = em.createNativeQuery("DELETE FROM c_agent_peripheral_agent WHERE peripheral_id = :peripheralId");
		query.setParameter("peripheralId", pd.getId());
		query.executeUpdate();
		// Insert cross-table records
		if (agentIds != null) {
			for (Long agentId : agentIds) {
				Query query2 = em.createNativeQuery(
						"INSERT INTO c_agent_peripheral_agent (agent_id, peripheral_id, device_id, device_path) VALUES (:agentId, :peripheralId, :deviceId, :devicePath)");
				query2.setParameter("agentId", agentId);
				query2.setParameter("peripheralId", pd.getId());
				query2.setParameter("deviceId", deviceId);
				query2.setParameter("devicePath", devicePath);
				query2.executeUpdate();
			}
		}
	}

}

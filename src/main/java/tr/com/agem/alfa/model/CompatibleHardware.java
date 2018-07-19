package tr.com.agem.alfa.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

/**
 * @author <a href="mailto:ali.ozeren@agem.com.tr">Ali Ozkan Ozeren</a>
 *
 */
@Entity
public class CompatibleHardware implements Serializable {

	private static final long serialVersionUID = -5967722775213127111L;
	
	@Id
	private Long id;
	private String name;
	private String product;
	private String version;
	private String type;

	public CompatibleHardware() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@PrePersist
	public void prePersist() {
	}

	@PreUpdate
	public void preUpdate() {
	}

}

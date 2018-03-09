package tr.com.agem.alfa.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "c_agent_peripheral")
public class PeripheralDevice extends BaseModel {

	private static final long serialVersionUID = -5929581631043566633L;

	String tag;

	String deviceId;

	String devicePath;

}

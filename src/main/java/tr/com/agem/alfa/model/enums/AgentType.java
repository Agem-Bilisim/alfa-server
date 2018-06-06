package tr.com.agem.alfa.model.enums;

import java.util.Locale;

/**
 * 
 * @author <a href="mailto:emre.akkaya@agem.com.tr">Emre Akkaya</a>
 *
 */
public enum AgentType {

	DEBIAN_BASED(1), WINDOWS_BASED(2), ALL(3);

	private int id;

	private AgentType(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	/**
	 * Provide mapping enums with a fixed ID in JPA (a more robust alternative
	 * to EnumType.String and EnumType.Ordinal)
	 * 
	 * @param id
	 * @return related AgentType enum
	 * @see http://blog.chris-ritchie.com/2013/09/mapping-enums-with-fixed-id-in
	 *      -jpa.html
	 * 
	 */
	public static AgentType getType(Integer id) {
		if (id == null) {
			return null;
		}
		for (AgentType type : AgentType.values()) {
			if (id.equals(type.getId())) {
				return type;
			}
		}
		throw new IllegalArgumentException("No matching type for id: " + id);
	}
	
	public static String getLabel(Integer id) {
		if (id == null) {
			return null;
		}
		AgentType t = getType(id);
		return "agent-type." + t.toString().toLowerCase(Locale.US).replace("_", "-");
	}

}

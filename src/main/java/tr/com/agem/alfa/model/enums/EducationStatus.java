package tr.com.agem.alfa.model.enums;

import java.util.Locale;

public enum EducationStatus {
	
	COMPLETED(1), NOT_STARTED(2), STARTED(3);
	
	private int id;

	private EducationStatus(int id) {
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
	 * @return related LdapEncryption enum
	 * @see http://blog.chris-ritchie.com/2013/09/mapping-enums-with-fixed-id-in
	 *      -jpa.html
	 * 
	 */
	public static EducationStatus getType(Integer id) {
		if (id == null) {
			return null;
		}
		for (EducationStatus type : EducationStatus.values()) {
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
		EducationStatus t = getType(id);
		return "education-status." + t.toString().toLowerCase(Locale.US).replace("_", "-");
	}
	
	// TODO think a better workaround
	public static EducationStatus getTypeFromLabel(String label) {
		if (label == null) {
			return null;
		}
		if ("Tamamlandı".equalsIgnoreCase(label)) {
			return EducationStatus.COMPLETED;
		} else if ("Başlanmadı".equalsIgnoreCase(label)) {
			return EducationStatus.NOT_STARTED;
		} else if ("Başlandı".equalsIgnoreCase(label) || "Devam etmekte".equalsIgnoreCase(label)) {
			return EducationStatus.STARTED;
		}
		throw new IllegalArgumentException("No matching type for label: " + label);
	}

}

package tr.com.agem.alfa.model.enums;

/**
 * @author <a href="mailto:emre.akkaya@agem.com.tr">Emre Akkaya</a>
 */
public enum ProblemReferenceType {

	CPU(1), PERIPHERAL(2), PROCESS(3), BIOS(4), DISK(5), GPU(6), PACKAGE(7), INET(8), MEMORY(9);

	private int id;

	private ProblemReferenceType(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	/**
	 * Provide mapping enums with a fixed ID in JPA (a more robust alternative to
	 * EnumType.String and EnumType.Ordinal)
	 * 
	 * @param id
	 * @return related ProblemReferenceType enum
	 * @see http://blog.chris-ritchie.com/2013/09/mapping-enums-with-fixed-id-in
	 *      -jpa.html
	 */
	public static ProblemReferenceType getType(Integer id) {
		if (id == null) {
			return null;
		}
		for (ProblemReferenceType type : ProblemReferenceType.values()) {
			if (id.equals(type.getId())) {
				return type;
			}
		}
		throw new IllegalArgumentException("No matching type for id: " + id);
	}

	public static ProblemReferenceType getType(String name) {
		if (name == null) {
			return null;
		}
		for (ProblemReferenceType type : ProblemReferenceType.values()) {
			if (name.equalsIgnoreCase(type.toString())) {
				return type;
			}
		}
		throw new IllegalArgumentException("No matching type for name: " + name);
	}

}

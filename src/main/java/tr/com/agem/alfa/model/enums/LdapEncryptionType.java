package tr.com.agem.alfa.model.enums;

/**
 * 
 * @author <a href="mailto:emre.akkaya@agem.com.tr">Emre Akkaya</a>
 *
 */
public enum LdapEncryptionType {

	NONE(1), SSL(2), START_TLS(3);

	private int id;

	private LdapEncryptionType(int id) {
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
	public static LdapEncryptionType getType(Integer id) {
		if (id == null) {
			return null;
		}
		for (LdapEncryptionType type : LdapEncryptionType.values()) {
			if (id.equals(type.getId())) {
				return type;
			}
		}
		throw new IllegalArgumentException("No matching type for id: " + id);
	}

}

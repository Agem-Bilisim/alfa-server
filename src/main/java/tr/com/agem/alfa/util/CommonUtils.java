package tr.com.agem.alfa.util;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import tr.com.agem.alfa.security.CustomAuthenticationProvider;

/**
 * @author <a href="mailto:emre.akkaya@agem.com.tr">Emre Akkaya</a>
 */
public class CommonUtils {

	// Used only for debug purposes!
//	public static void main(String[] args) {
//		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(CustomAuthenticationProvider.ENCODER_STRENGTH);
//		System.out.println("PWD: " + encoder.encode("test"));
//	}

	public static final String SPACE_DELIM = " ";
	public static final String COMMA_DELIM = ",";
	public static final String SINGLE_QUOTE_DELIM = "'";
	public static final String HYPHEN_DELIM = "-";

	public static String join(String delim, String... parts) {
		StringBuilder sb = new StringBuilder();
		if (parts != null) {
			String loopDelim = "";
			for (String s : parts) {
				sb.append(loopDelim);
				sb.append(s);
				loopDelim = delim;
			}
		}
		return sb.toString();
	}

	public static String generateUID(String seed) {
		if (seed == null) {
			throw new IllegalArgumentException("Seed was null.");
		}
		byte[] bytes = seed.getBytes(StandardCharsets.UTF_8);
		return UUID.nameUUIDFromBytes(bytes).toString();
	}

	public static boolean isInteger(String s, int radix) {
		if (s.isEmpty()) return false;
		for (int i = 0; i < s.length(); i++) {
			if (i == 0 && s.charAt(i) == '-') {
				if (s.length() == 1)
					return false;
				else
					continue;
			}
			if (Character.digit(s.charAt(i), radix) < 0) return false;
		}
		return true;
	}

}

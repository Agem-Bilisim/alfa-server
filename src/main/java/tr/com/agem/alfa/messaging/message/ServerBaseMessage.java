package tr.com.agem.alfa.messaging.message;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author <a href="mailto:emre.akkaya@agem.com.tr">Emre Akkaya</a>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class ServerBaseMessage implements Serializable {

	private static final long serialVersionUID = -3367855278447922663L;

	/**
	 * @return message recipient
	 */
	public abstract String getTo();

}

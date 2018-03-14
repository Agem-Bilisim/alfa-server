package tr.com.agem.alfa.messaging.message;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author <a href="mailto:emre.akkaya@agem.com.tr">Emre Akkaya</a>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class AgentBaseMessage implements Serializable {

	private static final long serialVersionUID = -783778297126872499L;

	/**
	 * @return message sender
	 */
	public abstract String getFrom();

}

package tr.com.agem.alfa.messaging.message;

import java.io.Serializable;

/**
 * @author <a href="mailto:emre.akkaya@agem.com.tr">Emre Akkaya</a>
 */
public abstract class AgentBaseMessage implements Serializable {

	private static final long serialVersionUID = -783778297126872499L;

	/**
	 * @return message sender
	 */
	public abstract String getFrom();

}

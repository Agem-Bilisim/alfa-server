package tr.com.agem.alfa.messaging.client;

import tr.com.agem.alfa.messaging.message.ServerBaseMessage;

/**
 * @author <a href="mailto:emre.akkaya@agem.com.tr">Emre Akkaya</a>
 */
public abstract class BaseMessagingClient {

	public abstract void sendMessage(String host, String url, ServerBaseMessage message) throws Exception;

}

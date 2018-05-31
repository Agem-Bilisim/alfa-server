package tr.com.agem.alfa.messaging.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import tr.com.agem.alfa.messaging.client.BaseMessagingClient;
import tr.com.agem.alfa.messaging.message.ServerBaseMessage;

/**
 * @author <a href="mailto:emre.akkaya@agem.com.tr">Emre Akkaya</a>
 */
@Service
public class MessagingService {

	private final BaseMessagingClient messagingClient;

	@Autowired
	public MessagingService(BaseMessagingClient messagingClient) {
		this.messagingClient = messagingClient;
	}

	public void send(ServerBaseMessage message) throws Exception {
		Assert.notNull(message, "Message must not be null.");
		messagingClient.sendMessage(message);
	}

}

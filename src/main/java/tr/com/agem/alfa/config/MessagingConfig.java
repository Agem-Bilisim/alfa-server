package tr.com.agem.alfa.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import tr.com.agem.alfa.messaging.client.BaseMessagingClient;
import tr.com.agem.alfa.messaging.client.HttpClient;

/**
 * @author <a href="mailto:emre.akkaya@agem.com.tr">Emre Akkaya</a>
 */
@Configuration
public class MessagingConfig {

	@Bean("messagingClient")
	@ConditionalOnProperty(value = "sys.messaging.protocol", havingValue = "http", matchIfMissing = true)
	@ConditionalOnMissingBean
	public BaseMessagingClient sshClient() {
		return new HttpClient();
	}

}

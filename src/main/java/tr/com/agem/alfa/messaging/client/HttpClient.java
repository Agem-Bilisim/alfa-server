package tr.com.agem.alfa.messaging.client;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.util.Assert;

import com.fasterxml.jackson.databind.ObjectMapper;

import tr.com.agem.alfa.messaging.message.ServerBaseMessage;

/**
 * @author <a href="mailto:emre.akkaya@agem.com.tr">Emre Akkaya</a>
 */
public class HttpClient extends BaseMessagingClient {

	@Override
	public void sendMessage(String host, String url, ServerBaseMessage message) throws Exception {
		CloseableHttpClient client = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(host + url);

		httpPost.setEntity(
				new StringEntity(new ObjectMapper().writeValueAsString(message), ContentType.APPLICATION_JSON));
		httpPost.setHeader("Accept", "application/json");
		httpPost.setHeader("Content-type", "application/json");

		CloseableHttpResponse response = client.execute(httpPost);
		Assert.isTrue(response.getStatusLine().getStatusCode() == 200, "Gönderilen ajanda beklenmeyen hata oluştu.");
		client.close();
	}

}

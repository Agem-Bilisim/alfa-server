package tr.com.agem.alfa.messaging.message;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author <a href="mailto:emre.akkaya@agem.com.tr">Emre Akkaya</a>
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class URLRedirectionMessage extends ServerBaseMessage {

	private static final long serialVersionUID = -6631635753575935966L;

	private String to;

	private String url;

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}

package tr.com.agem.alfa.controller;

import java.io.Serializable;
import java.util.Map;

/**
 * This is used for base response body in RESTful web services.
 * 
 * @author <a href="mailto:emre.akkaya@agem.com.tr">Emre Akkaya</a>
 *
 */
public class RestResponseBody implements Serializable {

	private static final long serialVersionUID = 8565456285497269898L;

	private String message;

	private Map<String, Object> data;

	public RestResponseBody() {
	}

	public RestResponseBody(String message) {
		this.message = message;
	}

	public RestResponseBody(Map<String, Object> data) {
		this.data = data;
	}

	public RestResponseBody(String message, Map<String, Object> data) {
		this.message = message;
		this.data = data;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Map<String, Object> getData() {
		return data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}

}

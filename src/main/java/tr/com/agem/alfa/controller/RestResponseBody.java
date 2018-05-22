package tr.com.agem.alfa.controller;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.springframework.data.domain.Page;

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

	/**
	 * Helper method to easily add data.
	 * 
	 * @param key
	 * @param value
	 */
	public void add(String key, Object value) {
		if (this.data == null) {
			data = new HashMap<String, Object>();
		}
		data.put(key, value);
	}

}

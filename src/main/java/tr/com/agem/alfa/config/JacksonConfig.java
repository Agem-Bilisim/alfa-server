package tr.com.agem.alfa.config;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import com.fasterxml.jackson.module.afterburner.AfterburnerModule;

/**
 * @author <a href="mailto:emre.akkaya@agem.com.tr">Emre Akkaya</a>
 */
@Configuration
public class JacksonConfig {

	@Value("${sys.datetime-format}")
	private String applicationDatetimeFormat;

	@Bean
	public Module afterburnerModule() {
		return new AfterburnerModule();
	}

	/**
	 * support JSON serialization and deserialization of Hibernate specific
	 * datatypes and properties; especially lazy-loading aspects
	 */
	@Bean
	public Module hibernateModule() {
		return new Hibernate5Module();
	}

	@Bean
	public ObjectMapper objectMapper() {
		DateFormat fmt = new SimpleDateFormat(applicationDatetimeFormat);
		ObjectMapper mapper = new ObjectMapper();
		mapper.setDateFormat(fmt);
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		return mapper.registerModule(afterburnerModule()).registerModule(hibernateModule());
	}

}

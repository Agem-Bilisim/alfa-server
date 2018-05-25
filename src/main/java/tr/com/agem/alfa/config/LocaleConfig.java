package tr.com.agem.alfa.config;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

/**
 * @author <a href="mailto:emre.akkaya@agem.com.tr">Emre Akkaya</a>
 */
@Configuration
public class LocaleConfig extends WebMvcConfigurerAdapter {

	@Value("${spring.messages.basename}")
	private String basename;

	@Value("${spring.messages.encoding}")
	private String encoding;

	@Value("${spring.messages.cache-seconds}")
	private Integer cacheSeconds;

	@Value("${sys.locale}")
	private String locale;

	@Bean
	public LocaleResolver localeResolver() {
		Locale.setDefault(Locale.forLanguageTag(locale));
		SessionLocaleResolver slr = new SessionLocaleResolver();
		slr.setDefaultLocale(Locale.forLanguageTag(locale));
		return slr;
	}

	@Bean
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename(basename);
		messageSource.setCacheSeconds(cacheSeconds);
		messageSource.setDefaultEncoding(encoding);
		messageSource.setFallbackToSystemLocale(false);
		return messageSource;
	}

}

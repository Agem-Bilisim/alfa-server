package tr.com.agem.alfa.locale;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
public class MessageSourceDecorator {

	private final MessageSource messageSource;

	@Value("${sys.locale}")
	private String locale;

	@Autowired
	public MessageSourceDecorator(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	public String decorate(String old) {
		return messageSource.getMessage(old, null, Locale.forLanguageTag(locale));
	}

	public <T> List<T> decorate(String src, String dest, List<T> objects) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, IntrospectionException {
		if (objects == null || objects.isEmpty())
			return objects;
		for (int i = 0; i < objects.size(); i++) {
			T t = objects.get(i);
			if (t instanceof MessageSourceDecoratable) {
				String convention = ((MessageSourceDecoratable) t).getMessageConvention();
				Object srcVal = new PropertyDescriptor(src, t.getClass()).getReadMethod().invoke(t);
				if (srcVal == null || srcVal.toString().isEmpty())
					continue;
				String destVal = decorate(String.format(convention,
						srcVal.toString().toLowerCase(Locale.ENGLISH).replace("_", "-")));
				new PropertyDescriptor(dest, t.getClass()).getWriteMethod().invoke(t, destVal);
			}
		}
		return objects;
	}

	public interface MessageSourceDecoratable {
		String getMessageConvention();
	}

}

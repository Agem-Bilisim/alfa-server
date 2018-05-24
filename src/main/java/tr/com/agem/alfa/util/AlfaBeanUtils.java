/**
 * 
 */
package tr.com.agem.alfa.util;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tr.com.agem.alfa.exception.AlfaException;
import tr.com.agem.alfa.model.enums.ProblemReferenceType;

/**
 * @author <a href="mailto:ali.ozeren@agem.com.tr">Ali Ozkan Ozeren</a>
 */
public class AlfaBeanUtils {

	private static final Logger log = LoggerFactory.getLogger(AlfaBeanUtils.class);

	private static AlfaBeanUtils instance = null;

	private AlfaBeanUtils() {
	}

	public static AlfaBeanUtils getInstance() {
		if (instance == null) {
			instance = new AlfaBeanUtils();
			instance.addConverter(new HashSetConverter(), Set.class);
			instance.addConverter(new HashSetConverter(), HashSet.class);
			instance.addConverter(new ReferenceTypeContverter(), ProblemReferenceType.class);
			
		}

		return instance;
	}

	public void addConverter(Converter converter, Class<?> clazz) {
		assert (converter != null && clazz != null);

		ConvertUtils.register(converter, clazz);
		log.info("Converter added for class : " + clazz);

	}

	public void addConverters(Collection<AlfaBeanConverter> converters) {

		for (AlfaBeanConverter c : converters) {
			if (c.isNotEmpty()) {
				ConvertUtils.register(c.getConverter(), c.getClazz());
				log.info("Converter added for class : " + c.getClazz());
			}
		}
	}

	public void copyProperties(Object src, Object dest) {
		try {
			BeanUtils.copyProperties(dest, src);
		} catch (IllegalAccessException | InvocationTargetException e) {
			log.error("Error in copying properties");
			throw new AlfaException(e);
		}
	}

	public <T> List<T> copyListProperties(List<?> src, Class<T> clazz)
			throws InstantiationException, IllegalAccessException, InvocationTargetException {
		if (src == null || src.isEmpty()) return null;
		List<T> list = new ArrayList<>();
		for (Object o : src) {
			T t = clazz.newInstance();
			BeanUtils.copyProperties(t, o);
			list.add(t);
		}
		return list;
	}

}
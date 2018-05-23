/**
 * 
 */
package tr.com.agem.alfa.util;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.Converter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tr.com.agem.alfa.exception.AlfaException;

/**
 * @author <a href="mailto:ali.ozeren@agem.com.tr">Ali Ozkan Ozeren</a>
 *
 */
public class AlfaBeanUtils 
{

	private static final Logger log = LoggerFactory.getLogger(AlfaBeanUtils.class);
	
	private static AlfaBeanUtils instance = null;
	
	private AlfaBeanUtils () {
	}
	
	public static AlfaBeanUtils getInstance() 
	{
		if (instance == null) {
			instance = new AlfaBeanUtils();
		}
		
		return instance;
	}
	
	public void addConverter(Converter converter, Class<?> clazz) 
	{
		assert(converter != null && clazz != null);
		
		BeanUtilsBean.getInstance().getConvertUtils().register(converter, clazz);
		
	}

	public void addConverters(Collection<AlfaBeanConverter> converters) 
	{
		BeanUtilsBean beanUtilsBean = BeanUtilsBean.getInstance();

		for (AlfaBeanConverter c : converters) {
			if (c.isNotEmpty()) {
				beanUtilsBean.getConvertUtils().register(c.getConverter(), c.getClazz());
			}
		}
	}
	
	public void copyProperties(Object src, Object dest) 
	{
		try {
			BeanUtils.copyProperties(dest, src);
		} catch (IllegalAccessException | InvocationTargetException e) {
			log.error("Error in copying properties");
			throw new AlfaException(e);
		}
	}
	
	
}
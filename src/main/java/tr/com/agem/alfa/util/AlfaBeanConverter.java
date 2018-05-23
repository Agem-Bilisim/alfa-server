/**
 * 
 */
package tr.com.agem.alfa.util;

import org.apache.commons.beanutils.Converter;

/**
 * @author <a href="mailto:ali.ozeren@agem.com.tr">Ali Ozkan Ozeren</a>
 *
 */
public class AlfaBeanConverter 
{
	private Converter converter = null;
	private Class<?> clazz = null;
	
	
	/**
	 * @param converter
	 * @param clazz
	 */
	public AlfaBeanConverter(Converter converter, Class<?> clazz) {
		super();
		this.converter = converter;
		this.clazz = clazz;
	}
	
	public Converter getConverter() {
		return converter;
	}
	public void setConverter(Converter converter) {
		this.converter = converter;
	}
	public Class<?> getClazz() {
		return clazz;
	}
	public void setClazz(Class<?> clazz) {
		this.clazz = clazz;
	}
	
	public boolean isNotEmpty()  {
		return converter != null && clazz != null;
	}
	
}
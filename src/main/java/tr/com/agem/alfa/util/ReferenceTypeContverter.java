/**
 * 
 */
package tr.com.agem.alfa.util;

import org.apache.commons.beanutils.Converter;

import tr.com.agem.alfa.model.enums.ProblemReferenceType;

/**
 * @author <a href="mailto:ali.ozeren@agem.com.tr">Ali Ozkan Ozeren</a>
 *
 */
public class ReferenceTypeContverter implements Converter {

	/* (non-Javadoc)
	 * @see org.apache.commons.beanutils.Converter#convert(java.lang.Class, java.lang.Object)
	 */
    @Override
    public <T> T convert(Class<T> clazz, Object value) {
    	if (value != null) {
    		if (value instanceof Integer && clazz.equals(ProblemReferenceType.class)) {
    			Integer type = (Integer) value;
    			return clazz.cast(ProblemReferenceType.getType(type));
    		} else if (value instanceof ProblemReferenceType && clazz.equals(Integer.class)) {
    			return clazz.cast(((ProblemReferenceType)value).getId());
    		}
    	}
        return null;
    }
	
}

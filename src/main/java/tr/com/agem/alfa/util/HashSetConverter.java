/**
 * 
 */
package tr.com.agem.alfa.util;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.beanutils.Converter;

import tr.com.agem.alfa.form.BaseForm;
import tr.com.agem.alfa.model.BaseModel;

/**
 * @author <a href="mailto:ali.ozeren@agem.com.tr">Ali Ozkan Ozeren</a>
 *
 */
public class HashSetConverter implements Converter {

	/* (non-Javadoc)
	 * @see org.apache.commons.beanutils.Converter#convert(java.lang.Class, java.lang.Object)
	 */
	@Override
	public <T> T convert(Class<T> clazz, Object obj) {
		HashSet<Object> ret = null;
		if (obj != null) {
			if (clazz.equals(Set.class) && obj.getClass().isAssignableFrom(HashSet.class)) {
				Set<?> set = (Set<?>) obj;
				if (obj != null) {
					ret = new HashSet<Object>();
					for (Object src : set) {
						if (src instanceof BaseForm) {
							Object dest = ((BaseForm)src).getCorrespondingModel();
							AlfaBeanUtils.getInstance().copyProperties(src, dest);
							ret.add(dest);
						} else if (src instanceof BaseModel) {
							Object dest = ((BaseModel)src).getCorrespondingForm();
							AlfaBeanUtils.getInstance().copyProperties(src, dest);
							ret.add(dest);
						}
					}
				}
			}
		}
		return clazz.cast(ret);
	}
	
	

	/* (non-Javadoc)
	 * @see org.apache.commons.beanutils.Converter#convert(java.lang.Class, java.lang.Object)
	 */
//	@SuppressWarnings("unchecked")
//	@Override
//	public Object convert(@SuppressWarnings("rawtypes") Class clazz, Object obj) {
//
//		Object ret = null;
//		if (obj != null) {
//			if (clazz.equals(HashSet.class)) {
//				if (ProblemReferenceForm.class.equals(clazz.getGenericSuperclass())) {
//					ret = new HashSet<ProblemReference>();
//				} else if (ProblemReference.class.equals(clazz.getGenericSuperclass())) {
//					ret = new HashSet<ProblemReferenceForm>();
//				} 
//			}
////			if (ret != null) {
////				AlfaBeanUtils.getInstance().copyProperties(obj, ret);
////			}
//		}
//		return ret;
//	}

	
}

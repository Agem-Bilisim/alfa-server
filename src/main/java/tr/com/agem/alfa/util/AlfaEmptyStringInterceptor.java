package tr.com.agem.alfa.util;

import java.io.Serializable;
import java.util.Iterator;

import org.hibernate.CallbackException;
import org.hibernate.EntityMode;
import org.hibernate.Interceptor;
import org.hibernate.Transaction;
import org.hibernate.type.Type;
import org.springframework.stereotype.Component;

import tr.com.agem.alfa.model.BaseModel;

/**
 * @author <a href="mailto:ali.ozeren@agem.com.tr">Ali Ozkan Ozeren</a>
 *
 */

@Component
public class AlfaEmptyStringInterceptor implements Interceptor {

	/* (non-Javadoc)
	 * @see org.hibernate.Interceptor#onFlushDirty(java.lang.Object, java.io.Serializable, java.lang.Object[], java.lang.Object[], java.lang.String[], org.hibernate.type.Type[])
	 */
	/**
	 * runs on update
	 * @see org.hibernate.Interceptor#onFlushDirty(java.lang.Object, java.io.Serializable, java.lang.Object[], java.lang.Object[], java.lang.String[], org.hibernate.type.Type[])
	 */
	@Override
	public boolean onFlushDirty(Object entity, Serializable id, Object[] currentState, Object[] previousState,
			String[] propertyNames, Type[] types) throws CallbackException {
		
		boolean retVal = false;
		if ( entity instanceof BaseModel ) {
			for (int i=0; i<currentState.length; i++ ) {
				if ("".equals(currentState[i])) {
					currentState[i]= null;
					retVal = true;
				}
			}
		}
		return retVal;
	}
	
	/* (non-Javadoc)
	 * @see org.hibernate.Interceptor#onFlushDirty(java.lang.Object, java.io.Serializable, java.lang.Object[], java.lang.Object[], java.lang.String[], org.hibernate.type.Type[])
	 */	
	/**
	 * runs on insert
	 * @see org.hibernate.Interceptor#onFlushDirty(java.lang.Object, java.io.Serializable, java.lang.Object[], java.lang.Object[], java.lang.String[], org.hibernate.type.Type[])
	 */	
	@Override
	public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types)
			throws CallbackException {
		boolean retVal = false;
		if ( entity instanceof BaseModel ) {
			for (int i=0; i<state.length; i++ ) {
				if ("".equals(state[i])) {
					state[i]= null;
					retVal = true;
				}
			}
		}
		return retVal;
	}

	/* (non-Javadoc)
	 * @see org.hibernate.Interceptor#onDelete(java.lang.Object, java.io.Serializable, java.lang.Object[], java.lang.String[], org.hibernate.type.Type[])
	 */
	@Override
	public void onDelete(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types)
			throws CallbackException {
	}

	/* (non-Javadoc)
	 * @see org.hibernate.Interceptor#onLoad(java.lang.Object, java.io.Serializable, java.lang.Object[], java.lang.String[], org.hibernate.type.Type[])
	 */
	@Override
	public boolean onLoad(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types)
			throws CallbackException {
		return false;
	}

	
	/* (non-Javadoc)
	 * @see org.hibernate.Interceptor#onCollectionRecreate(java.lang.Object, java.io.Serializable)
	 */
	@Override
	public void onCollectionRecreate(Object collection, Serializable key) throws CallbackException {
	}


	/* (non-Javadoc)
	 * @see org.hibernate.Interceptor#onCollectionRemove(java.lang.Object, java.io.Serializable)
	 */
	@Override
	public void onCollectionRemove(Object collection, Serializable key) throws CallbackException {
	}


	/* (non-Javadoc)
	 * @see org.hibernate.Interceptor#onCollectionUpdate(java.lang.Object, java.io.Serializable)
	 */
	@Override
	public void onCollectionUpdate(Object collection, Serializable key) throws CallbackException {
		
	}


	/* (non-Javadoc)
	 * @see org.hibernate.Interceptor#preFlush(java.util.Iterator)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public void preFlush(Iterator entities) throws CallbackException {
	}


	/* (non-Javadoc)
	 * @see org.hibernate.Interceptor#postFlush(java.util.Iterator)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public void postFlush(Iterator entities) throws CallbackException {
		
	}


	/* (non-Javadoc)
	 * @see org.hibernate.Interceptor#isTransient(java.lang.Object)
	 */
	@Override
	public Boolean isTransient(Object entity) {
		return null;
	}


	/* (non-Javadoc)
	 * @see org.hibernate.Interceptor#findDirty(java.lang.Object, java.io.Serializable, java.lang.Object[], java.lang.Object[], java.lang.String[], org.hibernate.type.Type[])
	 */
	@Override
	public int[] findDirty(Object entity, Serializable id, Object[] currentState, Object[] previousState,
			String[] propertyNames, Type[] types) {
		return null;
	}


	/* (non-Javadoc)
	 * @see org.hibernate.Interceptor#instantiate(java.lang.String, org.hibernate.EntityMode, java.io.Serializable)
	 */
	@Override
	public Object instantiate(String entityName, EntityMode entityMode, Serializable id) throws CallbackException {
		return null;
	}


	/* (non-Javadoc)
	 * @see org.hibernate.Interceptor#getEntityName(java.lang.Object)
	 */
	@Override
	public String getEntityName(Object object) throws CallbackException {
		return null;
	}


	/* (non-Javadoc)
	 * @see org.hibernate.Interceptor#getEntity(java.lang.String, java.io.Serializable)
	 */
	@Override
	public Object getEntity(String entityName, Serializable id) throws CallbackException {
		// TODO Auto-generated method stub
		return null;
	}


	/* (non-Javadoc)
	 * @see org.hibernate.Interceptor#afterTransactionBegin(org.hibernate.Transaction)
	 */
	@Override
	public void afterTransactionBegin(Transaction tx) {
		
	}


	/* (non-Javadoc)
	 * @see org.hibernate.Interceptor#beforeTransactionCompletion(org.hibernate.Transaction)
	 */
	@Override
	public void beforeTransactionCompletion(Transaction tx) {
		
	}


	/* (non-Javadoc)
	 * @see org.hibernate.Interceptor#afterTransactionCompletion(org.hibernate.Transaction)
	 */
	@Override
	public void afterTransactionCompletion(Transaction tx) {
		
	}


	/* (non-Javadoc)
	 * @see org.hibernate.Interceptor#onPrepareStatement(java.lang.String)
	 */
	@Override
	public String onPrepareStatement(String sql) {
		return null;
	}
	

}
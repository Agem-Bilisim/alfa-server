package tr.com.agem.alfa.config;

import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.autoconfigure.transaction.TransactionManagerCustomizers;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.jta.JtaTransactionManager;

import tr.com.agem.alfa.util.AlfaEmptyStringInterceptor;

/**
 * @author <a href="mailto:ali.ozeren@agem.com.tr">Ali Ozkan Ozeren</a>
 *
 */
@Configuration
public class HibernateConfig extends HibernateJpaAutoConfiguration
{

	/**
	 * @param dataSource
	 * @param jpaProperties
	 * @param jtaTransactionManager
	 * @param transactionManagerCustomizers
	 */
	public HibernateConfig(DataSource dataSource, JpaProperties jpaProperties,
			ObjectProvider<JtaTransactionManager> jtaTransactionManager,
			ObjectProvider<TransactionManagerCustomizers> transactionManagerCustomizers) {
		super(dataSource, jpaProperties, jtaTransactionManager, transactionManagerCustomizers);

	}

	@Override
	protected void customizeVendorProperties(Map<String, Object> vendorProperties) {
		vendorProperties.put("hibernate.ejb.interceptor", new AlfaEmptyStringInterceptor());
		
		// ya da alttaki tanımı application.properties dosyasına yapmak gerekiyor. ikisi de calisiyor
		// spring.jpa.properties.hibernate.ejb.interceptor=tr.com.agem.alfa.util.AlfaEmptyStringInterceptor

	}

	
	


}

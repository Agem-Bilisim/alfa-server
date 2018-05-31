package tr.com.agem.alfa.bpmn.inputs;

import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import tr.com.agem.alfa.bpmn.types.AbstractCommonFormType;

/**
 * @author <a href="mailto:ali.ozeren@agem.com.tr">Ali Ozkan Ozeren</a>
 *
 */
public class HTMLInputUtils 
{

	private static HTMLInputUtils instance;
	
	private HTMLInputUtils() {}
	
	private String DEFAULT_DISCARDED_ATTR[] = 
			{"id","name","label","value","checked"};
					
	public static HTMLInputUtils getInstance() 
	{
		if (instance == null) {
			instance = new HTMLInputUtils();
		}
		
		return instance;
	}
	
	public String prepareAttributes(AbstractCommonFormType field, Map<String,String> attributes, final String... except) 
	{
			return 
					attributes.entrySet()
							   .stream().filter(new Predicate<Map.Entry<String,String>>() {
									@Override
									public boolean test(Map.Entry<String,String> entry) {
										if (except != null) {
											for (int i=0; i<except.length; i++) {
												if (entry.getKey().equals(except[i])) {
													return false;
												}
											}
										}
										for (int i=0; i<DEFAULT_DISCARDED_ATTR.length; i++) {
											if (entry.getKey().equals(DEFAULT_DISCARDED_ATTR[i])) {
												return false;
											}
										}										return true;
									}
								 })
								.map(e -> e.getKey()+"='"+ StringUtils.replace(e.getValue(),"'","\'") + "' ")
								.collect(Collectors.joining(" "));
	}
}

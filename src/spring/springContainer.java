package spring;

import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public final class springContainer {
	private static final ApplicationContext context = new ClassPathXmlApplicationContext("spring/spring.xml");
	
	/**
	 * get an object with matching bean name
	 * @param beanName name of the bean
	 * @return object with matching bean name
	 */
	public static Object getBean(String beanName) {
		return context.getBean(beanName);
	}

	/**
	 * get an object class that is <T> or subclass of <T> with matching bean name.
	 * @param <T> parent class
	 * @param clazz parent class or subclass of parent class
	 * @param beanName name of the bean
	 * @return <T> object with matching bean name
	 */
	public static <T extends Object> T getBeanOfClass(Class<? extends T> clazz, String beanName) {
		return context.getBean(beanName, clazz);
	}
	
	/**
	 * Get the first bean that is <T> or subclass of <T>
	 * @param <T> parent class
	 * @param clazz parent class or subclass of parent class
	 * @return <T> object
	 */
	public static <T extends Object> T getBeanOfClass(Class<? extends T> clazz) {
		return context.getBean(clazz);
	}
	
	/**
	 * Get all beans that are <T> or subclass of <T>
	 * @param <T> parent class
	 * @param clazz parent class or subclass of parent class
	 * @return <T> object mapped by their bean names
	 */
	public static <T extends Object> Map<String, ? extends T> getAllBeansOfClass(Class<? extends T> clazz) {
		return context.getBeansOfType(clazz);
	}
}

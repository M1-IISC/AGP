package jUnit;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.springframework.util.Assert;

import businessLogic.IBusinessLogicController;
import businessLogic.dataAccess.DataAccesObject;
import businessLogic.itinerary.ItineraryGraphBuilder;
import businessLogic.journeyPoint.JourneyPointFactory;
import businessLogic.stay.builders.IStayBuilder;
import businessLogic.transport.TransportStrategy;
import spring.springContainer;

public class SpringIntegrationTestCase {
	
	@Before
	public void prepare()
	{
		
	}

	@Test
	public void testDataAccesObjectBean()
	{
		Assert.notNull(springContainer.getBeanOfClass(DataAccesObject.class));
	}
	
	@Test
	public void testHotelFactoryBean()
	{
		Assert.notNull(springContainer.getBeanOfClass(JourneyPointFactory.class, "HotelFactory"));
	}
	
	@Test
	public void testTouristicSiteFactoryBean()
	{
		Assert.notNull(springContainer.getBeanOfClass(JourneyPointFactory.class, "TouristicSiteFactory"));
	}
	
	@Test
	public void testGraphBuilderBean()
	{
		Assert.notNull(springContainer.getBeanOfClass(ItineraryGraphBuilder.class));
		Assert.notNull(springContainer.getBean("DBItineraryGraphBuilder"));
	}
	
	@Test
	public void testStayBuilderBean()
	{
		Assert.notNull(springContainer.getBeanOfClass(IStayBuilder.class));
		Assert.notNull(springContainer.getBean("StayBuilder"));
	}
	
	@Test
	public void testBusinessLogicControllerBean()
	{
		Assert.notNull(springContainer.getBeanOfClass(IBusinessLogicController.class));
		Assert.notNull(springContainer.getBean("BusinessLogicController"));
	}
	
	@Test
	public void testTransportStrategyBeans()
	{
		@SuppressWarnings("unchecked")
		Map<String, TransportStrategy> strategies = (Map<String, TransportStrategy>) springContainer.getAllBeansOfClass(TransportStrategy.class);
		Assert.notNull(strategies);
		Assert.notEmpty(strategies);
		Assert.notNull(springContainer.getBeanOfClass(TransportStrategy.class, "WalkStrategy"));
		Assert.notNull(springContainer.getBeanOfClass(TransportStrategy.class, "BusStrategy"));
		Assert.notNull(springContainer.getBeanOfClass(TransportStrategy.class, "BoatStrategy"));
	}
}

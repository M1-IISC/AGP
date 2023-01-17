package jUnit;

import org.junit.Before;
import org.junit.Test;
import org.springframework.util.Assert;

import businessLogic.IBusinessLogicController;
import businessLogic.dataAccess.DataAccesObject;
import businessLogic.itinerary.ItineraryGraphBuilder;
import businessLogic.journeyPoint.JourneyPointFactory;
import businessLogic.stay.builders.IStayActivityBuilder;
import businessLogic.stay.builders.IStayBuilder;
import spring.springContainer;

public class SpringIntegrationTestCase {
	
	@Before
	public void prepare()
	{
		
	}

	@Test
	public void testDataAccesObjectBean()
	{
		Assert.notNull(springContainer.getBeanOfClass(DataAccesObject.class));;
	}
	
	@Test
	public void testHotelFactoryBean()
	{
		Assert.notNull(springContainer.getBeanOfClass(JourneyPointFactory.class, "HotelFactory"));;
	}
	
	@Test
	public void testTouristicSiteFactoryBean()
	{
		Assert.notNull(springContainer.getBeanOfClass(JourneyPointFactory.class, "TouristicSiteFactory"));;
	}
	
	@Test
	public void testGraphBuilderBean()
	{
		Assert.notNull(springContainer.getBeanOfClass(ItineraryGraphBuilder.class));
	}
	
	@Test
	public void testStayBuilderBean()
	{
		Assert.notNull(springContainer.getBeanOfClass(IStayBuilder.class));;	
	}
	
	@Test
	public void testBusinessLogicControllerBean()
	{
		Assert.notNull(springContainer.getBeanOfClass(IBusinessLogicController.class));;	
	}
}

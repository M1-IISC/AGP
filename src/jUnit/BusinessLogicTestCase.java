package jUnit;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.util.Assert;

import businessLogic.dataAccess.DataAccesObject;
import businessLogic.dataAccess.PlaceObject;
import businessLogic.itinerary.DBItineraryGraphBuilder;
import businessLogic.itinerary.ItineraryGraph;
import businessLogic.itinerary.ItineraryGraphBuilder;
import businessLogic.journeyPoint.JourneyPointFactory;
import spring.springContainer;

public class BusinessLogicTestCase {
	
	DataAccesObject dataAccessor = springContainer.getBeanOfClass(DataAccesObject.class);
	
	JourneyPointFactory hotelFactory = springContainer.getBeanOfClass(JourneyPointFactory.class, "HotelFactory");
	JourneyPointFactory touristicSiteFactory = springContainer.getBeanOfClass(JourneyPointFactory.class, "TouristicSiteFactory");
	
	ItineraryGraphBuilder graphBuilder = new DBItineraryGraphBuilder(dataAccessor, touristicSiteFactory, hotelFactory);

	@Before
	public void prepare()
	{

	}
	
	@Test
	public void springIntegation()
	{
		Assert.notNull(dataAccessor);
		Assert.notNull(hotelFactory);
		Assert.notNull(touristicSiteFactory);
	}
	
	@Test
	public void dataBaseAccesObjectTests()
	{
		List<PlaceObject> objects = dataAccessor.fetchAllPlaces();
		Assert.notEmpty(objects);
	}
	
	@Test
	public void graphTests()
	{
		ItineraryGraph graph = graphBuilder.build("");
		Assert.notNull(graph);
		Assert.notNull(graph.getHead());
	}
}

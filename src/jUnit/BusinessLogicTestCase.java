package jUnit;

import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.springframework.util.Assert;

import businessLogic.IBusinessLogicController;
import businessLogic.dataAccess.DataAccesObject;
import businessLogic.dataAccess.PlaceObject;
import businessLogic.itinerary.ItineraryGraph;
import businessLogic.itinerary.ItineraryGraphBuilder;
import businessLogic.journeyPoint.CategoryOfSite;
import businessLogic.journeyPoint.JourneyPointFactory;
import businessLogic.stay.StayProfile;
import businessLogic.transport.TransportStrategy;
import spring.springContainer;

public class BusinessLogicTestCase {
	
	private DataAccesObject dataAccessor;
	private JourneyPointFactory hotelFactory;
	private JourneyPointFactory touristicSiteFactory;
	private ItineraryGraphBuilder graphBuilder;
	private IBusinessLogicController businessLogicController;
	
	@Before
	public void prepare()
	{
		dataAccessor = springContainer.getBeanOfClass(DataAccesObject.class);
		hotelFactory = springContainer.getBeanOfClass(JourneyPointFactory.class, "HotelFactory");
		touristicSiteFactory = springContainer.getBeanOfClass(JourneyPointFactory.class, "TouristicSiteFactory");
		graphBuilder = springContainer.getBeanOfClass(ItineraryGraphBuilder.class);
		businessLogicController = springContainer.getBeanOfClass(IBusinessLogicController.class);	
	}
	
	@Test
	public void transportStrategyTest()
	{
		@SuppressWarnings("unchecked")
		Map<String, TransportStrategy> strategies = (Map<String, TransportStrategy>) springContainer.getAllBeansOfClass(TransportStrategy.class);
		for (Map.Entry<String, TransportStrategy> strategy : strategies.entrySet()) {
			TransportStrategy transportStrategy = strategy.getValue();
			Assert.isTrue(transportStrategy.calculateConfort(0) != 0, String.format("Comfort at zero for %s bean", strategy.getKey()));
			Assert.isTrue(transportStrategy.calculatePrice(0) >= 0, String.format("Negative price for %s bean", strategy.getKey()));
			Assert.isTrue(transportStrategy.calculateTime(30) != 0, String.format("Time at zero for %s bean", strategy.getKey()));
		}
	}
	
	@Test
	public void DAOFetchEveryPlacesTest()
	{
		List<PlaceObject> objects = dataAccessor.fetchAllPlaces();
		Assert.notNull(objects);
		Assert.notEmpty(objects);
	}
	
	@Test
	public void DAOFetchEveryHotelsTest()
	{
		List<PlaceObject> objects = dataAccessor.fetchAllHotels();
		Assert.notNull(objects);
		Assert.notEmpty(objects);
		for (PlaceObject place : objects) {
			Assert.isTrue(place.getAttractionTime()==0);
			Assert.isTrue(place.getCost()==0);
			Assert.isTrue(place.getDescription()==null);
			Assert.isTrue(place.getLunchCost()!=0);
			Assert.isTrue(place.getNightCost()!=0);
			Assert.isTrue(place.getAccuracy()==1);
		}
	}
	
	@Test
	public void DAOFetchEverySitesTest()
	{
		List<PlaceObject> objects = dataAccessor.fetchAllSites();
		Assert.notNull(objects);
		Assert.notEmpty(objects);
		for (PlaceObject place : objects) {
			Assert.isTrue(place.getAttractionTime()!=0);
			Assert.isTrue(place.getCost()!=0);
			Assert.isTrue(place.getDescription()!=null);			
			Assert.isTrue(place.getLunchCost()==0);
			Assert.isTrue(place.getNightCost()==0);
		}
	}
	
	@Test
	public void DAOFetchPlacesByKeywords()
	{
		List<PlaceObject> objects = dataAccessor.fetchSitesByKeywords("");
		double accuracyAccumulator = 0;
		Assert.notEmpty(objects);
		for (PlaceObject place : objects) {
			accuracyAccumulator+=place.getAccuracy();
		}
		Assert.isTrue(accuracyAccumulator/(double)(objects.size()) == 1);
		
		objects = dataAccessor.fetchSitesByKeywords("plage activitées");
		Assert.notEmpty(objects);
		accuracyAccumulator = 0;
		for (PlaceObject place : objects) {
			accuracyAccumulator+=place.getAccuracy();
		}
		Assert.isTrue(accuracyAccumulator/(double)(objects.size()) != 1);
		Assert.isTrue(accuracyAccumulator/(double)(objects.size()) >= 0);
	}
	
	@Test
	public void DAOFetchPlacesTransportRelationsByKeywords()
	{
		Assert.notEmpty(dataAccessor.fetchSitesRelationsByKeywords("plage activitées"));
	}
	
	@Test
	public void journeyPointBuilderTests()
	{
		Assert.notNull(hotelFactory.factory("Hotel", null, 0.5, 0, 0, 30, 100, null));
		Assert.notNull(touristicSiteFactory.factory("Site", "Dummy site", 0.5, 1.5, 15, 0, 0, CategoryOfSite.HISTORIC));
	}
	
	@Test
	public void graphBuilderTests()
	{
		ItineraryGraph graph = graphBuilder.build("plage activitées");
		Assert.notNull(graph);
		Assert.notNull(graph.getHead());
	}
	
	@Test
	public void BusinessControllerTests()
	{
		Assert.notEmpty(businessLogicController.getAllActivitySites());
		Assert.notEmpty(businessLogicController.getAllHistoricalSites());
		Assert.notEmpty(businessLogicController.getAllHotels());
		Assert.notEmpty(businessLogicController.searchForTouristicSites("plage activitées"));
		Assert.notEmpty(businessLogicController.searchPlansForAStay(3,100,10000,StayProfile.Discovery,0.6,"plage activitées"));
	}
}

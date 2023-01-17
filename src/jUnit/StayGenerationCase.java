package jUnit;

import org.junit.Before;
import org.junit.Test;

import businessLogic.persistance.DBDataAccesObject;
import businessLogic.persistance.DataAccesObject;
import spring.springContainer;

public class StayGenerationCase {

	DataAccesObject dataAccessor = springContainer.getBeanOfClass(DBDataAccesObject.class);

	@Before
	public void prepare()
	{
		
	}
	
	@Test
	public void base()
	{
		
	}
	
}

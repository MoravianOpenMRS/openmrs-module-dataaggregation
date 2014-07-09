package org.openmrs.module.dataaggregation.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.openmrs.api.context.Context;
import org.openmrs.module.dataaggregation.api.impl.DataCounts;
import org.openmrs.test.BaseModuleContextSensitiveTest;
import org.openmrs.test.SkipBaseSetup;

public class DiseaseBurdenServiceTest extends BaseModuleContextSensitiveTest {	

	private static final String DATASET_XML_PATH_NAME = "DataAggregationDiseaseBurdenTestDataset.xml";
	
	private DataAggregationService service;
	
	//
	@Before
	public void setup() throws Exception {
		executeDataSet(DATASET_XML_PATH_NAME);
		service = Context.getService(DataAggregationService.class);
	}
	
	@Test
	@SkipBaseSetup
	public void testBetweenDatesEverything() {
		
		System.out.println("Running Test: " + Thread.currentThread().getStackTrace()[1].getMethodName());
		
		// testing that it will do the same with a list of all the cities same as the null
				
		
		List<Object> results = service.getDiseaseBurden(null, null, "2000-01-01", "2010-01-01", -1, -1);
		
		DataCounts map = new DataCounts(results);
				
		assertEquals("1", map.getValue("ANEMIA"));		
		assertEquals("2", map.getValue("BRONCHITIS"));	
		assertEquals("3", map.getValue("ECZEMA"));
		assertEquals("4", map.getValue("GINGIVITIS"));
		assertEquals("5", map.getValue("MALARIA"));
	
	}

	@Test
	@SkipBaseSetup
	public void testBetweenDatesNotIncludeEnds() {
		
		System.out.println("Running Test: " + Thread.currentThread().getStackTrace()[1].getMethodName());
		
		// testing that it will do the same with a list of all the cities same as the null
				
		
		
		List<Object> results = service.getDiseaseBurden(null, null, "2008-08-03", "2008-08-18", -1, -1);
		DataCounts map = new DataCounts(results);
		
		assertEquals("1", map.getValue("ANEMIA"));	
		assertNull(map.getValue("BRONCHITIS"));	
		assertEquals("3", map.getValue("ECZEMA"));
		assertEquals("3", map.getValue("GINGIVITIS"));
		assertNull(map.getValue("MALARIA"));
	
	}

	@Test
	@SkipBaseSetup
	public void testBetweenDatesNothing() {
		
		System.out.println("Running Test: " + Thread.currentThread().getStackTrace()[1].getMethodName());
		
		// testing that it will do the same with a list of all the cities same as the null
				
		
		List<Object> results = service.getDiseaseBurden(null, null, "2008-08-03", "2008-08-10", -1, -1);
		
		DataCounts map = new DataCounts(results);
		
		assertNull(map.getValue("MALARIA"));
	
	}

	@Test
	@SkipBaseSetup
	public void testBetweenDatesIncludeStart() {
		
		System.out.println("Running Test: " + Thread.currentThread().getStackTrace()[1].getMethodName());
		
		// testing that it will do the same with a list of all the cities same as the null
				
		
		List<Object> results = service.getDiseaseBurden(null, null, "2008-08-01", "2008-08-18", -1, -1);
		
		DataCounts map = new DataCounts(results);
				
		assertEquals("1", map.getValue("ANEMIA"));		
		assertEquals("2", map.getValue("BRONCHITIS"));	
		assertEquals("3", map.getValue("ECZEMA"));
		assertEquals("3", map.getValue("GINGIVITIS"));
		assertNull(map.getValue("MALARIA"));
	
	}

	@Test
	@SkipBaseSetup
	public void testBetweenDatesIncludeEnd() {
		
		System.out.println("Running Test: " + Thread.currentThread().getStackTrace()[1].getMethodName());
		
		// testing that it will do the same with a list of all the cities same as the null
				
		
		List<Object> results = service.getDiseaseBurden(null, null, "2008-08-03", "2008-08-15", -1, -1);
		
		DataCounts map = new DataCounts(results);
				
		assertEquals("1", map.getValue("ANEMIA"));		
		assertNull(map.getValue("BRONCHITIS"));	
		assertEquals("3", map.getValue("ECZEMA"));
		assertEquals("3", map.getValue("GINGIVITIS"));
		assertNull(map.getValue("MALARIA"));
	
	}

	@Test
	@SkipBaseSetup
	public void testBetweenDatesIncludeBoth() {
		
		System.out.println("Running Test: " + Thread.currentThread().getStackTrace()[1].getMethodName());
		
		// testing that it will do the same with a list of all the cities same as the null
				
		
		List<Object> results = service.getDiseaseBurden(null, null, "2008-08-01", "2008-08-15", -1, -1);
		
		DataCounts map = new DataCounts(results);
				
		assertEquals("1", map.getValue("ANEMIA"));		
		assertEquals("2", map.getValue("BRONCHITIS"));	
		assertEquals("3", map.getValue("ECZEMA"));
		assertEquals("3", map.getValue("GINGIVITIS"));
		assertNull(map.getValue("MALARIA"));
	
	}

	@Test
	@SkipBaseSetup
	public void testStartDateBeforeEverything() {
		
		System.out.println("Running Test: " + Thread.currentThread().getStackTrace()[1].getMethodName());
		
		// testing that it will do the same with a list of all the cities same as the null
				
		
		List<Object> results = service.getDiseaseBurden(null, null, "2000-01-01", null, -1, -1);
		
		DataCounts map = new DataCounts(results);
				
		assertEquals("1", map.getValue("ANEMIA"));		
		assertEquals("2", map.getValue("BRONCHITIS"));	
		assertEquals("3", map.getValue("ECZEMA"));
		assertEquals("4", map.getValue("GINGIVITIS"));
		assertEquals("5", map.getValue("MALARIA"));

	}
	
	@Test
	@SkipBaseSetup
	public void testStartDateAfterEverything() {
		
		System.out.println("Running Test: " + Thread.currentThread().getStackTrace()[1].getMethodName());
		
		// testing that it will do the same with a list of all the cities same as the null
				
		
		List<Object> results = service.getDiseaseBurden(null, null, "2010-01-01", null, -1, -1);
		
		DataCounts map = new DataCounts(results);
				
		assertNull(map.getValue("ANEMIA"));		


	}
	
	@Test
	@SkipBaseSetup
	public void testStartDateInTheMiddle() {
		
		System.out.println("Running Test: " + Thread.currentThread().getStackTrace()[1].getMethodName());
		
		// testing that it will do the same with a list of all the cities same as the null
				
		
		List<Object> results = service.getDiseaseBurden(null, null, "2008-08-16", null, -1, -1);
		
		DataCounts map = new DataCounts(results);
				
		assertEquals("1", map.getValue("GINGIVITIS"));
		assertEquals("5", map.getValue("MALARIA"));

	}
	
	@Test
	@SkipBaseSetup
	public void testStartDateOnEnd() {
		
		System.out.println("Running Test: " + Thread.currentThread().getStackTrace()[1].getMethodName());
		
		// testing that it will do the same with a list of all the cities same as the null
				
		
		List<Object> results = service.getDiseaseBurden(null, null, "2008-08-15", null, -1, -1);
		
		DataCounts map = new DataCounts(results);
				
		assertEquals("1", map.getValue("ANEMIA"));	
		assertNull(map.getValue("BRONCHITIS"));
		assertEquals("3", map.getValue("ECZEMA"));
		assertEquals("4", map.getValue("GINGIVITIS"));
		assertEquals("5", map.getValue("MALARIA"));
	
	}

	@Test
	@SkipBaseSetup
	public void testEndDateEverything() {
		
		System.out.println("Running Test: " + Thread.currentThread().getStackTrace()[1].getMethodName());
		
		// testing that it will do the same with a list of all the cities same as the null
				
		
		List<Object> results = service.getDiseaseBurden(null, null, null, "2010-01-01", -1, -1);
		
		DataCounts map = new DataCounts(results);
				
		assertEquals("1", map.getValue("ANEMIA"));		
		assertEquals("2", map.getValue("BRONCHITIS"));	
		assertEquals("3", map.getValue("ECZEMA"));
		assertEquals("4", map.getValue("GINGIVITIS"));
		assertEquals("5", map.getValue("MALARIA"));

	}
	
	@Test
	@SkipBaseSetup
	public void testEndDateBeforeEverything() {
		
		System.out.println("Running Test: " + Thread.currentThread().getStackTrace()[1].getMethodName());
		
		// testing that it will do the same with a list of all the cities same as the null
				
		
		List<Object> results = service.getDiseaseBurden(null, null, null, "2000-01-01", -1, -1);
		
		DataCounts map = new DataCounts(results);
				
		assertNull(map.getValue("ANEMIA"));

	}
	
	@Test
	@SkipBaseSetup
	public void testEndDateInTheMiddle() {
		
		System.out.println("Running Test: " + Thread.currentThread().getStackTrace()[1].getMethodName());
		
		// testing that it will do the same with a list of all the cities same as the null
				
		
		List<Object> results = service.getDiseaseBurden(null, null, null, "2008-08-16", -1, -1);
		
		DataCounts map = new DataCounts(results);
				
		assertEquals("1", map.getValue("ANEMIA"));		
		assertEquals("2", map.getValue("BRONCHITIS"));	
		assertEquals("3", map.getValue("ECZEMA"));
		assertEquals("3", map.getValue("GINGIVITIS"));
		assertNull(map.getValue("MALARIA"));

	}
	
	@Test
	@SkipBaseSetup
	public void testEndDateOnEnd() {
		
		System.out.println("Running Test: " + Thread.currentThread().getStackTrace()[1].getMethodName());
		
		// testing that it will do the same with a list of all the cities same as the null
				
		
		List<Object> results = service.getDiseaseBurden(null, null, null, "2008-08-15", -1, -1);
		
		DataCounts map = new DataCounts(results);
				
		assertEquals("1", map.getValue("ANEMIA"));		
		assertEquals("2", map.getValue("BRONCHITIS"));	
		assertEquals("3", map.getValue("ECZEMA"));
		assertEquals("3", map.getValue("GINGIVITIS"));
		assertNull(map.getValue("MALARIA"));

	}
}

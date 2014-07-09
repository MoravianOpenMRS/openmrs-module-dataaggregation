/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.dataaggregation.api;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.openmrs.api.context.Context;
import org.openmrs.module.dataaggregation.api.impl.DataCounts;
import org.openmrs.test.BaseModuleContextSensitiveTest;
import org.openmrs.test.SkipBaseSetup;

/**
 * Tests {@link ${DataAggregationService}}.
 */

public class  DataAggregationServiceTest extends BaseModuleContextSensitiveTest {	

	private static final String DATASET_XML_PATH_NAME = "DataAggregationDiseaseBurdenTestDataset.xml";
	//
	@Before
	public void setup() throws Exception {
		executeDataSet(DATASET_XML_PATH_NAME);
	}
	
	@Test
	@SkipBaseSetup
	public void testAllParametersNull() {	
		
		// testing to see if all the null will default to everything
		
		DataAggregationService service = Context.getService(DataAggregationService.class);
		List<Object> results = service.getDiseaseBurden(null, null, null, null, null, null);
		
		DataCounts map = new DataCounts(results);
				
		assertEquals("1", map.getValue("ANEMIA"));		
		assertEquals("2", map.getValue("BRONCHITIS"));	
		assertEquals("3", map.getValue("ECZEMA"));
		assertEquals("4", map.getValue("GINGIVITIS"));
		assertEquals("5", map.getValue("MALARIA"));
	
	}
	
	@Test
	@SkipBaseSetup
	public void testDiseaseParameterNull() {	
		
		// testing to see if just putting null into the disease list will make it get all the diseases
		
		String cityList = "Indianapolis:Philadelphia:New York:Chicago:Detroit:Los Angeles:San Diego";
		DataAggregationService service = Context.getService(DataAggregationService.class);
		List<Object> results = service.getDiseaseBurden(null, cityList, "0000-01-01", "2114-07-01", 1, 15000);
		
		DataCounts map = new DataCounts(results);
				
		assertEquals("1", map.getValue("ANEMIA"));		
		assertEquals("2", map.getValue("BRONCHITIS"));	
		assertEquals("3", map.getValue("ECZEMA"));
		assertEquals("4", map.getValue("GINGIVITIS"));
		assertEquals("5", map.getValue("MALARIA"));
	
	}
	
	@Test
	@SkipBaseSetup
	public void testAllDiseases() {
		
		// testing that if all the diseases are in the list then it will get each one
				
		String diseases = "MALARIA:BRONCHITIS:ECZEMA:ANEMIA:GINGIVITIS";
		
		DataAggregationService service = Context.getService(DataAggregationService.class);
		List<Object> results = service.getDiseaseBurden(diseases, null, null, null, -1, -1);
		
		DataCounts map = new DataCounts(results);
				
		assertEquals("1", map.getValue("ANEMIA"));		
		assertEquals("2", map.getValue("BRONCHITIS"));	
		assertEquals("3", map.getValue("ECZEMA"));
		assertEquals("4", map.getValue("GINGIVITIS"));
		assertEquals("5", map.getValue("MALARIA"));
		
	}
	
	@Test
	@SkipBaseSetup
	public void testOneDisease() {
		
		// testing to make sure that if one particular disease is wanted it will return just that disease
		
		String diseases = "MALARIA";
		
		DataAggregationService service = Context.getService(DataAggregationService.class);
		List<Object> results = service.getDiseaseBurden(diseases, null, null, null, -1, -1);
		
		DataCounts map = new DataCounts(results);
				
		assertEquals("5", map.getValue("MALARIA"));		
		assertNull(map.getValue("ANEMIA"));
	}
	
	@Test
	@SkipBaseSetup
	public void testCoupleOfDiseases() {
		
		// testing to make sure that it can also get a few diseases but not all 
		
		String diseases = "MALARIA:ECZEMA";
		
		DataAggregationService service = Context.getService(DataAggregationService.class);
		List<Object> results = service.getDiseaseBurden(diseases, null, null, null, -1, -1);
		
		DataCounts map = new DataCounts(results);
				
		assertEquals("5", map.getValue("MALARIA"));
		assertEquals("3", map.getValue("ECZEMA"));
		assertNull(map.getValue("ANEMIA"));
	}
	
	@Test
	@SkipBaseSetup
	public void testDiseaseNotExists() {
		
		// testing to make sure that if a disease is specified it will not return anything for that disease if there are no cases of it
		
		String diseases = "COOTIES";
		
		DataAggregationService service = Context.getService(DataAggregationService.class);
		List<Object> results = service.getDiseaseBurden(diseases, null, null, null, -1, -1);
		
		DataCounts map = new DataCounts(results);
				
		assertEquals(null, map.getValue("COOTIES"));
		assertNull(map.getValue("ANEMIA"));
		
	}
	
	@Test
	@SkipBaseSetup
	public void testOneDiseaseNotExistsOtherDoes() {
		
		// testing that with a disease that does not exist it will still get the disease that does exist
		
		String diseases = "ECZEMA:COOTIES";
		
		DataAggregationService service = Context.getService(DataAggregationService.class);
		List<Object> results = service.getDiseaseBurden(diseases, null, null, null, -1, -1);
		
		DataCounts map = new DataCounts(results);
				
		assertEquals("3", map.getValue("ECZEMA"));
		assertNull(map.getValue("COOTIES"));
		assertNull(map.getValue("ANEMIA"));
	}
	
	@Test
	@SkipBaseSetup
	public void testNeitherDiseaseExists() {
		
		// testing that with multiple diseases it will not get a cases for either of them
		
		String diseases = "ROOSTER-POX:COOTIES";
		
		DataAggregationService service = Context.getService(DataAggregationService.class);
		List<Object> results = service.getDiseaseBurden(diseases, null, null, null, -1, -1);
		
		DataCounts map = new DataCounts(results);
				
		assertEquals(null, map.getValue("ROOSTER-POX"));
		assertEquals(null, map.getValue("COOTIES"));
		assertNull(map.getValue("ANEMIA"));
	}
	
	@Test
	@SkipBaseSetup
	public void testMinNumberOfCases() {
		
		// testing that it will only get diseases with the certain cases above a threshold (inclusive at the threshold)
		
		
		DataAggregationService service = Context.getService(DataAggregationService.class);
		List<Object> results = service.getDiseaseBurden(null, null, null, null, 3, -1);
		
		DataCounts map = new DataCounts(results);
		
		assertEquals("3", map.getValue("ECZEMA"));
		assertEquals("4", map.getValue("GINGIVITIS"));
		assertEquals("5", map.getValue("MALARIA"));
		assertNull(map.getValue("ANEMIA"));
	}
	
	@Test
	@SkipBaseSetup
	public void testMaxNumberOfCases() {
		
		// testing that it will only get diseases with certain cases below a threshold (inclusive at the threshold)
		
		DataAggregationService service = Context.getService(DataAggregationService.class);
		List<Object> results = service.getDiseaseBurden(null, null, null, null, -1, 3);
		
		DataCounts map = new DataCounts(results);
				
		assertEquals("1", map.getValue("ANEMIA"));		
		assertEquals("2", map.getValue("BRONCHITIS"));	
		assertEquals("3", map.getValue("ECZEMA"));
		assertNull(map.getValue("MALARIA"));
	}
	
	@Test
	@SkipBaseSetup
	public void testOneCity() {
		
		// testing that uses only one city to get the diseases from just that city
		
		String cities = "Indianapolis";
		
		DataAggregationService service = Context.getService(DataAggregationService.class);
		List<Object> results = service.getDiseaseBurden(null, cities, null, null, -1, -1);
		
		DataCounts map = new DataCounts(results);
				
		assertEquals("1", map.getValue("MALARIA"));
		assertNull(map.getValue("ANEMIA"));
	}
	
	@Test
	@SkipBaseSetup
	public void testSomeCities() {
		
		// testing that uses two cities and gets the diseases of those cities
		
		String cities = "Indianapolis:Philadelphia";
		
		DataAggregationService service = Context.getService(DataAggregationService.class);
		List<Object> results = service.getDiseaseBurden(null, cities, null, null, -1, -1);
		
		DataCounts map = new DataCounts(results);
		
		assertEquals("1", map.getValue("ANEMIA"));		
		assertEquals("2", map.getValue("BRONCHITIS"));	
		assertEquals("3", map.getValue("ECZEMA"));
		assertEquals("4", map.getValue("GINGIVITIS"));
		assertEquals("2", map.getValue("MALARIA"));

	}
	
	@Test
	@SkipBaseSetup
	public void testCityDoesNotExist() {
		
		// testing that a city that does not exist will not return results
		
		String cities = "Jeffersonville";
		
		DataAggregationService service = Context.getService(DataAggregationService.class);
		List<Object> results = service.getDiseaseBurden(null, cities, null, null, -1, -1);
		
		DataCounts map = new DataCounts(results);

		assertNull(map.getValue("ANEMIA"));
	}
	
	@Test
	@SkipBaseSetup
	public void testAllCities() {
		
		// testing that it will do the same with a list of all the cities same as the null
		
		String cities = "Indianapolis:Philadelphia:New York:Chicago:Detroit:Los Angeles:San Diego";
		
		DataAggregationService service = Context.getService(DataAggregationService.class);
		List<Object> results = service.getDiseaseBurden(null, cities, null, null, -1, -1);
		
		DataCounts map = new DataCounts(results);
				
		assertEquals("1", map.getValue("ANEMIA"));		
		assertEquals("2", map.getValue("BRONCHITIS"));	
		assertEquals("3", map.getValue("ECZEMA"));
		assertEquals("4", map.getValue("GINGIVITIS"));
		assertEquals("5", map.getValue("MALARIA"));

	}
}

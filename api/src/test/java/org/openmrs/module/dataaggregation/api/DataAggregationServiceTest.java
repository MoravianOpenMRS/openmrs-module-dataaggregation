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
	
	private DataAggregationService service;
	
	//
	@Before
	public void setup() throws Exception {
		executeDataSet(DATASET_XML_PATH_NAME);
		service = Context.getService(DataAggregationService.class);
	}
	
	@Test
	@SkipBaseSetup
	public void testAllParametersNull() {	
		
		System.out.println("Running Test: " + Thread.currentThread().getStackTrace()[1].getMethodName());
		
		// testing to see if all the null will default to everything

		List<Object> results = service.getDiseaseBurden(null, null, null, null, null, null);
		service.getDiseaseBurden(null, null, null, null, null, null);
		service.getDiseaseBurden(null, null, null, null, null, null);
		service.getDiseaseBurden(null, null, null, null, null, null);
		service.getDiseaseBurden(null, null, null, null, null, null);
		service.getDiseaseBurden(null, null, null, null, null, null);
		service.getDiseaseBurden(null, null, null, null, null, null);
		service.getDiseaseBurden(null, null, null, null, null, null);
		service.getDiseaseBurden(null, null, null, null, null, null);
		service.getDiseaseBurden(null, null, null, null, null, null);
		service.getDiseaseBurden(null, null, null, null, null, null);
		service.getDiseaseBurden(null, null, null, null, null, null);
		service.getDiseaseBurden(null, null, null, null, null, null);
		service.getDiseaseBurden(null, null, null, null, null, null);
		service.getDiseaseBurden(null, null, null, null, null, null);
		service.getDiseaseBurden(null, null, null, null, null, null);
		service.getDiseaseBurden(null, null, null, null, null, null);
		service.getDiseaseBurden(null, null, null, null, null, null);
		service.getDiseaseBurden(null, null, null, null, null, null);
		service.getDiseaseBurden(null, null, null, null, null, null);
		service.getDiseaseBurden(null, null, null, null, null, null);
		service.getDiseaseBurden(null, null, null, null, null, null);
		service.getDiseaseBurden(null, null, null, null, null, null);
		
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
		
		System.out.println("Running Test: " + Thread.currentThread().getStackTrace()[1].getMethodName());
		
		// testing to see if just putting null into the disease list will make it get all the diseases
		
		String cityList = "Indianapolis:Philadelphia:New York:Chicago:Detroit:Los Angeles:San Diego";
		
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
		
		System.out.println("Running Test: " + Thread.currentThread().getStackTrace()[1].getMethodName());
		
		// testing that if all the diseases are in the list then it will get each one
				
		String diseases = "MALARIA:BRONCHITIS:ECZEMA:ANEMIA:GINGIVITIS";
		
		 
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
		
		System.out.println("Running Test: " + Thread.currentThread().getStackTrace()[1].getMethodName());
		
		// testing to make sure that if one particular disease is wanted it will return just that disease
		
		String diseases = "MALARIA";
		
		 
		List<Object> results = service.getDiseaseBurden(diseases, null, null, null, -1, -1);
		
		DataCounts map = new DataCounts(results);
				
		assertEquals("5", map.getValue("MALARIA"));		
		assertNull(map.getValue("ANEMIA"));
	}
	
	@Test
	@SkipBaseSetup
	public void testCoupleOfDiseases() {
		
		System.out.println("Running Test: " + Thread.currentThread().getStackTrace()[1].getMethodName());
		
		// testing to make sure that it can also get a few diseases but not all 
		
		String diseases = "MALARIA:ECZEMA";
		
		 
		List<Object> results = service.getDiseaseBurden(diseases, null, null, null, -1, -1);
		
		DataCounts map = new DataCounts(results);
				
		assertEquals("5", map.getValue("MALARIA"));
		assertEquals("3", map.getValue("ECZEMA"));
		assertNull(map.getValue("ANEMIA"));
	}
	
	@Test
	@SkipBaseSetup
	public void testDiseaseNotExists() {
		
		System.out.println("Running Test: " + Thread.currentThread().getStackTrace()[1].getMethodName());
		
		// testing to make sure that if a disease is specified it will not return anything for that disease if there are no cases of it
		
		String diseases = "COOTIES";
		
		 
		List<Object> results = service.getDiseaseBurden(diseases, null, null, null, -1, -1);
		
		DataCounts map = new DataCounts(results);
				
		assertNull(map.getValue("COOTIES"));
		assertNull(map.getValue("ANEMIA"));
		
	}
	
	@Test
	@SkipBaseSetup
	public void testOneDiseaseNotExistsOtherDoes() {
		
		System.out.println("Running Test: " + Thread.currentThread().getStackTrace()[1].getMethodName());
		
		// testing that with a disease that does not exist it will still get the disease that does exist
		
		String diseases = "ECZEMA:COOTIES";
		
		 
		List<Object> results = service.getDiseaseBurden(diseases, null, null, null, -1, -1);
		
		DataCounts map = new DataCounts(results);
				
		assertEquals("3", map.getValue("ECZEMA"));
		assertNull(map.getValue("COOTIES"));
		assertNull(map.getValue("ANEMIA"));
	}
	
	@Test
	@SkipBaseSetup
	public void testNeitherDiseaseExists() {
		
		System.out.println("Running Test: " + Thread.currentThread().getStackTrace()[1].getMethodName());
		
		// testing that with multiple diseases it will not get a cases for either of them
		
		String diseases = "ROOSTER-POX:COOTIES";
		
		 
		List<Object> results = service.getDiseaseBurden(diseases, null, null, null, -1, -1);
		
		DataCounts map = new DataCounts(results);
				
		assertNull(map.getValue("ROOSTER-POX"));
		assertNull(map.getValue("COOTIES"));
		assertNull(map.getValue("ANEMIA"));
	}
	
	@Test
	@SkipBaseSetup
	public void testMinNumberOfCases() {
		
		System.out.println("Running Test: " + Thread.currentThread().getStackTrace()[1].getMethodName());
		
		// testing that it will only get diseases with the certain cases above a threshold (inclusive at the threshold)
		
		
		 
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
		
		System.out.println("Running Test: " + Thread.currentThread().getStackTrace()[1].getMethodName());
		
		// testing that it will only get diseases with certain cases below a threshold (inclusive at the threshold)
		
		 
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
		
		System.out.println("Running Test: " + Thread.currentThread().getStackTrace()[1].getMethodName());
		
		// testing that uses only one city to get the diseases from just that city
		
		String cities = "Indianapolis";
		
		 
		List<Object> results = service.getDiseaseBurden(null, cities, null, null, -1, -1);
		
		DataCounts map = new DataCounts(results);
				
		assertEquals("1", map.getValue("MALARIA"));
		assertNull(map.getValue("ANEMIA"));
	}
	
	@Test
	@SkipBaseSetup
	public void testSomeCities() {
		
		System.out.println("Running Test: " + Thread.currentThread().getStackTrace()[1].getMethodName());
		
		// testing that uses two cities and gets the diseases of those cities
		
		String cities = "Indianapolis:Philadelphia";
		
		 
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
		
		System.out.println("Running Test: " + Thread.currentThread().getStackTrace()[1].getMethodName());
		
		// testing that a city that does not exist will not return results
		
		String cities = "Jeffersonville";
		
		 
		List<Object> results = service.getDiseaseBurden(null, cities, null, null, -1, -1);
		
		DataCounts map = new DataCounts(results);

		assertNull(map.getValue("ANEMIA"));
	}
	
	
	@Test
	@SkipBaseSetup
	public void testAllCities() {
		
		System.out.println("Running Test: " + Thread.currentThread().getStackTrace()[1].getMethodName());
		
		// testing that it will do the same with a list of all the cities same as the null
		
		String cities = "Indianapolis:Philadelphia:New York:Chicago:Detroit:Los Angeles:San Diego";
		
		 
		List<Object> results = service.getDiseaseBurden(null, cities, null, null, -1, -1);
		
		DataCounts map = new DataCounts(results);
				
		assertEquals("1", map.getValue("ANEMIA"));		
		assertEquals("2", map.getValue("BRONCHITIS"));	
		assertEquals("3", map.getValue("ECZEMA"));
		assertEquals("4", map.getValue("GINGIVITIS"));
		assertEquals("5", map.getValue("MALARIA"));

	}
	
}

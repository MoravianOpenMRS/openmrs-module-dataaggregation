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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	private Map<String, Integer> getMapData(List<Object> records) {

		Map<String, Integer> map = new HashMap<String, Integer>();
		
		// Each object in results is another record from our SQL statement
		for (Object o : records) {
			// Cast each object into an array where each column is another index into the array
			Object[] vals = (Object[]) o;
			// vals[1] is the name of the disease
			// vals[2] is the count for the disease
			// vals[0] is just the concept_id of the disease which we may or may not need and that is why vals[0] is not used here
			
			map.put((String) vals[1], (Integer) vals[2]);
		}
		
		
		return map;
	}

	@Test
	@SkipBaseSetup
	public void testAllDiseases() {		
				
		String diseases = "Malaria:BRONCHITIS:anemia:GIngIVITIS";
		String cityList = "Philadelphia";
		
		DataAggregationService service = Context.getService(DataAggregationService.class);
		List<Object> results = service.getDiseaseBurden(diseases, cityList, null, null, -1, -1);
		
		DataCounts map = new DataCounts(results);
		
		
		//Map<String, Integer> map = getMapData(results);
		
		assertEquals(1, map.get("ANEMIA"));		
		assertEquals(2, map.get("BRONCHITIS").intValue());				
		assertEquals(4, map.get("GINGIVITIS").intValue());
		assertEquals(5, map.get("MALARIA").intValue());
		
	}
	
	@Test
	@SkipBaseSetup
	public void testOneDisease() {
		
		String diseases = "MALARIA";
		
		DataAggregationService service = Context.getService(DataAggregationService.class);
		String resultString = service.getDiseaseBurden(diseases, null, null, null, -1, -1);
		
		String[] results = resultString.split("\n");
		
		assertEquals("MALARIA:5", results[0]);
		
	}
	
	@Test
	@SkipBaseSetup
	public void testCoupleOfDiseases() {
		
		String diseases = "MALARIA:ANEMIA:ECZEMA";
		
		DataAggregationService service = Context.getService(DataAggregationService.class);
		String resultString = service.getDiseaseBurden(diseases, null, null, null, -1, -1);
		
		String[] results = resultString.split("\n");
		
		assertEquals("ANEMIA:1", results[0]);
		assertEquals("ECZEMA:3", results[1]);
		assertEquals("MALARIA:5", results[2]);
		
	}
	
	@Test
	@SkipBaseSetup
	public void testDiseaseNotExists() {
		
		String diseases = "BI-POLAR";
		
		DataAggregationService service = Context.getService(DataAggregationService.class);
		String resultString = service.getDiseaseBurden(diseases, null, null, null, -1, -1);
		
		assertEquals("", resultString);
		
	}
	
	@Test
	@SkipBaseSetup
	public void testOneDiseaseNotExistsOtherDoes() {
		
	}
	
	@Test
	@SkipBaseSetup
	public void testNeitherExist() {
		
>>>>>>> Stashed changes
	}
}

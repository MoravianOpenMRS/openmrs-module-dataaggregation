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

import org.junit.Before;
import org.junit.Test;
import org.openmrs.api.context.Context;
import org.openmrs.test.BaseModuleContextSensitiveTest;

/**
 * Tests {@link ${DataAggregationService}}.
 */
public class  DataAggregationServiceTest extends BaseModuleContextSensitiveTest {	

	private static final String DATASET_XML_PATH_NAME = "sampleDiseases.xml";
	//
	@Before
	public void setup() throws Exception {
		executeDataSet(DATASET_XML_PATH_NAME);
	}

	@Test
	public void testDiseaseQuery() {		
				
		String diseases = "MALARIA:SCABIES:ANEMIA:MENINGITIS, CRYPTOCOCCAL";
		
		DataAggregationService service = Context.getService(DataAggregationService.class);

		String thing = service.getDiseaseBurden(null, null, "1900-01-20 00:00:00", "2100-01-20 00:00:00", 0, 1000);
		
		System.out.println();
		System.out.println("Thing = " + thing);
		System.out.println();
		
		String[] things = thing.split("\n");
		
		//assertEquals("ANEMIA:1", things[0]);		
		//assertEquals("BRONCHITIS:2", things[1]);		
		//assertEquals("ECZEMA:3", things[2]);		
		//assertEquals("GINGIVITIS:4", things[3]);
		//assertEquals("MALARIA:5", things[4]);

		
	}
}

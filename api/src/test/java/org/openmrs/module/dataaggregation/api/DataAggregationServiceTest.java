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

	@Before
	public void setup() throws Exception {
		executeDataSet("DataAggregationDiseaseBurdenTestDataset.xml");
	}

	@Test
	public void testSchmig() {		
		
		String testsOrdered = "X-RAY, CHEST:CD4 PANEL";		
		
		String diseases = "MALARIA:SCABIES:ANEMIA:MENINGITIS, CRYPTOCOCCAL";
		
		DataAggregationService service = Context.getService(DataAggregationService.class);
		
		System.out.println();
		System.out.println("Thing = " + service.getDiseaseBurden(diseases, null, "1900-01-20 00:00:00", "2100-01-20 00:00:00", -1, -1));
		
		String thing = service.getDiseaseBurden(null, null, "1900-01-20 00:00:00", "2100-01-20 00:00:00", -1, -1);
		
		System.out.println("Thing = " + thing);
		System.out.println();
		
		String[] things = thing.split("\n");
		/*
		assertEquals("MALARIA:3", things[0]);
		
		assertEquals("SCABIES:2", things[1]);
		
		assertEquals("ANEMIA:4", things[2]);
		
		assertEquals("MENINGITIS, CRYPTOCOCCAL:1", things[3]);
		*/
		
		assertNotNull(Context.getService(DataAggregationService.class));
		
	}
}

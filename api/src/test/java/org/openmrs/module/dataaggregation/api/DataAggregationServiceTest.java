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
import org.openmrs.module.dataaggregation.api.impl.DataAggregationServiceImpl;
import org.openmrs.module.dataaggregation.api.impl.DataCounts;
import org.openmrs.test.BaseModuleContextSensitiveTest;
import org.openmrs.test.SkipBaseSetup;

/**
 * Tests {@link $ DataAggregationService}}.
 */

public class DataAggregationServiceTest extends BaseModuleContextSensitiveTest {

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
		assertNotNull(DataAggregationServiceImpl.class);	
	}
}

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
package org.openmrs.module.dataaggregation.web.controller;

import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.dataaggregation.api.DataAggregationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * The main controller.
 */
@Controller
public class  DataAggregationManageController {
	
	protected final Log log = LogFactory.getLog(getClass());
	
	@RequestMapping(value = "/module/dataaggregation/manage", method = RequestMethod.GET)
	public void manage(ModelMap model) {
		model.addAttribute("user", Context.getAuthenticatedUser());
	
		DataAggregationService service = Context.getService(DataAggregationService.class);
		model.addAttribute("patients", service.getAllPatientNames());
		
		DataAggregationService serv = Context.getService(DataAggregationService.class);
		HashMap<String, Integer> diseaseBurden = serv.getDiseaseBurden();//I have no idea why it gives this error, the method exists
		
		//convert the data to a string that is roughly csv	
		model.addAttribute("diseaseBurden", diseaseBurden.toString());
	}
	@RequestMapping(value = "/module/dataaggregation/names", method = RequestMethod.GET)
	@ResponseBody
	public Object names() {
		return Context.getService(DataAggregationService.class).getDiseaseBurden();	
	}
}

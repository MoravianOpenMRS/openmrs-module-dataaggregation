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

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.dataaggregation.api.DataAggregationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
		
		//model.addAttribute("patients", service.getAllPatientNames());
		
		// create a list that we had in the curl command and display to see which part is the problem
		LinkedList<String> diseases = new LinkedList<String>();
		diseases.add("hepatitis");
		diseases.add("pneumonia");
		diseases.add("measles");
		diseases.add("arthritis");
		diseases.add("gingivitis");
		
		model.addAttribute("diseases", service.getDiseaseCounts(diseases, "1900-01-20 00:00:00", "2100-01-20 00:00:00", 10 , 5000));
		//model.addAttribute("diseases", service.getDiseaseCounts(new LinkedList<String>(), "1900-01-20 00:00:00", "2100-01-20 00:00:00"));
		
		LinkedList<String> testsOrdered = new LinkedList<String>();
		testsOrdered.add("X-RAY, CHEST");
		testsOrdered.add("CD4 PANEL");		
		model.addAttribute("testsOrdered", service.getTestsOrdered(testsOrdered, "1900-01-20 00:00:00", "2100-01-20 00:00:00", -1, -1));
		
		model.addAttribute("weights", service.getWeights('M', 20, 30));

	}
	
	/*
	@RequestMapping(value = "/module/dataaggregation/diseasecounts", method = RequestMethod.GET)
	@ResponseBody
	public String names() {		
		//String toReturn = Context.getService(DataAggregationService.class).getDiseaseCounts(new LinkedList<String>(), startDate, endDate);		
		String toReturn = Context.getService(DataAggregationService.class).getDiseaseCounts(new LinkedList<String>(), "1900-01-20 00:00:00", "2100-01-20 00:00:00");
		return (toReturn);
	}
	*/
	
	@RequestMapping(value = "/module/dataaggregation/diseasecounts", method = RequestMethod.GET)
	@ResponseBody
	public String names(@RequestParam("diseaseList") String diseaseList, @RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate, 
						@RequestParam("minNumber") int minNumber, @RequestParam("maxNumber") int maxNumber) {
		
		List<String> diseases = Arrays.asList(diseaseList.split(":"));		
		String toReturn = Context.getService(DataAggregationService.class).getDiseaseCounts(diseases, startDate, endDate, minNumber , maxNumber);
		return (toReturn);
	}
	
	/*
	@RequestMapping(value = "/module/dataaggregation/diseasecounts", method = RequestMethod.GET)
	@ResponseBody
	public String names(@RequestParam("diseaseList") String diseaseList, @RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate) {		
		//String toReturn = Context.getService(DataAggregationService.class).getDiseaseCounts(diseaseList.split(":"), startDate, endDate);
		//return (toReturn);
		return "";
	}
	*/
	
	private String hashMapToCSV(HashMap<?,?> map){
		String toReturn = new String();
		for(Object val:map.keySet()){
			toReturn = toReturn + val + "," + map.get(val) + " \n";
		}
		return toReturn;
	}
}

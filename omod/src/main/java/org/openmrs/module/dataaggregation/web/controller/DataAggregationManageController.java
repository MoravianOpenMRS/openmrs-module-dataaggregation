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


import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.dataaggregation.api.DataAggregationService;
import org.openmrs.module.dataaggregation.api.impl.CSVFormatter;
import org.openmrs.module.dataaggregation.api.impl.JSONFormatter;
import org.openmrs.module.dataaggregation.api.impl.XMLFormatter;
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
				
		// create a list that we had in the curl command and display to see which part is the problem
		String diseases = "anemia:gastroenteritis:malaria";
		String cities = "ZiWa:YemIt:YeNga:YamuBi:WeSt Indies:WEt Indies";

		String testsOrdered = "X-RAY, CHEST:CD4 PANEL";		
		model.addAttribute("testsOrdered", CSVFormatter.formatTestsOrdered(service.getTestsOrdered(testsOrdered, "1900-01-20 00:00:00", "2100-01-20 00:00:00", -1, -1)));
		
		model.addAttribute("diseases", CSVFormatter.formatDiseaseBurden(service.getDiseaseBurden(diseases, null, "2006-01-01 00:00:00", "2006-02-20 00:00:00", null, null)));
		//model.addAttribute("diseases", service.getDiseaseBurden(diseases, null, "1900-01-20 00:00:00", "2100-01-20 00:00:00", 10, 5000));
		model.addAttribute("cities", CSVFormatter.formatDiseaseBurden(service.getDiseaseBurden(null, cities, "1900-01-20 00:00:00", "2100-01-20 00:00:00", -1, -1)));

		model.addAttribute("start", CSVFormatter.formatDiseaseBurden(service.getDiseaseBurden(diseases, null, null, "2100-01-20 00:00:00", -1, -1)));
		model.addAttribute("end", CSVFormatter.formatDiseaseBurden(service.getDiseaseBurden(diseases, null, "1900-01-20 00:00:00", null, -1, -1)));
		model.addAttribute("min", CSVFormatter.formatDiseaseBurden(service.getDiseaseBurden(diseases, null, "1900-01-20 00:00:00", "2100-01-20 00:00:00", null, -1)));
		model.addAttribute("max", CSVFormatter.formatDiseaseBurden(service.getDiseaseBurden(diseases, null, "1900-01-20 00:00:00", "2100-01-20 00:00:00", -1, null)));
		
		model.addAttribute("weights", CSVFormatter.formatWeights(service.getWeights('M', 25, 28)));


	}
	
	@RequestMapping(value = "/module/dataaggregation/diseasecounts", method = RequestMethod.GET)
	@ResponseBody
	public String diseases(@RequestParam(value = "diseaseList", required = false) String diseaseList, @RequestParam(value = "cityList", required = false) String cityList,
						@RequestParam(value = "startDate", required = false) String startDate, @RequestParam(value = "endDate", required = false) String endDate,
						@RequestParam(value = "minNumber", required = false) Integer minNumber, @RequestParam(value = "maxNumber", required = false) Integer maxNumber,
						@RequestParam(value = "format", required = false) String format) {
		List<Object> toReturn = Context.getService(DataAggregationService.class).getDiseaseBurden(diseaseList, cityList, startDate, endDate, minNumber , maxNumber);
		
		if(format == null){
			return JSONFormatter.formatDiseaseBurden(toReturn);
		}
		String form = format.toLowerCase();//make case insensitive
		if(form.equals("csv")){
			return CSVFormatter.formatDiseaseBurden(toReturn);
		}else if (form.equals("json")){
			return JSONFormatter.formatDiseaseBurden(toReturn);
		}else if (form.equals("xml")){
			return XMLFormatter.formatDiseaseBurden(toReturn);
		}
			return JSONFormatter.formatDiseaseBurden(toReturn);
	}

	@RequestMapping(value = "/module/dataaggregation/testsordered", method = RequestMethod.GET)
	@ResponseBody
	public String tests(@RequestParam(value = "testList", required = false) String diseaseList,
						@RequestParam(value = "startDate", required = false) String startDate, @RequestParam(value = "endDate", required = false) String endDate,
						@RequestParam(value = "minNumber", required = false) Integer minNumber, @RequestParam(value = "maxNumber", required = false) Integer maxNumber,
						@RequestParam(value = "format", required = false) String format) {
		
		List<Object> toReturn = Context.getService(DataAggregationService.class).getTestsOrdered(diseaseList, startDate, endDate, minNumber , maxNumber);

		if(format == null){
			return JSONFormatter.formatTestsOrdered(toReturn);
		}
		String form = format.toLowerCase();//make case insensitive
		if(form.equals("csv")){
			return CSVFormatter.formatTestsOrdered(toReturn);
		}else if (form.equals("json")){
			return JSONFormatter.formatTestsOrdered(toReturn);
		}else if (form.equals("xml")){
			return XMLFormatter.formatTestsOrdered(toReturn);
		}
			return JSONFormatter.formatTestsOrdered(toReturn);
	}
	
	@RequestMapping(value = "/module/dataaggregation/weights", method = RequestMethod.GET)
	@ResponseBody
	public String weights(@RequestParam(value = "gender", required = false) Character gender, 
							@RequestParam(value = "minAge", required = false) Integer minAge, @RequestParam(value = "maxAge", required = false) Integer maxAge,
							@RequestParam(value = "format", required = false) String format) {
		List<Object> toReturn = Context.getService(DataAggregationService.class).getWeights(gender, minAge, maxAge);
		
		
		if(format == null){
			return JSONFormatter.formatWeights(toReturn);
		}
		String form = format.toLowerCase();//make case insensitive
		if(form.equals("csv")){
			return CSVFormatter.formatWeights(toReturn);
		}else if (form.equals("json")){
			return JSONFormatter.formatWeights(toReturn);
		}else if (form.equals("xml")){
			return XMLFormatter.formatWeights(toReturn);
		}
			return JSONFormatter.formatWeights(toReturn);
	}
}

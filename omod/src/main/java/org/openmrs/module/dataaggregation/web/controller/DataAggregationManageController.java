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

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
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
				
		// create a list that we had in the curl command and display to see which part is the problem
		//String diseases = "hepatitis:pneumonia:measles:arthritis:gingivitis";	
		String diseases = "anemia:gastroenteritis:malaria";
		String cities = "Ziwa:Yemit:Yenga:Yamubi:West Indies:Wet Indies";
   
		String testsOrdered = "X-RAY, CHEST:CD4 PANEL";		
		model.addAttribute("testsOrdered", service.getTestsOrdered(testsOrdered, "1900-01-20 00:00:00", "2100-01-20 00:00:00", -1, -1));
		
		model.addAttribute("diseases", service.getDiseaseBurden(diseases, null, "2006-01-01 00:00:00", "2006-02-20 00:00:00", null, null));
		//model.addAttribute("diseases", service.getDiseaseBurden(diseases, null, "1900-01-20 00:00:00", "2100-01-20 00:00:00", 10, 5000));
		model.addAttribute("cities", service.getDiseaseBurden(null, null, "1900-01-20 00:00:00", "2100-01-20 00:00:00", -1, -1));

		model.addAttribute("start", service.getDiseaseBurden(diseases, null, null, "2100-01-20 00:00:00", -1, -1));
		model.addAttribute("end", service.getDiseaseBurden(diseases, null, "1900-01-20 00:00:00", null, -1, -1));
		model.addAttribute("min", service.getDiseaseBurden(diseases, null, "1900-01-20 00:00:00", "2100-01-20 00:00:00", null, -1));
		model.addAttribute("max", service.getDiseaseBurden(diseases, null, "1900-01-20 00:00:00", "2100-01-20 00:00:00", -1, null));
		
		model.addAttribute("weights", service.getWeights('M', 25, 28));


	}
	
	@RequestMapping(value = "/module/dataaggregation/diseasecounts", method = RequestMethod.GET)
	@ResponseBody
	public String diseases(@RequestParam(value = "diseaseList", required = false) String diseaseList, @RequestParam(value = "cityList", required = false) String cityList,
						@RequestParam(value = "startDate", required = false) String startDate, @RequestParam(value = "endDate", required = false) String endDate,
						@RequestParam(value = "minNumber", required = false) Integer minNumber, @RequestParam(value = "maxNumber", required = false) Integer maxNumber,
						@RequestParam(value = "format", required = false) String format) {
		
		String toReturn = Context.getService(DataAggregationService.class).getDiseaseBurden(diseaseList, cityList, startDate, endDate, minNumber , maxNumber);
		return selectFormat(format, toReturn);
	}

	@RequestMapping(value = "/module/dataaggregation/testsordered", method = RequestMethod.GET)
	@ResponseBody
	public String tests(@RequestParam(value = "testList", required = false) String diseaseList,
						@RequestParam(value = "startDate", required = false) String startDate, @RequestParam(value = "endDate", required = false) String endDate,
						@RequestParam(value = "minNumber", required = false) Integer minNumber, @RequestParam(value = "maxNumber", required = false) Integer maxNumber,
						@RequestParam(value = "format", required = false) String format) {
		
		String toReturn = Context.getService(DataAggregationService.class).getTestsOrdered(diseaseList, startDate, endDate, minNumber , maxNumber);
		return selectFormat(format, toReturn);
	}
	
	@RequestMapping(value = "/module/dataaggregation/weights", method = RequestMethod.GET)
	@ResponseBody
	public String weights(@RequestParam(value = "gender", required = false) Character gender, 
							@RequestParam(value = "minAge", required = false) Integer minAge, @RequestParam(value = "maxAge", required = false) Integer maxAge,
							@RequestParam(value = "format", required = false) String format) {
		
		String toReturn = Context.getService(DataAggregationService.class).getWeights(gender, minAge, maxAge);
		return selectFormat(format, toReturn);
	}
		
	private String selectFormat(String format, String toReturn){
		if(format == null){
			return toReturn;
		}else if(format.equals("CSV")){
			return toReturn;
		}else if (format.equals("JSON")){
			return convertToJSON(toReturn);
		}else if (format.equals("XML")){
			return convertToXML(toReturn);
		}
			return toReturn;
	}
	
	private String convertToJSON(String csvString) {
		JsonArrayBuilder table = Json.createArrayBuilder();
		String[] rows = csvString.split("\n");//split by rows
		
		for(String row:rows){
			JsonArrayBuilder jsonRow = Json.createArrayBuilder();
			String[] cols = row.split(":");//split by cols
			for(String col:cols){
				jsonRow.add(col);
			}
			table.add(jsonRow.build());
		}
		JsonArray toReturn = table.build();
		return toReturn.toString();		
	}

	private String convertToXML(String csvString){
		String [] rows = csvString.split("\n");
		Element table = new Element("table");
		Document doc = new Document(table);
		int i = 0;
		while(i < rows.length){
			Element row = new Element("row" + i);
			String [] cols = rows[i].split(":");
			int j =0;
			for(String rowS:cols){
				row.setAttribute(new Attribute("col" + j,rowS));
				j++;
			}
						
			doc.getRootElement().addContent(row);
			
			i++;
		}

		XMLOutputter xmlOutput = new XMLOutputter();
		xmlOutput.setFormat(Format.getPrettyFormat());
		return xmlOutput.outputString(doc);
	}
}

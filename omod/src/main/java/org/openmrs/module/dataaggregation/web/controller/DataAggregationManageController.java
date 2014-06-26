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



import org.jfree.chart.JFreeChart;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import java.io.File;
import java.io.IOException;

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
		
		model.addAttribute("diseases", service.getDiseaseCounts(diseases, "1900-01-20 00:00:00", "2100-01-20 00:00:00", 10, 5000));
		
		LinkedList<String> testsOrdered = new LinkedList<String>();
		testsOrdered.add("X-RAY, CHEST");
		testsOrdered.add("CD4 PANEL");
		
		model.addAttribute("testsOrdered", service.getTestsOrdered(testsOrdered, "1900-01-20 00:00:00", "2100-01-20 00:00:00"));
		
		model.addAttribute("weights", service.getWeights());

		DefaultCategoryDataset diseaseDataset = new DefaultCategoryDataset();
		String diseaseData = service.getDiseaseCounts(new LinkedList<String>(), "1900-01-20 00:00:00", "2100-01-20 00:00:00", 100, 5000);
		
		String[] list = diseaseData.split("\n");

		for (String s : list) {			
			String[] vals = s.split(":");		
			diseaseDataset.setValue(Integer.valueOf(vals[1]), "Count", vals[0]);
		}
		
		JFreeChart chart1 = ChartFactory.createBarChart("Disease comparison", "Disease", "Count", diseaseDataset, PlotOrientation.VERTICAL, false, true, false);
		
		try {
			ChartUtilities.saveChartAsJPEG(new File("/Users/openmrs/workspace/openmrs-module-dataaggregation/omod/src/main/resources/diseaseChart.jpg"), chart1, 500, 300);
		} 
		catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		

		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		dataset.setValue(6, "Profit", "Jane");
		dataset.setValue(7, "Profit", "Tom");
		dataset.setValue(8, "Profit", "Jill");
		dataset.setValue(5, "Profit", "John");
		dataset.setValue(12, "Profit", "Fred");
		JFreeChart chart = ChartFactory.createBarChart("Comparison between Salesman", "Salesman", "Profit", dataset, PlotOrientation.VERTICAL, false, true, false);
		
		try {
			//org.openmrs.module.dataaggregation
			ChartUtilities.saveChartAsJPEG(new File("/Users/openmrs/workspace/openmrs-module-dataaggregation/omod/src/main/resources/chart.jpg"), chart, 500, 300);
		} 
		catch (IOException e) {
			System.err.println("Problem occurred creating chart.");
			e.printStackTrace();
		}		

		//DataAggregationService serv = Context.getService(DataAggregationService.class);
		//HashMap<String, Integer> diseaseBurden = serv.getDiseaseBurden();
		
		//convert the data to a string that is roughly csv	
		//model.addAttribute("diseaseBurden", diseaseBurden.toString());
	}
	
	@RequestMapping(value = "/module/dataaggregation/diseasecounts", method = RequestMethod.GET)
	@ResponseBody
	public String names(@RequestParam(value = "diseaseList", required = false) String diseaseList,
						@RequestParam(value = "startDate", required = false) String startDate, @RequestParam(value = "endDate", required = false) String endDate,
						@RequestParam(value = "minNumber", required = false) int minNumber, @RequestParam(value = "maxNumber", required = false) int maxNumber) {
		List<String> diseases = Arrays.asList(diseaseList.split(":"));
		endDate = "2100-01-20 00:00:00";
		String toReturn = Context.getService(DataAggregationService.class).getDiseaseCounts(diseases, startDate, endDate, minNumber , maxNumber);
		return (toReturn);
	}
	
	private String hashMapToCSV(HashMap<?,?> map){
		String toReturn = new String();
		for(Object val:map.keySet()){
			toReturn = toReturn + val + "," + map.get(val) + " \n";
		}
		return toReturn;
	}
}

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

import java.util.HashMap;
import java.util.List;

import org.jfree.chart.JFreeChart;
import org.openmrs.api.OpenmrsService;
import org.springframework.transaction.annotation.Transactional;

/**
 * This service exposes module's core functionality. It is a Spring managed bean which is configured in moduleApplicationContext.xml.
 * <p>
 * It can be accessed only via Context:<br>
 * <code>
 * Context.getService(DataAggregationService.class).someMethod();
 * </code>
 * 
 * @see org.openmrs.api.context.Context
 */
@Transactional
public interface DataAggregationService extends OpenmrsService {
     
	/**
	 * Returns all patient names in the database
	 * @return a string containing all the patient names in the format "firstName lastName \n"
	 */
	public String getAllPatientNames();
	
	/**
	 * This method returns a string containing the count of the desired disease.
	 * @param diseaseList a string in the format "desiredDisease1:desiredDisease2:...:desiredDiseaseN"
	 * 					This method will only list the results of the disease specified in the string.
	 * 					If this parameter is null, then all the diseases will be included in the result.
	 * @param cityList a string in the format "desiredCity1:desiredCity2:...:desiredCityN"
	 * 					This method will only list the results of the diseases in the city specified in the string.
	 * 				   	If the parameter in null, then all the diseases will be included in the result.
	 * @param startDate a string in the format "YYYY-MM-DD HH:MM:SS" for example : "2006-01-30 00:00:00"
	 * 					This string bounds the query only to tests ordered after a specific date (inclusive or exclusive?).
	 * 					If this parameter is null, then no lower bound will exist.
	 * @param endDate a string in the format "YYYY-MM-DD HH:MM:SS" for example : "2006-01-30 00:00:00"
	 * 					This string bounds the query only to tests ordered before a certain date (inclusive or exclusive?).
	 * 					If this parameter is null, the no upper bound will exist.
	 * @param minNumber a positive integer
	 * 					This integer bounds the query only to tests ordered at least a certain amount of times (inclusive or exclusive?).
	 * 					If this parameter is null or negative, there will be no lower bound.
	 * @param maxNumber a positive integer
	 * 					This integer bounds the query only to tests ordered less than a certain amount of times (inclusive or exclusive?).
	 * 					If this parameter is null or negative, there will be no upper bound.
	 * @return a string in the format "diseaseName:count \n diseaseName:count \n"
	 * 					if there are no results the string will be empty
	 */
	public String getDiseaseBurden(String diseaseList, String cities, String startDate, String endDate, Integer minNumber, Integer maxNumber);
	
	/**
	 * This method returns a string containing the count of the desired tests.
	 * @param testList a string in the format "desiredTest0:desiredTest1:...:desiredTestN"
	 * 					This method will only list the results of the test specified in this string.
	 * 					If this parameter is null, then all tests will be included in the result.
	 * @param startDate a string in the format "YYYY-MM-DD HH:MM:SS" for example : "2006-01-30 00:00:00"
	 * 					This string bounds the query only to tests ordered after a specific date (inclusive or exclusive?).
	 * 					If this parameter is null, then no lower bound will exist.
	 * @param endDate a string in the format "YYYY-MM-DD HH:MM:SS" for example : "2006-01-30 00:00:00"
	 * 					This string bounds the query only to tests ordered before a certain date (inclusive or exclusive?).
	 * 					If this parameter is null, the no upper bound will exist.
	 * @param minNumber a positive integer
	 * 					This integer bounds the query only to tests ordered at least a certain amount of times (inclusive or exclusive?).
	 * 					If this parameter is null or negative, there will be no lower bound.
	 * @param maxNumber a positive integer
	 * 					This integer bounds the query only to tests ordered less than a certain amount of times (inclusive or exclusive?).
	 * 					If this parameter is null or negative, there will be no upper bound.
	 * @return a string in the format "testName:count \n testName:count \n"
	 * 					if there are no results the string will be empty
	 */
	public String getTestsOrdered(String testsOrderedList, String startDate, String endDate, Integer minNumber, Integer maxNumber);

	/**
	 * This method returns a string containing the weight of people in a desired age group and/or gender.
	 * @param gender 
	 * @param minAge a positive integer
	 * 				This integer bounds the query only to weights of people at least a certain age (inclusive or exclusive?).
	 * 				If this parameter is null or negative, there will be no lower age bound. 
	 * @param maxAge a positive integer
	 * 				This integer bounds the query only to weights of people at a maximum age (inclusive or exclusive?).
	 * 				If this parameter is null or negative, there will be no upper age bound.
	 * @return a string in the format "personID:weight"
	 * 				If there are no results the string will be empty
	 */
	public String getWeights(Character gender, Integer minAge, Integer maxAge);
	 
	public HashMap<String, Integer> getDiseaseBurden();
}
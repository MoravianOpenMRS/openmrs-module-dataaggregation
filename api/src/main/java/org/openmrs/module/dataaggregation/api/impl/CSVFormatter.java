package org.openmrs.module.dataaggregation.api.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.SQLQuery;
import org.hibernate.Session;

public class CSVFormatter {

	public static String formatDiseaseBurden(List<Object> results){
	
		// vals[1] is the name of the disease
		// vals[2] is the count for the disease
		// vals[0] is just the concept_id of the disease
				
		Map<String, Integer> fieldNames = new LinkedHashMap<String,Integer>();
		fieldNames.put("diseaseName", 1);
		fieldNames.put("count", 2);
				
		return formatCounts(results, fieldNames);
    }
	
	public static String formatTestsOrdered(List<Object> data){
		// vals[1] is the name of the test
		// vals[2] is the count for the test
		// vals[0] is just the concept_id of the test which we may or may not need but that is why vals[0] is not used here
		
		Map<String, Integer> fieldNames = new LinkedHashMap<String,Integer>();
		fieldNames.put("testName", 1);
		fieldNames.put("count", 2);
		
		return formatCounts(data, fieldNames);
			
	}
	public static String formatWeights(List<Object> data){
		// vals[0] is the person id.
					// vals[1] is the gender.
					// vals[2] is the birthdate.
					// vals[3] is the weight in KG.
					// vals[4] is the observation/encounter datetime (representing the most recent encounter,
					// due to the MAX() function, allowing for patient's current weight.
		Map<String, Integer> fieldNames = new LinkedHashMap<String,Integer>();
		fieldNames.put("personId", 0);
		fieldNames.put("weightKG", 3);
		
		return formatCounts(data,fieldNames);
		
	}
	
	private static String formatCounts(List<Object> data, Map<String,Integer> fieldNames){
		StringBuilder resultString = new StringBuilder();
		for(String fieldName: fieldNames.keySet()){//format the column names
			resultString.append(fieldName + ":");
		}
		resultString.deleteCharAt(resultString.lastIndexOf(":"));//remove the extra colon
		resultString.append("\n");
		
		for(Object row : data){//add in all the data rows
			Object[] columns = (Object[]) row;
			for(String fieldName: fieldNames.keySet()){//add in all the columns
				resultString.append(columns[fieldNames.get(fieldName)] + ":");
			}
			resultString.deleteCharAt(resultString.lastIndexOf(":"));//remove the extra colon
			resultString.append("\n");
		}
		return resultString.toString();
	}
}

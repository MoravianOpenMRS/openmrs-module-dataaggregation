package org.openmrs.module.dataaggregation.api.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;

public class JSONFormatter {
	
	public static String formatDiseaseBurden(List<Object> data){
		// vals[1] is the name of the disease
		// vals[2] is the count for the disease
		// vals[0] is just the concept_id of the disease
		
		Map<String, Integer> fieldNames = new LinkedHashMap<String,Integer>();
		fieldNames.put("diseaseName", 1);
		fieldNames.put("count", 2);
		
		return formatCounts(data, fieldNames);
		
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
		JsonObjectBuilder table = Json.createObjectBuilder();
		JsonArrayBuilder results = Json.createArrayBuilder();
		for(Object row: data){
			Object[] columns = (Object[]) row;
			JsonObjectBuilder outputRow = Json.createObjectBuilder();
			for(String value:fieldNames.keySet()){
				outputRow.add(value, columns[fieldNames.get(value)].toString());
			}
			results.add(outputRow.build());
		}
		table.add("Entries", results.build());
		return table.build().toString();
	}
}

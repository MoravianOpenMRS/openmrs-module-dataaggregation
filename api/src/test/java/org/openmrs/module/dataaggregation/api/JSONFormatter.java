package org.openmrs.module.dataaggregation.api;

import java.util.HashMap;
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
		fieldNames.put("DiseaseName", 1);
		fieldNames.put("Count", 2);
		
		return formatCounts(data, fieldNames);
		
	}
	public static String formatTestsOrdered(List<Object> data){
		// vals[1] is the name of the test
		// vals[2] is the count for the test
		// vals[0] is just the concept_id of the test which we may or may not need but that is why vals[0] is not used here
		
		Map<String, Integer> fieldNames = new LinkedHashMap<String,Integer>();
		fieldNames.put("TestName", 1);
		fieldNames.put("Count", 2);
		
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
		fieldNames.put("PersonId", 0);
		fieldNames.put("WeightKG", 3);
		
		return formatCounts(data,fieldNames);
		
	}
	
	private static String formatCounts(List<Object> data, Map<String,Integer> fieldNames){
		JsonObjectBuilder table = Json.createObjectBuilder();
		JsonArrayBuilder results = Json.createArrayBuilder();
		for(Object row: data){
			Object[] columns = (Object[]) row;
			for(String value:fieldNames.keySet()){
				JsonObjectBuilder outputRow = Json.createObjectBuilder();
				outputRow.add(value, columns[fieldNames.get(value)].toString());
				results.add(outputRow.build());
			}
		}
		table.add("Entries", results.build());
		return table.build().toString();
	}
	
	/**
	 * Converts a csv file to JSON
	 * @param csvString a string that is a csv file separated by colons
	 * @return a string is a JSON file
	 */
	public String convertToJSON(String csvString) {
		JsonArrayBuilder table = Json.createArrayBuilder();
		String[] rows = csvString.split("\n");//split by rows
		String[] fieldNames = rows[0].split(":");
		for(int j = 1; j < rows.length; j++){
			JsonArrayBuilder jsonRow = Json.createArrayBuilder();
			String[] cols = rows[j].split(":");//split by cols
			int i = 0;
			for(String col:cols){
				jsonRow.add(fieldNames[i] + "=" + col);
				i++;
			}
			table.add(jsonRow.build());
		}
		JsonArray toReturn = table.build();
		return toReturn.toString();		
	}
}

package org.openmrs.module.dataaggregation.api;

import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;

public class CSVFormatter {

	public static String formatDiseaseBurden(List<Object> results){
	
		StringBuilder resultString = new StringBuilder();
    	resultString.append("testName:count\n");
		// Each object in results is another record from our SQL statement
		for (Object o : results) {
			// Cast each object into an array where each column is another index into the array
			Object[] vals = (Object[]) o;
			// vals[1] is the name of the disease
			// vals[2] is the count for the disease
			// vals[0] is just the concept_id of the disease which we may or may not need but that is why vals[0] is not used here
			resultString.append(vals[1] + ":" + vals[2] + "\n");
		}
		
    	return resultString.toString();
    }
	
	public static String formatTestsOrdered(List<Object> results){
		
		StringBuilder resultString = new StringBuilder();
    	resultString.append("diseaseName:count\n");
		// Each object in results is another record from our SQL statement
		for (Object o : results) {
			// Cast each object into an array where each column is another index into the array
			Object[] vals = (Object[]) o;
			// vals[1] is the name of the test
			// vals[2] is the count for the test
			// vals[0] is just the concept_id of the test which we may or may not need but that is why vals[0] is not used here
			resultString.append(vals[1] + ":" + vals[2] + "\n");
		}
		
    	return resultString.toString();
	}
	
	public static String formatWeights(List<Object> results){
		
		StringBuilder resultString = new StringBuilder();
    	resultString.append("personId:personWeight(KG)\n");
		// Each object in results is another record from our SQL statement
		for (Object o : results) {
			// Cast each object into an array where each column is another index into the array
			Object[] vals = (Object[]) o;
			// vals[0] is the person id.
			// vals[1] is the gender.
			// vals[2] is the birthdate.
			// vals[3] is the weight in KG.
			// vals[4] is the observation/encounter datetime (representing the most recent encounter,
			// due to the MAX() function, allowing for patient's current weight.
			resultString.append(vals[0] + ":" + vals[3] + "(KG)" + "\n");
		}
		
    	return resultString.toString();
	}
	
	private static String format
}

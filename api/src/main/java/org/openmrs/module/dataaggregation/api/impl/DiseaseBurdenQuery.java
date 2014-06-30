package org.openmrs.module.dataaggregation.api.impl;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.openmrs.module.dataaggregation.api.db.DataAggregationDAO;

public class DiseaseBurdenQuery {
	
	private static final String  default_start_date = "1900-01-20 00:00:00";
	private static final String  default_end_date   = "2100-01-20 00:00:00";
	private static final Integer default_min_number = -1;
	private static final Integer default_max_number = -1;	
	
	private DataAggregationDAO dao;
	
	public DiseaseBurdenQuery(DataAggregationDAO dao) {
		this.dao = dao;
	}
	
	
	public String getQueryInfo(String diseaseList, String cityList, 
									String startDate, String endDate, 
									Integer minNumber, Integer maxNumber) {
		
		List<String> diseases;		
		if (diseaseList == null) {
			diseases = new LinkedList<String>(); // default: get all diseases
		}
		else {
			diseases = Arrays.asList(diseaseList.split(":"));		
		}
		
		List<String> cities;		
		if (cityList == null) {
			cities = new LinkedList<String>(); // default: get all cities 
		}
		else {
			cities = Arrays.asList(cityList.split(":"));
		}
		
		if (minNumber == null) {
			minNumber = default_min_number; // default: no min on number of diseases 
		}
		
		if (maxNumber == null) { 
			maxNumber = default_max_number; // default: no max on number of diseases 
		}
		
		if (startDate == null) { 
			startDate = default_start_date; // default: time before everything
		}
		
		if (endDate == null) { 
			endDate = default_end_date; // default: time after everything
		}
		
		return getDiseaseCounts(diseases, cities, startDate, endDate, minNumber , maxNumber);
	}
	

	 /**
     * 
     */
    private String getDiseaseCounts(List<String> diseaseList, List<String> cities, 
    								String startDate, String endDate, 
    								Integer minNumber, Integer maxNumber) {   	
    	
    	
    	Session session = dao.getSessionFactory().openSession();
    	
    	// This code is to get the concept_id number that corresponds to PROBLEM ADDED concept
    	// For our instances the concept_id number is always 6042 but if a different concept map was used that number could change
    	String problem_Query = "SELECT concept_id FROM concept_name WHERE name='PROBLEM ADDED'";   	
    	SQLQuery problem_q = session.createSQLQuery(problem_Query);
    	
    	@SuppressWarnings("unchecked")
		List<Object> code_list = problem_q.list();
    	// The num_coded does not need to be gotten out from the list through indexing because the SQL statement returns one record with one column
    	int num_coded = (Integer) code_list.get(0);
    	
    	// This is the HQL statement that is used with the database in order to get the data we want
    	StringBuilder SQL_Query = new StringBuilder();
    	
    	SQL_Query.append("SELECT o.value_coded, c.name, count(*) "); // columns we want to have
    	SQL_Query.append("FROM obs o, concept_name c "); // tables we need to join together
    	
    	if (cities.size() != 0) { // make sure they specify locations
    		SQL_Query.append(", person_address pa "); // only check where people come from one particular city
    	}
    	
    	SQL_Query.append("WHERE o.value_coded = c.concept_id "); // want the names of the concepts
    	
    	if (cities.size() != 0) { // make sure they specify locations
    		SQL_Query.append("AND o.person_id=pa.person_id "); // get the addresses of the people with the observations
    	}    	
    	
    	SQL_Query.append("AND o.concept_id = :coded_id "); // only get observations that have the PROBLEM ADDED concept (concept_id = 6042)
    	SQL_Query.append("AND c.concept_name_type = 'FULLY_SPECIFIED' "); // this prevents the repeats in the concept_name table (multiple names for same concept_id)    				
    	SQL_Query.append("AND ( o.obs_datetime BETWEEN :start_date AND :end_date ) "); // specifies the date range that we want
    	
    	
    	// This is dealing with going through a list of cities to only include the specified ones
    	int count = 0;    	
    	for (String city : cities) {
    		if (count == 0) {
    			SQL_Query.append("AND (pa.city_village = '" + city + "' "); // add the first disease with ( at beginning 
    			count = 1;
    		}
    		else {
    			SQL_Query.append(" OR pa.city_village = '" + city + "' "); // add the next disease
    		}
    	}    	
    	if (count == 1) {
    		SQL_Query.append(") "); // only add the ending ) if there was a starting one
    	}   	

    	// This is dealing with going through a list of diseases to only include the specified ones
    	count = 0;    	
    	for (String disease : diseaseList) {
    		if (count == 0) {
    			SQL_Query.append("AND (c.name = '" + disease + "'"); // add the first disease with ( at beginning 
    			count = 1;
    		}
    		else {
    			SQL_Query.append(" OR c.name = '" + disease + "'"); // add the next disease
    		}
    	}    	
    	if (count == 1) {
    		SQL_Query.append(") "); // only add the ending ) if there was a starting one
    	}    	
    	
    	
    	SQL_Query.append("GROUP BY o.value_coded "); // group by the value_coded (disease)
    	
    	if (minNumber > -1 && maxNumber > -1) {
    		SQL_Query.append("HAVING COUNT(*) BETWEEN " + minNumber + " AND " + maxNumber); // if they specify an upper AND lower bound
    	}
    	else if (minNumber > -1) {
    		SQL_Query.append("HAVING COUNT(*) >= " + minNumber); // if they just want diseases above a certain number
    	}
    	else if (maxNumber > -1) {
    		SQL_Query.append("HAVING COUNT(*) <= " + maxNumber); // if they only want diseases below a certain number
    	}
    	
    	
    	
		System.out.println();
		System.out.println("###########################");
		System.out.println("Setting the end_date");
		System.out.println();
		
		System.out.println("Query is: " + SQL_Query);
		
		System.out.println();
		System.out.println("Setting the end_date");
		System.out.println("###########################");		
		System.out.println();
    	
    	
		SQLQuery query = session.createSQLQuery(SQL_Query.toString());

		// This sets the parameter coded_id to whatever we got from the number above (should be 6042)	
		query.setParameter("coded_id", num_coded);	
		query.setParameter("start_date", startDate);
		query.setParameter("end_date", endDate);
		
		
		@SuppressWarnings("unchecked")
		// This gets the list of records from our SQL statement each record is a row in the table
		List<Object> results = query.list();

		StringBuilder resultString = new StringBuilder();
		
		// Each object in results is another record from our SQL statement
		for (Object o : results) {
			// Cast each object into an array where each column is another index into the array
			Object[] vals = (Object[]) o;
			// vals[1] is the name of the disease
			// vals[2] is the count for the disease
			// vals[0] is just the concept_id of the disease which we may or may not need and that is why vals[0] is not used here
			resultString.append(vals[1] + ":" + vals[2] + "\n");
		}
		
    	return resultString.toString();
    }
}

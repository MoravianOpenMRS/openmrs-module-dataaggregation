package org.openmrs.module.dataaggregation.api.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.openmrs.module.dataaggregation.api.db.DataAggregationDAO;

public class TestsOrderedQuery extends DataAggregationQuery {
	
	private static final String  default_start_date = "0000-00-00 00:00:00";
	private static final Integer default_min_number = -1;
	private static final Integer default_max_number = -1;	
	
	public TestsOrderedQuery(DataAggregationDAO dao) {
		super(dao);
	}	
	
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
	public List<Object> getQueryInfo(String testList, String cityList, String 
										startDate, String endDate, 
										Integer minNumber, Integer maxNumber) {

		List<String> tests;		
		if (testList == null) {
			tests = new LinkedList<String>(); // default: get all diseases
		}
		else {
			tests = Arrays.asList(testList.split(":"));		
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
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date date = new Date();
			endDate = dateFormat.format(date); // default: current date
		}
	
		return getTestsOrdered(tests, cities, startDate, endDate, minNumber , maxNumber);
	}
	

	@SuppressWarnings("unchecked")
	private List<Object> getTestsOrdered(List<String> testsOrderedList, List<String> cities, String startDate, String endDate, Integer minNumber, Integer maxNumber) {
	    	
			// Open the Hibernate session
	    	Session session = dao.getSessionFactory().openSession();

	    	// get the concept_id of the concept "TESTS ORDERED"
			int num_coded = getConceptIdOfKeyWord("TESTS ORDERED");
	    	
	    	// This is the HQL statement that is used with the database in order to get the data we want
	    	StringBuilder SQL_Query = new StringBuilder();
	    	
	    	SQL_Query.append("SELECT o.value_coded, c.name, count(*) ");
	    	SQL_Query.append("FROM obs o, concept_name c ");
	    	
	    	if (cities.size() != 0) { // make sure they specify locations
	    		SQL_Query.append(", person_address pa "); // only check where people come from one particular city
	    	}
	    	
	    	SQL_Query.append("WHERE o.value_coded = c.concept_id "); // want the names of the concepts ON
	    	
	    	if (cities.size() != 0) { // make sure they specify locations
	    		SQL_Query.append("AND o.person_id=pa.person_id "); // get the addresses of the people with the observations
	    	}  
	    	
	    	SQL_Query.append("AND o.concept_id = :coded_id ");
	    	SQL_Query.append("AND c.concept_name_type = 'FULLY_SPECIFIED' ");	    	
	    	SQL_Query.append("AND (o.obs_datetime BETWEEN  :start_date  AND  :end_date ) ");
	    	
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
	    	
	    	count = 0;	    	
	    	for (String testOrdered : testsOrderedList) {
	    		if (count == 0) {
	    			SQL_Query.append("AND (c.name = '" + testOrdered + "'");
	    			count = 1;
	    		}
	    		else {
	    			SQL_Query.append(" OR c.name = '" + testOrdered + "'"); 
	    		}
	    	}
	    	
	    	if (count == 1) {
	    		SQL_Query.append(") ");
	    	}
	    	
	    	//SQL_Query.append("GROUP BY o.value_coded ");
	    	SQL_Query.append("GROUP BY c.name ");
	    	
	    	if (minNumber != -1 && maxNumber != -1){
	    		SQL_Query.append("HAVING COUNT(*) BETWEEN " + minNumber + "AND " + maxNumber);
	    	}
			else if (minNumber > -1) {
				SQL_Query.append("HAVING COUNT(*) >= " + minNumber);
			}
			else if (maxNumber > -1) {
				SQL_Query.append("HAVING COUNT(*) <= " + maxNumber);
			}
	    	
	    	SQL_Query.append(" ORDER BY c.name ");
	    	
	    	SQLQuery query = session.createSQLQuery(SQL_Query.toString());	    	
	    	
	    	// This sets the parameter coded_id to whatever we got from the number above (should be 1271)
	    	query.setParameter("coded_id", num_coded);
	    	query.setParameter("start_date", startDate);
	    	query.setParameter("end_date", endDate);
	    	
	    	
			// This gets the list of records from our SQL statement each record is a row in the table
	    	List<Object> results = (List<Object>)query.list();
			
	    	// Close the Hibernate session - VERY IMPORTANT
	    	session.close();
			
			return results;
	    }
}

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

public class DiseaseBurdenQuery extends DataAggregationQuery {
	
	private static final String  default_start_date =  "0000-01-01 00:00:00";
	private static final Integer default_min_number = -1;
	private static final Integer default_max_number = -1;	
		
	public DiseaseBurdenQuery(DataAggregationDAO dao) {
		super(dao);
	}

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
	 * @return a List<Obect> It is the list of records coming back from the database, each object representing a row in the table which
	 * 						index 1 is the name of the disease, index 2 is the count for the disease, index 0 is the concept_id of the disease
	 */
	public List<Object> getQueryInfo(String diseaseList, String cityList, 
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
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = new Date();
			endDate = dateFormat.format(date); // default: current date
		}
		
		return getDiseaseCounts(diseases, cities, startDate, endDate, minNumber , maxNumber);
	}
	

	 /**
     * 
     */
	@SuppressWarnings("unchecked")
	private List<Object> getDiseaseCounts(List<String> diseaseList, List<String> cities, 
    								String startDate, String endDate, 
    								Integer minNumber, Integer maxNumber) {   	
    	
    	// Open the Hibernate session
    	Session session = dao.getSessionFactory().openSession();
    	
    	int num_coded = getConceptIdOfKeyWord("PROBLEM ADDED");
    	
    	// This is the HQL statement that is used with the database in order to get the data we want
    	StringBuilder SQL_Query = new StringBuilder();
    	
    	SQL_Query.append("SELECT o.value_coded, c.name, count(*) "); // columns we want to have
    	SQL_Query.append("FROM obs o, concept_name c "); // tables we need to join together JOIN
    	
    	if (cities.size() != 0) { // make sure they specify locations
    		SQL_Query.append(", person_address pa "); // only check where people come from one particular city
    	}
    	
    	SQL_Query.append("WHERE o.value_coded = c.concept_id "); // want the names of the concepts ON
    	
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
    	
    	
    	//SQL_Query.append("GROUP BY o.value_coded "); // group by the value_coded (disease)
    	SQL_Query.append("GROUP BY c.name "); // this makes it possible to test apparently grouping by the value_coded does not allow for testing
    	
    	
    	if (minNumber > -1 && maxNumber > -1) {
    		SQL_Query.append("HAVING COUNT(*) BETWEEN " + minNumber + " AND " + maxNumber); // if they specify an upper AND lower bound
    	}
    	else if (minNumber > -1) {
    		SQL_Query.append("HAVING COUNT(*) >= " + minNumber); // if they just want diseases above a certain number
    	}
    	else if (maxNumber > -1) {
    		SQL_Query.append("HAVING COUNT(*) <= " + maxNumber); // if they only want diseases below a certain number
    	}    	
    	
    	SQL_Query.append(" ORDER BY c.name "); // this way the diseases are sorted alphabetically 
    	
		SQLQuery query = session.createSQLQuery(SQL_Query.toString());

		// This sets the parameter coded_id to whatever we got from the number above (should be 6042)	
		query.setParameter("coded_id", num_coded);	
		query.setParameter("start_date", startDate);
		query.setParameter("end_date", endDate);
		
		// The list method returns a List (without the generic specified).  We cast to the desired return
		// type.  Note the "suppressWarning" command on this method.
		List<Object> results = (List<Object>)query.list();
		
    	// Close the Hibernate session - VERY IMPORTANT
		session.close();
		
		return results;
    }
}

package org.openmrs.module.dataaggregation.api.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.openmrs.module.dataaggregation.api.db.DataAggregationDAO;

public class WeightQuery extends DataAggregationQuery {

	private static final Character default_gender  = 'M';
	private static final Integer   default_min_age = -1;
	private static final Integer   default_max_age = -1;
	
	public WeightQuery(DataAggregationDAO dao) {
		super(dao);
	}
	
	
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
	public String getQueryInfo(Character gender, Integer minAge, Integer maxAge) {
		
		if (gender == null) { 
			gender = default_gender;
		}
		
		if (minAge == null) {
			minAge = default_min_age;
		}
		
		if (maxAge == null) {
			maxAge = default_max_age;
		}
		
		return getWeights(gender, minAge, maxAge);
	}
	
	
    private String getWeights(Character gender, Integer minAge, Integer maxAge) {
    	
    	Session session = dao.getSessionFactory().openSession();
    	
    	int num_coded = getConceptIdOfKeyWord("WEIGHT (KG)");
    	
    	// This is the HQL statement that is used with the database in order to get the data we want
    	StringBuilder SQL_Query = new StringBuilder();
    	
    	SQL_Query.append("SELECT o.person_id, p.gender, p.birthdate, o.value_numeric, o.obs_datetime ");
    	SQL_Query.append("FROM obs o, person p ");
    	SQL_Query.append("WHERE o.person_id = p.person_id ");
    	SQL_Query.append("AND p.gender = :gender ");
    	SQL_Query.append("AND o.concept_id = :coded_id ");
    	SQL_Query.append("AND o.obs_datetime = (SELECT MAX(obs_datetime) FROM obs AS obs1 WHERE obs1.person_id = o.person_id) ");
    	
    	// Formats the birthdate field based on given age
    	DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
    	Calendar minCal = Calendar.getInstance();
    	Calendar maxCal = Calendar.getInstance();
    	
    	if (minAge != -1 && maxAge != -1){
    		minCal.add(Calendar.YEAR, -minAge);
        	maxCal.add(Calendar.YEAR, -maxAge);
        	SQL_Query.append("AND p.birthdate BETWEEN '" + dateFormat.format(maxCal.getTime()) + "' AND '" + dateFormat.format(minCal.getTime()) + "' ");
    	}
		else if (minAge > -1) {
			minCal.add(Calendar.YEAR, -minAge);
			SQL_Query.append("AND p.birthdate >= '" + dateFormat.format(minCal.getTime()) + "' ");
		}
		else if (maxAge > -1) {
			maxCal.add(Calendar.YEAR, -maxAge);
			SQL_Query.append("AND p.birthdate <= '" + dateFormat.format(maxCal.getTime()) + "' ");
		}
    	
    	SQLQuery query = session.createSQLQuery(SQL_Query.toString());
    	
    	query.setParameter("coded_id", num_coded);
    	query.setParameter("gender", gender);
    	
    	@SuppressWarnings("unchecked")
		// This gets the list of records from our SQL statement each record is a row in the table
		List<Object> results = query.list();
    	
    	StringBuilder resultString = new StringBuilder();
		
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
			resultString.append(vals[0] + ": " + vals[3] + "(KG)" + "\n");
		}
		
    	return resultString.toString();
    }
}

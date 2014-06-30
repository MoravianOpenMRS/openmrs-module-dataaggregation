package org.openmrs.module.dataaggregation.api.impl;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.openmrs.module.dataaggregation.api.db.DataAggregationDAO;

public class TestsOrderedQuery extends DataAggregationQuery {
	
	private static final String  default_start_date = "1900-01-20 00:00:00";
	private static final String  default_end_date   = "2100-01-20 00:00:00";
	private static final Integer default_min_number = -1;
	private static final Integer default_max_number = -1;	
	
	public TestsOrderedQuery(DataAggregationDAO dao) {
		super(dao);
	}	
	
	
	public String getQueryInfo(String testList,
								String startDate, String endDate, 
								Integer minNumber, Integer maxNumber) {

		List<String> tests;		
		if (testList == null) {
			tests = new LinkedList<String>(); // default: get all diseases
		}
		else {
			tests = Arrays.asList(testList.split(":"));		
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
	
		return getTestsOrdered(tests, startDate, endDate, minNumber , maxNumber);
	}
	
	
	private String getTestsOrdered(List<String> testsOrderedList, String startDate, String endDate, Integer minNumber, Integer maxNumber) {
	    	
	    	Session session = dao.getSessionFactory().openSession();

	    	// get the concept_id of the concept "TESTS ORDERED"
			int num_coded = getConceptIdOfKeyWord("TESTS ORDERED");
	    	
	    	// This is the HQL statement that is used with the database in order to get the data we want
	    	StringBuilder SQL_Query = new StringBuilder();
	    	
	    	SQL_Query.append("SELECT o.value_coded, c.name, count(*) ");
	    	SQL_Query.append("FROM obs o, concept_name c ");
	    	SQL_Query.append("WHERE o.value_coded = c.concept_id ");
	    	SQL_Query.append("AND o.concept_id = :coded_id ");
	    	SQL_Query.append("AND c.concept_name_type = 'FULLY_SPECIFIED' ");	    	
	    	SQL_Query.append("AND (o.obs_datetime BETWEEN  :start_date  AND  :end_date ) ");
	    	
	    	int count = 0;
	    	
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
	    	
	    	SQL_Query.append("GROUP BY o.value_coded ");
	    	
	    	if (minNumber != -1 && maxNumber != -1){
	    		SQL_Query.append("HAVING COUNT(*) BETWEEN " + minNumber + "AND " + maxNumber);
	    	}
			else if (minNumber > -1) {
				SQL_Query.append("HAVING COUNT(*) >= " + minNumber);
			}
			else if (maxNumber > -1) {
				SQL_Query.append("HAVING COUNT(*) <= " + maxNumber);
			}
	    	
	    	SQLQuery query = session.createSQLQuery(SQL_Query.toString());
	    	
	    	// This sets the parameter coded_id to whatever we got from the number above (should be 1271)
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
				// vals[0] is just the concept_id of the disease which we may or may not need but that is why vals[0] is not used here
				resultString.append(vals[1] + ":" + vals[2] + "\n");
			}
			
	    	return resultString.toString();
	    }

}

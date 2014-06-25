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
package org.openmrs.module.dataaggregation.api.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.openmrs.Patient;
import org.openmrs.api.context.Context;

import java.util.HashMap;

import org.openmrs.api.impl.BaseOpenmrsService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.module.dataaggregation.api.DataAggregationService;
import org.openmrs.module.dataaggregation.api.db.DataAggregationDAO;

/**
 * It is a default implementation of {@link DataAggregationService}.
 */
public class DataAggregationServiceImpl extends BaseOpenmrsService implements DataAggregationService {
	
	protected final Log log = LogFactory.getLog(this.getClass());
	
	private DataAggregationDAO dao;
	
	/**
     * @param dao the dao to set
     */
    public void setDao(DataAggregationDAO dao) {
	    this.dao = dao;
    }
    
    /**
     * @return the dao
     */
    public DataAggregationDAO getDao() {
	    return dao;
    }
    
    public String getAllPatientNames() {
		String toReturn = new String();
		List<Patient> patients = Context.getPatientService().getAllPatients();
		for (Patient patient : patients) {
		  toReturn = toReturn + ("\n Patient: " + patient.getGivenName() + " " + patient.getFamilyName());
		}
		return toReturn;
    }
    
    
    public String getQuestionAnswer(String testName, String par1, String par2, String par3) {
    	
    	
    	//String result = state.execute(testName, par1, par2, par3);
    	
    	if (testName.equalsIgnoreCase("Disease")) {
    		
    	}
    	else if (testName.equalsIgnoreCase("Tests")) {
    		
    	}
		else if (testName.equalsIgnoreCase("Pulse")) {		    		
		
		}
		else if (testName.equalsIgnoreCase("Weight")) {
			
		}
		else if (testName.equalsIgnoreCase("etc..")) {
			
		}
		else if (testName.equalsIgnoreCase("Goodbye")) {
			
		}
		else if (testName.equalsIgnoreCase("Goodbye")) {
			
		}
		else if (testName.equalsIgnoreCase("Goodbye")) {
			
		}
		else if (testName.equalsIgnoreCase("Goodbye")) {
			
		}
		else if (testName.equalsIgnoreCase("Goodbye")) {
			
		}
		else if (testName.equalsIgnoreCase("Goodbye")) {
			
		}
		else if (testName.equalsIgnoreCase("Goodbye")) {
			
		}
		else if (testName.equalsIgnoreCase("Goodbye")) {
			
		}
		else if (testName.equalsIgnoreCase("Goodbye")) {
			
		}
		else if (testName.equalsIgnoreCase("Goodbye")) {
			
		}
		else if (testName.equalsIgnoreCase("Goodbye")) {
			
		}
		else if (testName.equalsIgnoreCase("Goodbye")) {
			
		}
		else if (testName.equalsIgnoreCase("Goodbye")) {
			
		}
		else if (testName.equalsIgnoreCase("Goodbye")) {
			
		}
		else if (testName.equalsIgnoreCase("Goodbye")) {
			
		}
		else if (testName.equalsIgnoreCase("Goodbye")) {
			
		}
		else if (testName.equalsIgnoreCase("Goodbye")) {
			
		}
    	
    	return "";
    }
    
    /*
    public String getDiseaseCounts(String startDate, String endDate) {
    	
    	String SQL_Query = "select o.value_coded, c.name, count(*) from obs o, concept_name c "
				+ "where o.value_coded = c.concept_id and o. and o.concept_id= :coded_id and c.concept_name_type = 'FULLY_SPECIFIED'"
				+ "group by o.value_coded";
				
				select o.value_coded, c.name, count(*) from obs o, concept_name c where o.value_coded = 
				c.concept_id and c.concept_id = :coded_id and c.concept_name_type = 'FULLY_SPECIFIED' group by o.value_coded
    	
    	return "";
    }*/
    
    /**
     * 
     */
    public String getDiseaseCounts(List<String> diseaseList, String startDate, String endDate) {
    	
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
    	
    	SQL_Query.append("SELECT o.value_coded, c.name, count(*) ");
    	SQL_Query.append("FROM obs o, concept_name c ");
    	SQL_Query.append("WHERE o.value_coded = c.concept_id ");
    	SQL_Query.append("AND o.concept_id = :coded_id ");
    	SQL_Query.append("AND c.concept_name_type = 'FULLY_SPECIFIED' ");
    				
    	//+ "and (o.obs_datetime between ' :start_date ' and ' :end_date ') "

    	int count = 0;
    	
    	for (String disease : diseaseList) {
    		if (count == 0) {
    			SQL_Query.append("AND (c.name = '" + disease + "'");
    			count = 1;
    		}
    		else {
    			SQL_Query.append(" OR c.name = '" + disease + "'"); 
    		}
    	}
    	
    	if (count == 1) {
    		SQL_Query.append(") ");
    	}    	
    	
    	SQL_Query.append("GROUP BY o.value_coded ");
    	
    	/*
    	System.out.println();
    	System.out.println("######################");
    	System.out.println("OUR CODE BELOW");
    	System.out.println();
    	
    	System.out.println("query = " + SQL_Query.toString());
    	
    	System.out.println();
    	System.out.println("OUR CODE ABOVE");
    	System.out.println("######################");
    	System.out.println();
    	*/
    	
		SQLQuery query = session.createSQLQuery(SQL_Query.toString());

		// This sets the parameter coded_id to whatever we got from the number above (should be 6042)
		query.setParameter("coded_id", num_coded);
		//query.setParameter("start_date", startDate);
		//query.setParameter("end_date", endDate);
		
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

    public String getTestsOrdered(List<String> testsOrderedList, String startDate, String endDate) {
    	
    	int min = -1;
    	int max = -1;
    	
    	Session session = dao.getSessionFactory().openSession();
    	
    	// This code is to get the concept_id number that corresponds to TESTS ORDERED concept
    	// For our instances the concept_id number is always 1271 but if a different concept map was used that number could change
    	String problem_Query = "SELECT concept_id FROM concept_name WHERE name='TESTS ORDERED'";
    	SQLQuery problem_q = session.createSQLQuery(problem_Query);
    	
    	@SuppressWarnings("unchecked")
		List<Object> code_list = problem_q.list();
    	// The num_coded does not need to be gotten out from the list through indexing because the SQL statement returns one record with one column
    	int num_coded = (Integer) code_list.get(0);
    	
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
    	
    	if (min != -1 && max != -1){
    		SQL_Query.append("HAVING COUNT(*) BETWEEN " + min + "AND " + max);
    	}
		else if (min > -1) {
			SQL_Query.append("HAVING COUNT(*) >= " + min);
		}
		else if (max > -1) {
			SQL_Query.append("HAVING COUNT(*) <= " + max);
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
    
    public String getWeights() {
    	
    	char male = 'M';
    	//char female = 'F';
    	
    	Session session = dao.getSessionFactory().openSession();
    	
    	// This code is to get the concept_id number that corresponds to WEIGHT (KG) concept
    	// For our instances the concept_id number is always 5089 but if a different concept map was used that number could change
    	String problem_Query = "SELECT concept_id FROM concept_name WHERE name='WEIGHT (KG)'";
    	SQLQuery problem_q = session.createSQLQuery(problem_Query);
    	
    	@SuppressWarnings("unchecked")
		List<Object> code_list = problem_q.list();
    	// The num_coded does not need to be gotten out from the list through indexing because the SQL statement returns one record with one column
    	int num_coded = (Integer) code_list.get(0);
    	
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
    	int minAge = 20;
    	int maxAge = 30;
    	
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
    	query.setParameter("gender", male);
    	
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
    
	@Override
	public HashMap<String, Integer> getDiseaseBurden() {
		//make up fake data for now
		HashMap<String, Integer> diseaseBurden = new HashMap<String, Integer>();
		
		diseaseBurden.put("malaria", 1202);
		diseaseBurden.put("cholera", 1202);
		diseaseBurden.put("typhoid", 1202);
		diseaseBurden.put("hiv/aids", 1202);
		diseaseBurden.put("syphilis", 1202);
		
		return diseaseBurden;
	}
}
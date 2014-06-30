package org.openmrs.module.dataaggregation.api.impl;

import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.openmrs.module.dataaggregation.api.db.DataAggregationDAO;

public abstract class DataAggregationQuery {
	
	protected DataAggregationDAO dao;	
		
	public DataAggregationQuery(DataAggregationDAO dao) {
		this.dao = dao;
	}
	
	public int getConceptIdOfKeyWord(String keyWord) {
		
		Session session = dao.getSessionFactory().openSession();
		
    	String problem_Query = "SELECT concept_id FROM concept_name WHERE name= :concept_name";
    	SQLQuery problem_q = session.createSQLQuery(problem_Query);
    	
    	problem_q.setParameter("concept_name", keyWord); // set the name = to whatever the concept name you want the concept_id of
    	
    	@SuppressWarnings("unchecked")
		List<Object> code_list = problem_q.list();
    	// The num_coded does not need to be gotten out from the list through indexing because the SQL statement returns one record with one column
    	return (Integer) code_list.get(0);
	}
}

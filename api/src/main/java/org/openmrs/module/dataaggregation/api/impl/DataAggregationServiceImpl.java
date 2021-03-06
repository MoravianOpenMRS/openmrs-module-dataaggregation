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


import java.util.List;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.openmrs.Patient;
import org.openmrs.api.context.Context;

import java.util.HashMap;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;

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

    
    /**
     * 
     */
    public List<Object> getDiseaseBurden(String diseaseList, String cityList,
    								String startDate, String endDate,
    								Integer minNumber, Integer maxNumber) {  
    	
    	DiseaseBurdenQuery dbq = new DiseaseBurdenQuery(dao);    	
    	return dbq.getQueryInfo(diseaseList, cityList, startDate, endDate, minNumber, maxNumber);
		
    }


    public List<Object> getTestsOrdered(String testsOrderedList, String cityList,
    								String startDate, String endDate, 
    								Integer minNumber, Integer maxNumber) {
    	
    	TestsOrderedQuery toq = new TestsOrderedQuery(dao);    	
    	return toq.getQueryInfo(testsOrderedList, cityList, startDate, endDate, minNumber, maxNumber);
    }
    
    public List<Object> getWeights(Character gender, Integer minAge, Integer maxAge) {
    	
    	WeightQuery wq = new WeightQuery(dao);
    	return wq.getQueryInfo(gender, minAge, maxAge);
    }

	@Override
	public String convertToJSON(String csvString) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String convertToXML(String csvString) {
		// TODO Auto-generated method stub
		return null;
	}
   
	
}
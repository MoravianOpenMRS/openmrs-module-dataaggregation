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


    public String getTestsOrdered(String testsOrderedList,
    								String startDate, String endDate, 
    								Integer minNumber, Integer maxNumber) {
    	
    	TestsOrderedQuery toq = new TestsOrderedQuery(dao);    	
    	return toq.getQueryInfo(testsOrderedList, startDate, endDate, minNumber, maxNumber);
    }
    
    public String getWeights(Character gender, Integer minAge, Integer maxAge) {
    	
    	WeightQuery wq = new WeightQuery(dao);
    	return wq.getQueryInfo(gender, minAge, maxAge);
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

	/**
	 * Converts a csv file to XML
	 * @param csvString a string that is a csv file separated by colons
	 * @return a string is a XML file
	 */
	public String convertToXML(String csvString){
		String [] rows = csvString.split("\n");
		String[] fieldNames = rows[0].split(":");
		Element table = new Element("table");
		Document doc = new Document(table);
		int i = 1;
		while(i < rows.length){
			Element row = new Element("row" + (i-1));
			String [] cols = rows[i].split(":");
			int j =0;
			for(String rowS:cols){
				row.setAttribute(new Attribute(fieldNames[j],rowS));
				j++;
			}
						
			doc.getRootElement().addContent(row);
			
			i++;
		}

		XMLOutputter xmlOutput = new XMLOutputter();
		xmlOutput.setFormat(Format.getPrettyFormat());
		return xmlOutput.outputString(doc);
	}

	
}
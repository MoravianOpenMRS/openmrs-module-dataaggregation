package org.openmrs.module.dataaggregation.api.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class XMLFormatter {

	
	public static String formatDiseaseBurden(List<Object> diseases){
		// vals[1] is the name of the disease
		// vals[2] is the count for the disease
		
		LinkedHashMap<String, Integer> data = new LinkedHashMap<String, Integer>();
		data.put("diseaseName", 1);
		data.put("count", 2);
		
		String toReturn = formatCounts(diseases, data);
		
		return toReturn;
		
		
	}
	
	public static String formatTestsOrdered(List<Object> tests){
		// vals[1] is the name of the disease
		// vals[2] is the count for the disease
				
		LinkedHashMap<String, Integer> data = new LinkedHashMap<String, Integer>();
		data.put("testName", 1);
		data.put("count", 2);
				
		String toReturn = formatCounts(tests, data);
				
		return toReturn;
		
	}
	
	public static String formatWeights(List<Object> weights){
		// vals[0] is the person id.
		// vals[1] is the gender.
		// vals[2] is the birthdate.
		// vals[3] is the weight in KG.
		// vals[4] is the observation/encounter datetime
		
		LinkedHashMap<String, Integer> data = new LinkedHashMap<String, Integer>();
		data.put("patientID", 0);
		data.put("weight", 3);
				
		String toReturn = formatCounts(weights, data);
				
		return toReturn;
		
		
	}
	
	private static String formatCounts(List<Object> data, Map<String, Integer> dataMembers){
		
		
		Element results = new Element("results");
		Document doc = new Document(results);
		
		
		for(Object row: data)
		{
			Object[] columns = (Object[]) row;
			Element result = new Element("result");
			
			for(String value: dataMembers.keySet())
			{
				
				Element fieldName = new Element(value);
				fieldName.addContent(columns[dataMembers.get(value)].toString());
				
				result.addContent(fieldName);
				
				
			}
			doc.getRootElement().addContent(result);
		}
		
		XMLOutputter xmlOutput = new XMLOutputter();
		xmlOutput.setFormat(Format.getPrettyFormat());
		return xmlOutput.outputString(doc);
		
	}
	
	

}

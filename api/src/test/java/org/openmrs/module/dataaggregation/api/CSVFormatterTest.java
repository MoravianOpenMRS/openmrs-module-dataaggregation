package org.openmrs.module.dataaggregation.api;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.openmrs.module.dataaggregation.api.impl.CSVFormatter;


public class CSVFormatterTest{

	@Test
	public void testCSVFormatterDiseaseBurden(){
		List<Object> data = new ArrayList<Object>();
		Object[] row1 = new Object[3];
		row1[0] = new String("");
		row1[1] = new String("badDisease");
		row1[2] = new String("10123");
		
		data.add(row1);
		String expectedResult = "diseaseName:count\n" + "badDisease" + ":" + "10123" + "\n";
		assertEquals(expectedResult, CSVFormatter.formatDiseaseBurden(data));
	
	} 
	@Test
	public void testCSVFormatterTestsOrdered(){
		List<Object> data = new ArrayList<Object>();
		Object[] row1 = new Object[3];
		row1[0] = new String("");
		row1[1] = new String("cancerTest");
		row1[2] = new String("10123");
		
		data.add(row1);
		String expectedResult = "testName:count\n" + "cancerTest" + ":" + "10123" + "\n";
		assertEquals(expectedResult, CSVFormatter.formatTestsOrdered(data));
	}
	@Test
	public void testCSVFormatterWeights(){
		List<Object> data = new ArrayList<Object>();
		Object[] row1 = new Object[4];
		row1[0] = new String("1");
		row1[3] = new String("50");
		
		data.add(row1);
		String expectedResult = "personId:weightKG\n" + "1" + ":" + "50" + "\n";
		assertEquals(expectedResult, CSVFormatter.formatWeights(data));
	}
}

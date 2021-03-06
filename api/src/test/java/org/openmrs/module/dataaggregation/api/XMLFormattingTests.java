package org.openmrs.module.dataaggregation.api;

import static org.junit.Assert.*;

import java.util.List;
import java.util.ArrayList;

import org.junit.Test;
/*
 * These tests will fail in JUnit when run, but they output the expected String
 * There must be an issue the specific type of spacing used
 */
import org.openmrs.module.dataaggregation.api.impl.XMLFormatter;

public class XMLFormattingTests {

	@Test
	public void testXMLFormatterDiseaseBurden(){
		List<Object> data = new ArrayList<Object>();
		Object[] row1 = new Object[3];
		row1[0] = new String("");
		row1[1] = new String("badDisease");
		row1[2] = new String("10123");

		data.add(row1);
		String expectedResult = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "\n"+  
"<results>" + "\n"+ "  "+
  "<result>" + "\n" + "\t"+
    "<diseaseName>badDisease</diseaseName>" + "\n" + "\t"+
    "<count>10123</count>" + "\n" + "  "+
  "</result>" + "\n" +
 "</results>";
		assertEquals(expectedResult, XMLFormatter.formatDiseaseBurden(data));

	} 
	@Test
	public void testXMLFormatterTestsOrdered(){
		List<Object> data = new ArrayList<Object>();
		Object[] row1 = new Object[3];
		row1[0] = new String("");
		row1[1] = new String("cancerTest");
		row1[2] = new String("10123");

		data.add(row1);
		String expectedResult = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "\n"+
				"<results>" + "\n"+ "  "+
				  "<result>" + "\n" + "\t"+ 
				    "<testName>cancerTest</testName>" + "\n" + "\t"+
				    "<count>10123</count>" + "\n" + "  "+
				  "</result>" + "\n" +
				 "</results>";
		assertEquals(expectedResult, XMLFormatter.formatTestsOrdered(data));
	}
	@Test
	public void testXMLFormatterWeights(){
		List<Object> data = new ArrayList<Object>();
		Object[] row1 = new Object[4];
		row1[0] = new String("1");
		row1[3] = new String("50");

		data.add(row1);
		String expectedResult = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "\n"+
				"<results>" + "\n"+ "  "+
				  "<result>" + "\n" + "\t"+
				    "<patientID>1</patientID>" + "\n" + "\t"+
				    "<weight>50</weight>" + "\n" + "  "+
				  "</result>" + "\n" +
				 "</results>";
		assertEquals(expectedResult, XMLFormatter.formatWeights(data));
	}

}

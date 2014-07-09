package org.openmrs.module.dataaggregation.api;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class JSONFormatterTest {

	@Test
	public void test() {
		fail("Not yet implemented");
	}

	@Test
	public void testJSONFormatterDiseaseBurden(){
		List<Object> data = new ArrayList<Object>();
		Object[] row1 = new Object[3];
		row1[0] = new String("");
		row1[1] = new String("badDisease");
		row1[2] = new String("10123");
		
		data.add(row1);
		
		System.out.println(JSONFormatter.formatDiseaseBurden(data));
	} 
	@Test
	public void testJSONFormatterTestsOrdered(){
		List<Object> data = new ArrayList<Object>();
		Object[] row1 = new Object[3];
		row1[0] = new String("");
		row1[1] = new String("cancerTest");
		row1[2] = new String("10123");
		
		data.add(row1);
		
		System.out.println(JSONFormatter.formatDiseaseBurden(data));
	}
	@Test
	public void testJSONFormatterWeights(){
		List<Object> data = new ArrayList<Object>();
		Object[] row1 = new Object[4];
		row1[0] = new String("1");
		row1[3] = new String("50");
		
		data.add(row1);
		
		System.out.println(JSONFormatter.formatWeights(data));
	}
}

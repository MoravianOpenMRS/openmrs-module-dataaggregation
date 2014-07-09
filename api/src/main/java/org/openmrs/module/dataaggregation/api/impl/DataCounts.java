package org.openmrs.module.dataaggregation.api.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DataCounts {
	
	Map<String, String> map;
	
	public DataCounts(List<Object> records) {
		
		map = new HashMap<String, String>();
		
		// Each object in results is another record from our SQL statement
		for (Object o : records) {
			// Cast each object into an array where each column is another index into the array
			Object[] vals = (Object[]) o;
			// vals[1] is the name of the disease
			// vals[2] is the count for the disease
			// vals[0] is just the concept_id of the disease which we may or may not need and that is why vals[0] is not used here
			
			map.put(vals[1].toString(), vals[2].toString());
		}

	}
	
	public Set<String> getKeys() {
		return map.keySet();
	}
	
	public String getValue(String key) {
		return map.get(key);
	}

}

package com.afis.cloud.solr.entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public abstract class AfisDynamicAbstractEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Map<String, Object> map = new HashMap<String,Object>();

	public Map<String, Object> getMap() {
		return map;
	}

	public void setMap(Map<String, Object> map) {
		this.map = map;
	}

}

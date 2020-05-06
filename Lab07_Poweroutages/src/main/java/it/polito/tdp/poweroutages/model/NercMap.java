package it.polito.tdp.poweroutages.model;

import java.util.HashMap;
import java.util.Map;

public class NercMap {
	
	private Map<Integer, Nerc> map;

	public Map<Integer, Nerc> getMap() {
		return map;
	}

	public NercMap(Map<Integer, Nerc> map) {
		super();
		this.map = map;
	}
	
	public NercMap() {
		map = new HashMap<>();
	}
	
}

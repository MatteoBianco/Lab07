package it.polito.tdp.poweroutages.model;

import java.util.ArrayList;

public class TestModel {

	public static void main(String[] args) {
		
		Model model = new Model();
		System.out.println(model.getNercList());
		System.out.println(model.getBlackoutByNerc(new Nerc(8, null)));
		System.out.println(model.getCustomersAffected(model.getBlackoutByNerc(new Nerc(8, null))));
		System.out.println(model.getBlackoutByNerc(new Nerc(1, null)).size());
		System.out.println(model.getYearMin(model.getBlackoutByNerc(new Nerc(8, null))));
		System.out.println(model.getYearMax(model.getBlackoutByNerc(new Nerc(8, null))));
		System.out.println(model.getWorstCase(new Nerc(8, null), 2, 5));
	}

}

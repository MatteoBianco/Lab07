package it.polito.tdp.poweroutages.model;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.poweroutages.DAO.PowerOutageDAO;

public class Model {
	
	private PowerOutageDAO podao;
	private List<PowerOutage> worstCase;
	private List<PowerOutage> outages;
	private int yearRange;
	private int hoursMax;
	private int livMax;
	
	public Model() {
		podao = new PowerOutageDAO();
	}
	
	public List<PowerOutage> getBlackoutByNerc (Nerc nerc) {
		return podao.getBlackoutByNerc(nerc);
	}
	
	public List<Nerc> getNercList() {
		return podao.getNercList();
	}
	
	/*public List<PowerOutage> getWorstCase(Nerc nerc, int yearRange, int hoursMax) {
		outages = this.getBlackoutByNerc(nerc);
		worstCase = new ArrayList<>();
		this.yearRange = yearRange;
		this.hoursMax = hoursMax;
		this.livMax = outages.size();
		this.ricorsione(new ArrayList<PowerOutage>(), 0);
		return worstCase;
	}
	
	private void ricorsione(List<PowerOutage> parziale, int livello) {
		
		//A: condizione di terminazione
		if(this.getHoursOutage(parziale) > this.hoursMax || 
				(this.getYearMax(parziale)-this.getYearMin(parziale)) > this.yearRange) {
			return;
		}
		
		
		//C: salvo la soluzione se è il caso peggiore
		if(worstCase.isEmpty()) {
			worstCase = new ArrayList<>(parziale);
		}
		if(this.getCustomersAffected(parziale) > this.getCustomersAffected(worstCase)) {
				worstCase = new ArrayList<>(parziale);
		}
		if(livello == livMax)
			return;
		
		//B: lancio la ricorsione
		
		parziale.add(outages.get(livello));
		ricorsione(parziale, livello+1);
		parziale.remove(parziale.size()-1);
		
		ricorsione(parziale, livello +1);
	}*/
	
	public List<PowerOutage> getWorstCase(Nerc nerc, int yearRange, int hoursMax) {
		outages = this.getBlackoutByNerc(nerc);
		worstCase = new ArrayList<>();
		this.yearRange = yearRange;
		this.hoursMax = hoursMax;
		this.ricorsione(new ArrayList<PowerOutage>());
		return worstCase;
	}
	
	
	private void ricorsione(ArrayList<PowerOutage> parziale) {
		if(worstCase.isEmpty()) {
			worstCase = new ArrayList<>(parziale);
		}
		if(this.getCustomersAffected(parziale) > this.getCustomersAffected(worstCase)) {
				worstCase = new ArrayList<>(parziale);
		}
		
		for(PowerOutage po : outages) {
			if(!parziale.contains(po)) {
				if(parziale.isEmpty() || (po.getDateEventFinished().getYear()-this.getYearMin(parziale) < this.yearRange &&
						this.getYearMax(parziale)-po.getDateEventBegan().getYear() < this.yearRange &&
						this.getHoursOutage(parziale) + po.hoursOutage() < this.hoursMax)) {
					parziale.add(po);
					ricorsione(parziale);
					parziale.remove(parziale.size()-1);
				}
			}
		}
	}

	public int getCustomersAffected(List<PowerOutage> blackout) {
		int customers = 0;
		for(PowerOutage po : blackout) {
			customers += po.getCustomersAffected();					
		}
		return customers;
	}
	
	public int getHoursOutage(List<PowerOutage> blackout) {
		int hours = 0;
		for(PowerOutage po : blackout) {
			hours += po.hoursOutage();
		}
		return hours;
	}
	
	public int getYearMin(List<PowerOutage> blackout) {
		int min = -1;
		for(PowerOutage po : blackout) {
			if(min == -1)
				min = po.getDateEventBegan().getYear();
			else {
				if(po.getDateEventBegan().getYear() < min)
					min = po.getDateEventBegan().getYear();
			}
		}
		return min;
	}
	
	public int getYearMax(List<PowerOutage> blackout) {
		int max = -1;
		for(PowerOutage po : blackout) {
			if(max == -1)
				max = po.getDateEventFinished().getYear();
			else {
				if(po.getDateEventFinished().getYear() > max)
					max = po.getDateEventFinished().getYear();
			}
		}
		return max;
	}

}

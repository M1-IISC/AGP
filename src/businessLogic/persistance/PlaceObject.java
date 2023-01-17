package businessLogic.persistance;

import businessLogic.journeyPoint.CategoryOfSite;

public class PlaceObject {
	private String name;
	private String description;
	private double confort;
	private double attractionTime;
	private double cost;
	private double lunchCost;
	private double nightcost;
	private CategoryOfSite category;
	private double accuracy;
	
	public PlaceObject(String name, String description, double confort, double attractionTime, double cost,
			double lunchCost, double nightcost, CategoryOfSite category, double accurency) {
		super();
		this.name = name;
		this.description = description;
		this.confort = confort;
		this.attractionTime = attractionTime;
		this.cost = cost;
		this.lunchCost = lunchCost;
		this.nightcost = nightcost;
		this.category = category;
		this.accuracy = accurency;
	}

	public double getAccuracy() {
		return accuracy;
	}

	public void setAccuracy(double accurency) {
		this.accuracy = accurency;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getConfort() {
		return confort;
	}

	public void setConfort(double confort) {
		this.confort = confort;
	}

	public double getAttractionTime() {
		return attractionTime;
	}

	public void setAttractionTime(double attractionTime) {
		this.attractionTime = attractionTime;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public double getLunchCost() {
		return lunchCost;
	}

	public void setLunchCost(double lunchCost) {
		this.lunchCost = lunchCost;
	}

	public double getNightcost() {
		return nightcost;
	}

	public void setNightcost(double nightcost) {
		this.nightcost = nightcost;
	}

	public CategoryOfSite getCategory() {
		return category;
	}

	public void setCategory(CategoryOfSite category) {
		this.category = category;
	}
	
	
}

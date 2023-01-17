package businessLogic.persistance;

import businessLogic.transports.TransportType;

public class PlacesTransportObject {
	private PlaceObject siteA;
	private PlaceObject siteB;
	private TransportType type;
	private double distance;
	
	public PlacesTransportObject(PlaceObject siteA, PlaceObject siteB, TransportType type, double distance) {
		super();
		this.siteA = siteA;
		this.siteB = siteB;
		this.type = type;
		this.distance = distance;
	}

	public PlaceObject getSiteA() {
		return siteA;
	}

	public void setSiteA(PlaceObject siteA) {
		this.siteA = siteA;
	}

	public PlaceObject getSiteB() {
		return siteB;
	}

	public void setSiteB(PlaceObject siteB) {
		this.siteB = siteB;
	}

	public TransportType getType() {
		return type;
	}

	public void setType(TransportType type) {
		this.type = type;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}
	
	
}

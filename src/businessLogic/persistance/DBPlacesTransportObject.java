package businessLogic.persistance;

import businessLogic.transports.TransportType;

public class DBPlacesTransportObject {
	private DBPlaceObject siteA;
	private DBPlaceObject siteB;
	private TransportType type;
	private double distance;
	
	public DBPlacesTransportObject(DBPlaceObject siteA, DBPlaceObject siteB, TransportType type, double distance) {
		super();
		this.siteA = siteA;
		this.siteB = siteB;
		this.type = type;
		this.distance = distance;
	}

	public DBPlaceObject getSiteA() {
		return siteA;
	}

	public void setSiteA(DBPlaceObject siteA) {
		this.siteA = siteA;
	}

	public DBPlaceObject getSiteB() {
		return siteB;
	}

	public void setSiteB(DBPlaceObject siteB) {
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

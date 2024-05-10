package parking.management.entity;

public class ParkingZoneViewForClient {
	Integer id;
	String parkingZoneName;
	
	
	
	public ParkingZoneViewForClient(Integer id, String parkingZoneName) {
		super();
		this.id = id;
		this.parkingZoneName = parkingZoneName;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getParkingZoneName() {
		return parkingZoneName;
	}
	public void setParkingZoneName(String parkingZoneName) {
		this.parkingZoneName = parkingZoneName;
	}
	
}

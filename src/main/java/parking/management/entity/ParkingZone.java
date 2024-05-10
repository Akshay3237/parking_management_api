package parking.management.entity;

import java.math.BigDecimal;
import java.util.List;
import jakarta.persistence.*;

@Entity
@Table(name = "parking_zone")
public class ParkingZone {
    
	  @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "parking_zone_id")
	    private Integer parkingZoneId;
	    
	    @Column(name = "parking_zone_name")
	    private String parkingZoneName;
	    
	    @Column(name = "number_of_car")
	    private Integer numberOfCar;
	    
	    @Column(name = "number_of_bike")
	    private Integer numberOfBike;
	    
	    @Column(name = "number_of_truck")
	    private Integer numberOfTruck;
	    
	    @Column(name = "number_of_auto")
	    private Integer numberOfAuto;
	    
	
	    @OneToOne(cascade = CascadeType.ALL)
	    @JoinColumn(name = "parking_manager_id")
	    private User parkingManager;
	    
	    @Column(name = "parking_cost")
	    private BigDecimal parkingCost;
	    
	    @Column(name = "delay_cost")
	    private BigDecimal delayCost;

    // Constructors, getters, and setters
    // Constructor
    public ParkingZone() {
    }

    // Getters and setters
    public Integer getParkingZoneId() {
        return parkingZoneId;
    }

    public void setParkingZoneId(Integer parkingZoneId) {
        this.parkingZoneId = parkingZoneId;
    }

    public String getParkingZoneName() {
        return parkingZoneName;
    }

    public void setParkingZoneName(String parkingZoneName) {
        this.parkingZoneName = parkingZoneName;
    }

    public Integer getNumberOfCar() {
        return numberOfCar;
    }

    public void setNumberOfCar(Integer numberOfCar) {
        this.numberOfCar = numberOfCar;
    }

    public Integer getNumberOfBike() {
        return numberOfBike;
    }

    public void setNumberOfBike(Integer numberOfBike) {
        this.numberOfBike = numberOfBike;
    }

    public Integer getNumberOfTruck() {
        return numberOfTruck;
    }

    public void setNumberOfTruck(Integer numberOfTruck) {
        this.numberOfTruck = numberOfTruck;
    }

    public Integer getNumberOfAuto() {
        return numberOfAuto;
    }

    public void setNumberOfAuto(Integer numberOfAuto) {
        this.numberOfAuto = numberOfAuto;
    }

    
    public User getParkingManager() {
        return parkingManager;
    }

    public void setParkingManager(User parkingManager) {
        this.parkingManager = parkingManager;
    }

    public BigDecimal getParkingCost() {
        return parkingCost;
    }

    public void setParkingCost(BigDecimal parkingCost) {
        this.parkingCost = parkingCost;
    }

    public BigDecimal getDelayCost() {
        return delayCost;
    }

    public void setDelayCost(BigDecimal delayCost) {
        this.delayCost = delayCost;
    }
}

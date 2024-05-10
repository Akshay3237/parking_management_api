package parking.management.entity;

import java.time.LocalDateTime;
import jakarta.persistence.*;

@Entity
@Table(name = "history")
public class History {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "history_id")
    private Integer historyId;
    
    @ManyToOne(cascade = CascadeType.ALL) // Cascade all operations to ParkingZone
    @JoinColumn(name = "parking_zone_id",nullable = true)
    private ParkingZone parkingZone;
    
    @ManyToOne(cascade = CascadeType.ALL) // Cascade all operations to User
    @JoinColumn(name = "user_id",nullable = true)
    private User user;
    
    @Column(name = "vehicle_type", length = 1)
    private String vehicleType; // Possible values: 'C' (Car), 'B' (Bike), 'T' (Truck), 'A' (Auto)
    
    @Column(name = "start_time")
    private LocalDateTime startTime;
    
    @Column(name = "vehicle_number")
    private Long vehicleNumber;
    
    @Column(name = "paid_complete_time")
    private LocalDateTime paidCompleteTime;

    // Constructors, getters, and setters
    // Constructor
    public History() {
    }

    // Getters and setters
    public Integer getHistoryId() {
        return historyId;
    }

    public void setHistoryId(Integer historyId) {
        this.historyId = historyId;
    }

 

    public ParkingZone getParkingZone() {
		return parkingZone;
	}

	public void setParkingZone(ParkingZone parkingZone) {
		this.parkingZone = parkingZone;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}

	public LocalDateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

	public Long getVehicleNumber() {
		return vehicleNumber;
	}

	public void setVehicleNumber(Long vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}

	public LocalDateTime getPaidCompleteTime() {
        return paidCompleteTime;
    }

    public void setPaidCompleteTime(LocalDateTime paidCompleteTime) {
        this.paidCompleteTime = paidCompleteTime;
    }
}

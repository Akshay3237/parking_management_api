package parking.management.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "user") // Specify the table name in snake_case
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    
    @Column(name = "user_name",unique = true) // Specify the column name in snake_case
    private String userName;
    
    @Column(name = "phone_number") // Specify the column name in snake_case
    private String phoneNumber;
    
    @Column(name = "address") // Specify the column name in snake_case
    private String address;
    
    @Column(name = "password") // Specify the column name in snake_case
    private String password;
    
    @Column(name = "active") // Specify the column name in snake_case
    private boolean active;
    
    // Constructors, getters, and setters
    // Constructor
    public User() {
    }

    // Getters and setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}

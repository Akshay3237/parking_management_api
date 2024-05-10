package parking.management.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import jakarta.persistence.*;

@Entity
@Table(name = "transaction")
public class Transaction {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private Integer transactionId;
    
    @ManyToOne(cascade = CascadeType.ALL) // Cascade all operations to User
    @JoinColumn(name = "user_id")
    private User user;
    
    @ManyToOne(cascade = CascadeType.ALL) // Cascade all operations to ParkingSpace
    @JoinColumn(name = "history_id")
    private History history;
    
    @Column(name = "total_cost")
    private BigDecimal totalCost;
    
    @Column(name = "status")
    private boolean status; // Paid (true) or Unpaid (false)
    
    @Column(name = "start_transaction_time")
    private LocalDateTime startTransactionTime;
    
    @Column(name = "delay_cost", columnDefinition = "DECIMAL(10,2) DEFAULT '0.00'")
    private BigDecimal delayCost;

    public BigDecimal getDelayCost() {
		return delayCost;
	}

	public void setDelayCost(BigDecimal delayCost) {
		this.delayCost = delayCost;
	}

	// Constructors, getters, and setters
    // Constructor
    public Transaction() {
    }

    // Getters and setters
    public Integer getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Integer transactionId) {
        this.transactionId = transactionId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

 

    public History getHistory() {
		return history;
	}

	public void setHistory(History history) {
		this.history = history;
	}

	public BigDecimal getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(BigDecimal totalCost) {
        this.totalCost = totalCost;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public LocalDateTime getStartTransactionTime() {
        return startTransactionTime;
    }

    public void setStartTransactionTime(LocalDateTime startTransactionTime) {
        this.startTransactionTime = startTransactionTime;
    }
}

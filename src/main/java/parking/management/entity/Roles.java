package parking.management.entity;
import jakarta.persistence.*;

@Entity
@IdClass(RolesId.class)
public class Roles {
    @Id
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @Id
    private String role;

    public Roles() {
		super();
	}

	public Roles(User user, String role) {
        this.user = user;
        this.role = role;
    }

    // Getters and setters
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}

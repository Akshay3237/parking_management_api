package parking.management.entity;
import java.io.Serializable;

public class RolesId implements Serializable {
    private User user;
    private String role;

    // Constructors
    public RolesId() {}

    public RolesId(User user, String role) {
        this.user = user;
        this.role = role;
    }

    // Equals and hashCode methods
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RolesId rolesId = (RolesId) o;

        if (!user.equals(rolesId.user)) return false;
        return role.equals(rolesId.role);
    }

    @Override
    public int hashCode() {
        int result = user.hashCode();
        result = 31 * result + role.hashCode();
        return result;
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

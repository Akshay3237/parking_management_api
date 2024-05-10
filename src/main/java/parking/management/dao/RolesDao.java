package parking.management.dao;

import parking.management.entity.Roles;
import parking.management.entity.User;

import java.util.List;

public interface RolesDao {
    void insert(Roles roles);

    void delete(Roles roles);

    void update(Roles roles);

    List<Roles> findAll();

    Roles findById(User user, String role);
}

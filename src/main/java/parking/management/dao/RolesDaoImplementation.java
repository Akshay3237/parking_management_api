package parking.management.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import parking.management.entity.Roles;
import parking.management.entity.RolesId;
import parking.management.entity.User;

import java.util.List;

@Repository
public class RolesDaoImplementation implements RolesDao {

    @Autowired
    private EntityManager entityManager;

    @Override
    @Transactional
    public void insert(Roles roles) {
        if (roles == null) {
            throw new NullPointerException("Roles object cannot be null");
        }
        try {
            entityManager.persist(roles);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex; // Rethrow the exception
        }
    }

    @Override
    @Transactional
    public void delete(Roles roles) {
        if (roles == null) {
            throw new NullPointerException("Roles object cannot be null");
        }
        try {
            entityManager.remove(entityManager.contains(roles) ? roles : entityManager.merge(roles));
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex; // Rethrow the exception
        }
    }

    @Override
    @Transactional
    public void update(Roles roles) {
        if (roles == null) {
            throw new NullPointerException("Roles object cannot be null");
        }
        try {
            entityManager.merge(roles);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex; // Rethrow the exception
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Roles> findAll() {
        try {
            TypedQuery<Roles> query = entityManager.createQuery("SELECT r FROM Roles r", Roles.class);
            return query.getResultList();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex; // Rethrow the exception
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Roles findById(User user, String role) {
        if (user == null || role == null) {
            throw new NullPointerException("User and role cannot be null");
        }
        try {
            Roles roles = entityManager.find(Roles.class, new RolesId(user, role));
          
            return roles;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex; // Rethrow the exception
        }
    }
}

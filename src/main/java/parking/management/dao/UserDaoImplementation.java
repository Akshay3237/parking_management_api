package parking.management.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import parking.management.bcrypt.BcryptionPassword;
import parking.management.entity.User;

import java.util.List;
import java.util.Objects;

@Repository
public class UserDaoImplementation implements UserDao {

    @Autowired
    private EntityManager entityManager;

    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return entityManager.createQuery("SELECT u FROM User u", User.class).getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public User findById(Long userId) {
        User user = entityManager.find(User.class, userId);
        if (user == null) {
            throw new NullPointerException("User not found with ID: " + userId);
        }
        return user;
    }

    @Override
    @Transactional
    public void deleteById(Long userId) {
        User user = findById(userId);
        if (user == null) {
            throw new NullPointerException("User not found with ID: " + userId);
        }
        if (user != null) {
            entityManager.remove(user);
        }
    }

    @Override
    @Transactional
    public void delete(User user) {
      
        if (user == null) {
            throw new NullPointerException("User not found ");
        }
        
        entityManager.remove(entityManager.contains(user) ? user : entityManager.merge(user));
    }

    @Override
    @Transactional
    public void update(User user) {

        if (user == null) {
            throw new NullPointerException("User not found ");
        }
        entityManager.merge(user);
    }

    @Override
    @Transactional
    public void save(User user) {
      
        if (user == null) {
            throw new NullPointerException("User not found ");
        }
        
        user.setPassword(BcryptionPassword.Conversion(user.getPassword()));
        entityManager.persist(user);
    }

    @Override
    @Transactional(readOnly = true)
    public User findByUsername(String username) {
        try {
            return entityManager.createQuery("SELECT u FROM User u WHERE u.userName = :username", User.class)
                    .setParameter("username", username)
                    .getSingleResult();
        } catch (NoResultException ex) {
            return null; // Return null if no user is found with the given username
        }
    }

}

package br.com.ufes.bd1.goomer.repository.impl;

import br.com.ufes.bd1.goomer.model.Restaurant;
import br.com.ufes.bd1.goomer.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Collection;
import java.util.LinkedHashSet;

public class RestaurantRepositoryImpl implements RestaurantRepository {

    @PersistenceContext
    private final EntityManager entityManager;


    @Autowired
    public RestaurantRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Integer save(Restaurant restaurant) {
        String sql = "insert into restaurant (name, image_path, address_id) values (?, ?, ?) returning id";

        Query query = entityManager.createNativeQuery(sql);
        query.setParameter(1, restaurant.getName());
        query.setParameter(2, restaurant.getImagePath());
        query.setParameter(3, restaurant.getAddress().getId());

        return (Integer) query.getSingleResult();
    }

    @Override
    public void saveBusinessHours(Integer restaurantId, Integer timespanId) {
        String sql = "insert into restaurant_business_hours (restaurant_id, timespan_id) values (?, ?)";

        Query query = entityManager.createNativeQuery(sql);
        query.setParameter(1, restaurantId);
        query.setParameter(2, timespanId);
        query.executeUpdate();
    }

    @Override
    public Restaurant getById(int id) {
        String sql =
                "select * " +
                        "from restaurant r " +
                        "inner join address a on r.address_id = a.id " +
                        "inner join restaurant_business_hours bh on r.id = bh.restaurant_id " +
                        "inner join timespan t on t.id = bh.timespan_id " +
                        "where r.id = ?";

        Query query = entityManager.createNativeQuery(sql, Restaurant.class);
        query.setParameter(1, id);

        return (Restaurant) query.getSingleResult();
    }

    @Override
    public Collection<Restaurant> getAll() {
        String sql =
                "select * " +
                        "from restaurant r " +
                        "inner join address a on r.address_id = a.id " +
                        "inner join restaurant_business_hours bh on r.id = bh.restaurant_id " +
                        "inner join timespan t on t.id = bh.timespan_id";

        Query query = entityManager.createNativeQuery(sql, Restaurant.class);

        return new LinkedHashSet<>(query.getResultList());
    }
}

package br.com.ufes.bd1.goomer.repository.impl;

import br.com.ufes.bd1.goomer.model.Restaurant;
import br.com.ufes.bd1.goomer.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.Query;

public class RestaurantRepositoryImpl implements RestaurantRepository {

    private final EntityManager entityManager;


    @Autowired
    public RestaurantRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Integer save(Restaurant restaurant) {
        String sql = "insert into restaurant (name, image_path, address_id) " +
                "values (?, ?, ?) returning id";

        Query query = entityManager.createNativeQuery(sql);
        query.setParameter(1, restaurant.getName());
        query.setParameter(2, restaurant.getImagePath());
        query.setParameter(3, restaurant.getAddress().getId());

        return (Integer) query.getSingleResult();
    }

    @Override
    public void saveBusinessHours(Integer restaurantId, Integer timespanId) {
        String sql = "insert into restaurant_business_hours (restaurant_id, timespan_id) " +
                "values (?, ?)";

        Query query = entityManager.createNativeQuery(sql);
        query.setParameter(1, restaurantId);
        query.setParameter(2, timespanId);
        query.executeUpdate();
    }

    @Override
    public Restaurant getById(int id) {
        return null;
    }
}

package br.com.ufes.bd1.goomer.repository.impl;

import br.com.ufes.bd1.goomer.model.Restaurant;
import br.com.ufes.bd1.goomer.repository.RestaurantRepository;
import br.com.ufes.bd1.goomer.repository.TimespanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;


public class RestaurantRepositoryImpl implements RestaurantRepository {

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
    public void saveBusinessHours(int restaurantId, int timespanId) {
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

    @Override
    public void deleteById(int id) {
        String sql = "delete from restaurant where id = ?";

        Query query = entityManager.createNativeQuery(sql);
        query.setParameter(1, id);
        query.executeUpdate();
    }

    @Override
    public void deleteBusinessHours(int restaurantId, int timespanId) {
        String sql = "delete from restaurant_business_hours where restaurant_id = ? and timespan_id = ?";

        Query query = entityManager.createNativeQuery(sql);
        query.setParameter(1, restaurantId);
        query.setParameter(2, timespanId);
        query.executeUpdate();

        getTimespanRepository().deleteById(timespanId);
    }

    @Transactional
    @Override
    public void deleteAllBusinessHours(int restaurantId){
        String sql = "delete from restaurant_business_hours where restaurant_id = ? returning timespan_id";

        Query query = entityManager.createNativeQuery(sql);
        query.setParameter(1, restaurantId);

        List<Integer> timespanId = query.getResultList();

        TimespanRepository timespanRepository = getTimespanRepository();
        timespanId.forEach(timespanRepository::deleteById);
    }

    private TimespanRepository getTimespanRepository() {
        return new TimespanRepositoryImpl(entityManager);
    }

}

package br.com.ufes.bd1.goomer.repository;

import br.com.ufes.bd1.goomer.model.Restaurant;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface RestaurantRepository extends org.springframework.data.repository.Repository<Restaurant, Integer> {

    Integer save(Restaurant restaurant);

    void saveBusinessHours(int restaurantId, int timespanId);

    Restaurant getById(int id);

    Collection<Restaurant> getAll();

    void deleteById(int id);

    void deleteBusinessHours(int restaurantId, int timespanId);

    void deleteAllBusinessHours(int restaurantId);
}

package br.com.ufes.bd1.goomer.service;

import br.com.ufes.bd1.goomer.model.Address;
import br.com.ufes.bd1.goomer.model.Restaurant;
import br.com.ufes.bd1.goomer.repository.AddressRepository;
import br.com.ufes.bd1.goomer.repository.RestaurantRepository;
import br.com.ufes.bd1.goomer.repository.TimespanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    private final AddressRepository addressRepository;

    private final TimespanRepository timespanRepository;


    @Autowired
    public RestaurantService(RestaurantRepository restaurantRepository, AddressRepository addressRepository, TimespanRepository timespanRepository) {
        this.restaurantRepository = restaurantRepository;
        this.addressRepository = addressRepository;
        this.timespanRepository = timespanRepository;
    }

    @Transactional
    public void save(Restaurant restaurant) {
        Address address = restaurant.getAddress();
        address.setId(addressRepository.save(address));

        restaurant.setId(restaurantRepository.save(restaurant));

        restaurant.getBusinessHours().forEach(timespan -> {
            timespan.setId(timespanRepository.save(timespan));
            restaurantRepository.saveBusinessHours(restaurant.getId(), timespan.getId());
        });
    }

    public Restaurant getById(int id) {
        return restaurantRepository.getById(id);
    }

    public Collection<Restaurant> getAll() {
        return restaurantRepository.getAll();
    }

    @Transactional
    public void deleteById(Integer id) {
        Restaurant restaurant = restaurantRepository.getById(id);

        restaurantRepository.deleteAllBusinessHours(restaurant.getId());

        restaurantRepository.deleteById(restaurant.getId());

        addressRepository.deleteById(restaurant.getAddress().getId());
    }


}

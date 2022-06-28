package br.com.ufes.bd1.goomer.repository;

import br.com.ufes.bd1.goomer.model.Product;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface ProductRepository extends org.springframework.data.repository.Repository<Product, Integer> {

    void save(Product productSale);

    Product getById(int id);

    Collection<Product> getAll();

    Collection<Product> getByRestaurantId(int restaurantId);

    void deleteById(int id);

    List<Integer> deleteAllByRestaurantId(int restaurantId);

    void update(Product product);

}

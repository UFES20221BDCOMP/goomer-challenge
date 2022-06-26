package br.com.ufes.bd1.goomer.repository;

import br.com.ufes.bd1.goomer.model.Product;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends org.springframework.data.repository.Repository<Product, Integer> {

    Integer save(Product productSale);

    Product getById(int id);

    void deleteById(int id);
}

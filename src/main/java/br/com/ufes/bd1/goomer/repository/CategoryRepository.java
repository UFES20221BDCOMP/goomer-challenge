package br.com.ufes.bd1.goomer.repository;

import br.com.ufes.bd1.goomer.model.ProductCategory;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface CategoryRepository extends org.springframework.data.repository.Repository<ProductCategory, Integer> {

    void save(ProductCategory category);

    ProductCategory getByName(String name);

    Collection<ProductCategory> getAll();
}

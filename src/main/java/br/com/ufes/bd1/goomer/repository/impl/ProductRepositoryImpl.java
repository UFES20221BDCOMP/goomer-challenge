package br.com.ufes.bd1.goomer.repository.impl;

import br.com.ufes.bd1.goomer.model.Product;
import br.com.ufes.bd1.goomer.model.ProductSale;
import br.com.ufes.bd1.goomer.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Optional;

public class ProductRepositoryImpl implements ProductRepository {

    private final EntityManager entityManager;

    @Autowired
    public ProductRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Integer save(Product product) {
        String sql = "insert into product (description, image_path, price, restaurant_id, sale_id, category_id) " +
                "values (?, ?, ?, ?, ?, (select id from product_category where name = ?)) returning id";

        Integer saleId = Optional.ofNullable(product.getSale()).map(ProductSale::getId).orElse(null);

        Query query = entityManager.createNativeQuery(sql);
        query.setParameter(1, product.getDescription());
        query.setParameter(2, product.getImagePath());
        query.setParameter(3, product.getPrice());
        query.setParameter(4, product.getRestaurant().getId());
        query.setParameter(5, saleId);
        query.setParameter(6, product.getProductCategory().getName());

        return (Integer) query.getSingleResult();
    }

    @Override
    public Product getById(int id) {
        return null;
    }

    @Override
    public void deleteById(int id) {

    }
}

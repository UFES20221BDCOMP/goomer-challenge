package br.com.ufes.bd1.goomer.repository.impl;

import br.com.ufes.bd1.goomer.model.Product;
import br.com.ufes.bd1.goomer.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Objects;

public class ProductRepositoryImpl implements ProductRepository {

    private final EntityManager entityManager;

    @Autowired
    public ProductRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Integer save(Product product) {
        String sql;
        boolean hasSale = Objects.nonNull(product.getSale());

        if (hasSale) {
            sql = "insert into product (description, image_path, price, restaurant_id, sale_id, category_id) " +
                    "values (?1, ?2, ?3, ?4, ?6, (select id from product_category where name = ?5)) returning id";
        }
        else {
            sql = "insert into product (description, image_path, price, restaurant_id, category_id) " +
                    "values (?1, ?2, ?3, ?4, (select id from product_category where name = ?5)) returning id";
        }
        Query query = entityManager.createNativeQuery(sql);
        query.setParameter(1, product.getDescription());
        query.setParameter(2, product.getImagePath());
        query.setParameter(3, product.getPrice());
        query.setParameter(4, product.getRestaurant().getId());
        query.setParameter(5, product.getProductCategory().getName());
        if (hasSale) {
            query.setParameter(6, product.getSale().getId());
        }

        return (Integer) query.getSingleResult();
    }

    @Override
    public Product getById(int id) {
        String sql =
                "select * " +
                        "from product p " +
                        "left join product_sale s on p.sale_id = s.id " +
                        "left join sale_validity_period v on v.sale_id = s.id " +
                        "left join timespan t on t.id = v.timespan_id " +
                        "where p.id = ?";

        Query query = entityManager.createNativeQuery(sql, Product.class);
        query.setParameter(1, id);

        return (Product) query.getSingleResult();
    }

    @Override
    public Collection<Product> getAll(){
        String sql = "select * " +
                "from product p " +
                "left join product_sale s on p.sale_id = s.id " +
                "left join sale_validity_period v on v.sale_id = s.id " +
                "left join timespan t on t.id = v.timespan_id";

        Query query = entityManager.createNativeQuery(sql, Product.class);

        return new LinkedHashSet<>(query.getResultList());
    }

    @Override
    public void deleteById(int id) {
        String sql = "delete from product where id = ?";

        Query query = entityManager.createNativeQuery(sql);
        query.setParameter(1, id);
        query.executeUpdate();
    }

    @Override
    public void update(Product product) {
        boolean hasSale = Objects.nonNull(product.getSale());
        String sql;

        if (hasSale) {
            sql = "update product set description = ?1, image_path = ?2, price = ?3, sale_id = ?6, " +
                    "category_id = (select id from product_category where name = ?4) where id = ?5";
        } else {
            sql = "update product set description = ?1, image_path = ?2, price = ?3, sale_id = null, " +
                    "category_id = (select id from product_category where name = ?4) where id = ?5";
        }
        Query query = entityManager.createNativeQuery(sql);
        query.setParameter(1, product.getDescription());
        query.setParameter(2, product.getImagePath());
        query.setParameter(3, product.getPrice());
        query.setParameter(4, product.getProductCategory().getName());
        query.setParameter(5, product.getId());
        if (hasSale) {
            query.setParameter(6, product.getSale().getId());
        }
        query.executeUpdate();
    }

}

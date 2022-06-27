package br.com.ufes.bd1.goomer.repository.impl;

import br.com.ufes.bd1.goomer.model.Product;
import br.com.ufes.bd1.goomer.model.ProductSale;
import br.com.ufes.bd1.goomer.model.Restaurant;
import br.com.ufes.bd1.goomer.repository.ProductRepository;
import br.com.ufes.bd1.goomer.repository.TimespanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
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
        String sql =
                "select * " +
                        "from product p " +
                        "inner join product_sale s on p.sale = s.id " +
                        "inner join sale_validity_period v on v.sale = s.id" +
                        "inner join timespan t on t.id = v.timespan_id " +
                        "where p.id = ?";

        Query query = entityManager.createNativeQuery(sql, Product.class);
        query.setParameter(1, id);

        return (Product) query.getSingleResult();
    }

    @Override
    public Collection<Product> getAll(){
        String sql = "select * " +
                "from product p " +
                "inner join product_sale s on p.sale = s.id " +
                "inner join sale_validity_period v on v.sale_id = s.id" +
                "inner join timespan t on t.id = v.timespan_id ";

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
        String sql = "update product"+
                " set description = ?, image_path = ?, price = ?, sale_id = ?, category_id = ?";

        Query query = entityManager.createNativeQuery(sql);
        query.setParameter(1, product.getDescription());
        query.setParameter(2, product.getImagePath());
        query.setParameter(3, product.getPrice());
        query.setParameter(4, product.getSale());
        query.setParameter(5, product.getProductCategory());
        query.executeUpdate();
    }

}

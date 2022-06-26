package br.com.ufes.bd1.goomer.repository.impl;

import br.com.ufes.bd1.goomer.model.ProductSale;
import br.com.ufes.bd1.goomer.repository.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.Query;

public class SaleRepositoryImpl implements SaleRepository {

    private final EntityManager entityManager;

    @Autowired
    public SaleRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Integer save(ProductSale productSale) {
        String sql = "insert into product_sale (description, price) values (?, ?) returning id";

        Query query = entityManager.createNativeQuery(sql);
        query.setParameter(1, productSale.getDescription());
        query.setParameter(2, productSale.getPrice());

        return (Integer) query.getSingleResult();
    }

    @Override
    public void saveValidityPeriod(int saleId, int timespanId) {
        String sql = "insert into sale_validity_period (sale_id, timespan_id) values (?, ?)";

        Query query = entityManager.createNativeQuery(sql);
        query.setParameter(1, saleId);
        query.setParameter(2, timespanId);

        query.executeUpdate();
    }

    @Override
    public void deleteById(int id) {

    }
}

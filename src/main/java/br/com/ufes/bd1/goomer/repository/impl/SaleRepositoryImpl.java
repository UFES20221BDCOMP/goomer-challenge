package br.com.ufes.bd1.goomer.repository.impl;

import br.com.ufes.bd1.goomer.model.ProductSale;
import br.com.ufes.bd1.goomer.repository.SaleRepository;
import br.com.ufes.bd1.goomer.repository.TimespanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import java.util.List;

public class SaleRepositoryImpl implements SaleRepository {

    private final EntityManager entityManager;

    @Autowired
    public SaleRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void save(ProductSale productSale) {
        try {
            String sql = "insert into product_sale (description, price) values (?, ?) returning id";

            Query query = entityManager.createNativeQuery(sql);
            query.setParameter(1, productSale.getDescription());
            query.setParameter(2, productSale.getPrice());

            productSale.setId((Integer) query.getSingleResult());
        }
        catch (Exception e) {
            throw new PersistenceException("Error saving sale");
        }
    }

    @Override
    public void saveValidityPeriod(int saleId, int timespanId) {
        try {
            String sql = "insert into sale_validity_period (sale_id, timespan_id) values (?, ?)";

            Query query = entityManager.createNativeQuery(sql);
            query.setParameter(1, saleId);
            query.setParameter(2, timespanId);

            query.executeUpdate();
        }
        catch (Exception e) {
            throw new PersistenceException("Error saving sale validity period");
        }
    }

    @Override
    public void deleteById(int id){
        try {
            String sql = "delete from product_sale where id = ?";

            Query query = entityManager.createNativeQuery(sql);
            query.setParameter(1, id);
            query.executeUpdate();
        }
        catch (Exception e) {
            throw new PersistenceException("Error deleting sale");
        }

    }

    @Override
    public void deleteSaleValidityPeriod(int saleId, int timespanId) {
        try{
            String sql = "delete from sale_validity_period where sale_id = ? and timespan_id = ?";

            Query query = entityManager.createNativeQuery(sql);
            query.setParameter(1, saleId);
            query.setParameter(2, timespanId);
            query.executeUpdate();

            getTimespanRepository().deleteById(timespanId);
        }
        catch (Exception e) {
            throw new PersistenceException("Error deleting sale validity period");
        }
    }

    @Transactional
    @Override
    public void deleteAllSaleValidityPeriods(int saleId) {
        try{
            String sql = "delete from sale_validity_period where sale_id = ? returning timespan_id";

            Query query = entityManager.createNativeQuery(sql);
            query.setParameter(1, saleId);

            List<Integer> timespanIds = query.getResultList();

            TimespanRepository timespanRepository = getTimespanRepository();
            timespanIds.forEach(timespanRepository::deleteById);
        }
        catch (Exception e) {
            throw new PersistenceException("Error deleting sale validity periods");
        }
    }

    private TimespanRepository getTimespanRepository() {
        return new TimespanRepositoryImpl(entityManager);
    }

    @Override
    public void update(ProductSale sale){
        try{
            String sql = "update product_sale set description = ?, price = ? where id = ?";

            Query query = entityManager.createNativeQuery(sql);
            query.setParameter(1, sale.getDescription());
            query.setParameter(2, sale.getPrice());
            query.executeUpdate();
        }
        catch (Exception e) {
            throw new PersistenceException("Error updating sale");
        }
    }
}

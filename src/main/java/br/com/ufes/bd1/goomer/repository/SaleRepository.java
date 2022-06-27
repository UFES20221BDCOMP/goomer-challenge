package br.com.ufes.bd1.goomer.repository;

import br.com.ufes.bd1.goomer.model.ProductSale;
import org.springframework.stereotype.Repository;

@Repository
public interface SaleRepository extends org.springframework.data.repository.Repository<ProductSale, Integer>{

    Integer save(ProductSale productSale);

    void saveValidityPeriod(int saleId, int timespanId);

    void deleteById(int id);

    void deleteSalesValidityPeriod(int saleId, int timespanId);

    void deleteAllSaleValidityPeriods(int saleId);

    void update(ProductSale sale);
}

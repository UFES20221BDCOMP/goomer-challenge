package br.com.ufes.bd1.goomer.service;

import br.com.ufes.bd1.goomer.model.Product;
import br.com.ufes.bd1.goomer.model.ProductSale;
import br.com.ufes.bd1.goomer.repository.ProductRepository;
import br.com.ufes.bd1.goomer.repository.SaleRepository;
import br.com.ufes.bd1.goomer.repository.TimespanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    private final SaleRepository saleRepository;

    private final TimespanRepository timespanRepository;


    @Autowired
    public ProductService(ProductRepository productRepository, SaleRepository saleRepository, TimespanRepository timespanRepository) {
        this.productRepository = productRepository;
        this.saleRepository = saleRepository;
        this.timespanRepository = timespanRepository;
    }

    @Transactional
    public void save(Product product) {
        Optional.ofNullable(product.getSale()).ifPresent(sale -> {
            sale.setId(saleRepository.save(sale));

            sale.getSaleValidityPeriods().forEach(timespan -> {
                timespan.setId(timespanRepository.save(timespan));

                saleRepository.saveValidityPeriod(sale.getId(), timespan.getId());
            });
        });
        product.setId(productRepository.save(product));
    }

    @Transactional
    public void update(Product updated) {
        Product original = productRepository.getById(updated.getId());

        ProductSale originalSale = original.getSale();
        ProductSale updatedSale = updated.getSale();

        if (!original.equals(updated)) {
            productRepository.update(updated);
        }
        if (!compareSales(originalSale, updated.getSale())) {
            if (Objects.nonNull(originalSale)) {
                saleRepository.deleteAllSaleValidityPeriods(originalSale.getId());
                saleRepository.deleteById(originalSale.getId());
            }
            if (Objects.nonNull(updated.getSale())) {
                updatedSale.setId(saleRepository.save(updatedSale));

                updatedSale.getSaleValidityPeriods().forEach(timespan -> {
                    timespan.setId(timespanRepository.save(timespan));
                    saleRepository.saveValidityPeriod(updatedSale.getId(), timespan.getId());
                });
            }
        }
    }

    private boolean compareSales(ProductSale originalSale, ProductSale updatedSale) {
        if (Objects.isNull(originalSale) || Objects.isNull(updatedSale)) {
            return Objects.isNull(originalSale) && Objects.isNull(updatedSale);
        }
        return originalSale.equals(updatedSale);
    }

    @Transactional
    public void deleteById(Integer id) {
        Product product = productRepository.getById(id);
        ProductSale sale = product.getSale();

        saleRepository.deleteAllSaleValidityPeriods(sale.getId());
        saleRepository.deleteById(sale.getId());
        productRepository.deleteById(product.getId());
    }
}

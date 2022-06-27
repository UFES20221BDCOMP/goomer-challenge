package br.com.ufes.bd1.goomer.service;

import br.com.ufes.bd1.goomer.model.Product;
import br.com.ufes.bd1.goomer.model.ProductSale;
import br.com.ufes.bd1.goomer.repository.ProductRepository;
import br.com.ufes.bd1.goomer.repository.SaleRepository;
import br.com.ufes.bd1.goomer.repository.TimespanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
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

    public Product getById(Integer id) {
        return productRepository.getById(id);
    }

    public Collection<Product> getAll() {
        return productRepository.getAll();
    }

    @Transactional
    public void update(Product updated) {
        Product original = productRepository.getById(updated.getId());

        ProductSale originalSale = original.getSale();
        ProductSale updatedSale = updated.getSale();

        if (!compareSales(originalSale, updatedSale)) {
            if (Objects.nonNull(originalSale)) {
                original.setSale(null);
                productRepository.update(original);

                saleRepository.deleteAllSaleValidityPeriods(originalSale.getId());
                saleRepository.deleteById(originalSale.getId());
            }
            if (Objects.nonNull(updatedSale)) {
                updatedSale.setId(saleRepository.save(updatedSale));

                updatedSale.getSaleValidityPeriods().forEach(timespan -> {
                    timespan.setId(timespanRepository.save(timespan));
                    saleRepository.saveValidityPeriod(updatedSale.getId(), timespan.getId());
                });
            }
        }
        else if (Objects.nonNull(originalSale)) {
            updatedSale.setId(originalSale.getId());
        }
        productRepository.update(updated);
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

        productRepository.deleteById(product.getId());

        if (Objects.nonNull(sale)) {
            saleRepository.deleteAllSaleValidityPeriods(sale.getId());
            saleRepository.deleteById(sale.getId());
        }
    }
}

package br.com.ufes.bd1.goomer.service;

import br.com.ufes.bd1.goomer.model.Product;
import br.com.ufes.bd1.goomer.repository.ProductRepository;
import br.com.ufes.bd1.goomer.repository.SaleRepository;
import br.com.ufes.bd1.goomer.repository.TimespanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}

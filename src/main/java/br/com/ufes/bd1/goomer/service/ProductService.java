package br.com.ufes.bd1.goomer.service;

import br.com.ufes.bd1.goomer.exception.ProvidedDataInconsistencyException;
import br.com.ufes.bd1.goomer.model.Product;
import br.com.ufes.bd1.goomer.model.ProductCategory;
import br.com.ufes.bd1.goomer.model.ProductSale;
import br.com.ufes.bd1.goomer.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    private final SaleRepository saleRepository;

    private final TimespanRepository timespanRepository;

    private final RestaurantRepository restaurantRepository;

    private final CategoryRepository categoryRepository;


    @Autowired
    public ProductService(ProductRepository productRepository, SaleRepository saleRepository,
                          TimespanRepository timespanRepository, RestaurantRepository restaurantRepository,
                          CategoryRepository categoryRepository) {

        this.productRepository = productRepository;
        this.saleRepository = saleRepository;
        this.timespanRepository = timespanRepository;
        this.restaurantRepository = restaurantRepository;
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    public void save(Product product) {
        restaurantRepository.getById(product.getRestaurant().getId());

        ProductCategory productCategory = categoryRepository.getByName(product.getProductCategory().getName());
        product.setProductCategory(productCategory);

        Optional.ofNullable(product.getSale()).ifPresent(sale -> {
            saleRepository.save(sale);

            sale.getSaleValidityPeriods().forEach(timespan -> {
                timespanRepository.save(timespan);

                saleRepository.saveValidityPeriod(sale.getId(), timespan.getId());
            });
        });
        productRepository.save(product);
    }

    public Product getById(Integer id) {
        return productRepository.getById(id);
    }

    public Collection<Product> getAll() {
        return productRepository.getAll();
    }

    public Collection<Product> getByRestaurantId(int id) {
        return productRepository.getByRestaurantId(id);
    }

    @Transactional
    public void update(Product updated) {
        if (Objects.isNull(updated.getId())) {
            throw new ProvidedDataInconsistencyException("product id must be provided in order to update");
        }

        Product original = productRepository.getById(updated.getId());

        ProductCategory originalCategory = original.getProductCategory();
        ProductCategory updatedCategory = updated.getProductCategory();

        if (!originalCategory.getName().equals(updatedCategory.getName())) {
            updated.setProductCategory(
                    categoryRepository.getByName(updatedCategory.getName()));
        }
        else {
            updatedCategory.setId(originalCategory.getId());
        }

        if (!original.getRestaurant().getId().equals(updated.getRestaurant().getId())) {
            throw new ProvidedDataInconsistencyException("restaurant cannot be changed " +
                    "--product belongs to another restaurant (id = " + original.getRestaurant().getId() + ")");
        }

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
                saleRepository.save(updatedSale);

                updatedSale.getSaleValidityPeriods().forEach(timespan -> {
                    timespanRepository.save(timespan);
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

    public void deleteAllByRestaurantId(Integer restaurantId) {
        List<Integer> saleIds = productRepository.deleteAllByRestaurantId(restaurantId);

        saleIds.forEach(saleId -> {
            saleRepository.deleteAllSaleValidityPeriods(saleId);
            saleRepository.deleteById(saleId);
        });
    }
}

package br.com.ufes.bd1.goomer.repository.impl;

import br.com.ufes.bd1.goomer.exception.CategoryDoesNotExistException;
import br.com.ufes.bd1.goomer.model.ProductCategory;
import br.com.ufes.bd1.goomer.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import java.util.Collection;

public class CategoryRepositoryImpl implements CategoryRepository {

    private final EntityManager entityManager;


    @Autowired
    public CategoryRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void save(ProductCategory category) {
        try {
            String sql = "insert into product_category (name) values (?) returning id";

            Query query = entityManager.createNativeQuery(sql);
            query.setParameter(1, category.getName());

            category.setId((Integer) query.getSingleResult());
        }
        catch (Exception e) {
            throw new PersistenceException("Error saving category");
        }
    }

    @Override
    public ProductCategory getByName(String name) {
        try {
            String sql = "select * from product_category where name = ?";

            Query query = entityManager.createNativeQuery(sql, ProductCategory.class);
            query.setParameter(1, name);

            return (ProductCategory) query.getSingleResult();
        }
        catch (NoResultException e) {
            throw new CategoryDoesNotExistException("No category found with name " + name);
        }
        catch (Exception e) {
            throw new PersistenceException("Error getting category");
        }
    }

    @Override
    public Collection<ProductCategory> getAll() {
        try {
            String sql = "select * from product_category";

            Query query = entityManager.createNativeQuery(sql, ProductCategory.class);

            return query.getResultList();
        }
        catch (Exception e) {
            throw new PersistenceException("Error getting categories");
        }
    }
}

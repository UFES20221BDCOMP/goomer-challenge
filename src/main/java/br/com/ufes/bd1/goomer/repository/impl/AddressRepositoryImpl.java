package br.com.ufes.bd1.goomer.repository.impl;

import br.com.ufes.bd1.goomer.model.Address;
import br.com.ufes.bd1.goomer.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

public class AddressRepositoryImpl implements AddressRepository {

    private final EntityManager entityManager;


    @Autowired
    public AddressRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void save(Address address) {
        try {
            String sql = "insert into address (address_line, neighborhood, city, state, zip_code) " +
                    "values (?, ?, ?, ?, ?) returning id";

            Query query = entityManager.createNativeQuery(sql);
            query.setParameter(1, address.getAddressLine());
            query.setParameter(2, address.getNeighborhood());
            query.setParameter(3, address.getCity());
            query.setParameter(4, address.getState());
            query.setParameter(5, address.getZipCode());

            address.setId((Integer) query.getSingleResult());
        }
        catch (Exception e) {
            throw new PersistenceException("Error saving address");
        }
    }

    @Override
    public void deleteById(int id){
        try {
            String sql = "delete from address a where a.id = ? ";

            Query query = entityManager.createNativeQuery(sql);
            query.setParameter(1, id);
            query.executeUpdate();
        }
        catch (Exception e) {
            throw new PersistenceException("Error saving address");
        }
    }

    @Override
    public void update(Address address){
        try {
            String sql = "update address set address_line = ?, neighborhood = ?, city = ?, state = ?, zip_code = ? where id = ?";

            Query query = entityManager.createNativeQuery(sql);
            query.setParameter(1, address.getAddressLine());
            query.setParameter(2, address.getNeighborhood());
            query.setParameter(3, address.getCity());
            query.setParameter(4, address.getState());
            query.setParameter(5, address.getZipCode());
            query.setParameter(6, address.getId());

            query.executeUpdate();
        }
        catch (Exception e) {
            throw new PersistenceException("Error saving address");
        }
    }
}

package br.com.ufes.bd1.goomer.repository;

import br.com.ufes.bd1.goomer.model.Address;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends org.springframework.data.repository.Repository<Address, Integer> {

    Integer save(Address address);

    void deleteById(int id);

    public void update(Address address);
}

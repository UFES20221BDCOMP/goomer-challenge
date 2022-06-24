package br.com.ufes.bd1.goomer.repository;

import br.com.ufes.bd1.goomer.model.Timespan;
import org.springframework.stereotype.Repository;

@Repository
public interface TimespanRepository extends org.springframework.data.repository.Repository<Timespan, Integer> {

    Integer save(Timespan timespan);
}
//mateus e bruno
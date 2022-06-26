package br.com.ufes.bd1.goomer.repository.impl;

import br.com.ufes.bd1.goomer.model.Timespan;
import br.com.ufes.bd1.goomer.repository.TimespanRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.Query;

public class TimespanRepositoryImpl implements TimespanRepository {

    private final EntityManager entityManager;


    @Autowired
    public TimespanRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Integer save(Timespan timespan) {
        String sql = "insert into timespan (weekday_start, weekday_end, time_start, time_end) values (?, ?, ?, ?) returning id";

        Query query = entityManager.createNativeQuery(sql);
        query.setParameter(1, timespan.getWeekdayStart().name());
        query.setParameter(2, timespan.getWeekdayEnd().name());
        query.setParameter(3, timespan.getTimeStart());
        query.setParameter(4, timespan.getTimeEnd());

        return (Integer) query.getSingleResult();
    }

    @Override
    public void deleteById(Integer id){
        String sql = "delete from timespan t where t.id = ?";
        
        Query query = entityManager.createNativeQuery(sql);
        query.setParameter(1, id);
        query.executeUpdate();
    }
    
    @Override
    public void update(Timespan timespan){
        String sql = "update timespan set weekday_start = ?, weekday_end = ?, time_start = ?, time_end = ?";
        
        Query query = entityManager.createNativeQuery(sql);
        query.setParameter(1, timespan.getWeekdayStart().name());
        query.setParameter(2, timespan.getWeekdayEnd().name());
        query.setParameter(3, timespan.getTimeStart());
        query.setParameter(4, timespan.getTimeEnd());

        query.executeUpdate();
    }
}

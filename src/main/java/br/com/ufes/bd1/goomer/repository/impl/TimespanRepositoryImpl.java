package br.com.ufes.bd1.goomer.repository.impl;

import br.com.ufes.bd1.goomer.model.Timespan;
import br.com.ufes.bd1.goomer.repository.TimespanRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

public class TimespanRepositoryImpl implements TimespanRepository {

    private final EntityManager entityManager;


    @Autowired
    public TimespanRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void save(Timespan timespan) {
        try {
            String sql = "insert into timespan (weekday_start, weekday_end, time_start, time_end) values (?, ?, ?, ?) " +
                    "returning id";

            Query query = entityManager.createNativeQuery(sql);
            query.setParameter(1, timespan.getWeekdayStart().name());
            query.setParameter(2, timespan.getWeekdayEnd().name());
            query.setParameter(3, timespan.getTimeStart());
            query.setParameter(4, timespan.getTimeEnd());

            timespan.setId((Integer) query.getSingleResult());
        }
        catch (Exception e) {
            throw new PersistenceException("Error saving timespan");
        }
    }

    @Override
    public void deleteById(Integer id) {
        try {
            String sql = "delete from timespan where id = ?";

            Query query = entityManager.createNativeQuery(sql);
            query.setParameter(1, id);
            query.executeUpdate();
        }
        catch (Exception e) {
            throw new PersistenceException("Error deleting timespan");
        }
    }
    
    @Override
    public void update(Timespan timespan){
        try {
            String sql = "update timespan set weekday_start = ?, weekday_end = ?, time_start = ?, time_end = ? where id = ?";

            Query query = entityManager.createNativeQuery(sql);
            query.setParameter(1, timespan.getWeekdayStart().name());
            query.setParameter(2, timespan.getWeekdayEnd().name());
            query.setParameter(3, timespan.getTimeStart());
            query.setParameter(4, timespan.getTimeEnd());
            query.setParameter(5, timespan.getId());

            query.executeUpdate();
        }
        catch (Exception e) {
            throw new PersistenceException("Error updating timespan");
        }
    }
}

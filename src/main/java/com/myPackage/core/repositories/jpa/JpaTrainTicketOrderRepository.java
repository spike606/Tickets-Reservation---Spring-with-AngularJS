package com.myPackage.core.repositories.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.myPackage.core.entities.TrainTicketOrder;
import com.myPackage.core.repositories.TrainTicketOrderRepository;

@Repository
public class JpaTrainTicketOrderRepository implements TrainTicketOrderRepository {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public TrainTicketOrder createTrainTicketOrder(TrainTicketOrder data) {
		entityManager.persist(data);
		return data;
	}

	@Override
	public List<TrainTicketOrder> findAllTrainTicketOrders() {
        Query query = entityManager.createQuery("SELECT o FROM TrainTicket o order by id");
        return query.getResultList();
	}

	@Override
	public TrainTicketOrder findTrainTicketOrder(Long id) {
		return entityManager.find(TrainTicketOrder.class, id);

	}

	@Override
	public TrainTicketOrder deleteTrainTicketOrder(Long id) {
		TrainTicketOrder trainTicketOrder = entityManager.find(TrainTicketOrder.class, id);
		entityManager.remove(trainTicketOrder);
		return trainTicketOrder;
	}
	@Override
	public List<TrainTicketOrder> findAllTrainTicketOrdersForAccount(Long accountId) {
        Query query = entityManager.createQuery("SELECT o FROM TrainTicket o where a.owner.id=?1 order by id");
        query.setParameter(1, accountId);
        List<TrainTicketOrder> trainTicketOrder = query.getResultList();
        if(trainTicketOrder.size() == 0) {
            return null;
        } else {
            return trainTicketOrder;
        }
	}

}

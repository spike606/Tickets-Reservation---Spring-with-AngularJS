package com.myPackage.core.repositories.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.myPackage.core.entities.PlaneTicketOrder;
import com.myPackage.core.entities.TrainTicketOrder;
import com.myPackage.core.repositories.PlaneTicketOrderRepository;

@Repository
public class JpaPlaneTicketOrderRepository implements PlaneTicketOrderRepository {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public PlaneTicketOrder createPlaneTicketOrder(PlaneTicketOrder data) {
		entityManager.persist(data);
		return data;
	}

	@Override
	public List<PlaneTicketOrder> findAllPlaneTicketOrders() {
        Query query = entityManager.createQuery("SELECT o FROM PlaneTicket o order by id");
        return query.getResultList();
	}

	@Override
	public PlaneTicketOrder deletePlaneTicketOrder(Long id) {
		PlaneTicketOrder planeTicketOrder = entityManager.find(PlaneTicketOrder.class, id);
		entityManager.remove(planeTicketOrder);
		return planeTicketOrder;
	}

	@Override
	public PlaneTicketOrder findPlaneTicketOrder(Long id) {
		return entityManager.find(PlaneTicketOrder.class, id);

	}
	@Override
	public List<PlaneTicketOrder> findAllPlaneTicketOrdersForAccount(Long accountId) {
        Query query = entityManager.createQuery("SELECT o FROM PlaneTicket o where a.owner.id=?1 order by id");
        query.setParameter(1, accountId);
        List<PlaneTicketOrder> planeTicketOrder = query.getResultList();
        if(planeTicketOrder.size() == 0) {
            return null;
        } else {
            return planeTicketOrder;
        }
	}



}

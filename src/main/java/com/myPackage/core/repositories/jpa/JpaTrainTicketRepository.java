package com.myPackage.core.repositories.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.myPackage.core.entities.TrainTicket;
import com.myPackage.core.repositories.TrainTicketRepository;

@Repository
public class JpaTrainTicketRepository implements TrainTicketRepository {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public TrainTicket createTrainTicket(TrainTicket data) {
		entityManager.persist(data);
		return data;
	}

	@Override
	public List<TrainTicket> findAllTrainTickets() {
        Query query = entityManager.createQuery("SELECT a FROM TrainTicket a order by id");
        return query.getResultList();
	}

	@Override
	public TrainTicket findTrainTicket(Long id) {
		return entityManager.find(TrainTicket.class, id);

	}

	@Override
	public TrainTicket deleteTrainTicket(Long id) {
		TrainTicket trainTicket = entityManager.find(TrainTicket.class, id);
		entityManager.remove(trainTicket);
		return trainTicket;
	}

	@Override
	public TrainTicket updateTrainTicket(Long id, TrainTicket data) {
		TrainTicket trainTicket = entityManager.find(TrainTicket.class, id);
		trainTicket.setTransitFrom(data.getTransitFrom());
		trainTicket.setTransitTo(data.getTransitTo());
		trainTicket.setTransitDateStart(data.getTransitDateStart());
		trainTicket.setTransitDateStop(data.getTransitDateStop());
		trainTicket.setTransitHourStart(data.getTransitHourStart());
		trainTicket.setTransitHourStop(data.getTransitHourStop());
		trainTicket.setTransitNumber(data.getTransitNumber());
		trainTicket.setTransitPrice(data.getTransitPrice());
		trainTicket.setTransitName(data.getTransitName());

		return trainTicket;
	}

}

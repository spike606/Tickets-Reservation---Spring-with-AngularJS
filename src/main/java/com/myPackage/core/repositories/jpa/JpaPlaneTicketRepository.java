package com.myPackage.core.repositories.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.myPackage.core.entities.PlaneTicket;
import com.myPackage.core.repositories.PlaneTicketRepository;

@Repository
public class JpaPlaneTicketRepository implements PlaneTicketRepository {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public PlaneTicket createPlaneTicket(PlaneTicket data) {
		entityManager.persist(data);
		return data;
	}

	@Override
	public List<PlaneTicket> findAllPlaneTickets() {
        Query query = entityManager.createQuery("SELECT a FROM PlaneTicket a order by id");
        return query.getResultList();
	}

	@Override
	public PlaneTicket findPlaneTicket(Long id) {
		return entityManager.find(PlaneTicket.class, id);
	}

	@Override
	public PlaneTicket deletePlaneTicket(Long id) {
		PlaneTicket planeTicket = entityManager.find(PlaneTicket.class, id);
		entityManager.remove(planeTicket);
		return planeTicket;
	}

	@Override
	public PlaneTicket updatePlaneTicket(Long id, PlaneTicket data) {
		PlaneTicket planeticket = entityManager.find(PlaneTicket.class, id);
		planeticket.setFlightFrom(data.getFlightFrom());
		planeticket.setFlightTo(data.getFlightTo());
		planeticket.setFlightDateStart(data.getFlightDateStart());
		planeticket.setFlightDateStop(data.getFlightDateStop());
		planeticket.setFlightHourStart(data.getFlightHourStart());
		planeticket.setFlightHourStop(data.getFlightHourStop());
		planeticket.setFlightNumber(data.getFlightNumber());
		planeticket.setFlightPrice(data.getFlightPrice());
		return planeticket;
	}

}

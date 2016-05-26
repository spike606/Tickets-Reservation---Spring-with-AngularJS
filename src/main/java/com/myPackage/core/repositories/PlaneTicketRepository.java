package com.myPackage.core.repositories;

import java.util.List;

import com.myPackage.core.entities.PlaneTicket;

public interface PlaneTicketRepository {
    public PlaneTicket createPlaneTicket(PlaneTicket data);
    public List<PlaneTicket> findAllPlaneTickets();
    public PlaneTicket findPlaneTicket(Long id);
    public PlaneTicket deletePlaneTicket(Long id);
    public PlaneTicket updatePlaneTicket(Long id, PlaneTicket data);
}

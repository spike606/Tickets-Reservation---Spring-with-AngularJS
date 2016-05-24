package com.myPackage.core.services;

import com.myPackage.core.entities.PlaneTicket;
import com.myPackage.core.services.util.PlaneTicketList;

public interface PlaneTicketService {
    public PlaneTicket createPlaneTicket(PlaneTicket data);
    public PlaneTicketList findAllPlaneTickets();
    public PlaneTicket findPlaneTicket(Long id);
    public PlaneTicket deletePlaneTicket(Long id);
    public PlaneTicket updatePlaneTicket(Long id, PlaneTicket data);

}

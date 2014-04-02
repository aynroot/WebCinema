package com.aynroot.cinemamanager.dao;

import com.aynroot.cinemamanager.domain.Film;
import com.aynroot.cinemamanager.domain.Hall;
import com.aynroot.cinemamanager.domain.HallRow;
import com.aynroot.cinemamanager.domain.Ticket;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class TicketDAO {
    @PersistenceContext
    private EntityManager em;

    @SuppressWarnings("unchecked")
    public List<Ticket> listShowTickets(Long showId) {
        Query q = em.createQuery("SELECT ticket FROM Ticket ticket WHERE ticket.showId = :showId", Ticket.class);
        q.setParameter("showId", showId);
        return q.getResultList();
    }

    public void createTickets(Long showId, List<HallRow> rows, Float price) {
        for (HallRow row : rows) {
            Ticket ticket = new Ticket();
            ticket.setPrice(price);
            ticket.setIsOrdered(false);
            ticket.setRowId(row.id);
            for (Integer i = 1; i <= row.nSeats; i++)
                ticket.setSeat(i);
            ticket.setShowId(showId);
            em.persist(ticket);
        }
        em.flush();
    }
}
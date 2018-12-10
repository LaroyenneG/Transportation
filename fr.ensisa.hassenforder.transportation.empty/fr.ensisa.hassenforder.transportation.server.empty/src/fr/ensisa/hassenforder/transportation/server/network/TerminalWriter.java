package fr.ensisa.hassenforder.transportation.server.network;

import fr.ensisa.hassenforder.network.BasicAbstractWriter;
import fr.ensisa.hassenforder.transportation.server.model.*;
import fr.ensisa.hassenforder.transportation.terminal.network.Protocol;

import java.io.OutputStream;
import java.util.List;

public class TerminalWriter extends BasicAbstractWriter {

    public TerminalWriter(OutputStream outputStream) {
        super(outputStream);
    }

    public void writePass(Pass pass) {
        writeInt(Protocol.REPLY_PASS);
        writeLong(pass.getPassId());
        writeString(pass.getDescription());


        List<Ticket> tickets = pass.getTickets();

        writeLong(tickets.size());

        for (Ticket ticket : tickets) {
            writeTicket(ticket);
        }
    }

    private void writeTicket(Ticket ticket) {

        if (ticket instanceof Route) {
            writeRouteTicket((Route) ticket);
        }

        if (ticket instanceof Urban) {
            writeUrbanTicket((Urban) ticket);
        }

        if (ticket instanceof Subscription) {
            writeSubscription((Subscription) ticket);
        }
    }

    private void writeSubscription(Subscription subscription) {
        writeInt(Ticket.Type.SUBSCRIPTION.ordinal());
        writeString(subscription.getTicketId());
        writeInt(subscription.getMonth().ordinal());
        writeInt(subscription.getUsed());
    }

    private void writeUrbanTicket(Urban urban) {
        writeInt(Ticket.Type.URBAN.ordinal());
        writeString(urban.getTicketId());
        writeInt(urban.getCount());
        writeInt(urban.getUsed());
    }

    private void writeRouteTicket(Route route) {
        writeInt(Ticket.Type.ROUTE.ordinal());
        writeString(route.getTicketId());
        writeString(route.getFrom());
        writeString(route.getTo());
        writeInt(route.getCount());
        writeInt(route.getUsed());
    }

    public void writeKO() { writeInt(Protocol.REPLY_KO); }

    public void writeOK() {
        writeInt(Protocol.REPLY_OK);
    }
}

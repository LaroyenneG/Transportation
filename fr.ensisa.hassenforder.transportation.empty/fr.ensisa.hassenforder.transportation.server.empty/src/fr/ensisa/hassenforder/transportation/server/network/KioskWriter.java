package fr.ensisa.hassenforder.transportation.server.network;

import fr.ensisa.hassenforder.network.BasicAbstractWriter;
import fr.ensisa.hassenforder.transportation.kiosk.network.Protocol;
import fr.ensisa.hassenforder.transportation.server.model.*;

import java.io.OutputStream;
import java.util.List;

public class KioskWriter extends BasicAbstractWriter {

    public KioskWriter(OutputStream outputStream) {
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

    public void writeTransaction(Transaction transaction) {

        writeInt(Protocol.REPLY_TRANSACTION);

        writeLong(transaction.getId());
        writeInt(transaction.getAmount());
    }

    public void writeOK() {
        writeInt(Protocol.REPLY_OK);
    }

    public void writeKO() {
        writeInt(Protocol.REPLY_KO);
    }

    public void writePassId(long passId) {
        writeInt(Protocol.REPLY_BUY);
        writeLong(passId);
    }
}

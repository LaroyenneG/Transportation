package fr.ensisa.hassenforder.transportation.kiosk.network;

import java.io.InputStream;
import fr.ensisa.hassenforder.network.BasicAbstractReader;

import fr.ensisa.hassenforder.transportation.kiosk.network.Protocol;
import fr.ensisa.hassenforder.transportation.kiosk.model.Pass;
import fr.ensisa.hassenforder.transportation.kiosk.model.Ticket;
import fr.ensisa.hassenforder.transportation.kiosk.model.Transaction;

public class KioskReader extends BasicAbstractReader {

    private Pass pass;
    private Transaction transaction;

    public KioskReader(InputStream inputStream) {
        super(inputStream);
    }

    public void receive() {
        type = readInt();
        switch (type) {
            case Protocol.REPLY_OK:
                break;

            case Protocol.REPLY_KO:
                break;

            case Protocol.REPLY_PASS:
                readPass();
                break;

            case Protocol.REPLY_TRANSACTION:
                readTransaction();
                break;
        }
    }

    public Pass getPass() {
        return this.pass;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    private void readPass(){
        long passId = readLong();
        String description = readString();

        pass = new Pass(passId, description);
        long nbTicket = readLong();

        for(int i = 0; i < nbTicket; i++){
            pass.addTicket(readTicket());
        }
    }

    private Ticket readTicket() {
        Ticket.Type type = Ticket.Type.values()[readInt()];

        switch (type){
            case ROUTE:
                return readRoute();
            case URBAN:
                return readUrban();
            case SUBSCRIPTION:
                return readSubscription();
        }

        throw new IllegalStateException();
    }

    private Ticket readRoute(){
        String id = readString();
        String from = readString();
        String to = readString();
        int count = readInt();
        int used = readInt();

        return new Ticket(id, from, to, count, used);
    }

    private Ticket readUrban(){
        String id = readString();
        int count = readInt();
        int used = readInt();

        return new Ticket(id, count, used);
    }

    private Ticket readSubscription(){
        String id = readString();
        Ticket.Month month = Ticket.Month.values()[readInt()];
        int used = readInt();

        return new Ticket(id, month, used);
    }

    private void readTransaction(){
        long id = readLong();
        int amount = readInt();

        transaction = new Transaction(id, amount);
    }

}

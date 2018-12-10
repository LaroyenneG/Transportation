package fr.ensisa.hassenforder.transportation.server.network;

import fr.ensisa.hassenforder.network.BasicAbstractWriter;
import fr.ensisa.hassenforder.transportation.bank.network.Protocol;

import java.io.OutputStream;

public class BankWriter extends BasicAbstractWriter {

    public BankWriter(OutputStream outputStream) {
        super(outputStream);
    }

    public void createBuy(long cardId, int amount) {
        writeInt(Protocol.REQ_DEBIT);
        writeLong(cardId);
        writeInt(amount);
    }
}

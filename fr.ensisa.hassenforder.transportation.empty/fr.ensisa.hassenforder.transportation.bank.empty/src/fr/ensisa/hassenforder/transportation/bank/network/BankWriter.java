package fr.ensisa.hassenforder.transportation.bank.network;

import fr.ensisa.hassenforder.network.BasicAbstractWriter;

import java.io.OutputStream;

public class BankWriter extends BasicAbstractWriter {

    public BankWriter(OutputStream outputStream) {
        super(outputStream);
    }

    public void writeKO() {
        writeInt(Protocol.REPLY_OK);
    }

    public void writeOK() {
        writeInt(Protocol.REPLY_OK);
    }
}

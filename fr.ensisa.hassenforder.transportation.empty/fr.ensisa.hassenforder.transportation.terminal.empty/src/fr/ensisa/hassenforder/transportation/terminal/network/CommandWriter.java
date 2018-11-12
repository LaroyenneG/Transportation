package fr.ensisa.hassenforder.transportation.terminal.network;

import java.io.OutputStream;

import fr.ensisa.hassenforder.network.BasicAbstractWriter;
import fr.ensisa.hassenforder.transportation.terminal.network.Protocol;

public class CommandWriter extends BasicAbstractWriter {

    public CommandWriter(OutputStream outputStream) {
        super(outputStream);
    }

    public void createFetch(long passId){
        writeInt(Protocol.REQ_FETCH);
        writeLong(passId);
    }

    public void createUseTicket(long passId, String ticketId, int count) {
        writeInt(Protocol.REQ_USE_TICKET);
        writeLong(passId);
        writeString(ticketId);
        writeInt(count);
    }
}

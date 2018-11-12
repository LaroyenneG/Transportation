package fr.ensisa.hassenforder.transportation.server.network;

import fr.ensisa.hassenforder.network.BasicAbstractReader;
import fr.ensisa.hassenforder.transportation.terminal.network.Protocol;

import java.io.InputStream;

public class TerminalReader extends BasicAbstractReader {

    private String idTicket;
    private long idPass;
    private int count;


    public TerminalReader(InputStream inputStream) {
        super(inputStream);
        idPass = -1;
        idTicket = "";
        count = -1;
    }

    public void receive() {
        type = readInt();
        switch (type) {

            case Protocol
                    .REQ_FETCH:
                readFetch();
                break;

            case Protocol.REQ_USE_TICKET:
                readUseTicket();
                break;

            default:
                type = 0;
                break;
        }
    }

    private void readUseTicket() {
        idPass = readInt();
        idTicket = readString();
        count = readInt();
    }

    private void readFetch() {
        idPass = readLong();
    }

    public long getIdPass() {
        return idPass;
    }

    public String getTicketId() {
        return idTicket;
    }

    public int getCount() {
        return count;
    }
}

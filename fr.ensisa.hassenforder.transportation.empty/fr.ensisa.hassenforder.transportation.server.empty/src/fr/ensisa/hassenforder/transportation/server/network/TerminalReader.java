package fr.ensisa.hassenforder.transportation.server.network;

import fr.ensisa.hassenforder.network.BasicAbstractReader;
import fr.ensisa.hassenforder.transportation.terminal.network.Protocol;

import java.io.InputStream;

public class TerminalReader extends BasicAbstractReader {

    private long idPass;

    public TerminalReader(InputStream inputStream) {
        super(inputStream);
        idPass = -1;
    }

    public void receive() {
        type = readInt();
        switch (type) {

            case Protocol
                    .REQ_FETCH:
                readFetch();
                break;

            default:
                type = 0;
                break;
        }
    }

    private void readFetch() {
        idPass = readLong();
    }

    public long getIdPass() {
        return idPass;
    }
}

package fr.ensisa.hassenforder.transportation.server.network;

import fr.ensisa.hassenforder.network.BasicAbstractReader;
import fr.ensisa.hassenforder.transportation.bank.network.Protocol;

import java.io.InputStream;

public class BankReader extends BasicAbstractReader {

    public BankReader(InputStream inputStream) {
        super(inputStream);
    }

    public void receive() {

        type = readInt();
        switch (type) {

            case Protocol.REPLY_OK:
                // nothing to read
                break;

            case Protocol.REPLY_KO:
                // nothing to read
                break;

            default:
                type = 0; // un know, error case
                break;
        }
    }
}

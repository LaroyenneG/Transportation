package fr.ensisa.hassenforder.transportation.server.network;

import fr.ensisa.hassenforder.network.BasicAbstractReader;
import fr.ensisa.hassenforder.transportation.kiosk.network.Protocol;

import java.io.InputStream;

public class KioskReader extends BasicAbstractReader {

    public KioskReader(InputStream inputStream) {
        super(inputStream);
    }

    public void receive() {
        type = readInt();
        switch (type) {

            case Protocol.REQ_FETCH:
                readFetch();
                break;

            case Protocol.REQ_BUY_ROUTE:
                readBuyRoute();
                break;

            case Protocol.REQ_BUY_URBAN:
                readBuyUrban();
                break;

            default:
                type = 0;
                break;
        }
    }

    private void readBuyUrban() {
    }

    private void readBuyRoute() {
    }

    private void readFetch() {
    }

}

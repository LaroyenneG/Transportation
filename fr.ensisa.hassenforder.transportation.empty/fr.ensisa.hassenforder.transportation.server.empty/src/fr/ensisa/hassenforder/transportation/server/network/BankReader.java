package fr.ensisa.hassenforder.transportation.server.network;

import fr.ensisa.hassenforder.network.BasicAbstractReader;

import java.io.InputStream;

public class BankReader extends BasicAbstractReader {

    public BankReader(InputStream inputStream) {
        super(inputStream);
    }

    public void receive() {
        type = readInt();
        switch (type) {


            default:
                type = 0;
                break;
        }
    }

}

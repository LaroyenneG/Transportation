package fr.ensisa.hassenforder.transportation.terminal.network;

import java.io.InputStream;
import fr.ensisa.hassenforder.network.BasicAbstractReader;

import fr.ensisa.hassenforder.transportation.terminal.network.Protocol;
import fr.ensisa.hassenforder.transportation.terminal.model.Pass;
import fr.ensisa.hassenforder.transportation.terminal.model.Ticket;

public class CommandReader extends BasicAbstractReader {

    private Pass pass;

    public CommandReader(InputStream inputStream) {
        super(inputStream);

    }

    public void receive() {
        type = readInt();
        switch (type) {
            case Protocol.REPLY_PASS:
                readPass();
        }
    }

    public Pass getPass() {
        return null;
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
        return null; // TODO
    }
}

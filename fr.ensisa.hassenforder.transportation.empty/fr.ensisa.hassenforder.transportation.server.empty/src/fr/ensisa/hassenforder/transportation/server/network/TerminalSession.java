package fr.ensisa.hassenforder.transportation.server.network;

import fr.ensisa.hassenforder.transportation.server.NetworkListener;
import fr.ensisa.hassenforder.transportation.server.model.Pass;
import fr.ensisa.hassenforder.transportation.terminal.network.Protocol;

import java.io.IOException;
import java.net.Socket;


public class TerminalSession extends Thread {

    private Socket connection;
    private NetworkListener listener;

    public TerminalSession(Socket connection, NetworkListener listener) {
        this.connection = connection;
        this.listener = listener;
        if (listener == null) throw new RuntimeException("listener cannot be null");
    }

    public void close() {
        this.interrupt();
        try {
            if (connection != null)
                connection.close();
        } catch (IOException e) {
        }
        connection = null;
    }

    private void processRequestFetch(TerminalReader reader, TerminalWriter writer) {

        Pass pass = listener.terminalFetchPass(reader.getIdPass());

        if (pass == null) {
            writer.writeKO();
        } else {
            writer.writePass(pass);
        }
    }

    private void processRequestUse(TerminalReader reader, TerminalWriter writer) {

        boolean r = listener.terminalUseTicket(reader.getIdPass(), reader.getTicketId(), reader.getCount());

        if(r) {
            writer.writeOK();
        }else {
            writer.writeKO();
        }
    }

    public boolean operate() {
        try {
            TerminalWriter writer = new TerminalWriter(connection.getOutputStream());
            TerminalReader reader = new TerminalReader(connection.getInputStream());
            reader.receive();
            switch (reader.getType()) {

                case 0:
                    return false; // socket closed

                case Protocol.REQ_FETCH:
                    processRequestFetch(reader, writer);
                    break;

                case Protocol.REQ_USE_TICKET:
                    processRequestUse(reader, writer);
                    break;
            }
            writer.send();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public void run() {
        while (true) {
            if (!operate())
                break;
        }
        try {
            if (connection != null) connection.close();
        } catch (IOException e) {
        }
    }
}

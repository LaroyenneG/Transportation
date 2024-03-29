package fr.ensisa.hassenforder.transportation.terminal.network;

import fr.ensisa.hassenforder.transportation.terminal.model.Pass;

import java.io.IOException;
import java.net.Socket;

public class CommandSession implements ISession {

    private Socket connection;
    private long passId;

    public CommandSession() {
        this.passId = 0;
    }

    @Override
    synchronized public boolean close() {
        try {
            if (connection != null) {
                connection.close();
            }
            connection = null;
        } catch (IOException e) {
        }
        return true;
    }

    @Override
    synchronized public boolean open() {
        this.close();
        try {
            connection = new Socket("localhost", Protocol.TERMINAL_PORT);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    synchronized public Pass getPassById(long passId) {

        try {

            CommandWriter writer = new CommandWriter(connection.getOutputStream());
            writer.createFetch(passId);
            writer.send();

            CommandReader reader = new CommandReader(connection.getInputStream());
            reader.receive();

            if (reader.getType() == Protocol.REPLY_PASS) {
                this.passId = passId;
                return reader.getPass();
            }

            if (reader.getType() == Protocol.REPLY_KO) {
                this.passId = 0;
                return null;
            }

            throw new IllegalStateException();

        } catch (IOException e) {
            this.passId = 0;
            return null;
        }
    }

    @Override
    synchronized public boolean useTicket(String ticketId, int count) {

        try {

            CommandWriter writer = new CommandWriter(connection.getOutputStream());
            writer.createUseTicket(this.passId, ticketId, count);
            writer.send();

            CommandReader reader = new CommandReader(connection.getInputStream());
            reader.receive();

            return reader.getType() == Protocol.REPLY_OK;

        } catch (IOException e) {
            return false;
        }
    }
}

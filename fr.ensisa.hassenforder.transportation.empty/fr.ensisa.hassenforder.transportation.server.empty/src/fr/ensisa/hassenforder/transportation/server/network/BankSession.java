package fr.ensisa.hassenforder.transportation.server.network;

import fr.ensisa.hassenforder.transportation.bank.network.Protocol;

import java.io.IOException;
import java.net.Socket;

public class BankSession implements ISession {

    private Socket connection;

    public BankSession() {
        open(); // !!!!!
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
            connection = new Socket("localhost", Protocol.BANK_PORT);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    synchronized public boolean bankWithdraw(long cardId, int amount) {
        try {

            BankWriter writer = new BankWriter(connection.getOutputStream());
            BankReader reader = new BankReader(connection.getInputStream());

            writer.createBuy(cardId, amount);
            writer.send();

            reader.receive();

            return reader.getType() == Protocol.REPLY_OK;

        } catch (IOException e) {
            return false;
        }
    }

    @Override
    protected void finalize() throws Throwable {
        close();
        super.finalize();
    }

}

package fr.ensisa.hassenforder.transportation.bank.network;

import fr.ensisa.hassenforder.transportation.bank.BankNetworkListener;

import java.io.IOException;
import java.net.Socket;

public class BankSession extends Thread {

    private Socket connection;
    private BankNetworkListener listener;

    public BankSession(Socket connection, BankNetworkListener listener) {
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

    private void processRequestWithdraw(BankReader reader, BankWriter writer) {

        boolean r = listener.withdrawByCardId(reader.getCardId(), reader.getAmount());

        if (r) {
            writer.writeKO();
        } else {
            writer.writeOK();
        }
    }

    public boolean operate() {
        try {
            BankWriter writer = new BankWriter(connection.getOutputStream());
            BankReader reader = new BankReader(connection.getInputStream());
            reader.receive();
            switch (reader.getType()) {

                case 0:
                    return false; // socket closed

                case Protocol.REQ_DEBIT:
                    processRequestWithdraw(reader, writer);
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

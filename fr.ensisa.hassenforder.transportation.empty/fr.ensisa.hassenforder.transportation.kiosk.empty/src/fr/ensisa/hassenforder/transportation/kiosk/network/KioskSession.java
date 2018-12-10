package fr.ensisa.hassenforder.transportation.kiosk.network;

import fr.ensisa.hassenforder.transportation.kiosk.model.Pass;
import fr.ensisa.hassenforder.transportation.kiosk.model.Transaction;

import java.io.IOException;
import java.net.Socket;

public class KioskSession implements ISession {

    private Socket connection;

    public KioskSession() {
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
            connection = new Socket("localhost", Protocol.KIOSK_PORT);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public long createPass() {

        try {

            KioskWriter writer = new KioskWriter(connection.getOutputStream());
            writer.createNewPass();
            writer.send();

            KioskReader reader = new KioskReader(connection.getInputStream());
            reader.receive();

            if (reader.getType() == Protocol.REPLY_PASS_ID) {
                return reader.getPassId();
            }

            throw new IllegalStateException();
        } catch (IOException e) {
            return -1L;
        }
    }

    @Override
    public Pass getPassById(long passId) {

        try {

            KioskWriter writer = new KioskWriter(connection.getOutputStream());
            writer.createFetch(passId);
            writer.send();

            KioskReader reader = new KioskReader(connection.getInputStream());
            reader.receive();

            if (reader.getType() == Protocol.REPLY_PASS) {
                return reader.getPass();
            } else if (reader.getType() == Protocol.REPLY_KO) {
                return null;
            }

            throw new IllegalStateException();

        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public Transaction buyRoute(long passId, String from, String to, int count) {

        try {

            KioskWriter writer = new KioskWriter(connection.getOutputStream());
            writer.createBuyRoute(passId, from, to, count);
            writer.send();

            KioskReader reader = new KioskReader(connection.getInputStream());
            reader.receive();

            if (reader.getType() == Protocol.REPLY_TRANSACTION) {
                return reader.getTransaction();
            }

            throw new IllegalStateException();

        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public Transaction buyUrban(long passId, int count) {

        try {

            KioskWriter writer = new KioskWriter(connection.getOutputStream());
            writer.createBuyUrban(passId, count);
            writer.send();

            KioskReader reader = new KioskReader(connection.getInputStream());
            reader.receive();

            if (reader.getType() == Protocol.REPLY_TRANSACTION) {
                return reader.getTransaction();
            }

            throw new IllegalStateException();

        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public Transaction buySubscription(long passId, int month) {

        try {

            KioskWriter writer = new KioskWriter(connection.getOutputStream());
            writer.createBuySubscription(passId, month);
            writer.send();

            KioskReader reader = new KioskReader(connection.getInputStream());
            reader.receive();

            if (reader.getType() == Protocol.REPLY_TRANSACTION) {
                return reader.getTransaction();
            }

            throw new IllegalStateException();

        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public boolean cancelTransaction(long id) {

        try {

            KioskWriter writer = new KioskWriter(connection.getOutputStream());
            writer.createCancel(id);
            writer.send();

            KioskReader reader = new KioskReader(connection.getInputStream());
            reader.receive();

            return reader.getType() == Protocol.REPLY_OK;

        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public long payTransaction(long id, long cardId) {

        try {

            KioskWriter writer = new KioskWriter(connection.getOutputStream());
            writer.createPay(id, cardId);
            writer.send();

            KioskReader reader = new KioskReader(connection.getInputStream());
            reader.receive();

            if (reader.getType() == Protocol.REPLY_PASS_ID) {
                return reader.getPassId();
            }

            throw new IllegalStateException();

        } catch (IOException e) {
            return -1;
        }
    }

}

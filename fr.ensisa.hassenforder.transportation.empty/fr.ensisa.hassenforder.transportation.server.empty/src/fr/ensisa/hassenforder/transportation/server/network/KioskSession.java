package fr.ensisa.hassenforder.transportation.server.network;

import fr.ensisa.hassenforder.transportation.kiosk.network.Protocol;
import fr.ensisa.hassenforder.transportation.server.NetworkListener;
import fr.ensisa.hassenforder.transportation.server.model.*;

import java.io.IOException;
import java.net.Socket;

public class KioskSession extends Thread {

    private Socket connection;
    private NetworkListener listener;

    public KioskSession(Socket connection, NetworkListener listener) {
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

    private void processRequestBuyRoute(KioskReader reader, KioskWriter writer) {

        Ticket ticket = new Route(reader.getPassId(), reader.getFrom(), reader.getTo(), reader.getCount());

        Transaction transaction = listener.kioskCreateTransaction(ticket);

        writer.writeTransaction(transaction);
    }

    private void processRequestBuyUrban(KioskReader reader, KioskWriter writer) {


        Ticket ticket = new Urban(reader.getPassId(), reader.getCount());

        Transaction transaction = listener.kioskCreateTransaction(ticket);

        writer.writeTransaction(transaction);
    }

    private void processRequestBuySubscription(KioskReader reader, KioskWriter writer) {

        Ticket ticket = new Subscription(reader.getPassId(), reader.getMonth());

        Transaction transaction = listener.kioskCreateTransaction(ticket);

        writer.writeTransaction(transaction);
    }

    private void processRequestCancel(KioskReader reader, KioskWriter writer) {

        boolean r = listener.kioskCancelTransaction(reader.getTransactionId());

        if (r) {
            writer.writeOK();
        } else {
            writer.writeKO();
        }
    }

    private void processRequestFetch(KioskReader reader, KioskWriter writer) {

        Pass pass = listener.kioskFetchPass(reader.getPassId());

        if (pass == null) {
            writer.writeKO();
        } else {
            writer.writePass(pass);
        }
    }

    private void processRequestPay(KioskReader reader, KioskWriter writer) {

        long passId = listener.kioskPayTransaction(reader.getTransactionId(), reader.getCardId());

        writer.writePassId(passId);
    }

    private void processRequestNewPass(KioskReader reader, KioskWriter writer) {

        Pass pass = listener.kioskFetchPass(listener.kioskCreatePass());

        if (pass == null) {
            writer.writeKO();
        } else {
            writer.writePass(pass);
        }
    }

    public boolean operate() {

        try {
            KioskWriter writer = new KioskWriter(connection.getOutputStream());
            KioskReader reader = new KioskReader(connection.getInputStream());
            reader.receive();
            switch (reader.getType()) {
                case 0:
                    return false; // socket closed

                case Protocol.REQ_CANCEL:
                    processRequestCancel(reader, writer);
                    break;

                case Protocol.REQ_FETCH:
                    processRequestFetch(reader, writer);
                    break;

                case Protocol.REQ_BUY_ROUTE:
                    processRequestBuyRoute(reader, writer);
                    break;

                case Protocol.REQ_BUY_URBAN:
                    processRequestBuyUrban(reader, writer);
                    break;

                case Protocol.REQ_BUY_SUBSCRIPTION:
                    processRequestBuySubscription(reader, writer);
                    break;

                case Protocol.REQ_NEW_PASS:
                    processRequestNewPass(reader, writer);
                    break;

                case Protocol.REQ_PAY:
                    processRequestPay(reader, writer);
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

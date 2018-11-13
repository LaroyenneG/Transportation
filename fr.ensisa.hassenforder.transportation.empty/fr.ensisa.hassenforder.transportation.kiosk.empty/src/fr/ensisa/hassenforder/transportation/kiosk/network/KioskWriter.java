package fr.ensisa.hassenforder.transportation.kiosk.network;

import fr.ensisa.hassenforder.network.BasicAbstractWriter;

import java.io.OutputStream;

public class KioskWriter extends BasicAbstractWriter {

    public KioskWriter(OutputStream outputStream) {
        super(outputStream);
    }

    public void createNewPass() {
        writeInt(Protocol.REQ_NEW_PASS);
    }

    public void createFetch(long passId) {
        writeInt(Protocol.REQ_FETCH);
        writeLong(passId);
    }

    public void createBuyRoute(long passId, String from, String to, int count) {
        writeInt(Protocol.REQ_BUY_ROUTE);
        writeLong(passId);
        writeString(from);
        writeString(to);
        writeInt(count);
    }

    public void createBuyUrban(long passId, int count) {
        writeInt(Protocol.REQ_BUY_URBAN);
        writeLong(passId);
        writeInt(count);
    }

    public void createBuySubscription(long passId, int month) {
        writeInt(Protocol.REQ_BUY_SUBSCRIPTION);
        writeLong(passId);
        writeInt(month);
    }

    public void createPay(long id, long cardId) {
        writeInt(Protocol.REQ_PAY);
        writeLong(id);
        writeLong(cardId);
    }

    public void createCancel(long id) {
        writeInt(Protocol.REQ_CANCEL);
        writeLong(id);
    }

}

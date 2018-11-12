package fr.ensisa.hassenforder.transportation.server.network;

import fr.ensisa.hassenforder.network.BasicAbstractReader;
import fr.ensisa.hassenforder.transportation.kiosk.network.Protocol;
import fr.ensisa.hassenforder.transportation.server.model.Subscription;

import java.io.InputStream;

public class KioskReader extends BasicAbstractReader {

    private long passId;
    private long transactionId;
    private long cardId;
    private int count;
    private String to;
    private String from;
    private Subscription.Month month;

    public KioskReader(InputStream inputStream) {
        super(inputStream);
        passId = -1;
        transactionId = -1;
        cardId = -1;
        to = "";
        from = "";
        count = -1;
        month = Subscription.Month.JANUARY;
    }

    public void receive() {
        type = readInt();
        switch (type) {

            case Protocol.REQ_FETCH:
                readFetch();
                break;

            case Protocol.REQ_NEW_PASS:
                readNewPass();
                break;

            case Protocol.REQ_BUY_ROUTE:
                readBuyRoute();
                break;

            case Protocol.REQ_BUY_URBAN:
                readBuyUrban();
                break;

            case Protocol.REQ_CANCEL:
                readCancel();
                break;

            case Protocol.REQ_BUY_SUBSCRIPTION:
                readBuySubscription();
                break;

            case Protocol.REQ_PAY:
                readPay();
                break;

            default:
                type = 0;
                break;
        }
    }

    private void readNewPass() {
        // rien a lire
    }

    private void readPay() {
        transactionId = readLong();
        cardId = readLong();
    }

    private void readCancel() {
        transactionId = readLong();
    }

    private void readBuyUrban() {
        passId = readLong();
        count = readInt();
    }

    private void readBuyRoute() {
        passId = readLong();
        from = readString();
        to = readString();
        count = readInt();
    }

    private void readBuySubscription() {
        passId = readLong();
        month = Subscription.Month.values()[readInt()];
    }

    private void readFetch() {
        passId = readLong();
    }

    public long getPassId() {
        return passId;
    }

    public long getTransactionId() {
        return transactionId;
    }

    public int getCount() {
        return count;
    }

    public String getTo() {
        return to;
    }

    public String getFrom() {
        return from;
    }

    public Subscription.Month getMonth() {
        return month;
    }

    public long getCardId() {
        return cardId;
    }
}

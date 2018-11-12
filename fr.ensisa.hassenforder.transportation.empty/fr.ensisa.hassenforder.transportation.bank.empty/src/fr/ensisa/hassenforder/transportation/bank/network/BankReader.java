package fr.ensisa.hassenforder.transportation.bank.network;

import fr.ensisa.hassenforder.network.BasicAbstractReader;

import java.io.InputStream;

public class BankReader extends BasicAbstractReader {

    private int amount;
    private long cardId;

    public BankReader(InputStream inputStream) {
        super(inputStream);
        cardId = -1;
        amount = -1;
    }

    public void receive() {
        type = readInt();
        switch (type) {

            case Protocol.REQ_DEBIT:
                readDebit();
                break;

            default:
                type = 0;
                break;
        }
    }

    private void readDebit() {
        cardId = readLong();
        amount = readInt();
    }

    public long getCardId() {
        return cardId;
    }

    public int getAmount() {
        return amount;
    }
}

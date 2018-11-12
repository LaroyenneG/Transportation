package fr.ensisa.hassenforder.transportation.bank.network;

public interface Protocol {

    public static final int BANK_PORT = 6666;

    public static final int REQ_DEBIT = 1010;

    public static final int REPLY_KO = 3000;

    public static final int REPLY_OK = 3001;
}

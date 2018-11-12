package fr.ensisa.hassenforder.transportation.kiosk.network;

public interface Protocol {

    public static final int KIOSK_PORT = 7777;

    public static final int REQ_FETCH = 1000;
    public static final int REQ_NEW_PASS = 1001;

    public static final int REQ_BUY_ROUTE = 1002;
    public static final int REQ_BUY_URBAN = 1003;
    public static final int REQ_BUY_SUBSCRIPTION = 1004;

    public static final int REQ_PAY = 1005;
    public static final int REQ_CANCEL = 1006;


    public static final int REPLY_OK = 2000;
    public static final int REPLY_KO = 2001;

    public static final int REPLY_PASS = 2002;
    public static final int REPLY_TRANSACTION = 2003;
}

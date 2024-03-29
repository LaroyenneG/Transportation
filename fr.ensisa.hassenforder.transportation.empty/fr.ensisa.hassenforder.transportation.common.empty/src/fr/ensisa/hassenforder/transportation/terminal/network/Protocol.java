package fr.ensisa.hassenforder.transportation.terminal.network;

public interface Protocol {

    public static final int TERMINAL_PORT = 8888;

    public static final int REQ_FETCH = 1000;
    public static final int REQ_USE_TICKET = 1001;

    public static final int REPLY_PASS = 2000;
    public static final int REPLY_OK = 2001;
    public static final int REPLY_KO = 2002;
}

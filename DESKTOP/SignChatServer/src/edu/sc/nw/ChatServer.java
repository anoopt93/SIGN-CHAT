package edu.sc.nw;

import java.net.*;
import java.io.*;
import java.util.*;
//TCP Chat Application Server

public class ChatServer implements Runnable {

    ServerSocket ser = null;
    Socket soc = null;
    ArrayList<Socket> cList = new ArrayList<Socket>();
    HashMap<String, PrintStream> outMap = new HashMap<String, PrintStream>();
    ArrayList<PrintStream> outList = new ArrayList<PrintStream>();

    public ChatServer(int port) {
        try {
            ser = new ServerSocket(port);
            pln("Chat Server Started At >>" + port);
            new Thread(this).start();
        } catch (Exception e) {
           e.printStackTrace();
        }
    }

    ////////////Sub Class/////////////////////
    class Client extends Thread {

        Socket cSoc = null;
        OutputStream cout = null;
        PrintStream cps = null;
        InputStream cin = null;
        BufferedReader cbr = null;

        public Client(Socket tSoc) {
            try {
                cSoc = tSoc;
                pln("Client Inited");
            } catch (Exception e) {
              e.printStackTrace();
            }
        }

        public void run() {

            try {

                cin = cSoc.getInputStream();
                cout = cSoc.getOutputStream();
                cbr = new BufferedReader(new InputStreamReader(cin));
                cps = new PrintStream(cout);

                String msg = "";
                msg = cbr.readLine();
                outMap.put(msg, cps);
               
                outList.add(cps);
                while (true) {
                    msg = cbr.readLine();
                    pln("Client Msg=" + msg);
                    if (msg == null) {
                        break;
                    }
                    if (msg.equals("exit")) {
                        break;
                    } else if (msg.equals("allchat")) {
                        String chatName = cbr.readLine();
                        String message = cbr.readLine();
                        sendToAll(chatName, message);
                    }
                    else if (msg.equals("chatone")) {
                        String chatName = cbr.readLine();
                        String toName = cbr.readLine();
                        String message = cbr.readLine();
                        sendToOne(chatName,toName, message);
                    }
                }
            } catch (Exception e) {
               e.printStackTrace();
            }
        }

    }

    ///////////Sub Class End///////////////////
    public void run() {
        try {
            while (true) {
                soc = ser.accept();
                cList.add(soc);
                new Client(soc).start();
            }
        } catch (Exception e) {
           e.printStackTrace();
        }
    }

    public void sendToAll(String chatName, String msg) {

        try {
            for (PrintStream ps : outList) {

                ps.println(chatName + ">>" + msg);
            }
            pln("Message Send To All");
        } catch (Exception e) {
         e.printStackTrace();
        }
    }

    public void sendToOne(String chatName, String toName, String msg) {

        try {
            PrintStream ps = outMap.get(toName);
            System.out.println(ps);
            ps.println( chatName + ">>" + msg);
            if(!chatName.equals(toName)){    
                ps = outMap.get(chatName);

                ps.println(chatName + ">>" + msg); 
            }
            pln("Message Send To One");
        } catch (Exception e) {
           e.printStackTrace();
        }
    }

    public static void pln(Object obj) {
        System.out.println(obj);
    }

    public static void main(String... args)
            throws Exception {
        new ChatServer(2222);
    }

}

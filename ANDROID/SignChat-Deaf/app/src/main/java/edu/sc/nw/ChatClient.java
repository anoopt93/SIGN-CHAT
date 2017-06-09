package edu.sc.nw;


import java.io.DataInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;

import edu.sc.ChatRoomActivity;
import edu.sc.db.DbProcess;
import edu.sc.models.ChatHistory;
import edu.sc.utils.DateAndTime;
import edu.sc.utils.Variables;
//Text Chat Client

public class ChatClient {

    Socket soc = null;
    String chatName = "";
    public Sender sender = null;
    Receiver receiver = null;

    public ChatClient(String ip, int port, String cN) {
        try {
            soc = new Socket(ip, port);
            chatName = cN;
            receiver = new Receiver(soc);
            sender = new Sender(soc);

        } catch (Exception e) {
            pln("SocketClient Err>>" + e);
        }
    }
///////////Sub Class Sender /////////

    public class Sender {

        OutputStream out = null;
        PrintStream ps = null;
        Socket sSoc = null;

        public Sender(Socket tSoc) {
            try {
                sSoc = tSoc;
                out = sSoc.getOutputStream();
                ps = new PrintStream(out);
                ps.println(chatName);
            } catch (Exception e) {
                pln("Sender Err>>" + e);
            }
        }

        public void sendToAll(String msg) {
            try {
                ps.println("allchat");
                ps.println(chatName);
                ps.println(msg);
            } catch (Exception e) {
                pln("Sender Run Err>>" + e);
            }
        }

        public void sendToOne(String msg, String toName) {
            try {
                ps.println("chatone");
                ps.println(chatName);
                ps.println(toName);
                ps.println(msg);
            } catch (Exception e) {
                pln("Sender Run Err>>" + e);
            }
        }

    }
    ///////////Sub Class Sender End//////
    ///////////Sub Class Receiver ////////

    class Receiver extends Thread {

        Socket rSoc = null;
        InputStream in = null;
        DataInputStream din = null;

        public Receiver(Socket tSoc) {
            try {
                rSoc = tSoc;
                in = rSoc.getInputStream();
                din = new DataInputStream(in);
                this.start();
            } catch (Exception e) {
                pln("Receiver Err>>" + e);
            }
        }

        public void run() {

            try {
                String msg = null;
                while (true) {
                    msg = din.readLine();
                    if (msg == null) {
                        break;
                    }

                    if (msg.equals("exit")) {
                        break;
                    }
                    pln(msg);
//                    if(Variables.isChatActivity) {
                    Variables.chatMessage = msg;
                    ChatRoomActivity.h.sendMessage(ChatRoomActivity.h.obtainMessage());
//                    }
                    String frname = msg.substring(msg.indexOf("]") + 1, msg.indexOf(">"));
                    String toname = Variables.uname;
                    if (!frname.equals(toname)) {
                        DbProcess dbp = new DbProcess(ChatRoomActivity.context);
                        String ctDate = DateAndTime.getDate();
                        String ctTime = DateAndTime.getTime();
                        ChatHistory ch = new ChatHistory(0, Variables.uname, "all", msg.substring(msg.lastIndexOf(">") + 1), "received", ctDate, ctTime);
                        dbp.insertChatHistory(ch);
                        dbp.close();

                    }
                }
            } catch (Exception e) {
                pln("Receiver Run Err>>" + e);
            }
        }
    }

    /////////Sub Class Receiver End//////

    public static void pln(Object obj) {
        System.out.println(obj);
    }

    public static void main(String... args) {
        System.out.println("Client2");
        new ChatClient("localhost", 1234, "Client2");
    }

}

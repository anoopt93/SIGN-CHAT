package edu.sc.nw;

import java.net.*;
import java.io.*;
import java.util.*;
//TCP Chat Application Server

public class FileServer implements Runnable {

    ServerSocket ser = null;
    Socket soc = null;
    ArrayList<Socket> cList = new ArrayList<Socket>();
    HashMap<String, PrintStream> outMap1 = new HashMap<String, PrintStream>();
    HashMap<String, OutputStream> outMap2 = new HashMap<String, OutputStream>();
    ArrayList<PrintStream> outList = new ArrayList<PrintStream>();

    public FileServer(int port) {
        try {
            ser = new ServerSocket(port);
            pln("File Server Started At >>" + port);
            new Thread(this).start();
        } catch (Exception e) {
            pln("ServerSocket Constructor Err>" + e);
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
                pln("Client Class Err>>" + e);
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
                outMap1.put(msg, cps);
                outMap2.put(msg, cout);

                outList.add(cps);
                while (true) {
                    msg = cbr.readLine();
                    pln("Client Msg=" + msg);
                    if (msg == null) {
                        break;
                    }
                    if (msg.equals("exit")) {
                        break;
                    } else if (msg.equals("sendfile")) {
                        String chatName = cbr.readLine();
                        String toName = cbr.readLine();
                        String fname = cbr.readLine();
                        String fsz = cbr.readLine();
                        File f = new File("./out/fileserver");
                        f.mkdirs();
                        f = new File("./out/fileserver/" + chatName + "_" + fname);
                        f.delete();
                        int sz = Integer.parseInt(fsz);
                        int cnt = 0, ch = 0;
                        FileOutputStream fout = new FileOutputStream(f);
                        byte b[] = new byte[2024];
                        System.out.println("Receiving>>");
                        while ((ch = cin.read(b)) != -1) {
                            cnt += ch;
                            System.out.println(ch);
                            fout.write(b, 0, ch);
                            if (cnt >= sz) {
                                break;
                            }
                        }
                        fout.close();
                        System.out.println("Received");
                        sendFile(chatName, toName, fname, fsz, f.getAbsolutePath());
                    }
                }
            } catch (Exception e) {
                pln("Client Class Run Err>>" + e);
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
            pln("Server Run Err>>" + e);
        }
    }

    /* public void sendToAll(String chatName, String msg) {

     try {
     for (PrintStream ps : outList) {

     ps.println("[public]" + chatName + ">>" + msg);
     }
     pln("Message Send To All");
     } catch (Exception e) {
     pln("SendToAll Err>>" + e);
     }
     }
     */
    public void sendFile(String chatName, String toName, String fname, String fsz, String location) {

        try {
            PrintStream ps = outMap1.get(toName);
            OutputStream out = outMap2.get(toName);
            ps.println("fileSend");
            ps.println(chatName);
            ps.println(fname);
            ps.println(fsz);

            Thread.sleep(300);
            int cnt = 0, ch = 0;
            int sz = Integer.parseInt(fsz);
            byte b[] = new byte[2024];
            File f = new File(location);
            FileInputStream fin = new FileInputStream(f);
            System.out.println("Sending");
            while ((ch = fin.read(b)) != -1) {
                cnt += ch;
                System.out.println("ch" + ch);
                out.write(b, 0, ch);
                if (cnt >= sz) {
                    break;
                }
            }
            fin.close();
            //  f.delete();
            pln("File Send To One");
        } catch (Exception e) {
            pln("SendToAll Err>>" + e);
        }
    }

    public static void pln(Object obj) {
        System.out.println(obj);
    }

    public static void main(String... args)
            throws Exception {
        new FileServer(4444);
    }

}

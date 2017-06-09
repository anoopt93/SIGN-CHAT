package edu.sc.nw;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;

import edu.sc.HomeActivity;
import edu.sc.db.DbProcess;
import edu.sc.models.FileHistory;
import edu.sc.utils.DateAndTime;
import edu.sc.utils.Variables;
//Text Chat Client

public class FileClient {

    Socket soc = null;
    String chatName = "";
    public Sender sender = null;
    Receiver receiver = null;

    public FileClient(String ip, int port, String cN) {
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


        public void sendFile(String fpath, String toName) {
            try {
                ps.println("sendfile");
                ps.println(chatName);
                ps.println(toName);
                File f = new File(fpath);
                String fname = f.getName();
                ps.println(f.getName());
                String fsz = "" + f.length();
                ps.println(f.length() + "");
                int ch = 0, cnt = 0;
                int sz = (int) f.length();
                byte b[] = new byte[2024];
                Thread.sleep(300);

                FileInputStream fin = new FileInputStream(f);
                while ((ch = fin.read(b)) != -1) {
                    out.write(b, 0, ch);
                    cnt += ch;
                    if (cnt >= sz) break;
                }
                fin.close();

                DbProcess dbp = new DbProcess(HomeActivity.context);
                String fromName = Variables.uname;
                String fDate = DateAndTime.getDate();
                String fTime = DateAndTime.getTime();
                FileHistory fh = new FileHistory(0, fromName, toName, fname, fsz, "send", fDate, fTime);
                dbp.insertFileHistory(fh);
                dbp.close();


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
        BufferedReader br = null;

        public Receiver(Socket tSoc) {
            try {
                rSoc = tSoc;
                in = rSoc.getInputStream();
                br = new BufferedReader(new InputStreamReader(in));
                this.start();
            } catch (Exception e) {
                pln("Receiver Err>>" + e);
            }
        }

        public void run() {

            try {
                String msg = null;
                while (true) {
                    msg = br.readLine();
                    if (msg == null) {
                        break;
                    }

                    if (msg.equals("exit")) {
                        break;
                    }
                    pln(msg);
                    if (msg.equals("fileSend")) {
                        String chatName = br.readLine();
                        String fname = br.readLine();
                        String fsz = br.readLine();
                        File f = new File(Variables.root + Variables.inbox);
                        f.mkdirs();
                        f = new File(Variables.root + Variables.inbox + fname);
                        int sz = Integer.parseInt(fsz);
                        int cnt = 0, ch = 0;
                        FileOutputStream fout = new FileOutputStream(f);
                        byte b[] = new byte[2024];
                        while ((ch = in.read(b)) != -1) {
                            cnt += ch;
                            fout.write(b, 0, ch);
                            if (cnt >= sz) break;
                        }
                        fout.close();

                        DbProcess dbp = new DbProcess(HomeActivity.context);
                        String fromName = chatName;
                        String toName = Variables.uname;
                        String fDate = DateAndTime.getDate();
                        String fTime = DateAndTime.getTime();
                        FileHistory fh = new FileHistory(0, fromName, toName, fname, fsz, "received", fDate, fTime);
                        dbp.insertFileHistory(fh);
                        dbp.close();
                        Variables.isFileReceived = true;
                        HomeActivity.h.sendMessage(HomeActivity.h.obtainMessage());
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
        new FileClient("localhost", 1234, "Client2");
    }

}

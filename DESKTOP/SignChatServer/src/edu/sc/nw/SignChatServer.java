/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.sc.nw;

import edu.sc.db.DbProcess;
import edu.sc.models.UserDetails;
import edu.sc.models.UserLogin;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author pvr
 */
public class SignChatServer implements Runnable {

    ServerSocket ss = null;
    Socket s = null;

    public SignChatServer(int port) {
        try {
            ss = new ServerSocket(port);
            System.out.println("Sign Chat Server Started At Port>>> " + port);
            new Thread(this).start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                s = ss.accept();
                new SignChatChild(s).start();;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class SignChatChild extends Thread {

        Socket socket = null;
        InputStream is = null;
        OutputStream os = null;
        BufferedReader br = null;
        PrintStream ps = null;
        DbProcess dbProcess = null;

        public SignChatChild(Socket socket) {
            try {
                this.socket = socket;

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void run() {
            try {
                is = socket.getInputStream();
                os = socket.getOutputStream();
                br = new BufferedReader(new InputStreamReader(is));
                ps = new PrintStream(os);
                while (true) {
                    String line = br.readLine();
                    if (line == null) {
                        break;
                    }
                    if (line.equals("exit")) {
                        is.close();
                        socket.close();
                        break;
                    } else if (line.equals("userRegister")) {
                        String name = br.readLine();
                        String mobile = br.readLine();
                        String email = br.readLine();
                        String uname = br.readLine();
                        String passwd = br.readLine();
                        dbProcess = new DbProcess();
                        boolean result = dbProcess.isMobileFound(mobile);
                        if (result) {
                            ps.println("mobileFound");
                        } else {
                            result = dbProcess.isEmailFound(email);
                            if (result) {
                                ps.println("emailFound");
                            } else {
                                result = dbProcess.isUnameFound(uname);
                                if (result) {
                                    ps.println("unameFound");
                                } else {
                                    UserLogin ul = new UserLogin(0, uname, passwd, "");
                                    UserDetails ud = new UserDetails(0, name, mobile, email, ul);
                                    result = dbProcess.userRegister(ud);
                                    if (result) {
                                        ps.println("ok");
                                    } else {
                                        ps.println("fail");
                                    }
                                }

                            }
                        }

                    } else if (line.equals("userLogin")) {
                        String uname = br.readLine();
                        String passwd = br.readLine();
                        UserLogin ul = new UserLogin(0, uname, passwd, "");

                        dbProcess = new DbProcess();
                        boolean result = dbProcess.userLogin(ul);
                        ps.println(result);

                    } else if (line.equals("changePassword")) {
                        String uname = br.readLine();
                        String currentPasswd = br.readLine();
                        String newPasswd = br.readLine();
                        dbProcess = new DbProcess();
                        boolean result = dbProcess.changePassword(uname, currentPasswd, newPasswd);
                        ps.println(result);

                    } else if (line.equals("getAllOnlineUsers")) {
                        String uname = br.readLine();
                        dbProcess = new DbProcess();
                        ArrayList<String> onlineUsers = dbProcess.getAllOnlineUsers(uname);
                        ps.println(onlineUsers);

                    } else if (line.equals("logout")) {
                        String uname = br.readLine();
                        dbProcess = new DbProcess();
                        boolean result = dbProcess.logout(uname);
                        ps.println(result);

                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public static void main(String[] args) {
        SignChatServer chatServer = new SignChatServer(1111);
    }
}

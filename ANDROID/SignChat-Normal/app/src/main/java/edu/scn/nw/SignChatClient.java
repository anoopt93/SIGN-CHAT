package edu.scn.nw;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;

import edu.scn.models.UserDetails;
import edu.scn.models.UserLogin;
import edu.scn.utils.Variables;

/**
 * Created by pvr on 1/10/16.
 */
public class SignChatClient {
    public static String userRegister(UserDetails ud) {
        String result = "";
        try {
            Socket socket = new Socket(Variables.signChatServerIP, Variables.signChatServerPORT);
            InputStream is = null;
            OutputStream os = null;
            BufferedReader br = null;
            PrintStream ps = null;
            is = socket.getInputStream();
            os = socket.getOutputStream();
            br = new BufferedReader(new InputStreamReader(is));
            ps = new PrintStream(os);
            ps.println("userRegister");
            ps.println(ud.name);
            ps.println(ud.mobile);
            ps.println(ud.email);
            ps.println(ud.userLogin.uname);
            ps.println(ud.userLogin.passwd);
            result = br.readLine().trim();
            ps.println("exit");


        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static boolean userLogin(UserLogin ul) {
        boolean result = false;
        try {
            Socket socket = new Socket(Variables.signChatServerIP, Variables.signChatServerPORT);
            InputStream is = null;
            OutputStream os = null;
            BufferedReader br = null;
            PrintStream ps = null;
            is = socket.getInputStream();
            os = socket.getOutputStream();
            br = new BufferedReader(new InputStreamReader(is));
            ps = new PrintStream(os);
            ps.println("userLogin");
            ps.println(ul.uname);
            ps.println(ul.passwd);
            result = Boolean.valueOf(br.readLine());
            ps.println("exit");


        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static boolean logout(String uname) {
        boolean result = false;
        try {
            Socket socket = new Socket(Variables.signChatServerIP, Variables.signChatServerPORT);
            InputStream is = null;
            OutputStream os = null;
            BufferedReader br = null;
            PrintStream ps = null;
            is = socket.getInputStream();
            os = socket.getOutputStream();
            br = new BufferedReader(new InputStreamReader(is));
            ps = new PrintStream(os);
            ps.println("logout");
            ps.println(uname);
            result = Boolean.valueOf(br.readLine());
            ps.println("exit");


        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static boolean changePassword(String uname, String currentPasswd, String newPasswd) {
        boolean result = false;
        try {
            Socket socket = new Socket(Variables.signChatServerIP, Variables.signChatServerPORT);
            InputStream is = null;
            OutputStream os = null;
            BufferedReader br = null;
            PrintStream ps = null;
            is = socket.getInputStream();
            os = socket.getOutputStream();
            br = new BufferedReader(new InputStreamReader(is));
            ps = new PrintStream(os);
            ps.println("changePassword");
            ps.println(uname);
            ps.println(currentPasswd);
            ps.println(newPasswd);
            result = Boolean.valueOf(br.readLine());
            ps.println("exit");


        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static ArrayList<String> getAllOnlineUsers() {
        ArrayList<String> onlineUsers = new ArrayList<>();
        try {
            Socket socket = new Socket(Variables.signChatServerIP, Variables.signChatServerPORT);
            InputStream is = null;
            OutputStream os = null;
            BufferedReader br = null;
            PrintStream ps = null;
            is = socket.getInputStream();
            os = socket.getOutputStream();
            br = new BufferedReader(new InputStreamReader(is));
            ps = new PrintStream(os);
            ps.println("getAllOnlineUsers");
            ps.println(Variables.uname);
            String users = br.readLine().replace("[", "").replace("]", "").trim();
            ps.println("exit");
            String userArray[] = users.split(",");
            for (int i = 0; i < userArray.length; i++) {
                onlineUsers.add(userArray[i].trim());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return onlineUsers;
    }
}

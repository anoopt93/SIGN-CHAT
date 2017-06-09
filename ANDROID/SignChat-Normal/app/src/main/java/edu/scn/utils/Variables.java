package edu.scn.utils;

import android.os.Environment;

/**
 * Created by pvr on 1/10/16.
 */
public class Variables {
    public static String signChatServerIP = "192.168.43.51";
    public static String chatServerIP = "192.168.43.51";
    public static String fileServerIP="192.168.43.51";
    public static int signChatServerPORT = 1111;
    public static int chatServerPORT = 2222;
    public static int fileServerPort = 3333;
    public static String uname="";
    public static String chatMessage="";
    public static String inbox="inbox/";
    public static String root= Environment.getExternalStorageDirectory() +"/SignChat/";
    public static boolean isFileReceived=false;

}

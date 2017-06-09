/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.sc;

import edu.sc.nw.ChatServer;
import edu.sc.nw.FileServer;
import edu.sc.nw.SignChatServer;

/**
 *
 * @author pvr
 */
public class StartServer {

    public static void main(String[] args) {
        SignChatServer chatServer = new SignChatServer(1111);
        ChatServer cs = new ChatServer(2222);
        FileServer fs = new FileServer(3333);
    }
}

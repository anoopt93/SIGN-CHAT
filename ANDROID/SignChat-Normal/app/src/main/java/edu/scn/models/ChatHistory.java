/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.scn.models;

/**
 * @author root
 */
public class ChatHistory {


    public int id;
    public String fromName;
    public String toName;
    public String msg;
    public String status;
    public String ctDate;
    public String ctTime;

    public ChatHistory(int id, String fromName, String toName, String msg, String status, String ctDate, String ctTime) {
        this.id = id;
        this.fromName = fromName;
        this.toName = toName;
        this.msg = msg;
        this.status = status;
        this.ctDate = ctDate;
        this.ctTime = ctTime;
    }

    @Override
    public String toString() {
        return "from = " + fromName + "\n to = " + toName + "\n Message =" + msg + "(" + ctDate + ')';
    }


}

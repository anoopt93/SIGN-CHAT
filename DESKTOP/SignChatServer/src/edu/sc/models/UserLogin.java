/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.sc.models;

/**
 *
 * @author pvr
 */
public class UserLogin {

    public int id;
    public String uname;
    public String passwd;
    public String sessionStatus;

    public UserLogin() {
    }

    public UserLogin(int id, String uname, String passwd, String sessionStatus) {
        this.id = id;
        this.uname = uname;
        this.passwd = passwd;
        this.sessionStatus = sessionStatus;
    }

}

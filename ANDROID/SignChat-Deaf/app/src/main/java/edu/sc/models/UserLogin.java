package edu.sc.models;

/**
 * Created by pvr on 1/10/16.
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

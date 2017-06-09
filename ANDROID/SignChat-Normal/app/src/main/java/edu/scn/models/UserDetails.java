package edu.scn.models;

/**
 * Created by pvr on 1/10/16.
 */
public class UserDetails {

    public int id;
    public String name;
    public String mobile;
    public String email;
    public UserLogin userLogin;

    public UserDetails() {
    }

    public UserDetails(int id, String name, String mobile, String email, UserLogin userLogin) {
        this.id = id;
        this.name = name;
        this.mobile = mobile;
        this.email = email;
        this.userLogin = userLogin;
    }
}

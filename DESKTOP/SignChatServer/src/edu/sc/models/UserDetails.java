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

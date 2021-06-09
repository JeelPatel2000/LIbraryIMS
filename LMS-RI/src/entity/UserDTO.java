/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author jeelp
 */
public class UserDTO implements Serializable {

    String userid;
    String name;
    Date dob;
    String email;
    String address;
    String password;
    String userGroup;
    Boolean active;

    public UserDTO(String userid, String name, Date dob, String email, String address, String password, String userGroup, Boolean active) {
        this.userid = userid;
        this.name = name;
        this.dob = dob;
        this.email = email;
        this.address = address;
        this.password = password;
        this.userGroup = userGroup;
        this.active = active;
    }

    public String getUserid() {
        return userid;
    }

    public String getName() {
        return name;
    }

    public Date getDob() {
        return dob;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getUserGroup() {
        return userGroup;
    }

    public Boolean getActive() {
        return active;
    }

    public String getAddress() {
        return address;
    }

}

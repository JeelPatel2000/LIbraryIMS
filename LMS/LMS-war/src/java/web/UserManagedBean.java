/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import entity.UserDTO;
import javax.inject.Named;
import javax.enterprise.context.ConversationScoped;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import static java.util.Objects.isNull;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import session.UserFacadeRemote;

/**
 *
 * @author jeelp
 */
@Named(value = "userManagedBean")
@ConversationScoped
public class UserManagedBean implements Serializable {

    @Inject
    private Conversation conversation;

    @EJB
    private UserFacadeRemote userFacade;

    private String userid;
    private String name;
    private String dob;
    private String email;
    private String address;
    private String password;
    private String cPassword;
    private String userGroup;
    private Boolean active;

    private List<UserDTO> users;

    public UserManagedBean(String userid, String name, String dob, String email, String address, String password, String cPassword, String userGroup, Boolean active) {
        this.userid = userid;
        this.name = name;
        this.dob = dob;
        this.email = email;
        this.address = address;
        this.password = password;
        this.cPassword = cPassword;
        this.userGroup = userGroup;
        this.active = active;
        this.users = this.getUsers();
    }

    /**
     * Creates a new instance of UserManagedBean
     */
    public UserManagedBean() {

    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserFacadeRemote getUserFacade() {
        return userFacade;
    }

    public void setUserFacade(UserFacadeRemote userFacade) {
        this.userFacade = userFacade;
    }

    public String getCPassword() {
        return cPassword;
    }

    public void setCPassword(String cPassword) {
        this.cPassword = cPassword;
    }

    public String getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(String userGroup) {
        this.userGroup = userGroup;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String hashPassword(String data) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(data.getBytes("UTF-8"));
            // Change this to "UTF-16" if needed
            byte[] digest = md.digest();
            BigInteger bigInt = new BigInteger(1, digest);
            return bigInt.toString(16);
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {

        }
        return "encryption error";
    }

    public String addUser() throws ParseException {

        //check if user id is null
        if (isNull(userid)) {
            return "debug";
        }

        //Parsing string date to java.util.Date format for DB
        Date temp_dob = new SimpleDateFormat("dd-MM-yyyy").parse(dob);
        //hashing password using sha256 before storing it to DB
        String encrypted_password = hashPassword(password);

        UserDTO userDTO = new UserDTO(userid, name, temp_dob, email, address, encrypted_password, userGroup, active);

        boolean result = userFacade.createRecord(userDTO);

        if (result) {
            return "success";
        } else {
            return "failure";
        }

    }

    public String deleteEmployee() {
        // check empId is null
        if (isNull(userid)) {
            return "debug";
        }

        boolean result = userFacade.deleteRecord(userid);

        if (result) {
            return "success";
        } else {
            return "failure";
        }

    }

    public List<UserDTO> getUsers() {
        return userFacade.getUsers();
    }

    public void startConversation() {
        conversation.begin();
    }

    public void endConversation() {
        conversation.end();
    }

    private String setEmployeeDetails() {

        if (isNull(userid) || conversation == null) {
            return "debug";
        }

        UserDTO e = userFacade.getRecord(userid);

        if (e == null) {
            // no such employee
            return "failure";
        } else {
            // found - set details for display
            this.userid = e.getUserid();
            this.name = e.getName();
            this.address = e.getAddress();
            this.email = e.getEmail();
            this.dob = String.valueOf(e.getDob().getDate()) + "-" + String.valueOf(e.getDob().getMonth()) + "-" + String.valueOf(e.getDob().getYear() + 1900);
            this.password = e.getPassword();
            this.userGroup = e.getUserGroup();
            this.active = e.getActive();
            return "success";
        }
    }

    public String setEmployeeDetailsForChange() {
        // check empId is null
        if (isNull(userid) || conversation == null) {
            return "debug";
        }

        if (!userFacade.hasUser(userid)) {
            return "failure";
        }

//        FacesContext context = FacesContext.getCurrentInstance();
//        ExternalContext externalContext = context.getExternalContext();
//
//        if (externalContext.isUserInRole("ED-APP-USERS")) {
//            UserDTO e = userFacade.getRecord(userid);
//            String pwd = e.getPassword();
//
//            if (!pwd.equals(password)) {
//                return "authfailure";
//            }
//        }
        // note the startConversation of the conversation
        startConversation();

        // get employee details
        return setEmployeeDetails();
    }

    public String editUser() throws ParseException {
        // check empId is null
        if (isNull(userid)) {
            return "debug";
        }

        Date temp_dob = new SimpleDateFormat("dd-MM-yyyy").parse(dob);

        UserDTO userDTO = new UserDTO(userid, name, temp_dob, email, address, password, userGroup, active);
        boolean result = userFacade.updateRecord(userDTO);

        // note the endConversation of the conversation
        endConversation();

        if (result) {
            return "success";
        } else {
            return "failure";
        }
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import entity.UserDTO;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import session.UserFacadeRemote;

/**
 *
 * @author jeelp
 */
public class LMSclient {

    @javax.ejb.EJB
    private static UserFacadeRemote userFacade;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {

        Date dob = new SimpleDateFormat("dd/MM/yyyy").parse("03/03/2000");

        UserDTO user = new UserDTO("00002", "Jeel Patel", dob, "jeel.patel1111@gmail.com", "11, Ambersweet Drive, Tarneit", "12345678", "LMS-APP-ADMIN", true);

        if (userFacade.createRecord(user)) {
            System.out.println("User created!");
        } else {
            System.err.print("Error while adding user!");
        }

    }

}

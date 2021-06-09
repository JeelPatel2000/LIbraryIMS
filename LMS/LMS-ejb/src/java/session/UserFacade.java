/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Users;
import entity.UserDTO;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author jeelp
 */
@DeclareRoles({"LMS-APP-ADMIN", "LMS-APP-USER"})
@Stateless
public class UserFacade implements UserFacadeRemote {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @PersistenceContext(unitName = "LMS-ejbPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    private void create(Users user) {
        em.persist(user);
    }

    private void edit(Users user) {
        em.merge(user);
    }

    private void remove(Users user) {
        em.remove(em.merge(user));
    }

    private Users find(Object id) {
        return em.find(Users.class, id);
    }

    @RolesAllowed({"LMS-APP-ADMIN"})
    @Override
    public boolean createRecord(UserDTO userDTO) {
        if (find(userDTO.getUserid()) != null) {
            System.out.print("existing user found!");
            // user whose userid can be found
            return false;
        }
        // user whose userid could not be found
        try {
            Users user = this.myDTO2DAO(userDTO);
            this.create(user); // add to database
            return true;
        } catch (Exception ex) {
            return false; // something is wrong, should not be here though
        }
    }

    private Users myDTO2DAO(UserDTO userDTO) {
        Users user = new Users();
        user.setUserid(userDTO.getUserid());
        user.setName(userDTO.getName());
        user.setDob(userDTO.getDob());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setAddress(userDTO.getAddress());
        user.setUsergroup(userDTO.getUserGroup());
        user.setActive(userDTO.getActive());
        return user;
    }

    private UserDTO myDAO2DTO(Users user) {
        return new UserDTO(user.getUserid(), user.getName(), user.getDob(), user.getEmail(), user.getAddress(), user.getPassword(), user.getUsergroup(), user.getActive());
    }

    private List<UserDTO> usersListToUserDTOList(List<Users> users) {
        List<UserDTO> userDTOList = new ArrayList<UserDTO>();

        for (Users user : users) {
            userDTOList.add(myDAO2DTO(user));
        }

        return userDTOList;
    }

    private Users employeeDTO2Entity(UserDTO userDTO) {
        if (userDTO == null) {
            // just in case
            return null;
        }
        String userid = userDTO.getUserid();
        String name = userDTO.getName();
        Date dob = userDTO.getDob();
        String email = userDTO.getEmail();
        String address = userDTO.getAddress();
        String password = userDTO.getPassword();
        String userGroup = userDTO.getUserGroup();
        Boolean active = userDTO.getActive();

        Users user = new Users(userid, name, dob, address, email, password, userGroup, active);

        return user;
    }

    @RolesAllowed({"LMS-APP-ADMIN", "LMS-APP-USER"})
    @Override
    public UserDTO getRecord(String userid) {
        return myDAO2DTO(find(userid));

    }

    @RolesAllowed({"LMS-APP-ADMIN"})
    @Override
    public boolean deleteRecord(String userid) {
        // find the employee
        Users u = find(userid);

        // check again - just to play it safe
        if (u == null) {
            return false;
        }

        em.remove(u);
        return true;

    }

    @RolesAllowed({"LMS-APP-ADMIN", "LMS-APP-USER"})
    @Override
    public boolean updateRecord(UserDTO userDTO) {
        if (!hasUser(userDTO.getUserid())) {
            return false;
        }

        // employee exist, update details
        // convert to entity class
        Users user = this.employeeDTO2Entity(userDTO);
        // update details
        edit(user);

        return true;
    }

    @Override
    public List<UserDTO> getUsers() {
        List<Users> _users = em.createNamedQuery("Users.findAll", Users.class).getResultList();

        return usersListToUserDTOList(_users);
    }

    @Override
    public boolean hasUser(String userid) {
        System.out.println(userid);
        return (find(userid) != null);
    }
}

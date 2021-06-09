/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.UserDTO;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author jeelp
 */
@Remote
public interface UserFacadeRemote {

    public boolean createRecord(UserDTO userDTO);

    public UserDTO getRecord(String userid);

    public boolean deleteRecord(String userid);

    public boolean updateRecord(UserDTO userDTO);

    public List<UserDTO> getUsers();

    public boolean hasUser(String userid);
}

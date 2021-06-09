/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.BookDTO;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author jeelp
 */
@Remote
public interface BookFacadeRemote {

    public boolean createRecord(BookDTO userDTO);

    public BookDTO getRecord(String bookid);

    public boolean deleteRecord(String bookid);

    public boolean updateRecord(BookDTO bookDTO);

    public List<BookDTO> getBooks();

    public boolean hasBook(String bookid);
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.BookDTO;
import entity.Books;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author jeelp
 */
@Stateless
public class BookFacade implements BookFacadeRemote {

    @PersistenceContext(unitName = "LMS-ejbPU")
    private EntityManager em;

    public void persist(Object object) {
        em.persist(object);
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    protected EntityManager getEntityManager() {
        return em;
    }

    private void create(Books book) {
        em.persist(book);
    }

    private void edit(Books book) {
        em.merge(book);
    }

    private void remove(Books book) {
        em.remove(em.merge(book));
    }

    private Books find(Object id) {
        return em.find(Books.class, id);
    }

    @RolesAllowed({"LMS-APP-ADMIN"})
    @Override
    public boolean createRecord(BookDTO bookDTO) {
        if (find(bookDTO.getBookid()) != null) {
            System.out.print("existing book found!");
            // user whose bookid can be found
            return false;
        }
        // user whose userid could not be found
        try {
            Books book = this.myDTO2DAO(bookDTO);
            this.create(book); // add to database
            return true;
        } catch (Exception ex) {
            return false; // something is wrong, should not be here though
        }
    }

    private Books myDTO2DAO(BookDTO bookDTO) {
        Books book = new Books();
        book.setBookid(bookDTO.getBookid());
        book.setTitle(bookDTO.getTitle());
        book.setAuthor(bookDTO.getAuthor());
        book.setPrice(bookDTO.getPrice());
        book.setStockcount(bookDTO.getBookCount());

        return book;
    }

    private BookDTO myDAO2DTO(Books book) {
        return new BookDTO(book.getBookid(), book.getTitle(), book.getAuthor(), book.getStockcount(), book.getPrice());
    }

    private List<BookDTO> bookListToBookDTOList(List<Books> books) {
        List<BookDTO> bookDTOList = new ArrayList<BookDTO>();

        for (Books book : books) {
            bookDTOList.add(myDAO2DTO(book));
        }

        return bookDTOList;
    }

    private Books employeeDTO2Entity(BookDTO bookDTO) {
        if (bookDTO == null) {
            // just in case
            return null;
        }
        String bookid = bookDTO.getBookid();
        String title = bookDTO.getTitle();
        String author = bookDTO.getAuthor();
        Float price = bookDTO.getPrice();
        Integer stockCount = bookDTO.getBookCount();

        Books book = new Books(bookid, title, author, stockCount, price);

        return book;
    }

    @RolesAllowed({"LMS-APP-ADMIN", "LMS-APP-USER"})
    @Override
    public BookDTO getRecord(String bookid) {
        return myDAO2DTO(find(bookid));
    }

    @RolesAllowed({"LMS-APP-ADMIN"})
    @Override
    public boolean deleteRecord(String userid) {
        // find the employee
        Books u = find(userid);

        // check again - just to play it safe
        if (u == null) {
            return false;
        }

        em.remove(u);
        return true;
    }

    @RolesAllowed({"LMS-APP-ADMIN"})
    @Override
    public boolean updateRecord(BookDTO bookDTO) {
        if (!hasBook(bookDTO.getBookid())) {
            return false;
        }

        // employee exist, update details
        // convert to entity class
        Books book = this.employeeDTO2Entity(bookDTO);
        // update details
        edit(book);

        return true;
    }

    @Override
    public List<BookDTO> getBooks() {
        List<Books> _books = em.createNamedQuery("Books.findAll", Books.class).getResultList();

        return bookListToBookDTOList(_books);
    }

    @Override
    public boolean hasBook(String bookid) {
        System.out.println(bookid);
        return (find(bookid) != null);
    }
}

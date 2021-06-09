/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import entity.BookDTO;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import static java.util.Objects.isNull;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import session.BookFacadeRemote;

/**
 *
 * @author jeelp
 */
@Named(value = "bookManagedBean")
@ConversationScoped
public class BookManagedBean implements Serializable {

    @Inject
    private Conversation conversation;

    @EJB
    private BookFacadeRemote bookFacade;

    private String bookid;
    private String title;
    private String author;
    private Integer bookCount;
    private Float price;

    private List<BookDTO> books;

    /**
     * Creates a new instance of BookManagedBean
     */
    public BookManagedBean() {
    }

    public BookManagedBean(String bookid, String title, String author, Integer bookCount, Float price) {
        this.bookid = bookid;
        this.title = title;
        this.author = author;
        this.bookCount = bookCount;
        this.price = price;
        this.books = this.getBooks();
    }

    public String getBookid() {
        return bookid;
    }

    public void setBookid(String bookid) {
        this.bookid = bookid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getBookCount() {
        return bookCount;
    }

    public void setBookCount(Integer bookCount) {
        this.bookCount = bookCount;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String addBook() {
        //check if book id is null
        if (isNull(bookid)) {
            return "debug";
        }

        BookDTO bookDTO = new BookDTO(bookid, title, author, bookCount, price);

        boolean result = bookFacade.createRecord(bookDTO);

        if (result) {
            return "success";
        } else {
            return "failure";
        }
    }

    public String deleteBook() {
        // check empId is null
        if (isNull(bookid)) {
            return "debug";
        }

        boolean result = bookFacade.deleteRecord(bookid);

        if (result) {
            return "success";
        } else {
            return "failure";
        }
    }

    public List<BookDTO> getBooks() {
        return bookFacade.getBooks();
    }

    public void startConversation() {
        conversation.begin();
    }

    public void endConversation() {
        conversation.end();
    }

    public String setBookDetailsForChange() {
        // check empId is null
        if (isNull(bookid) || conversation == null) {
            return "debug";
        }

        if (!bookFacade.hasBook(bookid)) {
            return "failure";
        }

        // note the startConversation of the conversation
        startConversation();

        // get employee details
        return setBookDetails();
    }

    private String setBookDetails() {

        if (isNull(bookid) || conversation == null) {
            return "debug";
        }

        BookDTO b = bookFacade.getRecord(bookid);

        if (b == null) {
            // no such employee
            return "failure";
        } else {
            this.bookid = b.getBookid();
            this.title = b.getTitle();
            this.author = b.getAuthor();
            this.price = b.getPrice();
            this.bookCount = b.getBookCount();

            return "success";
        }
    }
}

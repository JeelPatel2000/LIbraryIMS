/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;

/**
 *
 * @author jeelp
 */
public class BookDTO implements Serializable {

    String bookid;
    String title;
    String author;
    Integer bookCount;
    Float price;

    public BookDTO(String bookid, String title, String author, Integer bookCount, Float price) {
        this.bookid = bookid;
        this.title = title;
        this.author = author;
        this.bookCount = bookCount;
        this.price = price;
    }

    public String getBookid() {
        return bookid;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public Integer getBookCount() {
        return bookCount;
    }

    public Float getPrice() {
        return price;
    }

}

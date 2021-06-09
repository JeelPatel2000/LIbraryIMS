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
public class BorrowedBookDTO implements Serializable {

    String userid;
    String bookid;
    String issueDate;
    String returnDate;

    public BorrowedBookDTO(String userid, String bookid, String issueDate, String returnDate) {
        this.userid = userid;
        this.bookid = bookid;
        this.issueDate = issueDate;
        this.returnDate = returnDate;
    }

    public String getUserid() {
        return userid;
    }

    public String getBookid() {
        return bookid;
    }

    public String getIssueDate() {
        return issueDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

}

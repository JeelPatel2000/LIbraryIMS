/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author jeelp
 */
@Entity
@Table(name = "ISSUED_BOOKS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IssuedBooks.findAll", query = "SELECT i FROM IssuedBooks i"),
    @NamedQuery(name = "IssuedBooks.findByIssueid", query = "SELECT i FROM IssuedBooks i WHERE i.issueid = :issueid"),
    @NamedQuery(name = "IssuedBooks.findByIssuedate", query = "SELECT i FROM IssuedBooks i WHERE i.issuedate = :issuedate"),
    @NamedQuery(name = "IssuedBooks.findByReturndate", query = "SELECT i FROM IssuedBooks i WHERE i.returndate = :returndate")})
public class IssuedBooks implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "ISSUEID")
    private String issueid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ISSUEDATE")
    @Temporal(TemporalType.DATE)
    private Date issuedate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "RETURNDATE")
    @Temporal(TemporalType.DATE)
    private Date returndate;
    @JoinColumn(name = "BOOKID", referencedColumnName = "BOOKID")
    @ManyToOne(optional = false)
    private Books bookid;
    @JoinColumn(name = "USERID", referencedColumnName = "USERID")
    @ManyToOne(optional = false)
    private Users userid;

    public IssuedBooks() {
    }

    public IssuedBooks(String issueid) {
        this.issueid = issueid;
    }

    public IssuedBooks(String issueid, Date issuedate, Date returndate) {
        this.issueid = issueid;
        this.issuedate = issuedate;
        this.returndate = returndate;
    }

    public String getIssueid() {
        return issueid;
    }

    public void setIssueid(String issueid) {
        this.issueid = issueid;
    }

    public Date getIssuedate() {
        return issuedate;
    }

    public void setIssuedate(Date issuedate) {
        this.issuedate = issuedate;
    }

    public Date getReturndate() {
        return returndate;
    }

    public void setReturndate(Date returndate) {
        this.returndate = returndate;
    }

    public Books getBookid() {
        return bookid;
    }

    public void setBookid(Books bookid) {
        this.bookid = bookid;
    }

    public Users getUserid() {
        return userid;
    }

    public void setUserid(Users userid) {
        this.userid = userid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (issueid != null ? issueid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof IssuedBooks)) {
            return false;
        }
        IssuedBooks other = (IssuedBooks) object;
        if ((this.issueid == null && other.issueid != null) || (this.issueid != null && !this.issueid.equals(other.issueid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.IssuedBooks[ issueid=" + issueid + " ]";
    }

}

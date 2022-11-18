package proj.vo;

import java.sql.Date;

public class BookVO {
	private int bookId;
	private String bookName;
	private String author;
	private String publisher;
	private Date regDate;
	private String rentAvail;
	private int reserNum;
	
	public int getBookId() {
		return bookId;
	}
	public void setBookId(int bookId) {
		this.bookId = bookId;
	}
	public String getBookName() {
		return bookName;
	}
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	public Date getRegDate() {
		return regDate;
	}
	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}
	public String getRentAvail() {
		return rentAvail;
	}
	public void setRentAvail(String rentAvail) {
		this.rentAvail = rentAvail;
	}
	public int getReserNum() {
		return reserNum;
	}
	public void setReserNum(int reserNum) {
		this.reserNum = reserNum;
	}
}

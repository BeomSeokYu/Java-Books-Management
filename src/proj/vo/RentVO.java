package proj.vo;

import java.sql.Date;

public class RentVO extends BookVO{
	private int rentId;
	private String id;
	private int bookId;
	private Date rentDate;
	private Date returnDeadline;
	private Date returnDate;
	
	public int getRentId() {
		return rentId;
	}
	public void setRentId(int rentId) {
		this.rentId = rentId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getBookId() {
		return bookId;
	}
	public void setBookId(int bookid) {
		this.bookId = bookid;
	}
	public Date getRentDate() {
		return rentDate;
	}
	public void setRentDate(Date rentDate) {
		this.rentDate = rentDate;
	}
	public Date getReturnDeadline() {
		return returnDeadline;
	}
	public void setReturnDeadline(Date returnDeadline) {
		this.returnDeadline = returnDeadline;
	}
	public Date getReturnDate() {
		return returnDate;
	}
	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}
}

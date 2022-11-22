package proj.vo;

import java.sql.Date;

public class AppVO {
	private int AppId;
	private String bookName;
	private String author;
	private String publisher;
	private String id;
	private String approval;
	private Date appDate;
	
	public AppVO() {
		
	}
	public AppVO(Integer appId, String bookName, String author, String publisher, Date appDate) {
		this.AppId = appId;
		this.bookName = bookName;
		this.author = author;
		this.publisher = publisher;
		this.appDate = appDate;
	}
	
	public AppVO(Integer appId, String approval, String bookName, String author, String publisher, Date appDate, String id) {
		this.AppId = appId;
		this.bookName = bookName;
		this.author = author;
		this.publisher = publisher;
		this.id = id;
		this.approval = approval;
		this.appDate = appDate;
	}
	
	public int getAppId() {
		return AppId;
	}
	public void setAppId(int appId) {
		AppId = appId;
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
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getApproval() {
		return approval;
	}
	public void setApproval(String approval) {
		this.approval = approval;
	}
	public Date getAppDate() {
		return appDate;
	}
	public void setAppDate(Date appDate) {
		this.appDate = appDate;
	}
}

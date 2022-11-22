package proj.vo;

import java.sql.Date;

public class NoticeVO {
	private int noticeId;
	private String title;
	private String content;
	private Date regDate;
	private Date modDate;
	
	public NoticeVO() {}
	
	public NoticeVO(Integer noticeId, String title, String content, Date regDate, Date modDate) {
		this.noticeId = noticeId;
		this.title = title;
		this.content = content;
		this.regDate = regDate;
		this.modDate = modDate;
	}
	
	public int getNoticeId() {
		return noticeId;
	}
	public void setNoticeId(int noticeId) {
		this.noticeId = noticeId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getRegDate() {
		return regDate;
	}
	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}
	public Date getModDate() {
		return modDate;
	}
	public void setModDate(Date modDate) {
		this.modDate = modDate;
	}
}

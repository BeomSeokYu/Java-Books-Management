package proj.vo;

import java.util.Date;

public class SuggesVO {
	private int suggesId;
	private String title;
	private String content;
	private String id;
	private String comment;
	private String checked ;
	private Date regDate;
	private Date modDate;
	
	public int getSuggesId() {
		return suggesId;
	}
	public void setSuggesId(int suggesId) {
		this.suggesId = suggesId;
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
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getChecked() {
		return checked;
	}
	public void setChecked(String checked) {
		this.checked = checked;
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

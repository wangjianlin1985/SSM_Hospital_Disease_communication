package dingzhen.entity.info;

import dingzhen.common.base.BaseEntity;
import dingzhen.entity.sys.Dept;
import dingzhen.entity.sys.User;

/**
 * 
 * 病情描述
 */
public class Condition extends BaseEntity{

	private String id;
	private String remarks;
	private String time;
	private Dept   dept;
	private User   user;
	private String title;
	private Integer replycount;
	
	
	public Integer getReplycount() {
		return replycount;
	}
	public void setReplycount(Integer replycount) {
		this.replycount = replycount;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public Dept getDept() {
		return dept;
	}
	public void setDept(Dept dept) {
		this.dept = dept;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
}

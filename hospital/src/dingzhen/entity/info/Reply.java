package dingzhen.entity.info;

import dingzhen.common.base.BaseEntity;
import dingzhen.entity.sys.User;

/**
 * 交流回复 
 *
 */
public class Reply extends BaseEntity{

	private String id;      // ID
	private String time;    // 时间
	private String remarks; // 内容
	private User doctor;    // 医生
	private User patient;   // 病人
	private User user;
	private String type;    // 1公共聊天  2 单聊
	private Condition condition;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Condition getCondition() {
		return condition;
	}
	public void setCondition(Condition condition) {
		this.condition = condition;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public User getDoctor() {
		return doctor;
	}
	public void setDoctor(User doctor) {
		this.doctor = doctor;
	}
	public User getPatient() {
		return patient;
	}
	public void setPatient(User patient) {
		this.patient = patient;
	}
}

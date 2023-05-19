package dingzhen.service.info;

import java.util.List;

import dingzhen.common.base.BaseService;
import dingzhen.entity.info.Reply;

public interface ReplyService extends BaseService<Reply> {
	
	public void addOneVSOne(Reply reply);
	
	public List<Reply> findListOneVSOne(Reply reply);
	
	public int countOneVSOne(Reply reply);
	
	public List<Reply> findSllist(Reply reply);
	
	public int deleletByConditionid(String conditionid);
}

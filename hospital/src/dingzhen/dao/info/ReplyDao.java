package dingzhen.dao.info;

import java.util.List;

import org.springframework.stereotype.Repository;

import dingzhen.common.base.BaseDao;
import dingzhen.entity.info.Reply;

@Repository
public interface ReplyDao extends BaseDao<Reply>{
	
	public void addOneVSOne(Reply reply);
	
	public List<Reply> findListOneVSOne(Reply reply);
	
	public int countOneVSOne(Reply reply);
	
	public List<Reply> findSllist(Reply reply);
	
	public int deleletByConditionid(String conditionid);

}

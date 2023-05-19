package dingzhen.service.impl.info;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dingzhen.common.base.BaseServiceImpl;
import dingzhen.dao.info.ReplyDao;
import dingzhen.entity.info.Reply;
import dingzhen.service.info.ReplyService;

@Service
public class ReplyServiceImpl extends BaseServiceImpl<Reply> implements ReplyService{

	@Autowired
	private ReplyDao dao;
	
	@Override
	public void addOneVSOne(Reply reply) {
		dao.addOneVSOne(reply);
	}

	@Override
	public List<Reply> findListOneVSOne(Reply reply) {
		return dao.findListOneVSOne(reply);
	}

	@Override
	public int countOneVSOne(Reply reply) {
		return dao.countOneVSOne(reply);
	}

	@Override
	public List<Reply> findSllist(Reply reply) {
		return dao.findSllist(reply);
	}

	@Override
	public int deleletByConditionid(String conditionid) {
		return dao.deleletByConditionid(conditionid);
	}

}

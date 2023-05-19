package dingzhen.common.base;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;


/**
 * 基础service实现类 
 */
@SuppressWarnings("rawtypes")
public class BaseServiceImpl<T> implements BaseService<T>{

	@Autowired
	private BaseDao<T> dao;
	
	public List<T> findList(T t) throws Exception {
		return dao.findList(t);
	}

	public T findOne(T t) throws Exception {
		return dao.findOne(t);
	}

	public void add(T t) throws Exception {
		dao.add(t);
	}

	public void update(T t) throws Exception {
		dao.update(t);
	}

	public int count(T t) throws Exception {
		return dao.count(t);
	}

	public void delete(T t) throws Exception {
		dao.delete(t);
	}

	public void deleteBatch(List<T> list) throws Exception {
		dao.deleteBatch(list);
	}

	
	public List<T> findListByMap(Map map) throws Exception {
		return dao.findListByMap(map);
	}

	
	
}

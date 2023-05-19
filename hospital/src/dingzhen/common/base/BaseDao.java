package dingzhen.common.base;

import java.util.List;
import java.util.Map;

/**
 * 基础dao 
 */
@SuppressWarnings("rawtypes")
public interface BaseDao<T> {

	/**
	 * 列表查询
	 * @param t
	 * @return
	 * @throws Exception
	 */
	public List<T> findList(T t) throws Exception;
	
	
	/**
	 * 查询单个
	 * @param t
	 * @return
	 * @throws Exception
	 */
	public T findOne(T t) throws Exception;
	
	
	/**
	 * 新增
	 * @param t
	 * @throws Exception
	 */
	public void add(T t) throws Exception;
	
	
	/**
	 * 修改
	 * @param t
	 * @throws Exception
	 */
	public void update(T t) throws Exception;
	
	
	/**
	 * 计数
	 * @param t
	 * @return
	 * @throws Exception
	 */
	public int count(T t) throws Exception;
	
	
	/**
	 * 删除
	 * @param t
	 * @throws Exception
	 */
	public void delete(T t) throws Exception;
	
	/**
	 * 批量删除
	 */
	public void deleteBatch(List<T> list) throws Exception;
	
	
	
	/**
	 * 自定义查询
	 */
	public List<T> findListByMap(Map map) throws Exception;
	
}

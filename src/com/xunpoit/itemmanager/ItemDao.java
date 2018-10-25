package com.xunpoit.itemmanager;

import java.sql.Connection;
import java.sql.SQLException;

import com.xunpoit.entity.PageModel;
import com.xunpoit.item.Item;

/**
 * 和ItemManager接口内的方法一样，
 * DAO:Data Access Object 数据存取对象，作用是隔离业务逻辑代码和数据访问代码，隔离不同数据库的实现
 * 组成部分：①DAO接口②DAO实现类 ③实体类  ④数据库连接和关闭工具类
 * @author 讯博科技--小豪
 *since
 * 2018-9-4
  下午2:31:59
 */
public interface ItemDao {

	/**
	 * 添加的方法
	 */
	public boolean addItems(Connection con,Item item) throws SQLException;
	
	/**
	 * 修改的方法
	 */
	public boolean modifyItems(Connection con,Item item)throws SQLException;
	
	/**
	 * 删除的方法
	 */
	public boolean removeItems(Connection con,String[] itemNos)throws SQLException;
	
	/**
	 * 查询单个的方法
	 */
	public Item queryItemById(Connection con,String itemNo)throws SQLException;
	
	/**
	 * 分页查询的方法
	 */
    public PageModel<Item> queryItems(Connection con,int pageSize,int currentPage,String itemNoOrName)throws SQLException;
	
}

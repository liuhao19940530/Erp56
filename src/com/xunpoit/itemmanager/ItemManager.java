package com.xunpoit.itemmanager;

import com.xunpoit.entity.PageModel;
import com.xunpoit.item.Item;

/**
 * 物料的业务逻辑处理层
 * @author 讯博科技--小豪
 *since
 * 2018-9-4
  下午2:20:48

 */
public interface ItemManager {

	/**
	 * 添加的方法
	 */
	public boolean addItems(Item item);
	
	/**
	 * 修改的方法
	 */
	public boolean modifyItems(Item item);
	
	/**
	 * 删除的方法
	 */
	public boolean removeItems(String[] itemNos);
	
	/**
	 * 查询单个的方法
	 */
	public Item queryItemById(String itemNo);
	
	/**
	 * 分页查询的方法
	 */
    public PageModel<Item> queryItems(int pageSize,int currentPage,String itemNoOrName);
	
}


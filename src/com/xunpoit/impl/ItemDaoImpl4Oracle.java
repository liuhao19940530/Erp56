package com.xunpoit.impl;

import java.sql.Connection;

import com.xunpoit.entity.PageModel;
import com.xunpoit.item.Item;
import com.xunpoit.itemmanager.ItemDao;

public class ItemDaoImpl4Oracle implements ItemDao{

	public boolean addItems(Connection con, Item item) {
		System.out.println("Oracle");
		return false;
	}

	public boolean modifyItems(Connection con, Item item) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean removeItems(Connection con, String[] itemNos) {
		// TODO Auto-generated method stub
		return false;
	}

	public Item queryItemById(Connection con, String itemNo) {
		// TODO Auto-generated method stub
		return null;
	}

	public PageModel<Item> queryItems(Connection con, int pageSize,
			int currentPage,String itemNoOrName) {
		// TODO Auto-generated method stub
		return null;
	}

}	

package com.xunpoit.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xunpoit.client.ItemCategory;
import com.xunpoit.client.ItemUnit;
import com.xunpoit.item.Item;
import com.xunpoit.itemmanager.ItemManager;
import com.xunpoit.manager.ItemManagerImpl;

public class ItemModifyServlet extends AbstractItemServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//创建一个实例，将属性封装进去
		Item item = new Item();
		
		item.setItemNo(request.getParameter("itemNo"));
		
		item.setItemName(request.getParameter("itemName"));
		
		item.setSpec(request.getParameter("spec"));
		
		item.setPattern(request.getParameter("pattern"));
		
		ItemCategory category = new ItemCategory();
		
		category.setId(request.getParameter("category"));
		
		item.setItemCategoryId(category);
		
		ItemUnit unit = new ItemUnit();
		
		unit.setId(request.getParameter("unit"));
		
		item.setItemUnitId(unit);
		
		//调用ItemManager中的方法
		//ItemManager manager = new ItemManagerImpl();

		manager.modifyItems(item);
		
		//请求转发
		request.getRequestDispatcher("/servlet/ItemFindAllServlet").forward(request,response);
	
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	   this.doGet(request, response);
	}

}

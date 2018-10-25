package com.xunpoit.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xunpoit.client.ItemCategory;
import com.xunpoit.client.ItemUnit;
import com.xunpoit.item.Item;
import com.xunpoit.itemmanager.ItemManager;
import com.xunpoit.manager.ItemManagerImpl;

public class ItemAddUser extends AbstractItemServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		//得到实体的实例，将属性封装进实例中
		Item item = new Item();
		
		item.setItemNo(request.getParameter("itemNo"));
		
		item.setItemName(request.getParameter("itemName"));
		
		item.setSpec(request.getParameter("spec"));
		
		item.setPattern(request.getParameter("pattern"));
		
		//因为与外表关联，所以要先得到外表的实例，将id设置进外表中，最后才将外表的实例作为一个属性，保存进物料表中
		ItemCategory category = new ItemCategory();

		category.setId(request.getParameter("category"));
		
		item.setItemCategoryId(category);
		
		ItemUnit unit = new ItemUnit();
		
		unit.setId(request.getParameter("unit"));
		
		item.setItemUnitId(unit);
		
		//得到ItemManager的实现子类，通过addItems()方法添加进去
		//ItemManager manager = new ItemManagerImpl();
		
		String msg = "";//设置一个标记
		
		/*
		//所以的异常都在servlet中处理，这里需要一个捕获处理异常的try{}catch()语句
		try{
			
		  manager.addItems(item);
		  
		  msg = "添加成功！";
		  
		}catch(RuntimeException e){
			
		  msg = "添加失败:"+e.getMessage();
		
		  e.printStackTrace();
			
		}
		*/
		
		//遇到错误页面，直接在web.xml中配置专门用户处理异常的页面
		manager.addItems(item);	
		
		request.setAttribute("message",msg);
		
		request.getRequestDispatcher("/servlet/ItemFindAllServlet").forward(request,response);
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}

}

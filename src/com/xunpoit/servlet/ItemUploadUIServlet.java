package com.xunpoit.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xunpoit.item.Item;
import com.xunpoit.itemmanager.ItemManager;
import com.xunpoit.manager.ItemManagerImpl;

public class ItemUploadUIServlet extends AbstractItemServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//去item_upload页面前的准备工作
		String itemNo = request.getParameter("itemNo");
		
		//ItemManager manager = new ItemManagerImpl();
		
		Item item = null;
		
		try{
		
			item = manager.queryItemById(itemNo);
			
		}catch(Exception e){
			
			e.printStackTrace();
			
		}		
		
		//设置在范围之中
		request.setAttribute("item",item);
		
		request.getRequestDispatcher("/basedata/item_upload.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}

}

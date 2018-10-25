package com.xunpoit.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xunpoit.item.Item;
import com.xunpoit.itemmanager.ItemManager;
import com.xunpoit.manager.ItemManagerImpl;

public class AddServlet extends AbstractItemServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String itemNo = request.getParameter("id");
		
		String time = request.getParameter("time");
		
		//ItemManager manager = new ItemManagerImpl();
		
		Item item = manager.queryItemById(itemNo);
		
		PrintWriter out = response.getWriter();
		
		if(item!=null){
			
			out.print("此物料代码已经存在！");
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request,response);
	}

}

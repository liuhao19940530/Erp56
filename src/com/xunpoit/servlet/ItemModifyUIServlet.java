package com.xunpoit.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xunpoit.client.AbstractDataDict;
import com.xunpoit.client.ItemCategory;
import com.xunpoit.client.ItemUnit;
import com.xunpoit.item.Item;
import com.xunpoit.itemmanager.ItemManager;
import com.xunpoit.manager.DataDictManager;
import com.xunpoit.manager.ItemManagerImpl;

public class ItemModifyUIServlet extends AbstractItemServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
         //获取参数
	     String itemNo = request.getParameter("itemNo");
	     
	     //调用ItemManager中的方法
	     //ItemManager manager = new ItemManagerImpl();
	     
	     Item item = manager.queryItemById(itemNo);
	     
	     //调用DataDictManager得到物料类别和计量单位的集合类
	     DataDictManager dict = DataDictManager.getInstance();
	     
	     List<AbstractDataDict> categoryList = dict.findAll("A",ItemCategory.class.getName());
	     
	     List<AbstractDataDict> unitList = dict.findAll("B",ItemUnit.class.getName());
	     
	     //保存对象item
	     request.setAttribute("item",item);
	     
	     //保存categoryList和unitList集合对象
	     request.setAttribute("categoryList",categoryList);
	     
	     request.setAttribute("unitList",unitList);
	     
	     //请求转发
	     request.getRequestDispatcher("/basedata/item_modify.jsp").forward(request,response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	    this.doGet(request, response);
	}

}

package com.xunpoit.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xunpoit.client.AbstractDataDict;
import com.xunpoit.client.ItemCategory;
import com.xunpoit.client.ItemUnit;
import com.xunpoit.manager.DataDictManager;

public class ItemAddServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//跳转到物料添加页面的准备工作
		//根据DataDictManager类中的方法，得到物料类别和计量单位的集合对象
		DataDictManager manager = DataDictManager.getInstance();
		
		List<AbstractDataDict> categoryList = manager.findAll("A", ItemCategory.class.getName());
		
		List<AbstractDataDict> unitList = manager.findAll("B", ItemUnit.class.getName());
		
		//将集合对象保存在request中，方便跳转页面的获取
		request.setAttribute("categoryList",categoryList);
		
		request.setAttribute("unitList",unitList);
		
		//跳转到其他页面
		request.getRequestDispatcher("../basedata/item_add.jsp").forward(request,response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request,response);
	}

}

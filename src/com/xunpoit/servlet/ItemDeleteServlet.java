package com.xunpoit.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xunpoit.itemmanager.ItemManager;
import com.xunpoit.manager.ItemManagerImpl;

public class ItemDeleteServlet extends AbstractItemServlet {
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//先获取你选中的复选框的值
         String[] itemNos = request.getParameterValues("selectFlag");

         //调用ItemManager方法
         //ItemManager manager = new ItemManagerImpl();
         
         try{
        	 
            manager.removeItems(itemNos);
         
         }catch(Exception e){
        	 
        	 e.printStackTrace();
         }    
         
         //请求转发
         request.getRequestDispatcher("/servlet/ItemFindAllServlet").forward(request,response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	    this.doGet(request, response);
	}

}

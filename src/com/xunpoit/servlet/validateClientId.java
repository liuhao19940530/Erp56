package com.xunpoit.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xunpoit.client.Client;
import com.xunpoit.manager.ClientManager;

public class validateClientId extends HttpServlet {

	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String cid = request.getParameter("cid");
		
		String time = request.getParameter("time");
		
		System.out.println(cid+":"+time);
		
		ClientManager manager = ClientManager.getInstance();
		
		//根据cid查询有没有此对象
		Client client = manager.selectClient(cid);
		
		if(client != null){
			
			PrintWriter out = response.getWriter();
			
			out.print("此代理商代码已经存在！");
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doGet(request, response);
	}

}

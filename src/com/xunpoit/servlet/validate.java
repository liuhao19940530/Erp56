package com.xunpoit.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xunpoit.termi.Termi;
import com.xunpoit.termi.TermiManager;

public class validate extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String termiId = request.getParameter("id");
		
		String time = request.getParameter("time");
		
		TermiManager manager = TermiManager.getInstance();
		
		Termi termi = manager.queryTermiId(termiId);
		
      //  ServletContext application = this.getServletContext();
		
		if(termi != null){
			
			PrintWriter out = response.getWriter();
			
			out.print("此终端客户代码已经存在！");
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doGet(request, response);
	}

}

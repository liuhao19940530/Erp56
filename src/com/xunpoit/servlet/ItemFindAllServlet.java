package com.xunpoit.servlet;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xunpoit.entity.PageModel;
import com.xunpoit.item.Item;
import com.xunpoit.itemmanager.ItemManager;
import com.xunpoit.manager.ItemManagerImpl;

/**
 * 子类继承父类之后，自动拥有父类非私有的属性和方法
 * 继承AbstractItemManager类之后，就会自动间接继承父类HttpServlet
 * @author 讯博科技--小豪
 *since
 * 2018-9-11
  上午10:31:50

 */
public class ItemFindAllServlet extends AbstractItemServlet {
	
	//初始化参数，作为成员变量
	int pageSize = 3;
	
	/**
	 * 在web.xml中设置了pageSize的参数之后，这里用init()初始化方法将其加载进去，才有更高的优先级
	 * 选择的是代表全局的init(config)方法，才能读取web.xml中配置的全局变量
	 */
	@Override
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		super.init(config);
		
		//此处的this.getServletContext()获取的是全局对象 application
		String num = this.getServletContext().getInitParameter("pageSize");
		
		if(num != null && !"".equals(num)){
			
			pageSize = Integer.parseInt(num);//具有比在该Servlet中定义的pageSize有更高的优先级
		}
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		int currentPage = 1;
		
		String currentPageStr = request.getParameter("currentPage");
		
		if(currentPageStr!=null){
			
			currentPage = Integer.valueOf(currentPageStr);
		}
		
		//获取模糊查询的条件
		String itemNoOrName = request.getParameter("clientIdOrName");
		
		//得到ItemManager的实现类
		//ItemManager manager = new ItemManagerImpl();
		
		//调用查询的方法
		PageModel<Item> pm = manager.queryItems(pageSize, currentPage, itemNoOrName);
		
		//将获取的pm保存进request范围之中
		request.setAttribute("pageModel",pm);
		
		//将获取的模糊查询的条件，设置进去
		request.setAttribute("itemNoOrName",itemNoOrName);
		
		//跳转页面，请求转发是应用程序的根
		request.getRequestDispatcher("/basedata/item_maint.jsp").forward(request,response);
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.doGet(request, response);
	}

}

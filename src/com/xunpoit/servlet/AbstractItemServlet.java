package com.xunpoit.servlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.xunpoit.itemmanager.ItemManager;
import com.xunpoit.manager.ItemManagerImpl;
import com.xunpoit.util.BeanFactory;

/**
 * 将此物料代码中，所有需要用到的ItemManager公共部分，抽取到此类中，其余的Servlet就可以直接继承此抽象类，而不用单独每次都new出ItemManager
 * @author 讯博科技--小豪
 *since
 * 2018-9-11
  上午10:26:32

 */
public abstract class AbstractItemServlet extends HttpServlet {

	//声明一个ItemManager的变量
	ItemManager manager;
	
	//在是初始化的方法中，加载itemManager的实例
	@Override
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		super.init(config);
		
		//获取BeanFactory的实例
		BeanFactory factory = (BeanFactory)this.getServletContext().getAttribute("factory");
		
		//取得配置在map中的信息，用类的静态方法forName()反射一个配置的实例
		try {
			manager = (ItemManager)Class.forName(factory.getMap("itemManager")).newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}

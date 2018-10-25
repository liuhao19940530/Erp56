package com.xunpoit.servlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.xunpoit.util.BeanFactory;
/**
 * 专门用于初始化的InitServlet，只实现init()，还要在web.xml中配置
 * @author 讯博科技--小豪
 *since
 * 2018-9-11
  下午3:45:33
 */
//InitServlet没有被继承
//如果一个servlet没有以url路径的方式被调用，或也没有被继承，就没有实例化自己
//这时候可以采用配置的方式，去调用servlet，当web.xml被加载的时候，servlet就被实例化
public class InitServlet extends HttpServlet {

	@Override
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		super.init(config);
		
		BeanFactory factory = BeanFactory.getInstance();
		
		//设置到全局中
		this.getServletContext().setAttribute("factory",factory);
	}
}

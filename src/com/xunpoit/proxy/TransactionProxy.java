package com.xunpoit.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;

import com.sun.xml.internal.txw2.TXW;

import com.xunpoit.flowcard.manager.FlowCardManager;
import com.xunpoit.util.ConnectionManager;

/**
 * 此类用于动态代理，FlowCardManagerImpl中的公共的代码部分
 * @author 讯博科技--小豪
 *since
 * 2018-9-21
  下午2:15:48
 */
public class TransactionProxy implements InvocationHandler{

	//获取实例
	Object target;
	
	//获取实例的公告的方法
	public Object getInstance(Object obj){
		
		this.target = obj;//传递过来的实际的动态实例赋值给此类的成员变量
		
		return Proxy.newProxyInstance(target.getClass().getClassLoader(),target.getClass().getInterfaces(),this);
	}
	
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		
		Object object;
		
		Connection con = ConnectionManager.getCon();
		
		try{
			
		   ConnectionManager.beginTransaction(con);
		   
		   object = method.invoke(target, args);
		   
		   ConnectionManager.commitTransaction(con);
			
		}catch(Exception e){
			
			ConnectionManager.rollbackTransaction(con);
			
			throw new RuntimeException();
			
		}finally{
			
			ConnectionManager.closeDB(con, null, null);
		}
		
		return object;
	}

}

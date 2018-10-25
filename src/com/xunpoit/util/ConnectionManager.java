package com.xunpoit.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 用ThreadLocal线程本地存储，位于java.lang包，来封装Connection,替换DbUtil的功能，就不需要手动的传递Connection参数
 * @author 讯博科技--小豪
 *since
 * 2018-9-15
  下午4:02:47

 */
public class ConnectionManager {

	//获取线程本地存储
	private static ThreadLocal<Connection> conTl = new ThreadLocal<Connection>();
	
	//获取DbConfig
	private static DbConfig dbConfig = DbConfigReader.getInstance().getDbConfig();
	
	static{//获取驱动
		
		try {
			Class.forName(dbConfig.getDriver());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("获取驱动出错了："+e.getMessage());
		}
	}
	
	public static Connection getCon(){
		
		Connection con = conTl.get();//线程本地存储的get()方法可以获取保存在其中的内容，此时就是Connection
		
		if(con==null){//如果是第一次调用，就保存con
			
			try {
				con = DriverManager.getConnection(dbConfig.getUrl(),dbConfig.getUserName(),dbConfig.getPassword());
				
				conTl.set(con);
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("获取连接出错了："+e.getMessage());
			}
		}
		
		return con;//如果里面有连接，则直接返回即可
	}
	
	public static void closeDB(Connection con,Statement st,ResultSet rs){
		
		try {
			if(rs != null){
				
				rs.close();
			}
			
			if(st != null){
				
				st.close();
			}

			if(con != null){

				con.close();
				
				conTl.remove();//在一条连接关闭的时候，也从线程本地存储中移除存进去的con
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("关闭连接的时候出错了："+e.getMessage());
		}
	}
	
	//开启事务
	public static void beginTransaction(Connection con){
		
		if(con != null){
			
			try {
				con.setAutoCommit(false);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("开启事务的时候出错了："+e.getMessage());
			}//手动开启事务
		}
	}
	
	//提交事务
		public static void commitTransaction(Connection con){
			
			if(con != null){
				
				try {
					con.commit();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					System.out.println("提交事务的时候出错了："+e.getMessage());
				}//手动开启事务
			}
		}
		
		//开启事务
		public static void rollbackTransaction(Connection con){
			
			if(con != null){
				
				try {
					con.rollback();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					System.out.println("回滚事务的时候出错了："+e.getMessage());
				}//手动开启事务
			}
		}
}

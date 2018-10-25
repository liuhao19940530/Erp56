package com.xunpoit.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 把数据库的信息，配置到xml文件中，完成相应解析，拿到数据
 * @author 讯博科技--小豪
 *since
 * 2018-8-6
  下午8:12:33

 */
public class DbUtil {

	//获取单例模式的实例
	private static DbConfigReader dbConfigReader = DbConfigReader.getInstance();
	
	//拿到dbConfig
	private static DbConfig dbConfig = dbConfigReader.getDbConfig();
	
	static{//静态块只执行一次，且里面只能执行静态的属性和方法
		
		try {
			Class.forName(dbConfig.getDriver());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("连接数据库驱动出错了：" + e.getMessage());
		}
	}
	
	public static Connection getCon(){
		
		try {
			
			Connection con = DriverManager.getConnection(dbConfig.getUrl(),dbConfig.getUserName(),dbConfig.getPassword());
			
			return con;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("连接数据库出错了：" + e.getMessage());
		}
		
		return null;
	}
	
	//开启事务的方法，手动提交
	public static void beginTransaction(Connection con){
		
		if(con != null){
			
			try {
				con.setAutoCommit(false);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	//提交事务的方法
	public static void commitTransaction(Connection con){
		
		if(con != null){
			
			try {
				con.commit();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
			
	}
	
	//回滚事务的方法
	public static void rollbackTransaction(Connection con){
		
		if(con != null){
			
			try {
				con.rollback();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
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
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("关闭资源时出错了：" + e.getMessage());
		}
	}
	
	public static void main(String[] args) {
		System.out.println(DbUtil.getCon());
	}
}

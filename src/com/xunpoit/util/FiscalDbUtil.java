package com.xunpoit.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 给t_fiscal_year_period设置连接的工具类
 * @author 讯博科技--小豪
 *since
 * 2018-8-16
  上午10:15:21

 */
public class FiscalDbUtil {

	//获取单利模式的实例
	private static FiscalReader fiscalReader = FiscalReader.getInstance();
	
	private static DbConfig db = fiscalReader.getDb();
	
	static{
		
		try {
			Class.forName(db.getDriver());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("获取连接的时候出错了："+e.getMessage());
		}
	}
	
	public static Connection getCon(){
		
		try {
			Connection con = DriverManager.getConnection(db.getUrl(),db.getUserName(),db.getPassword());
			
			return con;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("获取连接的时候出错了："+e.getMessage());
		}
		
		return null;
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
			System.out.println("关闭资源的时候出错了："+e.getMessage());
		}
	}
	
	//开启事务
	public static void beginTransaction(Connection con){
		
		if(con != null){
			
			try {
				con.setAutoCommit(false);//手动开启事务的方法
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("开启事务的时候出错了："+e.getMessage());
			}
		}
	}
	
	//提交事务
	public static void commitTransaction(Connection con){
		
		if(con != null){
			
			try {
				con.commit();//手动提交事务的方法
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("提交事务的时候出错了："+e.getMessage());
			}
		}
	}
	
	//回滚事务
	public static void rollbackTransaction(Connection con){
		
		if(con != null){
			
			try {
				con.rollback();//手动开启事务的方法
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("回滚事务的时候出错了："+e.getMessage());
			}
		}
	}
	
	public static void main(String[] args) {
		
		System.out.println(FiscalDbUtil.getCon());
	}
}

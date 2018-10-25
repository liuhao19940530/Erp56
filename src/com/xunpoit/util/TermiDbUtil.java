package com.xunpoit.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TermiDbUtil {

	private static TermiReader termi = TermiReader.getInstance();
	
	private static DbConfig db = termi.getDbConfig();
	
	static{
		
		try {
			Class.forName(db.getDriver());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("获取驱动的时候出错了:"+e.getMessage());
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
	
	//手动开启事务
	public static void beginTransaction(Connection con){
		
		if(con != null){
			
			try {
				con.setAutoCommit(false);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("手动开启事务的时候出错了："+e.getMessage());
			}
		}
	}
	
	//手动提交事务
	public static void commitTransaction(Connection con){
		
		if(con != null){
			
			try {
				con.commit();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("手动提交事务的时候出错了："+e.getMessage());
			}
		}
	}
	
	//回滚事务
	public static void rollbackTransaction(Connection con){
		
		if(con != null){
			
			try {
				con.rollback();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("手动回滚事务的时候出错了："+e.getMessage());
			}
		}
	}
	
	public static void closeDB(Connection con,Statement st,ResultSet rs){
		
		try {
			if(rs != null){
				
				rs.close();
				
				if(st != null){
					
					st.close();
					
					if(con != null){
						
						con.close();
					}
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("关闭连接的时候出错了："+e.getMessage());
		}
	}
	
	public static void main(String[] args) {
		
		System.out.println(TermiDbUtil.getCon());
	}
}

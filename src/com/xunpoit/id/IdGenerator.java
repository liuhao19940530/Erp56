package com.xunpoit.id;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.xunpoit.util.DbUtil;

/**
 * 维护代理商表的id和会计核算表的id
 * id生成器，因为在t_client中，添加代理商或者区域的id主键，都是由t_table_id来维护增长
 * 当t_client每增加一个id，对应的t_table_id中的id就要加1
 * @author 讯博科技--小豪
 *since
 * 2018-8-14
  上午10:53:01

 */
public class IdGenerator{
	
	//没有自身的特点，写成单例模式
	private static IdGenerator instance = new IdGenerator();
	
	private IdGenerator(){
		
	}
	
	public static synchronized IdGenerator getInstance(){
		
		return instance;
	}

	//先查询t_table_id中的id，根据表名获取主键id的方法
	//如果同时多个路径进行添加操作，有可能会出现线程安全问题，第一种解决方式是synchronized，第二种是使用for update悲观锁
	//悲观锁是数据库层面的锁，每次只执行一次操作
	//public synchronized int getValue(Connection con,String tableName){
		
	public int getValue(Connection con,String tableName){
		
		String sql = "select value from t_table_id where table_name = ? for update";
		
		PreparedStatement ps = null;
		
		ResultSet rs = null;
		
		try {
			ps = con.prepareStatement(sql);
			
			ps.setString(1,tableName);
			
			rs = ps.executeQuery();
			
			if(rs.next()){
				
				//得到t_table_id中的value
				int num = rs.getInt(1);
				
				this.modifyTableValue(con,tableName,num+1);//得到value后，还需要在同一条连接中，根据传递的表名将value加1，方便下次的获取
				
				return num;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("查询value时出错了：" + e.getMessage());
		}finally{
			
			DbUtil.closeDB(null,ps,rs);
		}
				
		return 0;
				
	}

	private void modifyTableValue(Connection con, String tableName, int i) {
		// TODO Auto-generated method stub
		String sql = "update t_table_id set value = ? where table_name = ?";
		
		PreparedStatement ps = null;
		
		try {
			ps = con.prepareStatement(sql);
			
			ps.setInt(1,i);
			
			ps.setString(2,tableName);
			
			ps.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("修改value+1的时候出错了："+ e.getMessage());
		}finally{
			
			DbUtil.closeDB(null, ps,null);
		}
	}
	
}

package com.xunpoit.manager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import java.sql.Connection;

import com.xunpoit.client.AbstractDataDict;
import com.xunpoit.util.DbUtil;

/**
 * 拿出数据字典表的类别
 * @author 讯博科技--小豪
 *since
 * 2018-8-13
  上午10:45:48
 */
public class DataDictManager {
   //单例模式
	private static DataDictManager instance = null;
	
	private DataDictManager(){
		
		
	}
	
	public static synchronized DataDictManager getInstance(){
		
		if(instance==null){
			
			instance = new DataDictManager();
		}
		
		return instance;
	}
	
	//根据类别category查询数据
	public List<AbstractDataDict> findAll(String category,String className){
		
		String sql = "select id,name from t_data_dict where category = ?";
				
		Connection con = DbUtil.getCon();
		
		PreparedStatement ps = null;
		
		ResultSet rs = null;
		
		List<AbstractDataDict> dataList = new ArrayList<AbstractDataDict>();
		
		try{
		  ps = con.prepareStatement(sql);
		  
		  ps.setString(1,category);
		  
		  rs = ps.executeQuery();
		  
		  while(rs.next()){//从结果集中获取数据
			  
			  //用类名获取完整路径 类名.class.getName()
			  AbstractDataDict dataDict = (AbstractDataDict)Class.forName(className).newInstance();
			  
			  dataDict.setId(rs.getString(1));
			  
			  dataDict.setName(rs.getString(2));
			  
			  dataList.add(dataDict);
		  }
		  
		}catch(SQLException e){
			
			System.out.println("查询类别的时候出错了；" + e.getMessage());
			
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			
			DbUtil.closeDB(con, ps, rs);
		}
		return dataList;
	
	}
	
}

package com.xunpoit.manager;

import java.sql.Connection;
import java.sql.SQLException;

import com.xunpoit.entity.PageModel;
import com.xunpoit.exception.ModifyException;
import com.xunpoit.exception.MyException;

import com.xunpoit.item.Item;
import com.xunpoit.itemmanager.ItemDao;
import com.xunpoit.itemmanager.ItemManager;
import com.xunpoit.util.BeanFactory;
import com.xunpoit.util.DbConfig;
import com.xunpoit.util.DbConfigReader;
import com.xunpoit.util.DbUtil;

/**
 * 物料的业务逻辑处理层：调用dao层
 * 这里不使用factory模式，使用另外的一种模式，可以直接获取itemDao的实例
 * 前提是可以替换的工厂模式实现的功能比较简单
 * @author 讯博科技--小豪
 *since
 * 2018-9-4
  下午3:25:52

 */
public class ItemManagerImpl implements com.xunpoit.itemmanager.ItemManager {

	ItemDao itemDao = null;
	
	public ItemManagerImpl(){//将实例放在构造方法里面，确保只有一个实例来共同使用
		
		//得到对象map中对应的key的value
	    String valueMsg = BeanFactory.getInstance().getMap("itemDao");
		
		try{
			
		   itemDao = (ItemDao)Class.forName(valueMsg).newInstance();
			
		}catch(Exception e){
			
			System.out.println("Class.forName()根据字符串反射，获取实例失败了："+e.getMessage());
		}
	}
	
	public boolean addItems(Item item){
	
		Connection con = DbUtil.getCon();//获取一条连接
	
		try{
			
		  //开启事务
		  DbUtil.beginTransaction(con);
			
		  //添加之前，先确认填写的物料代码是否已经存在
		  Item queryItem = itemDao.queryItemById(con,item.getItemNo());
		
		  if(queryItem==null){
		  
		    //添加物料
		    itemDao.addItems(con,item);
		  
		  }else{
		  	
			//给一个自己设定的异常，作为提示信息，表示该物料代码已经存在，不能重复添加
			throw new MyException("该物料代码已存在！不能重复添加！");
		  }
		    //提交事务
		    DbUtil.commitTransaction(con);
		
		}catch(Exception e){
			
		  //回滚事务
			DbUtil.rollbackTransaction(con);
			
			throw new MyException(e);
			
		}finally{
			
			DbUtil.closeDB(con,null,null);
		}
			
		return false;
	}

	public boolean modifyItems(Item item) {
	  
		Connection con = DbUtil.getCon();
		
		try{
			
	       DbUtil.beginTransaction(con);
	       
	       itemDao.modifyItems(con, item);
	       
	       DbUtil.commitTransaction(con);
	       
	       return true;
	       
		}catch(Exception e){ 
	       
			DbUtil.rollbackTransaction(con);
			
			throw new ModifyException("物料修改没有成功!");
			
		}finally{
			
			DbUtil.closeDB(con,null,null);
		}
		
	}

	public boolean removeItems(String[] itemNos) {

		Connection con = DbUtil.getCon();
		
		try{
			
	       DbUtil.beginTransaction(con);
	       
	       itemDao.removeItems(con, itemNos);
	       
	       DbUtil.commitTransaction(con);
	       
	       return true;
	       
		}catch(Exception e){ 
	       
			DbUtil.rollbackTransaction(con);
			
			throw new RuntimeException(e);
			
		}finally{
			
			DbUtil.closeDB(con,null,null);
		}
	
	}

	
	/**
	 * 根据物料代码查询物料对象的方法
	 */
	public Item queryItemById(String itemNo) {
	
		Connection con = DbUtil.getCon();
		
		try {
			
			DbUtil.beginTransaction(con);
			
			Item item =  itemDao.queryItemById(con, itemNo);
			
			DbUtil.commitTransaction(con);
			
			return item;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			throw new RuntimeException(e);
			
		}finally{
			
			DbUtil.closeDB(con,null,null);
		}
		
	}

	/**
	 * 即是查询全部数据的方法，也是根据文本框输入的值itemNoOrName，来模糊查询的方法
	 * @throws SQLException 
	 */
	public PageModel<Item> queryItems(int pageSize, int currentPage,String itemNoOrName){

        Connection con = DbUtil.getCon();
        
        try{
			
        	//开启事务
        	DbUtil.beginTransaction(con);
        	
        	//调用dao层的queryItems()方法  有4个参数
        	PageModel<Item> pm = itemDao.queryItems(con, pageSize, currentPage, itemNoOrName);
        	
        	//提交事务
        	DbUtil.commitTransaction(con);
        	
        	return pm;
        	
		}catch(Exception e){
			
			//回滚事务
			DbUtil.rollbackTransaction(con);
			
			throw new RuntimeException(e);
			
		}finally{
			
			DbUtil.closeDB(con,null,null);
		}
        
	}

	public static void main(String[] args) {
		
		ItemManager manager = new ItemManagerImpl();
		
		manager.addItems(null);
	}
}

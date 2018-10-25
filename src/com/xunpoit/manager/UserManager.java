package com.xunpoit.manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.xunpoit.entity.LoginException;
import com.xunpoit.entity.PageModel;
import com.xunpoit.user.User;
import com.xunpoit.util.DbUtil;

/**
 * 数据库crud的管理类
 * @author 讯博科技--小豪
 *since
 * 2018-8-7
  下午3:32:44

 */
public class UserManager {

	Connection con = null;
	
	PreparedStatement ps = null;
	
	ResultSet rs = null;
	
	/**
	 * 添加的方法
	 * @param user
	 * @return
	 */
	public boolean addUser(User user){
		
		String sql = "insert into t_user(user_id,user_name,password,contact_tel,email,create_date) values(?,?,?,?,?,?)";
		
		con = DbUtil.getCon();
		
		try {
			ps = con.prepareStatement(sql);
			
			ps.setString(1,user.getUserId());
			
			ps.setString(2,user.getUserName());
			
			ps.setString(3,user.getPassword());
			
			ps.setString(4,user.getContactTel());
			
			ps.setString(5,user.getEmail());
			
			ps.setTimestamp(6,new Timestamp(new Date().getTime()));
			
			ps.executeUpdate();
			
			return true;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("添加时候出错了："+e.getMessage());
		}finally{
			
			DbUtil.closeDB(con, ps, null);
		}
		
		return false;
	}
	
	/**
	 * 修改用户数据信息的方法
	 * @param user
	 * @return
	 */
	public boolean modifyUser(User user){
		
		String sql="update t_user set user_name=?,password=?,contact_tel=?,email=? where user_id=?";
		
		con = DbUtil.getCon();
				
		try {
			ps = con.prepareStatement(sql);
			
			ps.setString(1,user.getUserName());
			ps.setString(2,user.getPassword());
			ps.setString(3,user.getContactTel());
			ps.setString(4,user.getEmail());
			ps.setString(5,user.getUserId());
			
			ps.executeUpdate();
			
			return true;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("修改数据的时候出错了:"+e.getMessage());
		}finally{
			
			DbUtil.closeDB(con, ps, rs);
		}
		
		return false;
	}
	
	/**
	 * 修改密码的方法
	 */
public boolean modifyPassword(User user){
		
		String sql="update t_user set password=? where user_id=?";
		
		con = DbUtil.getCon();
				
		try {
			ps = con.prepareStatement(sql);
			
			ps.setString(1,user.getPassword());
			ps.setString(2,user.getUserId());
			
			ps.executeUpdate();
			
			return true;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("修改密码的时候出错了:"+e.getMessage());
		}finally{
			
			DbUtil.closeDB(con, ps, rs);
		}
		
		return false;
	}
	
	
	/**
	 * 删除数据的方法
	 * 要求可以删除多个，而且留下root的系统用户
	 */
	public boolean deleteUser(String userId){
		
		String sql="delete from t_user where user_id=? and user_id <> 'root'";
		
		con = DbUtil.getCon();
		
		try {
			ps = con.prepareStatement(sql);
			
			ps.setString(1,userId);
			
			ps.executeUpdate();
			
			return true;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("删除用户数据的时候出错了：" + e.getMessage());
		}finally{
			
			DbUtil.closeDB(con, ps, rs);
		}	
		
		return false;
			
	}
	
	/**
	 * 用批处理语句，批量删除
	 *这是预处理语句
	 * @param userId
	 * @return
	 */
    public boolean deleteUserByIds(String[] userIds){
		
		//String sql="delete from t_user where user_id=? and user_id <> 'root'";
    	StringBuffer sqlBuffer = new StringBuffer("delete from t_user where user_id in (");
		
    	for(int i=0;i<userIds.length;i++){
    		
    		sqlBuffer.append("?");//拼接？，根据数组的长度
    		
    		if(i<userIds.length-1){//因为?后面有,隔开，所以判断只有最后的那一个元素后面，没有,    			
    			sqlBuffer.append(",");
    		}
    	}
    	
    	sqlBuffer.append(") and user_id <> 'root'");
    	
		con = DbUtil.getCon();
		
		try {
			ps = con.prepareStatement(sqlBuffer.toString());//将sqlBuffer作为整个语句，传递
			
			for(int i=0;i<userIds.length;i++){
				
				ps.setString(i+1,userIds[i]);//i+1是因为，在mysql中，索引从1开始
			}
			
			ps.executeUpdate();
			
			return true;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("删除用户数据的时候出错了：" + e.getMessage());
		}finally{
			
			DbUtil.closeDB(con, ps, rs);
		}	
		
		return false;
			
	}
    
    /**
	 * 用批处理语句，批量删除
	 *这是拼接字符串方式
	 * @param userId
	 * @return
	 */
    public boolean deleteUserByIds01(String[] userIds){
		
		//String sql="delete from t_user where user_id=? and user_id <> 'root'";
    	StringBuffer sqlBuffer = new StringBuffer("delete from t_user where user_id in (");
		
    	for(int i=0;i<userIds.length;i++){
    		
    		sqlBuffer.append("'"+userIds[i]+"'");//拼接？，根据数组的长度
    		
    		if(i<userIds.length-1){//因为?后面有,隔开，所以判断只有最后的那一个元素后面，没有,    			
    			sqlBuffer.append(",");
    		}
    	}
    	
    	sqlBuffer.append(") and user_id <> 'root'");
    	
		con = DbUtil.getCon();
		
		try {
			ps = con.prepareStatement(sqlBuffer.toString());//将sqlBuffer作为整个语句，传递
			
			ps.executeUpdate();
			
			return true;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("删除用户数据的时候出错了：" + e.getMessage());
		}finally{
			
			DbUtil.closeDB(con, ps, rs);
		}	
		
		return false;
			
	}
	
	/**
	 * 查询userId的方法
	 * @param id
	 * @return
	 */
	public User findUserId(String id){
		
		String sql = "select user_id,user_name,password,contact_tel,email,create_date from t_user where user_id = ?";
		
		con = DbUtil.getCon();
		
		User user = null;
		
		try {
			ps = con.prepareStatement(sql);
			
			ps.setString(1,id);
			
			rs = ps.executeQuery();
			
			while(rs.next()){
				user = new User();
				//如果能查询出来
				user.setUserId(rs.getString(1));
				user.setUserName(rs.getString(2));
				user.setPassword(rs.getString(3));
				user.setContactTel(rs.getString(4));
				user.setEmail(rs.getString(5));
				user.setCreateDate(rs.getTimestamp(6));
			}
			
			return user;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("查询user_id时出错了：" + e.getMessage());
		}finally{
			
			DbUtil.closeDB(con,ps,rs);
		}
		
		return null;
	}
	
	/**
	 * 完成分页模型的代码查询
	 */
	public PageModel<User> queryAllUser(int currentPage,int pageSize){
		
		String sql = "select user_id,user_name,contact_tel,email,create_date from t_user order by create_date desc limit ?,?";
		
		con = DbUtil.getCon();
		
		PageModel<User> pm = new PageModel<User>();
		
		List<User> dataList = new ArrayList<User>();
		
		try {
			ps = con.prepareStatement(sql);
			
			ps.setInt(1,(currentPage-1)*pageSize);
			
			ps.setInt(2,pageSize);
			
			rs = ps.executeQuery();
			
			while(rs.next()){
				
				User user = new User();
				
				user.setUserId(rs.getString(1));
				user.setUserName(rs.getString(2));
				user.setContactTel(rs.getString(3));
				user.setEmail(rs.getString(4));
				user.setCreateDate((rs.getTimestamp(5)));
				
				dataList.add(user);
			}
			
			//传递当期页
			pm.setCurrentPage(currentPage);
			
			//得到总条数，传递一个连接，保证和当期处于同一个连接
			int totalCount = this.findCount(con);
			
			//得到总页数
			int totalPage = totalCount%pageSize==0?totalCount/pageSize:totalCount/pageSize+1;
			
			//传递总页数
			pm.setTotalPage(totalPage);
			
			//传递集合对象
			pm.setDataList(dataList);
			
			return pm;//返回分页模型
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("查询用户的所有信息出错了:" + e.getMessage());
		}finally{
			
			DbUtil.closeDB(con, ps, rs);
		}
		
		return null;
	}

	/**
	 * 配合queryAllUser()的小方法，查询的是总条数totalCount
	 * @param con
	 * @return
	 */
	private int findCount(Connection con) {

        String sql="select count(*) from t_user";
        
        try {
			ps = con.prepareStatement(sql);
			
			rs = ps.executeQuery();
			
			if(rs.next()){
				
				return rs.getInt(1);//将查询的总条数作为返回值
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("查询用户总条数时出错了："+e.getMessage());
		}finally{
			DbUtil.closeDB(null, ps, rs);
		}
    
		return 0;
	}
	
	/**
	 * 检验用户是否注册的方法
	 * 此时的逻辑是：先进行用户代码的验证，查询语句就根据user_id来查询，查询到了，再看password是否与数据库的password相同
	 */
	public User loginUser(User user) throws LoginException{
		
		String sql="select user_id,user_name,password,contact_tel,email,create_date from t_user where user_id=?";
		
		con = DbUtil.getCon();
		
		User user02 = null;
		
		try {
			ps = con.prepareStatement(sql);
			
			ps.setString(1,user.getUserId());
			
			//ps.setString(2,user.getPassword());
			
			rs = ps.executeQuery();
			
			if(rs.next()){//如果数据库里面存在rs.getString(3)
				
				if(rs.getString(3).equals(user.getPassword())){//匹配密码
					
					user02 = new User();
					
					user02.setUserId(rs.getString(1));
					user02.setUserName(rs.getString(2));
					user02.setPassword(rs.getString(3));
					user02.setContactTel(rs.getString(4));
					user02.setEmail(rs.getString(5));
					user02.setCreateDate(rs.getTimestamp(6));
					
					return user02;
					
				}else{
					
					throw new LoginException("您输入的密码不存在！");
				}
			}else{//数据库里面不存在
				
				throw new LoginException("您输入的用户名不存在！");
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("查询用户是否存在出错了：" + e.getMessage());
		}finally{
			
			DbUtil.closeDB(con,ps,rs);
		}
		
		return null;
	}
	
	/**
	 * sql注入的方式，拼接字符串，有安全问题
	 *' or'1'='1
	 * @param user
	 * @return
	 * @throws LoginException
	 */
    public User loginUser01(User user) throws LoginException{
		
		String sql="select user_name,password,contact_tel,email,create_date from t_user where user_id='"+user.getUserId()+"' and password='"+user.getPassword()+"'";
		
		con = DbUtil.getCon();
		
		User user02 = null;
		
		try {
			ps = con.prepareStatement(sql);
			
			//ps.setString(1,user.getUserId());
			
			//ps.setString(2,user.getPassword());
			
			rs = ps.executeQuery();
			
			if(rs.next()){//如果数据库里面存在
				
				user02 = new User();
				
				user02.setUserName(rs.getString(1));
				user02.setPassword(rs.getString(2));
				user02.setContactTel(rs.getString(3));
				user02.setEmail(rs.getString(4));
				user02.setCreateDate(rs.getTimestamp(5));
			
				return user02;
			}else{//数据库里面不存在
				
				throw new LoginException("您输入的用户名不存在！");
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("查询用户是否存在出错了：" + e.getMessage());
		}finally{
			
			DbUtil.closeDB(con,ps,rs);
		}
		
		return null;
	}
    
    /**
     * 根据user_id和password看是否存在于数据库中，修改密码的页面中
     */
public User queryPassword(String password){
		
		String sql = "select user_id,user_name,contact_tel,email,create_date from t_user where password=?";
		
		con = DbUtil.getCon();
		
		User userPassword = null;
		
		try {
			ps = con.prepareStatement(sql);
			
			ps.setString(1,password);
			
			rs = ps.executeQuery();
			
			while(rs.next()){
				userPassword = new User();
				//如果能查询出来
				userPassword.setUserId(rs.getString(1));
				userPassword.setUserName(rs.getString(2));
				userPassword.setContactTel(rs.getString(3));
				userPassword.setEmail(rs.getString(4));
				userPassword.setCreateDate(rs.getTimestamp(5));
			}
			
			return userPassword;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("查询password时出错了：" + e.getMessage());
		}finally{
			
			DbUtil.closeDB(con,ps,rs);
		}
		
		return null;
	}
}

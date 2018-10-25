package com.xunpoit.termi;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.xunpoit.client.TermiClientLevel;
import com.xunpoit.id.IdGenerator;
import com.xunpoit.user.Constant;
import com.xunpoit.util.TermiDbUtil;

/**
 * 终端客户的管理类，方法的集合类
 * @author 讯博科技--小豪
 *since
 * 2018-8-19
  下午7:45:47
 */
public class TermiManager {

	//单例模式
	private static TermiManager instance = new TermiManager();//饿汉模式
	
	private TermiManager(){
		
	}
	
	public static synchronized TermiManager getInstance(){
		
		return instance;
	}
	
	//创建一个处理页面信息的方法，调用的是外部的已经封装好的类
	public String getTermiClient(){
		
		return new TermiClient().getTermi();
	}
	
	/**
	 * 创建根据id来查询名称的方法，用到左外连接查询
	 */
	public Termi queryName(int id){
		
		String sql = "select t.pid,t.termi_name,d.name,t.termi_client_level_id,t.termi_client_id,t.contact_tel,t.address,t.zip_code,t.is_leaf,t.is_termi_client,t.contact_man from t_termi_client t left join t_data_dict d on t.termi_client_level_id = d.id where t.id = ?";
		
		Connection con = TermiDbUtil.getCon();
		
		PreparedStatement ps = null;
		
		ResultSet rs = null;
		
		try {
			ps = con.prepareStatement(sql);
			
			ps.setInt(1,id);
			
			rs = ps.executeQuery();
			
			Termi termi = null;
			
			if(rs.next()){
				
				termi = new Termi();
				
				termi.setId(id);
				
				termi.setPid(rs.getInt(1));
				
				termi.setTermiName(rs.getString(2));
				
				TermiClientLevel level = new TermiClientLevel();
				
				level.setName(rs.getString(3));
				
				level.setId(rs.getString(4));
				
				termi.setTermiLevel(level);
				
				termi.setTermiClientId(rs.getString(5));
				
				termi.setContactTel(rs.getString(6));
				
				termi.setAddress(rs.getString(7));
				
				termi.setZipCode(rs.getString(8));
				
				termi.setIsLeaf(rs.getString(9));
				
				termi.setIsTermiClient(rs.getString(10));
				
				termi.setContactMan(rs.getString(11));
			}
			
			return termi;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("根据id查询名字时出错了："+e.getMessage());
		}finally{
			
			TermiDbUtil.closeDB(con,ps,rs);
		}
		
		return null;
	}
	
	/**
	 * 修改区域名字的方法
	 */
	
	public boolean modifyName(Termi termi){
		
		String sql = "update t_termi_client set termi_client_level_id = ?,termi_name = ?,termi_client_id = ?,contact_tel = ?,address = ?,zip_code = ?,contact_man = ? where id = ?";
		
		Connection con = TermiDbUtil.getCon();
		
		PreparedStatement ps = null;
		
		ResultSet rs = null;
		
		try {
			ps = con.prepareStatement(sql);
			
			//TermiClientLevel level = termi.getTermiLevel();
			
			if(termi.getTermiLevel()!=null){//如果是终端
				
				ps.setString(1,termi.getTermiLevel().getId());
				
			}else{//如果是区域
				
				ps.setString(1,null);
			}
			
			ps.setString(2,termi.getTermiName());
			
			ps.setString(3,termi.getTermiClientId());
			
			ps.setString(4,termi.getContactTel());
			
			ps.setString(5,termi.getAddress());
			
			ps.setString(6,termi.getZipCode());
			
			ps.setString(7,termi.getContactMan());
			
			ps.setInt(8,termi.getId());
			
			ps.executeUpdate();
			
			return true;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("修改的时候出错了："+e.getMessage());
		}finally{
			
			TermiDbUtil.closeDB(con,ps,rs);
		}
		
		return false;
	}
	
	/**
	 * 用id查询终端客户的方法，先把信息赋值到temi_client_modify.jsp页面
	 */
	public Termi queryTermi(int id){
		
		String sql = "select termi_client_level_id,termi_name,termi_client_id,contact_tel,address,zip_code,contact_man from t_termi_client where id = ?";
		
		Connection con = TermiDbUtil.getCon();
		
		PreparedStatement ps = null;
		
		ResultSet rs = null;
		
		try {
			ps = con.prepareStatement(sql);
			
			ps.setInt(1,id);
			
			rs = ps.executeQuery();
			
			if(rs.next()){
				
				Termi termi = new Termi();
				
				//将获取的termi_client_level_id设置在辅助类的TermiClientLevel中
				TermiClientLevel level = new TermiClientLevel();
				
				level.setId(rs.getString(1));
				
				termi.setTermiLevel(level);
				
				termi.setTermiName(rs.getString(2));
				
				termi.setTermiClientId(rs.getString(3));
				
				termi.setContactTel(rs.getString(4));
				
				termi.setAddress(rs.getString(5));
				
				termi.setZipCode(rs.getString(6));
				
				termi.setContactMan(rs.getString(7));
				
				return termi;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("根据id查询终端客户信息出错了："+e.getMessage());
		}finally{
			
			TermiDbUtil.closeDB(con,ps,rs);
		}
		
		return null;
	}
	
	/**
	 * 建一个大的方法，做到同一个事务，同一条连接
	 */
	public boolean addTermi(Termi termi){
		
		Connection con = TermiDbUtil.getCon();
		
		try{
			
			//开启连接
			TermiDbUtil.beginTransaction(con);
			
			int id = IdGenerator.getInstance().getValue(con,"t_termi_client");
			
			termi.setId(id);
			
			//添加区域和终端的方法
			this.addNodeOrTermi(con,termi);
			
			//根据is_leaf字段来，区分是否需要修改图标，增加可视化
			int pid = termi.getPid();
			
			if(Constant.YES.equals(this.queryName(pid).getIsLeaf())){//如果父节点是叶子的话
				
				this.modifyIsLeaf(con,"N",pid);//变成不是叶子
			}
			
			//提交连接
			TermiDbUtil.commitTransaction(con);
			
			return true;
			
		}catch(Exception e){
			
			//如果有异常，就回滚事务
			TermiDbUtil.rollbackTransaction(con);
			
			System.out.println("添加区域或终端的时候出错了："+e.getMessage());
			
		}finally{
			
			TermiDbUtil.closeDB(con,null,null);
		}
		
		return false;
	}

	/**
	 * 根据pid来查询是否是叶子，如果是，is_leaf字段变成N，如果不是，递归查询下去
	 * @param con
	 * @param str
	 * @param pid
	 */
	private void modifyIsLeaf(Connection con, String str, int id) {
		// TODO Auto-generated method stub
		String sql = "update t_termi_client set is_leaf = ? where id = ?";
		
		PreparedStatement ps = null;
		
		try {
			ps = con.prepareStatement(sql);
			
			ps.setString(1,str);
			
			ps.setInt(2,id);
			
			ps.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("修改is_leaf字段的时候出错了："+e.getMessage());
		}finally{
			
			TermiDbUtil.closeDB(null,ps,null);
		}
	}

	/**
	 * 添加区域和终端的方法
	 * @param con
	 * @param termi
	 */
	private void addNodeOrTermi(Connection con, Termi termi) {
		// TODO Auto-generated method stub
		String sql = "insert into t_termi_client values(?,?,?,?,?,?,?,?,?,?,?)";
		
		PreparedStatement ps = null;
		
		try {
			ps = con.prepareStatement(sql);
			
			ps.setInt(1,termi.getId());
			
			ps.setInt(2,termi.getPid());
			
			if(termi.getTermiLevel().getId() != null){//如果是终端商
				
				ps.setString(3,termi.getTermiLevel().getId());
				
			}else{//如果是区域
				
				ps.setString(3,null);
			}
			
			ps.setString(4,termi.getTermiName());
			
			ps.setString(5,termi.getTermiClientId());
			
			ps.setString(6,termi.getContactTel());
		
		    ps.setString(7,termi.getAddress());
		    
		    ps.setString(8,termi.getZipCode());
		    
		    ps.setString(9,termi.getIsLeaf());
		    
		    ps.setString(10,termi.getIsTermiClient());
		    
		    ps.setString(11,termi.getContactMan());
		    
		    ps.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("添加区域或终端的方法出错了："+e.getMessage());
		}finally{
			
			TermiDbUtil.closeDB(null,ps,null);
		}
		
	}
	
	/**
	 *Ajax，异步提交机制，根据传递的termiId，来查询数据库，此对象是否已经存在
	 */
	public Termi queryTermiId(String termiId){
		
		String sql = "select id,termi_name from t_termi_client where termi_client_id = ?";
		
		Connection con = TermiDbUtil.getCon();
		
		PreparedStatement ps = null;
		
		ResultSet rs = null;
		
		Termi termi = null;
		
		try {
			ps = con.prepareStatement(sql);
			
			ps.setString(1,termiId);
			
			rs = ps.executeQuery();
			
			if(rs.next()){
				
				termi = new Termi();
				
				termi.setId(rs.getInt(1));
				
				termi.setTermiName(rs.getString(2));
				
				return termi;
			}
			
		} catch (SQLException e) {
			
			System.out.println("根据termi_client_id查询对象的时候出错了："+e.getMessage());
			
		}finally{
			
			TermiDbUtil.closeDB(con,ps,rs);
		}
		
		return null;
	}
	
	/**
	 * 建立一个删除区域或终端客户的方法，同一个事务，同一条连接
	 */
	public boolean removeTermi(int id){
		
		Connection con = TermiDbUtil.getCon();
		
		try{
			//开启事务
			TermiDbUtil.beginTransaction(con);
			
			String isLeaf = this.queryName(id).getIsLeaf();
			
			String isTermi = this.queryName(id).getIsTermiClient();
			
			//如果直接是终端客户，则直接删除
			if(Constant.YES.equals(isTermi)){
			//根据id来删除自己
			   this.removeTermiById(con,id);
			   
			}else{
				
				//判断是否是叶子
				if(Constant.NO.equals(isLeaf)){
					//如果不是叶子
					this.removeNodeById(con,id);
					
				}else{
					//如果是叶子
					this.removeTermiById(con,id);
					
				}
			}  
			
			//得到此id对应的父节点pid
			int pid = this.queryName(id).getPid();
			
			int count = this.getOther(con,pid);
			
			if(count < 1){
				
				//this.modifyIsLeaf(con,"Y",pid);//如果没有其他子节点，就父节点设置为叶子
				this.removeTermiById(con,pid);
			}
			
			//提交事务
			TermiDbUtil.commitTransaction(con);
			
			return true;
			
		}catch(Exception e){
			//回滚事务
			TermiDbUtil.rollbackTransaction(con);
			
			System.out.println("移除区域或终端客户的时候出错了："+e.getMessage());
			
		}finally{
			
			TermiDbUtil.closeDB(con,null,null);
		}
		
		return false;
	}

	/**
	 * 根据pid查询父节点，有多少条数据
	 * @param con
	 * @param pid
	 */
	private int getOther(Connection con, int pid) {
		
		String sql = "select count(*) from t_termi_client where pid = ?";
		
		PreparedStatement ps = null;
		
		ResultSet rs = null;
		
		try {
			ps = con.prepareStatement(sql);
			
			ps.setInt(1,pid);
			
			rs = ps.executeQuery();
			
			if(rs.next()){
				
				return rs.getInt(1);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("根据pid查询总条数的时候出错了："+e.getMessage());
		}finally{
			
			TermiDbUtil.closeDB(null,ps,rs);
		}
		
		return 0;
	}

	/**
	 * 如果既不是终端客户，也不是叶子，用到递归删除
	 * @param con
	 * @param id
	 */
	private void removeNodeById(Connection con, int pid) {
		//首先根据pid查询子节点
		String sql = "select id,is_leaf from t_termi_client where pid = ?";
		
		PreparedStatement ps = null;
		
		ResultSet rs = null;
		
		try {
			ps = con.prepareStatement(sql);
			
			ps.setInt(1,pid);
			
			rs = ps.executeQuery();
			
			while(rs.next()){
				
				int id = rs.getInt(1);
				
				String isLeaf = rs.getString(2);
				
				if(Constant.NO.equals(isLeaf)){//如果不是叶子，递归删除
					
					this.removeNodeById(con,id);
				}else{
					//如果是叶子，直接删除
					this.removeTermiById(con,id);
					
				}
				
				//最后删除自己
				this.removeTermiById(con,pid);
				
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("根据pid查询子节点时候出错了："+e.getMessage());
		}finally{
			
			TermiDbUtil.closeDB(null,ps,rs);
		}
		
	}

	/**
	 * 如果直接是终端客户，根据传递的id来删除
	 * @param id
	 */
	private void removeTermiById(Connection con,int id) {
		// TODO Auto-generated method stub
		String sql = "delete from t_termi_client where id = ?";
		
		PreparedStatement ps = null;
		
		try {
			ps = con.prepareStatement(sql);
			
			ps.setInt(1,id);
			
			ps.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("根据id删除终端客户的时候出错了："+e.getMessage());
		}finally{
			
			TermiDbUtil.closeDB(null,ps,null);
		}
	}
	
}

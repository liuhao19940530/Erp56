package com.xunpoit.manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.xunpoit.client.Client;
import com.xunpoit.client.ClientLevel;
import com.xunpoit.entity.AimClient;
import com.xunpoit.entity.PageModel;
import com.xunpoit.id.IdGenerator;
import com.xunpoit.user.Constant;
import com.xunpoit.util.ConnectionManager;
import com.xunpoit.util.DbUtil;

/**
 * 对于树形结构，在数据库中的增删改查的操作
 * 操作代理商或区域的业务逻辑类
 * @author 讯博科技--小豪
 *since
 * 2018-8-11
  下午3:20:31
 */
public class ClientManager {
	
	//单例模式
	//饿汉模式，上来就new出一个实例，会占用过多的系统资源
	private static ClientManager instance=null;//懒汉模式

	private ClientManager(){
		
	}
	
	//单例模式的懒汉模式会延迟加载
	public static ClientManager getInstance(){
			
		//类名.class获取到的是此类里面的属性，方法
		synchronized(ClientManager.class){//用同步块的形式，可以在方法内部将需要同步的属性放置，其他的属性不受影响
			
			if(instance==null){
				
				instance = new ClientManager();
			}
		
			return instance;
		}	
	}
	
	//获取数据的树形结构
	public String getClientTree(){
		
		//return new ClientTreeReader().getRoot();//调用封装好的树形结构的方法
		
	    return new ClientTree().getRoot();
	}
	
	//添加代理商或者区域的方法
	/**
	 * 同一个连接，同一个事务，有4步同时满足才可以，否则都失败
	 * @param client
	 * @return
	 */
	public boolean addClientOrNode(Client client){
		//在开启事务之前，首先获取一条连接作为参数传递
		Connection con = DbUtil.getCon();
		
		try{
			//开启事务
			DbUtil.beginTransaction(con);
			
			//根据表名获取id
			int id = IdGenerator.getInstance().getValue(con,"t_client");
			
			client.setId(id);//将获取的id主键保存起来
			
			//添加代理商或区域的详细信息
			this.addClientOrNodeDetails(con,client);
			
			int pid = client.getPid();
			
			//判断父节点是否是叶子，如果不是叶子，不用管，如果是叶子，要修改is_leaf字段为N
			if(Constant.YES.equals(this.getClientOrNodeById(pid).getIsLeaf())){
				
				this.modifyClientOrNodeIsLeaf(con,"N",pid);
			}
			
			//提交事务
			DbUtil.commitTransaction(con);

			return true;
	 
		}catch(Exception e){
			//出现异常回滚事务
			DbUtil.rollbackTransaction(con);
			
			System.out.println("用事务添加的时候出错了："+e.getMessage());
			
		}finally{
			
			DbUtil.closeDB(con,null,null);
		}		
			
		return false;
	}
	
	/**
	 * 修改代理商或区域是否是叶子，如果是 叶子，根据id修改为不是叶子
	 * @param con
	 * @param string
	 * @param pid
	 */
	private void modifyClientOrNodeIsLeaf(Connection con, String str, int id) {
		// TODO Auto-generated method stub
		String sql = "update t_client set is_leaf = ? where id = ?";
		
		PreparedStatement ps = null;
		
		try {
			ps = con.prepareStatement(sql);
			
			ps.setString(1,str);
			
			ps.setInt(2,id);
			
			ps.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("修改is_leaf字段出错了：" + e.getMessage());
		}finally{
			
			DbUtil.closeDB(null,ps,null);
		}
	}

	/**
	 * 专门用于添加代理商或区域的详细信息的方法
	 * @param con
	 * @param client
	 */
	private void addClientOrNodeDetails(Connection con, Client client) {

         String sql = "insert into t_client values(?,?,?,?,?,?,?,?,?,?,?)";
         
         PreparedStatement ps = null;
         
         try {
			ps = con.prepareStatement(sql);
			
			ps.setInt(1,client.getId());
			
			ps.setInt(2,client.getPid());
			
			if(client.getClientLevelId() != null){
			
			  ps.setString(3,client.getClientLevelId().getId());
			
			}else{
				
				ps.setString(3,null);
			}  
			
			ps.setString(4,client.getName());
			
			ps.setString(5,client.getClientId());
			
			ps.setString(6,client.getBankAcctNo());
			ps.setString(7,client.getContactTel());
			ps.setString(8,client.getAddress());
			ps.setString(9,client.getZipCode());
			ps.setString(10,client.getIsLeaf());
			ps.setString(11,client.getIsClient());
			
			ps.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("添加区域或代理商的时候出错了：" + e.getMessage());
		}finally{
			
			DbUtil.closeDB(null,ps,null);
		}
		
	}

	//根据主键id查询代理商或者是区域的详细信息
	public Client getClientOrNodeById(int id){
		
		//左外连接查询语句，目的是为了将client_level_id与它的外键t_data_dict表中的id对应，因为程序员看到的是id，客户看的是id对应的name
		String sql = "select c.pid,c.client_level_id,c.name,d.name,c.client_id,c.bank_acct_no,"+
				" c.contact_tel,c.address,c.Zip_code,c.is_leaf,c.is_client from t_client c left join t_data_dict d on c.client_level_id = d.id where c.id = ?";
		
		Connection con = DbUtil.getCon();
		
		PreparedStatement ps = null;
		
		ResultSet rs = null;
		
		Client client = null;//返回的对象
		
		try {
			ps = con.prepareStatement(sql);
			
			ps.setInt(1,id);
			
			rs = ps.executeQuery();
			
			while(rs.next()){
				
				client = new Client();
				
				//将查询到的数据，设置到client实例的属性中
				client.setId(id);
				
				client.setPid(rs.getInt(1));
				
				//设置代理商级别，得到实例，保存到数据字典表里
				ClientLevel level = new ClientLevel();
				
				level.setId(rs.getString(2));
				
				level.setName(rs.getString(4));
				
				client.setClientLevelId(level);//将level作为属性
				
				client.setName(rs.getString(3));
				client.setClientId(rs.getString(5));
				client.setBankAcctNo(rs.getString(6));
				client.setContactTel(rs.getString(7));
				client.setAddress(rs.getString(8));
				client.setZipCode(rs.getString(9));
				client.setIsLeaf(rs.getString(10));
				client.setIsClient(rs.getString(11));
				
			}
			
			return client;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("查询是否是代理商或者区域的时候出错了：" + e.getMessage());
			
			throw new RuntimeException("查询是否是代理商或者区域的时候出错了");
		}finally{
			
			DbUtil.closeDB(con,ps,rs);
		}
	}
	
	//修改，适用于修改代理商和区域
	public boolean modifyName(Client client){
		
		String sql = "update t_client set name = ?,client_level_id=?,bank_acct_no=?,contact_tel=?,address=?,Zip_code=? where id = ?";
		
		Connection con = DbUtil.getCon();
		
		PreparedStatement ps = null;
		
		try {
			ps = con.prepareStatement(sql);
			
			ps.setString(1,client.getName());
			
			if(client.getClientLevelId() != null){
				
			  ps.setString(2,client.getClientLevelId().getId());//有可能出现空指针
			  
			}else{
				
				ps.setString(2,null);
			} 
			  
			ps.setString(3,client.getBankAcctNo());
			ps.setString(4,client.getContactTel());
			ps.setString(5,client.getAddress());
			ps.setString(6,client.getZipCode());
			ps.setInt(7,client.getId());
			
			ps.executeUpdate();
			
			return true;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("修改区域的时候出错了:"+e.getMessage());
		}finally{
			
			DbUtil.closeDB(con,ps,null);
		}
		
		return false;
	}
	
	/**
	 * 根据client_id查询是否有此对象
	 */
	public Client selectClient(String cid){
		
		String sql = "select id,name from t_client where client_id = ?";
		
		Connection con = DbUtil.getCon();
		
		PreparedStatement ps = null;
		
		ResultSet rs = null;
		
		Client client = null;
		
		try {
			ps = con.prepareStatement(sql);
			
			ps.setString(1,cid);
			
			rs = ps.executeQuery();
			
			while(rs.next()){
				
				client = new Client();
				
				client.setId(rs.getInt(1));
				
				client.setName(rs.getString(2));
				
			}
			
			return client;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("根据client_id查询是否有对象的时候出错了："+e.getMessage());
		}finally{
			
			DbUtil.closeDB(con,ps,rs);
		}
		
		return null;
	}
	
	//删除的方法，同一条连接，同一个事务
	/**
	 * 删除的步骤：
	 * ①首先获取id，根据is_client字段判断要删除的是代理商还是区域，如果是代理商，直接删除
	 * ②如果是区域，用is_leaf字段还得判断是否有子节点，如果没有子节点，则直接删除
	 * ③如果有子节点，首先删除自己的子节点，递归删除
	 * ④删除所有的子节点之后，然后删除自己，再去判断父节点是否还有其他的子节点，有的话不用管
	 * ⑤如果父节点没有子节点了，修改is_leaf字段N为Y
	 */
	public boolean removeClientOrNodeById(int id){
		
		//首先根据传递过来的id，查询此对象的is_client字段
		Connection con = DbUtil.getCon();
		
		try {
			
			//开启事务
			DbUtil.beginTransaction(con);
			
			Client client = this.getClientOrNodeById(id);
			
			String isClient = client.getIsClient();
			
			String isLeaf = client.getIsLeaf();
			
			//判断是否是代理商，是代理商直接删除
			if(Constant.YES.equals(isClient)){
				
				//调用直接删除代理商的小方法
				this.removeClientById(con,id);
				
			}else{//如果是区域
				
				//判断是否是叶子，是叶子的话，也直接删除
				if(Constant.YES.equals(isLeaf)){
					
					this.removeClientById(con, id);
					
				}else{//如果不是叶子，此时的id就是子节点的pid
					
					this.removeNodeById(con,id);
				}
			
			}
				
			//再判断此id对应的父节点，有没有其他子节点，调用方法得到父节点下的所有子节点的个数
			int pid = client.getPid();
			
			int count = this.findOtherNodeByPid(con,pid);
			
			if(count < 1){//如果父节点已经没有其他子节点了，就将父节点的is_leaf字段从N变成Y
				
				this.modifyClientOrNodeIsLeaf(con,"Y",pid);
			}
			
			//提交事务
			DbUtil.commitTransaction(con);
		
			return true;
			
		} catch (Exception e) {
			
			//回滚事务
			DbUtil.rollbackTransaction(con);
			
			System.out.println("删除代理商或区域的时候出错了："+e.getMessage());
			
		}finally{
			
			DbUtil.closeDB(con,null,null);
		}
		
		return false;
	}

	private int findOtherNodeByPid(Connection con, int pid) {
		
		String sql = "select count(*) from t_client where pid = ?";
		
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
			System.out.println("根据pid查询父节点总条数的时候出错了："+e.getMessage());
		}finally{
			
			DbUtil.closeDB(null,ps,rs);
		}
		
		return 0;
	}

	/**
	 * 根据传递的id作为子节点的pid来删除子节点
	 * @param con
	 * @param pid
	 */
	private void removeNodeById(Connection con, int pid) {
 
		String sql = "select id,name,is_leaf,is_client from t_client where pid = ?";
   
		PreparedStatement ps = null;
		
		ResultSet rs = null;
		
		try {
			ps = con.prepareStatement(sql);
			
			ps.setInt(1,pid);
			
			ps.executeQuery();
			
			while(rs.next()){
				
				int id = rs.getInt(1);
				
				String isLeaf = rs.getString(3);
				
				if(Constant.NO.equals(isLeaf)){//如果不是叶子，要递归删除
					//如果是区域，并且有子节点的话，继续调用此方法
					this.removeNodeById(con,id);
					
				}else{//是叶子
					
					this.removeClientById(con,id);
				}
			}
			
			//证明已经删除了pid下的所有子节点，然后就删除自己
			this.removeClientById(con,pid);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("id作为pid删除的时候出错了：" + e.getMessage());
			
		}finally{
			
			DbUtil.closeDB(null,ps,rs);
		}
		
	}

	/**
	 * 直接删除代理商的方法
	 * @param con
	 * @param id
	 */
	private void removeClientById(Connection con, int id) {
		// TODO Auto-generated method stub
		String sql = "delete from t_client where id = ?";
		
		PreparedStatement ps = null;
		
		try {
			ps = con.prepareStatement(sql);
			
			ps.setInt(1,id);
			
			ps.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("直接删除代理商的时候出错了:"+e.getMessage());
		}finally{
			
			DbUtil.closeDB(null,ps,null);
		}
	}
	
	/**
	 * 分页查询所有供方的方法
	 */
	public PageModel<Client> findAllClient(int pageSize,int currentPage,String clientIdOrName){
		
		StringBuffer buffer = new StringBuffer("select t.id,d.name levelName,t.client_id,t.name from t_client t left join t_data_dict d on t.client_level_id = d.id where t.is_client = 'Y'");
		
		if(clientIdOrName != null && !"".equals(clientIdOrName)){
			
			buffer.append(" and t.client_id like '%"+clientIdOrName+"%' or t.name like '%"+clientIdOrName+"%'");
		}
		
		buffer.append(" limit?,?");
		
		Connection con = ConnectionManager.getCon();
		
		PreparedStatement ps = null;
		
		ResultSet rs = null;
		
		PageModel<Client> pm = new PageModel<Client>();
		
		List<Client> dataList = new ArrayList<Client>();
		
		try {
			ps = con.prepareStatement(buffer.toString());
			
			ps.setInt(1,(currentPage-1)*pageSize);
			
			ps.setInt(2,pageSize);
			
			rs = ps.executeQuery();
			
			while(rs.next()){
				
				Client client = new Client();
				
				client.setId(rs.getInt("id"));
				
				ClientLevel level = new ClientLevel();
				
				level.setName(rs.getString("levelName"));
				
				client.setClientLevelId(level);
				
				client.setClientId(rs.getString("client_id"));
				
				client.setName(rs.getString("name"));
				
				//将封装好的对象，保存进集合中
				dataList.add(client);
			}
			
			pm.setCurrentPage(currentPage);//保存当前页
			
			pm.setDataList(dataList);//保存集合
			
			//先查询所有供方的总条数
			int totalCount = this.findClientCount(clientIdOrName);
			
			int totalPage = totalCount%pageSize==0?totalCount/pageSize:totalCount/pageSize+1;
			
			pm.setTotalPage(totalPage);
			
			return pm;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("获取所有供方信息的时候出错了："+e.getMessage());
		}finally{
			
			ConnectionManager.closeDB(null, ps, rs);
		}
		
		return null;
	}
	
	/**
	 * 查询所有供方的总条数，如果模糊查询的条件带进来，就根据传递过来的参数查询总条数
	 * @param clientIdOrName
	 * @return
	 */
	private int findClientCount(String clientIdOrName) {

        StringBuffer buffer = new StringBuffer("select count(*) from t_client where is_client = 'Y' ");
        
        if(clientIdOrName != null && !"".equals(clientIdOrName)){
			
			buffer.append("and client_id like '%"+clientIdOrName+"%' or name like '%"+clientIdOrName+"%'");
		}
        
        Connection con = ConnectionManager.getCon();
        
        PreparedStatement ps = null;
        
        ResultSet rs = null;
        
        try {
			ps = con.prepareStatement(buffer.toString());
			
			rs = ps.executeQuery();
			
			if(rs.next()){
				
				return rs.getInt(1);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("查询所有供方的总条数的时候出错了："+e.getMessage());
		}finally{
			
			ConnectionManager.closeDB(null, ps, rs);
		}
        
		return 0;
	}

	/**
	 * 分页查询所有需方的方法，包括代理商和终端
	 */
	public PageModel<AimClient> findAllAimClient(int pageSize,int currentPage,String aimClientIdOrName){
		
		StringBuffer buffer = new StringBuffer("select id,name,client_termi_id,client_termi_level_name from v_aim_client ");
		
		if(aimClientIdOrName!=null&&!"".equals(aimClientIdOrName)){
			
			buffer.append("where name like '%"+aimClientIdOrName+"%' or client_termi_id like '%"+aimClientIdOrName+"%' ");
			
		}
		
		buffer.append("limit ?,?");
		
		Connection con = ConnectionManager.getCon();
		
		PreparedStatement ps = null;
		
		ResultSet rs = null;
		
		PageModel<AimClient> pm = new PageModel<AimClient>();
		
		List<AimClient> list = new ArrayList<AimClient>();
		
		try {
			ps = con.prepareStatement(buffer.toString());
			
			ps.setInt(1,(currentPage-1)*pageSize);
			
			ps.setInt(2,pageSize);
			
			rs = ps.executeQuery();
			
			while(rs.next()){
				
				AimClient aim = new AimClient();
				
				aim.setId(rs.getInt("id"));
				
				aim.setName(rs.getString("name"));
				
				aim.setClientTermiId(rs.getString("client_termi_id"));
				
				aim.setLevelName(rs.getString("client_termi_level_name"));
				
				list.add(aim);
			}
			
			pm.setCurrentPage(currentPage);
			
			pm.setDataList(list);
			
			int totalCount = this.findAimCount(aimClientIdOrName);
			
		    int totalPage = totalCount%pageSize==0?totalCount/pageSize:totalCount/pageSize+1;
		    
		    pm.setTotalPage(totalPage);
		    
		    return pm;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("查询所有需方的时候出粗了："+e.getMessage());
		}finally{
			
			ConnectionManager.closeDB(null, ps, rs);
		}
		
		return null;
	}

	/**
	 * 查询需方的总条数的方法
	 * @param aimClientIdOrName
	 * @return
	 */
	private int findAimCount(String aimClientIdOrName) {
		// TODO Auto-generated method stub
		StringBuffer buffer = new StringBuffer("select count(*) from v_aim_client ");
		
		if(aimClientIdOrName!=null&&!"".equals(aimClientIdOrName)){
			
			buffer.append("where name like '%"+aimClientIdOrName+"%' or client_termi_id like '%"+aimClientIdOrName+"%'");
		}
		
		Connection con = ConnectionManager.getCon();
		
		PreparedStatement ps = null;
		
		ResultSet rs = null;
		
		try {
			ps = con.prepareStatement(buffer.toString());
			
			rs = ps.executeQuery();
			
			if(rs.next()){
				
				return rs.getInt(1);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("模糊查询需方总条数的时候出错了："+e.getMessage());
		}finally{
			
			ConnectionManager.closeDB(null, ps, rs);
		}
		
		return 0;
	}
	
	/**
	 * 根据id查询需方信息的方法
	 */
	public AimClient getAimClientById(int id){
		
		String sql = "select name,client_termi_id from v_aim_client where id = ?";
		
		Connection con = DbUtil.getCon();
		
		PreparedStatement ps = null;
		
		ResultSet rs = null;
		
		AimClient aim = null;
		
		try {
			ps = con.prepareStatement(sql);
			
			ps.setInt(1,id);
			
			rs = ps.executeQuery();
			
			if(rs.next()){
				
				aim = new AimClient();
				
				aim.setId(id);
				
				aim.setName(rs.getString(1));
				
				aim.setClientTermiId(rs.getString(2));
				
				return aim;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("根据id查询需方信息的时候出错了："+e.getMessage());
		}finally{
			
			DbUtil.closeDB(con, ps, rs);
		}
		
		return null;
	}
}

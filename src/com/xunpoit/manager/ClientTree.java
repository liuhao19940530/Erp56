package com.xunpoit.manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.xunpoit.user.Constant;
import com.xunpoit.util.DbUtil;

/**
 * 返回客户端的树模型结构
 * @author 讯博科技--小豪
 *since
 * 2018-8-12
  上午9:42:33
 */
public class ClientTree {

	StringBuffer buffer = new StringBuffer();
	
	int level = 1;//用来定义是第几级节点
	
	//查询树形结构的根
	public String getRoot(){
		
		PreparedStatement ps = null;
		
		ResultSet rs = null;
		
		String sql = " select id,name,is_leaf,is_client from t_client where pid is null";
		
		Connection con = DbUtil.getCon();
		
		try {
			ps = con.prepareStatement(sql);
			
			rs  = ps.executeQuery();
			
			if(rs.next()){
				
				int id = rs.getInt(1);
				
				String name = rs.getString(2);
				
				String isLeaf = rs.getString(3);
				
				String isClient = rs.getString(4);
				
				buffer.append("<div>");
				buffer.append("<img alt=\"展开\" style=\"cursor:hand;\" onClick=\"display('"+id+"');\" id=\"img"+id+"\" src=\"../images/plus.gif\">");
                buffer.append("<img id=\"im"+id+"\" src=\"../images/closedfold.gif\">");
                buffer.append("<a href=\"client_node_crud.jsp?id="+id+"\" target=\"clientDispAreaFrame\">");
				 
				buffer.append(name);
				buffer.append("</a>");
				
				//判断是否是叶子
				if(Constant.NO.equals(isLeaf)){
					
					buffer.append("<div style=\"display:none;\" id=\"div"+id+"\">");
					this.getTree(con,id,level);
					buffer.append("</div>");
				}
				
				buffer.append("</div>");
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("查询所有代理商出错了："+e.getMessage());
			
		}finally{
			
			DbUtil.closeDB(con,ps,rs);
		}
		
		return buffer.toString();
	}
	
	/**
	 * 查询是否是叶子的方法，递归查询 
	 * @param con
	 * @param id
	 */
	private String getTree(Connection con, int pid,int level) {
		// TODO Auto-generated method stub
		PreparedStatement ps = null;
		
		ResultSet rs = null;
		
		String sql = "select id,name,is_leaf,is_client from t_client where pid = ?";
		
		con = DbUtil.getCon();
		
		try {
			ps = con.prepareStatement(sql);
			
			ps.setInt(1,pid);
			
			rs = ps.executeQuery();
			
			while(rs.next()){
				
				int id = rs.getInt(1);
				
				String name = rs.getString(2);
				
				String isLeaf = rs.getString(3);
				
				String isClient = rs.getString(4);
				
				buffer.append("<div>");
				
				for(int i=0;i<level;i++){
				   buffer.append("<img src=\"../images/white.gif\">");
				}
				
				if(Constant.NO.equals(isLeaf)){
					
					buffer.append("<img alt=\"展开\" style=\"cursor:hand;\" onClick=\"display('"+id+"');\" id=\"img"+id+"\" src=\"../images/plus.gif\">");
	                buffer.append("<img id=\"im"+id+"\" src=\"../images/closedfold.gif\">");
	                buffer.append("<a href=\"client_node_crud.jsp?id="+id + "\" target=\"clientDispAreaFrame\">");
					buffer.append(name);
					buffer.append("</a>");
					
					//递归查询，如果不是叶子，就继续查找子节点
					buffer.append("<div style=\"display:none;\" id=\"div"+id+"\">");
					this.getTree(con,id,level+1);
					buffer.append("</div>");
					
				}else{//如果是叶子，需要区分是区域，还是代理
					
					buffer.append("<img src=\"../images/minus.gif\">");
					buffer.append("<img src=\"../images/openfold.gif\">");
					
					if(Constant.YES.equals(isClient)){
						
						buffer.append("<a href=\"client_crud.jsp?id="+id+"\" target=\"clientDispAreaFrame\">");
						buffer.append(name);
						buffer.append("</a>");
					}else{
						
						buffer.append("<a href=\"client_node_crud.jsp?id="+id+"\" target=\"clientDispAreaFrame\">");
						buffer.append(name);
						buffer.append("</a>");
					}
				}
			
				buffer.append("</div>");
			}	
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("查询是否是区域或代理出错了："+e.getMessage());
		}finally{
			
			DbUtil.closeDB(null,ps,rs);
		}
		
		return buffer.toString();
	}
	
	public static void main(String[] args) {
		
		System.out.println(new ClientTree().getRoot());
	}
}

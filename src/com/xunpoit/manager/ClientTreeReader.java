package com.xunpoit.manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.xunpoit.user.Constant;
import com.xunpoit.util.DbUtil;

/**
 * 专门用于生成代理商或区域的属性结构的一个图
 * @author 讯博科技--小豪
 *since
 * 2018-8-11
  下午3:24:05
 */
public class ClientTreeReader {

	StringBuffer buffer = new StringBuffer();
	
	int level = 1;
	
	/**
	 * 查询根节点
	 * @return
	 */
	public String getRoot(){
		
		String sql="select id,name,is_leaf,is_client from t_client where pid is null";
		
	    Connection con= DbUtil.getCon();;
		PreparedStatement ps=null;
		ResultSet rs=null;
		
		try {
			
		    ps = con.prepareStatement(sql);
			
			rs = ps.executeQuery();
			
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
				
				if(Constant.NO.equals(isLeaf)){//当前根有子节点 
					
					buffer.append("<div style=\"display:none;\" id=\"div"+id+"\">");
					//把根的id作为子节点的pid继续查询
					this.getChildNodes(con,id,level);
				    buffer.append("</div>");
				}
				
				buffer.append("</div>");
			}
			
			return buffer.toString();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("获取树形结构的根的时候出错了：" + e.getMessage());
		}finally{
			
			DbUtil.closeDB(con,ps,rs);
		}
		
		return null;

	}

	/**
	 * 根据父id，查询自己的子节点
	 * @param con
	 * @param id
	 * @param level 表示是第几级子节点
	 */
	private String getChildNodes(Connection con, int pid,int level) {
		// TODO Auto-generated method stub
		String sql = "select id,name,is_leaf,is_client from t_client where pid = ?";
		
		PreparedStatement ps=null;
		
		ResultSet rs=null;
		
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
				
				//判断当前区域，是否不是叶子
				if(Constant.NO.equals(isLeaf)){
					
					buffer.append("<img alt=\"展开\" style=\"cursor:hand;\" onClick=\"display('"+id+"');\" id=\"img"+id+"\" src=\"../images/plus.gif\">");
					buffer.append("<img id=\"im"+id+"\" src=\"../images/closedfold.gif\">");
					buffer.append("<a href=\"client_node_crud.jsp?id="+id+"\" target=\"clientDispAreaFrame\">");
					buffer.append(name);
					buffer.append("</a>");
					
					buffer.append("<div style=\"display:none;\" id=\"div"+id+"\">");
					this.getChildNodes(con,id,level+1);//递归查询
					buffer.append("</div>");
				}else{//是叶子的话，没有子节点
					
					buffer.append("<img src=\"../images/minus.gif\">");
					buffer.append("<img src=\"../images/openfold.gif\">");
					
					if(Constant.YES.equals(isClient)){//是代理商的话
					 
					  buffer.append("<a href=\"client_crud.jsp?id="+id+"\" target=\"clientDispAreaFrame\">");
					  buffer.append(name);
					  buffer.append("</a>");
					  
					}else{
						//是区域
					  buffer.append("<a href=\"client_node_crud.jsp?id="+id+"\" target=\"clientDispAreaFrame\">");
					  buffer.append(name);
					  buffer.append("</a>");
					}
					
				}
				
				buffer.append("</div>");
				
			}
			
			return buffer.toString();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			
			DbUtil.closeDB(null,ps,rs);
		}
		
		return null;
	}
	
	public static void main(String[] args) {
		
		String str = new ClientTreeReader().getRoot();
		
		System.out.println(str);
		
	}
}

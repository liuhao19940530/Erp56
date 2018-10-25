package com.xunpoit.termi;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.xunpoit.user.Constant;
import com.xunpoit.util.TermiDbUtil;

/**
 * 终端客户，代理商的页面处理
 * @author 讯博科技--小豪
 *since
 * 2018-8-19
  下午8:30:26
 */
public class TermiClient {

	StringBuffer buffer = new StringBuffer(); 
	
	int level = 1;
	
	//先查询所有的终端客户，这个大的父节点，它的pid是null
	public String getTermi(){
		
		String sql = "select id,termi_name,is_leaf,is_termi_client from t_termi_client where pid is null";
		
		Connection con = TermiDbUtil.getCon();
		
		PreparedStatement ps = null;
		
		ResultSet rs = null;
		
		try {
			ps = con.prepareStatement(sql);
			
			rs = ps.executeQuery();
			
			if(rs.next()){
				
				int id = rs.getInt(1);
				
				String name = rs.getString(2);
				
				buffer.append("<div>");
				
				buffer.append("<img alt=\"展开\" style=\"cursor:hand;\" onClick=\"display('"+id+"');\" id=\"img"+id+"\" src=\"../images/plus.gif\">");

				buffer.append("<img id=\"im"+id+"\" src=\"../images/closedfold.gif\">");
				
				buffer.append("<a href=\"temi_client_node_crud.jsp?id="+id+"\" target=\"temiClientDispAreaFrame\">");
				
				buffer.append(name);
				
				buffer.append("</a>");
				
				String isLeaf = rs.getString(3);
				
				String isTermi = rs.getString(4);
				
				if(Constant.NO.equals(isLeaf)){
					
					buffer.append("<div style=\"display:none;\" id=\"div"+id+"\">");
					//继续查询自己的方法
				    this.getLeaf(con,id,level);
				
				    buffer.append("</div>");
				}

				buffer.append("</div>");
			}
			
			return buffer.toString();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("查询终端信息的时候出错了:"+e.getMessage());
		}finally{
			
			TermiDbUtil.closeDB(con,ps,rs);
		}
		
		return null;
	}
	
	/**
	 * 用传递过来的id作为此时查询的pid
	 * @param con
	 * @param id
	 */
	private String getLeaf(Connection con, int pid,int level) {
		// TODO Auto-generated method stub
		String sql = "select id,termi_name,is_leaf,is_termi_client from t_termi_client where pid=?";
		
		PreparedStatement ps = null;
		
		ResultSet rs = null;
		
		try {
			ps = con.prepareStatement(sql);
			
			ps.setInt(1,pid);
			
			rs = ps.executeQuery();
			
			while(rs.next()){
				
				int id = rs.getInt(1);
				
				String name = rs.getString(2);
				
				String isLeaf = rs.getString(3);
				
				String isTermi = rs.getString(4);
				
				buffer.append("<div>");
				
				for(int i=0;i<level;i++){
					
				  buffer.append("<img src=\"../images/white.gif\">");
				
				}  
				  
				if(Constant.NO.equals(isLeaf)){//如果不是叶子
					
					buffer.append("<img alt=\"展开\" style=\"cursor:hand;\" onClick=\"display('"+id+"');\" id=\"img"+id+"\" src=\"../images/plus.gif\">");

					buffer.append("<img id=\"im"+id+"\" src=\"../images/closedfold.gif\">");
					
					buffer.append("<a href=\"temi_client_node_crud.jsp?id="+id+"\" target=\"temiClientDispAreaFrame\">");
					
					buffer.append(name);
					
					buffer.append("</a>");
					
					buffer.append("<div style=\"display:none;\" id=\"div"+id+"\">");
					
					//递归查询
					this.getLeaf(con,id,level+1);
					
					buffer.append("</div>");
					
				}else{//如果是叶子，要么是终端代理商，要么是区域
					
					buffer.append("<img src=\"../images/minus.gif\">");
					
					buffer.append("<img src=\"../images/openfold.gif\">");
					
					if(Constant.YES.equals(isTermi)){//如果是代理商
						
						buffer.append("<a href=\"temi_client_crud.jsp?id="+id+"\" target=\"temiClientDispAreaFrame\">");
						
						buffer.append(name);
						
						buffer.append("</a>");
						
					}else if(Constant.YES.equals(isLeaf)){//如果是区域
						
						buffer.append("<a href=\"temi_client_node_crud.jsp?id="+id+"\" target=\"temiClientDispAreaFrame\">");
						
						buffer.append(name);
						
						buffer.append("</a>");
					}
				}
				
				buffer.append("</div>");
			}
			
			return buffer.toString();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("将id作为pid查询的时候，出错了："+e.getMessage());
		}finally{
			
			TermiDbUtil.closeDB(null,ps,rs);
		}
		
		return null;
	}
	
	public static void main(String[] args) {
		
		System.out.println(new TermiClient().getTermi());
	}
}

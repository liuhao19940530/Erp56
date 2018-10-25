package com.xunpoit.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.xunpoit.client.ItemCategory;
import com.xunpoit.client.ItemUnit;
import com.xunpoit.entity.PageModel;
import com.xunpoit.item.Item;
import com.xunpoit.itemmanager.ItemDao;
import com.xunpoit.util.DbUtil;

public class ItemDaoImpl4MySql implements ItemDao {

	//在子父类关系中，子类抛出的异常必须和父类抛出的异常一致
	public boolean addItems(Connection con, Item item) throws SQLException {
	
		String sql = "insert into t_items( item_no,item_category_id,item_unit_id,item_name,spec,pattern) values(?,?,?,?,?,?)";
		
		con = DbUtil.getCon();
		
		PreparedStatement ps = null;
		
		try{
			
			ps = con.prepareStatement(sql);
			
			ps.setString(1,item.getItemNo());
			
			ps.setString(2,item.getItemCategoryId().getId());
			
			ps.setString(3,item.getItemUnitId().getId());
			
			ps.setString(4,item.getItemName());
			
			ps.setString(5,item.getSpec());
			
			ps.setString(6,item.getPattern());
			
			ps.executeUpdate();
			
		}finally{
			
			DbUtil.closeDB(null,ps,null);
		}	
				
		return false;
	}

	/**
	 * 物料修改的方法
	 * @throws SQLException 
	 */
	public boolean modifyItems(Connection con, Item item) throws SQLException {

        String sql = "update t_items set item_name=?,spec=?,pattern=?,file_name=?,item_category_id=?,item_unit_id=? where item_no=?";
		
        PreparedStatement ps = null;
        
        try{
        	
        	ps = con.prepareStatement(sql);
        	
        	ps.setString(1,item.getItemName());
        	
        	ps.setString(2,item.getSpec());
        	
        	ps.setString(3,item.getPattern());
        	
        	ps.setString(4,item.getFileName());
        	
        	ps.setString(5,item.getItemCategoryId().getId());
        	
        	ps.setString(6,item.getItemUnitId().getId());
        	
        	ps.setString(7,item.getItemNo());
        	
        	ps.executeUpdate();
        	
        	return true;
        	
        }finally{
        	
        	DbUtil.closeDB(null,ps,null);
        }
        
	}

	public boolean removeItems(Connection con, String[] itemNos) throws SQLException {

        StringBuffer sqlBuffer = new StringBuffer("delete from t_items where item_no in (");
		
        for(int i=0;i<itemNos.length;i++){
        	
        	sqlBuffer.append("?");
        	
        	if(i<itemNos.length-1){
        		
        		sqlBuffer.append(",");
        	}
        }
        
        sqlBuffer.append(")");
        
        PreparedStatement ps = null;
        
        try{
		
        	ps = con.prepareStatement(sqlBuffer.toString());
        	
        	for(int i=0;i<itemNos.length;i++){
        		
        		ps.setString(i+1,itemNos[i]);
        	}
        	
            ps.executeUpdate();
            
            return true;
        	
        }finally{
			
			DbUtil.closeDB(null,ps,null);
		}

	}
 
	/**
	 * 根据物料代码，来查询物料的信息
	 * 用到2次连表查询，left outer join on
	 */
	public Item queryItemById(Connection con, String itemNo) throws SQLException {
		
		String sql = "select t1.item_name,t1.spec,t1.pattern,t1.item_category_id,t2.name categoryName,t1.item_unit_id,"
				
				+ "t3.name unitName,t1.file_name from t_items t1 left join t_data_dict t2 on t1.item_category_id=t2.id left join t_data_dict t3 "
				
				+ "on t1.item_unit_id = t3.id where t1.item_no = ?";
		
		PreparedStatement ps = null;
		
		ResultSet rs = null;
		
		try{
		
			ps = con.prepareStatement(sql);
			
			ps.setString(1,itemNo);
			
			rs = ps.executeQuery();
			
			if(rs.next()){
				
				Item item = new Item();
				
				item.setItemNo(itemNo);//后面需要用到itemNo，所以要设置进实例中
				
				item.setItemName(rs.getString(1));
				
				item.setSpec(rs.getString(2));
				
				item.setPattern(rs.getString(3));
				
				ItemCategory category = new ItemCategory();
				
				category.setId(rs.getString(4));
				
				category.setName(rs.getString(5));
				
				item.setItemCategoryId(category);
				
				ItemUnit unit = new ItemUnit();
				
				unit.setId(rs.getString(6));
				
				unit.setName(rs.getString(7));
				
				item.setItemUnitId(unit);
				
				item.setFileName(rs.getString(8));
				
				return item;
				
			}
			
		}finally{
			
			DbUtil.closeDB(null,ps,rs);
		}	
		return null;
	}

	/**
	 * dao层具体的查询方法，既可以查询全部数据，也可以根据条件来模糊查询
	 * @throws SQLException 
	 */
	public PageModel<Item> queryItems(Connection con, int pageSize,
			int currentPage,String itemNoOrName) throws SQLException {
    
		//因为涉及到字符串的拼接问题，所以用到StringBuffer()方便一些
		StringBuffer sqlBuffer = new StringBuffer("select t1.item_no,t1.item_name,t1.spec,t1.pattern,t2.name categoryName,t3.name unitName "
				
		+ "from t_items t1 left join t_data_dict t2 on t1.item_category_id = t2.id left join t_data_dict t3 on t1.item_unit_id = t3.id ");
		
		//判断有没有模糊查询的条件
		if(itemNoOrName != null && !"".equals(itemNoOrName)){
			
			//如果有模糊查询 如果是字符串类型like '%内容%' 如果是整数类型，like %内容%
			sqlBuffer.append("where t1.item_no like ? or t1.item_name like ? ");
		}

		//拼接上分页显示的部分
		sqlBuffer.append("limit ?,?");
		
		PreparedStatement ps = null;
		
		ResultSet rs = null;
		
		//获取该方法返回类型的实例
		PageModel<Item> pm = new PageModel<Item>();
		
		//获取保存item对象的集合类
		List<Item> list = new ArrayList<Item>();
		
		try{
		
			ps = con.prepareStatement(sqlBuffer.toString());
			
			//将预处理语句所需的参数设置进去
			if(itemNoOrName != null && !"".equals(itemNoOrName)){
			
				ps.setString(1,'%'+itemNoOrName+'%');
				
				ps.setString(2,'%'+itemNoOrName+'%');
				
				ps.setInt(3,(currentPage-1)*pageSize);
				
				ps.setInt(4,pageSize);
				
			}else{
				
				ps.setInt(1,(currentPage-1)*pageSize);
				
				ps.setInt(2,pageSize);
			}
			
			rs = ps.executeQuery();
			
			while(rs.next()){
				
				//将获取的数据，封装进实例类中
				Item item = new Item();
				
				item.setItemNo(rs.getString("item_no"));
				
				item.setItemName(rs.getString("item_name"));
				
				item.setSpec(rs.getString("spec"));
				
				item.setPattern(rs.getString("pattern"));
				
				//将连表查询获取的名字，设置进相应的实体类中
				ItemCategory category = new ItemCategory();
				
				category.setName(rs.getString("categoryName"));
				
				item.setItemCategoryId(category);
				
				ItemUnit unit = new ItemUnit();
				
				unit.setName(rs.getString("unitName"));
				
				item.setItemUnitId(unit);
				
				//将获取的一个个的item对象，保存在集合list中
				list.add(item);
			}
			
			//将list对象保存进pm中
			pm.setDataList(list);
			
			//将currentPage保存进pm中
			pm.setCurrentPage(currentPage);
			
			//得到总条数
			int totalCount = this.findTotalCount(con,itemNoOrName);
			
			//得到总页数
			int totalPage = totalCount%pageSize == 0 ? totalCount/pageSize : totalCount/pageSize+1;
			
			//将总页数设置进pm中
			pm.setTotalPage(totalPage);
			
			return pm;
			
		}finally{
			
			DbUtil.closeDB(null,ps,rs);
		}
		
	}

	/**
	 * 子类中可以增加新的功能实现。这既可以得到总条数的信息，也可以得到根据条件来模糊查询的总条数信息
	 * @param con
	 * @param itemNoOrName
	 * @return
	 * @throws SQLException 
	 */
	private int findTotalCount(Connection con, String itemNoOrName) throws SQLException {
		
		StringBuffer sqlBuffer = new StringBuffer("select count(*) from t_items ");
		
		//判断模糊查询的条件有没有
		if(itemNoOrName != null && !"".equals(itemNoOrName)){
			
			sqlBuffer.append("where item_no like '%" + itemNoOrName + "%' or item_name like '%" + itemNoOrName + "%'");
		}
		
		PreparedStatement ps = null;
		
		ResultSet rs = null;
		
		try{
			
			ps = con.prepareStatement(sqlBuffer.toString());
			
			rs = ps.executeQuery();
			
			if(rs.next()){
				
				return rs.getInt(1);
			}
			
		}finally{
			
			DbUtil.closeDB(null,ps,rs);
		}
		return 0;
	
	}

}

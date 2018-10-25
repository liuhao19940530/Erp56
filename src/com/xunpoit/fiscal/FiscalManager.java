package com.xunpoit.fiscal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.xunpoit.entity.PageModel;
import com.xunpoit.id.IdGenerator;
import com.xunpoit.util.FiscalDbUtil;

/**
 * 对于会计核算期表的所有数据库方法的定义
 * @author 讯博科技--小豪
 *since
 * 2018-8-16
  上午11:20:38
 */
public class FiscalManager {

	/**
	 * 查询t_fiscal_year_period中的全部数据信息
	 */
	public PageModel<FiscalYearPeriod> queryPeriod(int currentPage,int pageSize){
		
		String sql = "select * from t_fiscal_year_period order by begin_date desc limit ?,?";
		
		Connection con = FiscalDbUtil.getCon();
		
		PreparedStatement ps = null;
		
		ResultSet rs = null;
		
		PageModel<FiscalYearPeriod> pageModel = new PageModel<FiscalYearPeriod>();
		
		List<FiscalYearPeriod> fiscalList = new ArrayList<FiscalYearPeriod>();
		
		try {
			ps = con.prepareStatement(sql);
			
			ps.setInt(1,(currentPage-1)*pageSize);
			
			ps.setInt(2,pageSize);
			
			rs = ps.executeQuery();
			
			while(rs.next()){
				
				FiscalYearPeriod fiscalPeriod = new FiscalYearPeriod();
				
				fiscalPeriod.setId(rs.getInt("id"));
				
				fiscalPeriod.setFiscalYear(rs.getInt("fiscal_year"));
				
				fiscalPeriod.setFiscalPeriod(rs.getInt("fiscal_period"));
				
				fiscalPeriod.setBeginDate(rs.getString("begin_date"));
				
				fiscalPeriod.setEndDate(rs.getString("end_date"));
				
				fiscalPeriod.setPeriodSts(rs.getString("period_sts"));
				
				fiscalList.add(fiscalPeriod);
				
			}
			int totalCount = this.getTotalCount(con);
			
			int totalPage = totalCount%pageSize==0?totalCount/pageSize:totalCount/pageSize+1;
			
			pageModel.setCurrentPage(currentPage);
			
			pageModel.setTotalPage(totalPage);
			
			pageModel.setDataList(fiscalList);
			
			return pageModel;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("查询所有的会计核算期表中的信息出错了："+e.getMessage());
		}finally{
			
			FiscalDbUtil.closeDB(con,ps,rs);
		}
		
		return null;
	} 
	
	/**
	 * 得到总条数的方法
	 * @param con
	 * @return
	 */
	private int getTotalCount(Connection con) {
		// TODO Auto-generated method stub
		String sql = "select count(*) from t_fiscal_year_period";
		
		PreparedStatement ps = null;
		
		ResultSet rs = null;
		
		try {
			ps = con.prepareStatement(sql);
			
			rs = ps.executeQuery();
			
			if(rs.next()){
				
				return rs.getInt(1);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("查询总条数的时候出错了：" + e.getMessage());
		}finally{
			
			FiscalDbUtil.closeDB(null,ps,rs);
		}
		
		return 0;
	}

	/**
	 * 根据传递过来的id，查询对应的数据信息
	 * @return
	 */
    public FiscalYearPeriod queryById(int id){
		
		String sql = "select fiscal_year,fiscal_period,begin_date,end_date,period_sts from t_fiscal_year_period where id = ?";
		
		Connection con = FiscalDbUtil.getCon();
		
		PreparedStatement ps = null;
		
		ResultSet rs = null;
		
		FiscalYearPeriod fiscalPeriod = null;
		
		try {
			ps = con.prepareStatement(sql);
			
			ps.setInt(1,id);
			
			rs = ps.executeQuery();
			
			if(rs.next()){
				
				fiscalPeriod = new FiscalYearPeriod();
				
				fiscalPeriod.setFiscalYear(rs.getInt("fiscal_year"));
				
				fiscalPeriod.setFiscalPeriod(rs.getInt("fiscal_period"));
				
				fiscalPeriod.setBeginDate(rs.getString("begin_date"));
				
				fiscalPeriod.setEndDate(rs.getString("end_date"));
				
				fiscalPeriod.setPeriodSts(rs.getString("period_sts"));
				
			}
			
			return fiscalPeriod;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("根据id查询会计核算期表中的一条信息出错了："+e.getMessage());
		}finally{
			
			FiscalDbUtil.closeDB(con,ps,rs);
		}
		
		return null;
	} 
    
    /**
     * 根据id修改所选的行数据的方法
     */
    public boolean modifyById(FiscalYearPeriod fiscal){
    	
    	String sql = "update t_fiscal_year_period set fiscal_year=?,fiscal_period=?,begin_date=?,end_date=?,period_sts=? where id = ?";
    	
    	Connection con = FiscalDbUtil.getCon();
    	
        PreparedStatement ps = null;
        
        try {
        	
			ps = con.prepareStatement(sql);
			
			ps.setInt(1,fiscal.getFiscalYear());
			ps.setInt(2,fiscal.getFiscalPeriod());
			
			ps.setString(3,fiscal.getBeginDate());
			
			ps.setString(4,fiscal.getEndDate());
			ps.setString(5,fiscal.getPeriodSts());
			ps.setInt(6,fiscal.getId());
			
			ps.executeUpdate();
			
			return true;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("修改选择行的数据的时候出错了："+e.getMessage());
		}finally{
			
			FiscalDbUtil.closeDB(con,ps,null);
		}
    	
    	return false;
    } 
    
    /**
     * 添加会计核算期表的方法
     */
    public boolean addPeriod(FiscalYearPeriod fiscal){
    
    	Connection con = FiscalDbUtil.getCon();
    	
    	try{
    		
        //开启事务，同一条连接，同一个事务
        FiscalDbUtil.beginTransaction(con);		
    		
    	//获取靠t_table_id表，维护的自增长的主键id
    	int id = IdGenerator.getInstance().getValue(con,"t_fiscal_year_period");
    	
    	fiscal.setId(id);
    	
    	//调用添加的小方法
    	this.addObject(con,fiscal);
    	
    	//提交事务
    	FiscalDbUtil.commitTransaction(con);
    	
    	return true;
    	
    	}catch(Exception e){
    		
    		//回滚事务
    		FiscalDbUtil.rollbackTransaction(con);
    		
    		System.out.println("调用会计核算表的添加方法的时候出错了："+e.getMessage());
    		
    	}finally{
    		
    		FiscalDbUtil.closeDB(con,null,null);
    	}
    	
    	return false;
    }

    /**
     * 添加会计核算表的数据的方法
     * @param con
     * @param fiscal
     */
	private void addObject(Connection con, FiscalYearPeriod fiscal) {
		// TODO Auto-generated method stub
		String sql = "insert into t_fiscal_year_period values (?,?,?,?,?,?)";
		
		PreparedStatement ps = null;
		
		try {
			ps = con.prepareStatement(sql);
			
			ps.setInt(1,fiscal.getId());
			
			ps.setInt(2,fiscal.getFiscalYear());
			
			ps.setInt(3,fiscal.getFiscalPeriod());
			
			ps.setString(4,fiscal.getBeginDate());
			
			ps.setString(5,fiscal.getEndDate());
			
			ps.setString(6,fiscal.getPeriodSts());
			
			ps.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("addObject()方法出错了："+e.getMessage());
		}finally{
			
			FiscalDbUtil.closeDB(null,ps,null);
		}
	}
    
}

package com.xunpoit.flowcard.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.xunpoit.client.Client;
import com.xunpoit.client.ItemUnit;
import com.xunpoit.entity.AimClient;
import com.xunpoit.entity.PageModel;
import com.xunpoit.flowcard.FlowCardDetail;
import com.xunpoit.flowcard.FlowCardMaster;
import com.xunpoit.item.Item;
import com.xunpoit.user.User;
import com.xunpoit.util.ConnectionManager;

public class FlowCardDao4MySql implements FlowCardDao {

	/**
	 * 实现主页面的信息展示，以及模糊查询的页面
	 * @throws SQLException 
	 */
	public PageModel<FlowCardMaster> findAll(int pageSize, int currentPage,
			String clientId, String beginDate, String endDate) throws SQLException {
		
		StringBuffer buffer = new StringBuffer("select t1.flow_card_id,t2.client_id clientId,t2.name clientName,t3.user_name userName,t1.opt_date from t_flow_card_master t1 left join t_client t2 on t1.client_id = t2.id left join t_user t3 on t1.recorder_id = t3.user_id where t1.opt_date between '"+beginDate+"' and '"+endDate+"' and t1.vou_sts = 'N' ");
		
		if(clientId!=null&&!"".equals(clientId)){
			
			buffer.append(" and t1.client_id like '"+clientId+"'");
		}
		
		buffer.append(" limit ?,?");
		
		Connection con = ConnectionManager.getCon();
		
		PreparedStatement ps = null;
		
		ResultSet rs = null;
		
		PageModel<FlowCardMaster> pm = new PageModel<FlowCardMaster>();
		
		List<FlowCardMaster> dataList = new ArrayList<FlowCardMaster>();
		
		try{
			
			ps = con.prepareStatement(buffer.toString());
			
			ps.setInt(1,(currentPage-1)*pageSize);
			
			ps.setInt(2, pageSize);
			
			rs = ps.executeQuery();
			
			while(rs.next()){
				
				FlowCardMaster master = new FlowCardMaster();
				
				master.setFlowCardId(rs.getString("flow_card_id"));
				
				master.setOptDate(rs.getDate("opt_date"));
				
				Client client = new Client();
				
				client.setClientId(rs.getString("clientId"));
				
				client.setName(rs.getString("clientName"));
				
				master.setClientId(client);
				
				User user = new User();
				
				user.setUserName(rs.getString("userName"));
				
				master.setRecorderId(user);
				
				dataList.add(master);
			}
			
			pm.setCurrentPage(currentPage);
			
			pm.setDataList(dataList);
			
			int totalCount = this.findClientCount(clientId,beginDate,endDate);
			
			int totalPage = totalCount%pageSize==0?totalCount/pageSize:totalCount/pageSize+1;
			
			pm.setTotalPage(totalPage);
			
			return pm;
			
		}finally{
			
			ConnectionManager.closeDB(null, ps, rs);
		}
	
	}

	/**
	 * 查询总条数的时候的方法
	 * @param clientId
	 * @param beginDate
	 * @param endDate
	 * @return
	 * @throws SQLException 
	 */
	private int findClientCount(String clientId, String beginDate,
			String endDate) throws SQLException {

        StringBuffer buffer = new StringBuffer("select count(*) from t_flow_card_master where opt_date between '"+beginDate+"' and '"+endDate+"' and vou_sts = 'N'");
		
        if(clientId!=null&&!"".equals(clientId)){
			
        	buffer.append(" and client_id like '"+clientId+"'");
		}
        
        Connection con = ConnectionManager.getCon();
        
        PreparedStatement ps = null;
        
        ResultSet rs = null;
        
        try{
        	
        	ps = con.prepareStatement(buffer.toString());
        	
        	rs = ps.executeQuery();
        	
        	if(rs.next()){
        	
        	   return rs.getInt(1);
        	
        	}
        }finally{
        	
        	ConnectionManager.closeDB(null, ps, rs);
        }
        
        return 0;
	}

	/**
	 * 根据一个流向单号，查询出所有需要的信息，填写到流向单详细表中
	 * @throws SQLException 
	 */
	public FlowCardMaster findFlowCardMasterById(String flowCardId) throws SQLException {
		
		String sql = "select t2.client_id,t2.name,t1.opt_date from t_flow_card_master t1 left join t_client t2 on t1.client_id = t2.id where t1.flow_card_id = ?";
		
		Connection con = ConnectionManager.getCon();
		
		PreparedStatement ps = null;
		
		ResultSet rs = null;
		
		try {
			
			ps = con.prepareStatement(sql);
			
			ps.setString(1,flowCardId);
			
			rs = ps.executeQuery();
			
			while(rs.next()){
				
				//用主流向单来封装所有查询到的信息
				FlowCardMaster master = new FlowCardMaster();
				
				master.setFlowCardId(flowCardId);
				
				Client client = new Client();
				
				client.setClientId(rs.getString(1));
				
				client.setName(rs.getString(2));
				
				master.setClientId(client);
				
				master.setOptDate(rs.getDate(3));
				
				//调用本类中的获取集合类的方法
			    List<FlowCardDetail> flowList = this.findAllDetailsByNo(flowCardId);
			    
			    //设置进master的保存集合类的属性之中
			    master.setFlowCardList(flowList);
			    
			    return master;
			}
			
		}finally{
			
			ConnectionManager.closeDB(null,ps,rs);
		}
		
		return null;
	}
	
	/**
	 * 根据1个流向单号，查询出所有是此流向单号的流向单详细信息，然后在封装进集合中
	 * @throws SQLException 
	 */
	private List<FlowCardDetail> findAllDetailsByNo(String flowCardNo) throws SQLException{
		
		String sql = "select t2.client_termi_id,t2.name,t3.item_no,t3.item_name,t3.spec,t3.pattern,t4.name,t1.opt_qty from t_flow_card t1 left join v_aim_client t2 on t1.aim_client_id=t2.id left join t_items t3 on t1.item_no = t3.item_no left join t_data_dict t4 on t3.item_unit_id = t4.id where t1.flow_card_no = ?";
		
		Connection con = ConnectionManager.getCon();
		
		PreparedStatement ps = null;
		
		ResultSet rs = null;
		
		//保存流向单详细信息类的集合
		List<FlowCardDetail> list = new ArrayList<FlowCardDetail>();
		
		try{
			
			ps = con.prepareStatement(sql);
			
			ps.setString(1,flowCardNo);
			
			rs = ps.executeQuery();
			
			while(rs.next()){
				
				FlowCardDetail detail = new FlowCardDetail();
				
				AimClient aim = new AimClient();
				
				aim.setClientTermiId(rs.getString(1));
				
				aim.setName(rs.getString(2));
				
				detail.setAimClientId(aim);
				
				Item item = new Item();
				
				item.setItemNo(rs.getString(3));
				
				item.setItemName(rs.getString(4));
				
				item.setSpec(rs.getString(5));
				
				item.setPattern(rs.getString(6));
				
				//因为计量单位与t_data_dict有外键关联，显示页面用到名称，不能用到id
				ItemUnit unit = new ItemUnit();
				
				unit.setName(rs.getString(7));
				
				item.setItemUnitId(unit);
				
				detail.setItemNo(item);
				
				detail.setOptQty(rs.getInt(8));
				
				list.add(detail);
			}
			
			return list;
			
		}finally{
		
			ConnectionManager.closeDB(null, ps, rs);
		}
		
	}

	/**
	 * 每次添加流向单的时候，获取的流向单号，第一次是当前日期+"0001"，后面是每个最大的流向单号+1
	 */
	public String generateFlowCardId() throws SQLException {

        Connection con = ConnectionManager.getCon();
        
        //获取当前日期，并且格式化
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        
        Date date = new Date();
        
        String time = sdf.format(date);
        
        String sql = "select max(flow_card_id) from t_flow_card_master where flow_card_id like ?";
        
        PreparedStatement ps = null;
        
        ResultSet rs = null;
        
        try {
			ps = con.prepareStatement(sql);
			
			ps.setString(1,'%'+time+'%');
			//ps.setString(1,'%'+"20180917"+'%');
			
			rs = ps.executeQuery();
			
			if(rs.next()){
				
				long id = rs.getLong(1);//如果已经有了流向单号，且是最大的
				
				if(id!=0){
				//下一个是最大的流向单加1
				  return String.valueOf(id+1);
				
				}else{
					
					//第一次的流向单号
					return time+"0001";
					
				}
			}
			
		}finally{
			
			ConnectionManager.closeDB(null,ps,rs);
		}
        
		return null;
	}

	/**
	 * 操作主表中的内容
	 * @throws SQLException 
	 */
	public void addFlowCardMaster(FlowCardMaster flowMaster) throws SQLException {

          String sql = "insert into t_flow_card_master(flow_card_id,opt_type,fiscal_year_period_id,client_id,recorder_id,opt_date,vou_sts) values(?,?,?,?,?,?,?)";
		  
          Connection con = ConnectionManager.getCon();
	      
          PreparedStatement ps = null;
          
          try{
            ps = con.prepareStatement(sql);
            
            ps.setString(1,flowMaster.getFlowCardId());
            
            ps.setString(2,flowMaster.getOptType());
            
            ps.setInt(3,flowMaster.getFiscalYearPeriodId().getId());
            
            ps.setInt(4,flowMaster.getClientId().getId());
            
            ps.setString(5,flowMaster.getRecorderId().getUserId());
            
            ps.setTimestamp(6,new Timestamp(flowMaster.getOptDate().getTime()));//返回一个Date类型的时间
            
            ps.setString(7,flowMaster.getVouSts());
            
            ps.executeUpdate();//执行
            
          }finally{
        	  
        	  ConnectionManager.closeDB(null,ps,null);
          }
	     
	}

   /**
    * 填写流向单的详细信息表，先要获取List中的内容，因为一个流向单号，可能对应多个流向单
 * @throws SQLException 
    */
	public void addFlowCardDetail(FlowCardMaster flowMaster) throws SQLException {
		 
		  String sql = "insert into t_flow_card(flow_card_no,aim_client_id,item_no,opt_qty,adjust_flag) values(?,?,?,?,?)";
		
		  Connection con = ConnectionManager.getCon();
		
          List<FlowCardDetail> list = flowMaster.getFlowCardList();
		
          PreparedStatement ps = null;
          
          try {
        	  
			ps = con.prepareStatement(sql);
			
			 //循环添加
            for(FlowCardDetail flow:list){
        	  
            	ps.setString(1,flowMaster.getFlowCardId());//这个流向单号是存储设置在FlowCardMaster中的flowCardId属性
            	
            	ps.setInt(2,Integer.valueOf(flow.getAimClientId().getId()));
            	
            	ps.setString(3,flow.getItemNo().getItemNo());
            	
            	ps.setInt(4,flow.getOptQty());
            	
            	ps.setString(5,flow.getAdjustFlag());
            	
            	//执行批量添加语句
            	ps.addBatch();
        	  
            }
	
            //执行批处理语句
            ps.executeBatch();
            
		}finally{
			
			ConnectionManager.closeDB(null, ps,null);
		}	        
	   
	}

	/**
	 *因为有外键关联关系，所以先删除从表中的信息，才能够在删除主表中的信息，否则会报错
	 * @throws SQLException 
	 */
	public void removeFlowCards(String[] flowCardIds) throws SQLException {
		// TODO Auto-generated method stub
		StringBuffer buffer = new StringBuffer("delete from t_flow_card where flow_card_no in (");
		
		for(int i=0;i<flowCardIds.length;i++){
			
			buffer.append(flowCardIds[i]);
			
			if(i<flowCardIds.length-1){
				
				buffer.append(",");
			
			}
		}
		
		buffer.append(")");
		
		Connection con = ConnectionManager.getCon();
		
		PreparedStatement ps = null;
		
		try{
		
			ps = con.prepareStatement(buffer.toString());
			
			ps.executeUpdate();
		
		}finally{
			
			ConnectionManager.closeDB(null, ps, null);
		}
	}

	/**
	 * 删除主表信息的方法
	 * @throws SQLException 
	 */
	public void removeFlowCardMaster(String[] flowCardIds) throws SQLException {

		StringBuffer buffer = new StringBuffer("delete from t_flow_card_master where flow_card_id in (");
		
		for(int i=0;i<flowCardIds.length;i++){
			
			buffer.append(flowCardIds[i]);
			
			if(i<flowCardIds.length-1){
				
				buffer.append(",");
			
			}
		}
		
		buffer.append(")");
		
		Connection con = ConnectionManager.getCon();
		
		PreparedStatement ps = null;
		
		try{
		
			ps = con.prepareStatement(buffer.toString());
			
			ps.executeUpdate();
		
		}finally{
			
			ConnectionManager.closeDB(null, ps, null);
		}
	}

	public void modifyFlowCardById(FlowCardMaster flowMaster) {
		// TODO Auto-generated method stub

	}

	/**
	 * 此处的送审，就是将vou_sts从N修改为S
	 * @throws SQLException 
	 */
	public void submitFlowCardList(String[] flowCardIds) throws SQLException {

		StringBuffer sqlBuffer = new StringBuffer("update t_flow_card_master set vou_sts = 'S' where flow_card_id in (");
		
		for(int i=0;i<flowCardIds.length;i++){
			
			sqlBuffer.append("?");
			
			if(i<flowCardIds.length-1){
				
				sqlBuffer.append(",");
			}
		}
		
		sqlBuffer.append(")");

		Connection con = ConnectionManager.getCon();
		
		PreparedStatement ps = null;
		
		try{
			
			ps = con.prepareStatement(sqlBuffer.toString());
			
			//因为是预处理语句，所以这里要循环来设置每一个值
			for(int i=0;i<flowCardIds.length;i++){
				
				ps.setString(i+1,flowCardIds[i]);
			}
			
			ps.executeUpdate();//执行预处理语句
			
		}finally{
			
			ConnectionManager.closeDB(null, ps,null);
		}
	}
}

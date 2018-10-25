package com.xunpoit.flowcard.dao;

import java.sql.SQLException;
import java.util.List;

import com.xunpoit.entity.PageModel;
import com.xunpoit.flowcard.FlowCardDetail;
import com.xunpoit.flowcard.FlowCardMaster;

/**
 * 流向单的dao层
 * dao层多个方法的调用，去完成一个manager方法的实现
 * @author 讯博科技--小豪
 *since
 * 2018-9-13
  下午5:13:16
 */
public interface FlowCardDao {

		//分页查询
		public PageModel<FlowCardMaster> findAll(int pageSize,int currentPage,String clientId,String beginDate,String endDate) throws SQLException;
		
		//查询1个的方法
		public FlowCardMaster findFlowCardMasterById(String flowCardId)throws SQLException;
		
		//添加流向单，要分为3个步骤
		//1.获取当前添加的流向单的主键（流向单号）
		//2.如果是第一笔单子，就以当前日期+"0001"作为主键
		//3.如果不是第一笔单子，就应该首先获取今天最大单号，在此基础上加1作为主键
		public String generateFlowCardId()throws SQLException;
		
		//2.添加流向单的主信息
		public void addFlowCardMaster(FlowCardMaster flowMaster)throws SQLException;
		
		//3.添加流向单的详细信息	
		public void addFlowCardDetail(FlowCardMaster flowMaster)throws SQLException;
		
		//删除流向单，要先删除详细信息的表，再删除主表，因为他们具有外键关联关系，如果先删除主表，会报错
		public void removeFlowCards(String[] flowCardIds)throws SQLException;
		
		//删除主表
		public void removeFlowCardMaster(String[] flowCardIds)throws SQLException;
		
		//修改流向单
		public void modifyFlowCardById(FlowCardMaster flowMaster)throws SQLException;
		
		//送审流向单
		public void submitFlowCardList(String[] flowCardIds)throws SQLException;

}

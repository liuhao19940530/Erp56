package com.xunpoit.flowcard.manager;

import java.sql.SQLException;
import java.util.List;

import com.xunpoit.entity.PageModel;
import com.xunpoit.flowcard.FlowCardDetail;
import com.xunpoit.flowcard.FlowCardMaster;

/**
 * 关于流向单的manager部分
 * 一个manager中调用这一次的方法，在dao层，可能是几个方法组合使用的结果
 * @author 讯博科技--小豪
 *since
 * 2018-9-13
  下午4:56:33

 */
public interface FlowCardManager {

	//分页查询
	public PageModel<FlowCardMaster> findAll(int pageSize,int currentPage,String clientId,String beginDate,String endDate)throws SQLException;
	
	//查询1个的方法
	public FlowCardMaster findFlowCardMasterById(String flowCardId)throws SQLException;
	
	//添加流向单
	public void addFlowCardDetail(FlowCardMaster flowMaster)throws SQLException;
	
	//删除流向单
	public void removeFlowCards(String[] flowCardIds)throws SQLException;
	
	//修改流向单
	public void modifyFlowCardById(FlowCardMaster flowMaster)throws SQLException;
	
	//送审流向单
	public void submitFlowCardList(String[] flowCardIds)throws SQLException;
}

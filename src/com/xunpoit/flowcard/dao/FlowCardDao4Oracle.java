package com.xunpoit.flowcard.dao;

import java.sql.SQLException;
import java.util.List;

import com.xunpoit.entity.PageModel;
import com.xunpoit.flowcard.FlowCardDetail;
import com.xunpoit.flowcard.FlowCardMaster;

public class FlowCardDao4Oracle implements FlowCardDao {

	public PageModel<FlowCardMaster> findAll(int pageSize, int currentPage,
			String clientId, String beginDate, String endDate)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public FlowCardMaster findFlowCardMasterById(String flowCardId)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public String generateFlowCardId() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public void addFlowCardMaster(FlowCardMaster flowMaster)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void addFlowCardDetail(FlowCardMaster flowMaster)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void removeFlowCards(String[] flowCardIds) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void removeFlowCardMaster(String[] flowCardIds) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void modifyFlowCardById(FlowCardMaster flowMaster)
			throws SQLException {
		// TODO Auto-generated method stub
		
	}

	public void submitFlowCardList(String[] flowCardIds) throws SQLException {
		// TODO Auto-generated method stub
		
	}
	
}

package com.xunpoit.flowcard.manager;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JOptionPane;

import org.omg.CORBA.TCKind;

import com.xunpoit.entity.AimClient;
import com.xunpoit.entity.PageModel;
import com.xunpoit.exception.FlowCardException;
import com.xunpoit.exception.MyException;
import com.xunpoit.flowcard.FlowCardDetail;
import com.xunpoit.flowcard.FlowCardMaster;
import com.xunpoit.flowcard.dao.FlowCardDao;
import com.xunpoit.item.Item;
import com.xunpoit.util.BeanFactory;
import com.xunpoit.util.ConnectionManager;

public class FlowCardManagerImpl implements FlowCardManager{
	
	//声明一个变量，获取dao的实例
	FlowCardDao flowCardDao;
	
	public FlowCardManagerImpl(){
		
		String flowClass = BeanFactory.getInstance().getMap("flowCardDao");
		
		try {
			
			flowCardDao = (FlowCardDao)Class.forName(flowClass).newInstance();
		
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public PageModel<FlowCardMaster> findAll(int pageSize, int currentPage,
			String clientId, String beginDate, String endDate) throws SQLException {
	
        	PageModel<FlowCardMaster> pm = flowCardDao.findAll(pageSize, currentPage, clientId, beginDate, endDate);
    
        	return pm;
	}

	/**
	 * 根据流向单号，查询每个流向单的详细信息
	 * @throws SQLException 
	 */
	public FlowCardMaster findFlowCardMasterById(String flowCardId) throws SQLException {
	
        	FlowCardMaster master = flowCardDao.findFlowCardMasterById(flowCardId);
        	
        	return master;
        
	}

	/**
	 * 添加流向单的操作
	 * 1，获取当前添加的流向单的主键（流向单号）
	 * 2，如果是第一笔单子，就以当前日期+“0001”为主键
	 * 3，如果不是第一笔单子，就应该首先获取今天的最大单号，在此基础上加1作为主键
	 * @throws SQLException 
	 * @throws FlowCardException 
	 */
	public void addFlowCardDetail(FlowCardMaster flowMaster) throws SQLException{
		
			//获取流向单号
			String flowCardId = flowCardDao.generateFlowCardId();
			
			//添加主表中的信息，将获取的流向单号，设置给主的流向单
			flowMaster.setFlowCardId(flowCardId);
		
			flowCardDao.addFlowCardMaster(flowMaster);
			
			flowCardDao.addFlowCardDetail(flowMaster);
			//提交事务
		
	}

	public void removeFlowCards(String[] flowCardIds) throws SQLException {
		
			flowCardDao.removeFlowCards(flowCardIds);//因为外键关联关系，先要删除从表中的信息
			
			flowCardDao.removeFlowCardMaster(flowCardIds);//再删除主表中的信息

	}

	public void modifyFlowCardById(FlowCardMaster flowMaster) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * 送审时的方法，就是将vou_sts字段从N修改为S
	 * @throws SQLException 
	 */
	public void submitFlowCardList(String[] flowCardIds) throws SQLException {

    	   flowCardDao.submitFlowCardList(flowCardIds);
 
	}

}

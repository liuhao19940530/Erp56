package com.xunpoit.flowcard;

import java.util.Date;
import java.util.List;

import com.xunpoit.client.Client;
import com.xunpoit.fiscal.FiscalYearPeriod;
import com.xunpoit.user.User;

/**
 * 创建流向单的实体类，这是主表中的属性封装
 * @author 讯博科技--小豪
 *since
 * 2018-9-13
  下午4:41:33
 */
public class FlowCardMaster {

		private String flowCardId; 
		private String optType;
		private FiscalYearPeriod fiscalYearPeriodId;
		private Client clientId;
		private User recorderId;
		private Date optDate;
		private String vouSts;//单据状态
		private User adjusterId;//调整
		private String adjustDate;
		private User spotterId;//抽查
		private Date spotDate;
		private String spotDesc;
		private User confirmerId; //复审
		private Date confDate;
			                       
	    //表示一个流向单对应的具体的信息，可以有多个
		private List<FlowCardDetail> flowCardList;

		public String getFlowCardId() {
			return flowCardId;
		}

		public void setFlowCardId(String flowCardId) {
			this.flowCardId = flowCardId;
		}

		public String getOptType() {
			return optType;
		}

		public void setOptType(String optType) {
			this.optType = optType;
		}

		public FiscalYearPeriod getFiscalYearPeriodId() {
			return fiscalYearPeriodId;
		}

		public void setFiscalYearPeriodId(FiscalYearPeriod fiscalYearPeriodId) {
			this.fiscalYearPeriodId = fiscalYearPeriodId;
		}

		public Client getClientId() {
			return clientId;
		}

		public void setClientId(Client clientId) {
			this.clientId = clientId;
		}

		public User getRecorderId() {
			return recorderId;
		}

		public void setRecorderId(User recorderId) {
			this.recorderId = recorderId;
		}

		public Date getOptDate() {
			return optDate;
		}

		public void setOptDate(Date optDate) {
			this.optDate = optDate;
		}

		public String getVouSts() {
			return vouSts;
		}

		public void setVouSts(String vouSts) {
			this.vouSts = vouSts;
		}

		public User getAdjusterId() {
			return adjusterId;
		}

		public void setAdjusterId(User adjusterId) {
			this.adjusterId = adjusterId;
		}

		public String getAdjustDate() {
			return adjustDate;
		}

		public void setAdjustDate(String adjustDate) {
			this.adjustDate = adjustDate;
		}

		public User getSpotterId() {
			return spotterId;
		}

		public void setSpotterId(User spotterId) {
			this.spotterId = spotterId;
		}

		public Date getSpotDate() {
			return spotDate;
		}

		public void setSpotDate(Date spotDate) {
			this.spotDate = spotDate;
		}

		public String getSpotDesc() {
			return spotDesc;
		}

		public void setSpotDesc(String spotDesc) {
			this.spotDesc = spotDesc;
		}

		public User getConfirmerId() {
			return confirmerId;
		}

		public void setConfirmerId(User confirmerId) {
			this.confirmerId = confirmerId;
		}

		public Date getConfDate() {
			return confDate;
		}

		public void setConfDate(Date confDate) {
			this.confDate = confDate;
		}

		public List<FlowCardDetail> getFlowCardList() {
			return flowCardList;
		}

		public void setFlowCardList(List<FlowCardDetail> flowCardList) {
			this.flowCardList = flowCardList;
		}
	
}

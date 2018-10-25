package com.xunpoit.flowcard;

import com.xunpoit.entity.AimClient;
import com.xunpoit.item.Item;

/**
 * 流向单详细信息表的细节的实现类，属性的封装
 * @author 讯博科技--小豪
 *since
 * 2018-9-13
  下午4:53:22

 */
public class FlowCardDetail {
	
	private String flowCardNo;
	private AimClient aimClientId;//需方
	private Item itemNo;
	private int optQty;//操作数量
	private int adjustQty;//调整数量
	private String adjustReason;
	private String adjustFlag;
	
	public String getFlowCardNo() {
		return flowCardNo;
	}
	public void setFlowCardNo(String flowCardNo) {
		this.flowCardNo = flowCardNo;
	}
	public AimClient  getAimClientId() {
		return aimClientId;
	}
	public void setAimClientId(AimClient aimClientId) {
		this.aimClientId = aimClientId;
	}
	public Item getItemNo() {
		return itemNo;
	}
	public void setItemNo(Item itemNo) {
		this.itemNo = itemNo;
	}
	public int getOptQty() {
		return optQty;
	}
	public void setOptQty(int optQty) {
		this.optQty = optQty;
	}
	public int getAdjustQty() {
		return adjustQty;
	}
	public void setAdjustQty(int adjustQty) {
		this.adjustQty = adjustQty;
	}
	public String getAdjustReason() {
		return adjustReason;
	}
	public void setAdjustReason(String adjustReason) {
		this.adjustReason = adjustReason;
	}
	public String getAdjustFlag() {
		return adjustFlag;
	}
	public void setAdjustFlag(String adjustFlag) {
		this.adjustFlag = adjustFlag;
	}
	
}

package com.xunpoit.termi;

import com.xunpoit.client.TermiClientLevel;

/**
 * t_termi_client表的实体类
 * @author 讯博科技--小豪
 *since
 * 2018-8-20
  下午12:28:09
 */
public class Termi {
	
    private int id;
    private int pid;
    private TermiClientLevel termiLevel;
    private String termiName;
    private String termiClientId;
    private String contactTel;
    private String contactMan;
    private String address;
    private String zipCode;
    private String isLeaf;
    private String isTermiClient;
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	public TermiClientLevel getTermiLevel() {
		return termiLevel;
	}
	public void setTermiLevel(TermiClientLevel termiLevel) {
		this.termiLevel = termiLevel;
	}
	public String getTermiName() {
		return termiName;
	}
	public void setTermiName(String termiName) {
		this.termiName = termiName;
	}
	public String getTermiClientId() {
		return termiClientId;
	}
	public void setTermiClientId(String termiClientId) {
		this.termiClientId = termiClientId;
	}
	public String getContactTel() {
		return contactTel;
	}
	public void setContactTel(String contactTel) {
		this.contactTel = contactTel;
	}
	
	public String getContactMan() {
		return contactMan;
	}
	public void setContactMan(String contactMan) {
		this.contactMan = contactMan;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public String getIsLeaf() {
		return isLeaf;
	}
	public void setIsLeaf(String isLeaf) {
		this.isLeaf = isLeaf;
	}
	public String getIsTermiClient() {
		return isTermiClient;
	}
	public void setIsTermiClient(String isTermiClient) {
		this.isTermiClient = isTermiClient;
	}
	public Termi() {
		super();
		// TODO Auto-generated constructor stub
	}
       
}

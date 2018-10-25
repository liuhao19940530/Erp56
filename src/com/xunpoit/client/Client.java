package com.xunpoit.client;
/**
 * 代理商或者区域的实体类
 * @author 讯博科技--小豪
 *since
 * 2018-8-12
  下午9:28:57
 */
public class Client {

	//| id    | pid   | client_level_id | name| client_id | bank_acct_no| contact_tel |
	//address    | Zip_code | is_leaf | is_client |
	private int id;
	private int pid;
	
	private ClientLevel clientLevelId;
	private String name;
	private String clientId;
	private String bankAcctNo;
	private String contactTel;
	private String address;
	private String zipCode;
	private String isLeaf;
	private String isClient;
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
	public ClientLevel getClientLevelId() {
		return clientLevelId;
	}
	public void setClientLevelId(ClientLevel clientLevelId) {
		this.clientLevelId = clientLevelId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getBankAcctNo() {
		return bankAcctNo;
	}
	public void setBankAcctNo(String bankAcctNo) {
		this.bankAcctNo = bankAcctNo;
	}
	public String getContactTel() {
		return contactTel;
	}
	public void setContactTel(String contactTel) {
		this.contactTel = contactTel;
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
	public String getIsClient() {
		return isClient;
	}
	public void setIsClient(String isClient) {
		this.isClient = isClient;
	}
	
	
}

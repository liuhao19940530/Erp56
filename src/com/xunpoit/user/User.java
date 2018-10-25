package com.xunpoit.user;

import java.util.Date;

/**
 * 实体类的javabean
 * @author 讯博科技--小豪
 *since
 * 2018-8-7
  下午3:24:09

 */
public class User {
   //user_id user_name password contact_tel email create_date
	 private String userId;
	 private String userName;
	 private String password;
	 private String contactTel;
	 private String email;
	 private Date createDate;
		 
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getContactTel() {
		return contactTel;
	}
	public void setContactTel(String contactTel) {
		this.contactTel = contactTel;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	
	}
	
	public User(){
		
		
	}
}

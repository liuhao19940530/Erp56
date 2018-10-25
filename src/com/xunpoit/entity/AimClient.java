package com.xunpoit.entity;
/**
 * 创建需方客户的实体类
 * @author 讯博科技--小豪
 *since
 * 2018-8-15
  下午5:02:33
 */
public class AimClient {
	
	private int id;
	
	private String name;
	
	private String level;
	
	private String clientTermiId;
	
	private String levelName;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getClientTermiId() {
		return clientTermiId;
	}

	public void setClientTermiId(String clientTermiId) {
		this.clientTermiId = clientTermiId;
	}

	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	public AimClient() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}

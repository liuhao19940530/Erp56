package com.xunpoit.client;
/**
 * 数据字典表对应的抽象类
 * @author 讯博科技--小豪
 *since
 * 2018-8-12
  下午9:39:58
 */
public abstract class AbstractDataDict {

	//id  | name       | category
	private String id;
	
	private String name;
	
	private String category;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

}

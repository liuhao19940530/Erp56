package com.xunpoit.item;

import com.xunpoit.client.ItemCategory;
import com.xunpoit.client.ItemUnit;

public class Item {

	private String itemNo;
	private ItemCategory itemCategoryId;
	private ItemUnit itemUnitId;
	private String itemName;
	private String spec;
	private String pattern;
	private String fileName;
	
	public String getItemNo() {
		return itemNo;
	}
	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}
	
	public ItemCategory getItemCategoryId() {
		return itemCategoryId;
	}
	public void setItemCategoryId(ItemCategory itemCategoryId) {
		this.itemCategoryId = itemCategoryId;
	}
	public ItemUnit getItemUnitId() {
		return itemUnitId;
	}
	public void setItemUnitId(ItemUnit itemUnitId) {
		this.itemUnitId = itemUnitId;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getSpec() {
		return spec;
	}
	public void setSpec(String spec) {
		this.spec = spec;
	}
	public String getPattern() {
		return pattern;
	}
	public void setPattern(String pattern) {
		this.pattern = pattern;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
}                                 

package com.xunpoit.entity;

import java.util.List;

/**
 * 分页模型
 * @author 讯博科技--小豪
 *since
 * 2018-8-8
  下午4:23:45

 */
public class PageModel<T> {

	private int totalPage;//总页数
	
	private int currentPage;//当前页
	
	private int topPage;//首页
	
	private int bottomPage;//尾页
	
	private int previousPage;//上一页
	
	private int nextPage;//下一页
	
	private List<T> dataList;

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getTopPage() {
		return 1;
	}

	public void setTopPage(int topPage) {
		this.topPage = topPage;
	}

	public int getBottomPage() {
		return totalPage;
	}

	public void setBottomPage(int bottomPage) {
		this.bottomPage = bottomPage;
	}

	public int getPreviousPage() {
		if(currentPage==1){
			
			return currentPage;
		}
		return currentPage-1;
	}

	public void setPreviousPage(int previousPage) {
		this.previousPage = previousPage;
	}

	public int getNextPage() {
		
		if(currentPage==totalPage){
			
			return currentPage;
		}
		return currentPage+1;
	}

	public void setNextPage(int nextPage) {
		this.nextPage = nextPage;
	}

	public List<T> getDataList() {
		return dataList;
	}

	public void setDataList(List<T> dataList) {
		this.dataList = dataList;
	}
}

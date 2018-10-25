package com.xunpoit.fiscal;

//import java.util.Date;

/**
 * 会计核算表的实体类
 */
public class FiscalYearPeriod {

	private int id;
	private int fiscalYear;
	private int fiscalPeriod;
	private String beginDate;
	private String endDate;
	private String periodSts;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getFiscalYear() {
		return fiscalYear;
	}
	public void setFiscalYear(int fiscalYear) {
		this.fiscalYear = fiscalYear;
	}
	public int getFiscalPeriod() {
		return fiscalPeriod;
	}
	public void setFiscalPeriod(int fiscalPeriod) {
		this.fiscalPeriod = fiscalPeriod;
	}
	public String getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getPeriodSts() {
		return periodSts;
	}
	public void setPeriodSts(String periodSts) {
		this.periodSts = periodSts;
	}
	public FiscalYearPeriod() {
		super();
		// TODO Auto-generated constructor stub
	}
}                               

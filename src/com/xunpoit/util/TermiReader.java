package com.xunpoit.util;

import java.io.InputStream;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * 读取.xml文件的类
 * @author 讯博科技--小豪
 *since
 * 2018-8-19
  下午7:49:43
 */
public class TermiReader {

	private DbConfig dbConfig = null;
	
	public DbConfig getDbConfig() {
		return dbConfig;
	}

	//单例模式
	private static TermiReader instance = null;
	
	private TermiReader(){//创建对象的时候，就读取文件
		
		InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("db.xml");
		
		SAXReader reader = new SAXReader();
		
		try {
			Document doc = reader.read(in);
			
			Element e1 = (Element)doc.selectObject("db-config/drivername");
			
			Element e2 = (Element)doc.selectObject("db-config/url");
			
			Element e3 = (Element)doc.selectObject("db-config/username");
			
			Element e4 = (Element)doc.selectObject("db-config/password");
			
			String driver = e1.getStringValue();
			
			String url = e2.getStringValue();
			
			String user = e3.getStringValue();
			
			String password = e4.getStringValue();
			
			dbConfig = new DbConfig();
			
			dbConfig.setDriver(driver);
			
			dbConfig.setUrl(url);
			
			dbConfig.setUserName(user);
			
			dbConfig.setPassword(password);
			
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			System.out.println("读取文件流的时候出错了："+e.getMessage());
		}
		
	}
	
	public static synchronized TermiReader getInstance(){
		
		if(instance == null){
			
			instance = new TermiReader();
		}
		
		return instance;
	}

	public static void main(String[] args) {
		System.out.println(TermiReader.getInstance());
	}
}

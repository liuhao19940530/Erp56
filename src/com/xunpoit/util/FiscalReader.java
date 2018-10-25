package com.xunpoit.util;

import java.io.InputStream;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * 读取db.xml文件的类
 * @author 讯博科技--小豪
 *since
 * 2018-8-16
  上午10:18:37
 */

public class FiscalReader {

	private static FiscalReader instance = null;
	
	private DbConfig db;
	
	private FiscalReader(){//构造方法中读取
		
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
			
			String username= e3.getStringValue();
			
			String password = e4.getStringValue();
			
		    db = new DbConfig();
			
			db.setDriver(driver);
			
			db.setUrl(url);
			
			db.setUserName(username);
			
			db.setPassword(password);
			
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			System.out.println("获取元素的时候出错了："+e.getMessage());
		}
	}
	
	public static synchronized FiscalReader getInstance(){
		
		if(instance == null){
			
			instance = new FiscalReader();
		}
		return instance;
	}
	
	public static void main(String[] args) {
		System.out.println(FiscalReader.getInstance());
	}

	public DbConfig getDb() {
		return db;
	}
}

package com.xunpoit.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * 单例模式构建数据库
 * 导入jar包文件：实现包 dom4j-1.6.1.jar   依赖包 jaxen-1.1-beta-6.jar
 * @author 讯博科技--小豪
 *since
 * 2018-8-6
  下午7:32:46

 */
public class DbConfigReader {
 
	  //私有的，静态的，自身返回值类型 的变量
	private static DbConfigReader instance;
	
	private DbConfig dbConfig;

	//Map键值对的形式存取数据
	//private Map<String,String> map = new HashMap<String,String>();
	
	//私有的构造方法
	private DbConfigReader(){
		
		//将xml文件读取
		try {
			
			InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("db.xml");
			
			SAXReader reader = new SAXReader();//读取配置文件的实例
			
			try {
				Document doc = reader.read(in);
				
				//通过doc文件，拿到对应的子元素
				Element e1= (Element)doc.selectObject("db-config/drivername");
				Element e2 = (Element)doc.selectObject("db-config/url");
				Element e3 = (Element)doc.selectObject("db-config/username");
				Element e4 = (Element)doc.selectObject("db-config/password");
				//Element e5 = (Element)doc.selectObject("db-config/item-factory");
				
				//通过元素获取内容
				String driver = e1.getStringValue();
				
				String url = e2.getStringValue();
				
				String user = e3.getStringValue();
				
				String pwd = e4.getStringValue();
				
				//String facName = e5.getStringValue();
				
			    //map.put(e5.getName(),facName);//用元素的getName()属性可以获取字符串名称
				
				dbConfig = new DbConfig();
				
				//将在xml文件中获取的4大属性，设置给DbConfig
				dbConfig.setDriver(driver);
				
				dbConfig.setUrl(url);
				
				dbConfig.setUserName(user);
				
				dbConfig.setPassword(pwd);
				
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				System.out.println("读取输入流文件出错了："+e.getMessage());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("找不到属性文件：" + e.getMessage());
		}
		
	}
	
	//公共的get()，方便外部类的获取
	public DbConfig getDbConfig() {
		return dbConfig;
	}
	
	//公共的，静态的，同步的，自身返回值类型的方法
	public static synchronized DbConfigReader getInstance(){
		
		if(instance == null){
			
			instance = new DbConfigReader();
		}
		
		return instance;
	} 

}

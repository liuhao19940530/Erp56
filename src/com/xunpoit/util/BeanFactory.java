package com.xunpoit.util;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;

import org.dom4j.io.SAXReader;

/**
 * 创建一个类来解析bean-config.xmlw文件中，配置的itemDao
 * @author 讯博科技--小豪
 *since
 * 2018-9-11
  下午12:35:59
 */
public class BeanFactory {

	//单例模式
	private static BeanFactory bean = null;
	
	//map类型来保存信息
	private Map<String,String> map = new HashMap<String,String>();
	
	private BeanFactory(){
		//读取文件
		InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("bean-config.xml");
		
		SAXReader reader = new SAXReader();
		
		try {
			
			Document doc = reader.read(in);
			//获取的可能有1个，多个，0个，只有一个的时候，不能用集合接收，否则ClassCastException发生
			List<Element> beanList = (List<Element>)doc.selectObject("beans/bean");
			
			//迭代 
			for(Element el:beanList){
				
				Attribute idAttr = el.attribute("id");//使用元素的attribute()方法取得对象的属性
				
				Attribute classAttr = el.attribute("class");
				
				//根据属性来获取值
				String idVal = idAttr.getValue();
				
				String  classVal = classAttr.getValue();
				
				map.put(idVal,classVal);//具体的信息保存
			}
			
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
	}
	
	//公共的方法，方便外部获取
	public String getMap(String key){
		
		return map.get(key);
	}
	
	public static BeanFactory getInstance(){
	
		if(bean==null){
		
			synchronized(BeanFactory.class){
		
				bean = new BeanFactory();
			}
		}
		
		return bean;
	}
	
	public static void main(String[] args) {
		
		BeanFactory.getInstance();
	}
}

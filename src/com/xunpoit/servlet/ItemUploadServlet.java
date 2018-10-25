package com.xunpoit.servlet;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.xunpoit.item.Item;
import com.xunpoit.itemmanager.ItemManager;
import com.xunpoit.manager.ItemManagerImpl;

public class ItemUploadServlet extends AbstractItemServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		/*
		因为表单的enctype属性值不是默认的application/x-www-form-urlencoded，而是multipart/form-data
		所以这里用普通的获取表单元素的方法getParameter()就不能够获取到参数了，用到导入的fileupload和io2个jar包里面的属性和方法
		首先获取itemNo和图片的路径名  
		*/
		FileItemFactory factory = new DiskFileItemFactory();//获取到了你选择的磁盘信息
		
		ServletFileUpload upload = new ServletFileUpload(factory);//创建一个处理器
		
		//先设置一个空串
		String itemNo = "";
		
		try {
			//请求页面的表单数据存放在名称为items的list集合中
			List<FileItem> items =  upload.parseRequest(request);//获取到了你这次请求所选择的上传文件
			
			//迭代出里面此次请求的参数
			for(Iterator<FileItem> it = items.iterator();it.hasNext();){
				
				FileItem fileItem = (FileItem)it.next();
				
				//判断每个对象是不是普通的表单元素
				if(fileItem.isFormField()){
					//如果是普通的表单元素
					String name = fileItem.getFieldName();//属性名
					
					String value = fileItem.getString();//属性值
					
					if("itemNo".equals(name)){
						
						itemNo = value;//属性值赋值给字符串
					}
					
				}else{
					
					//如果不是普通的表单元素，图片控件
					String filedName = fileItem.getFieldName();//图片控件的名字
					
					String fileName = fileItem.getName();//上传的图片名，图片控件的值
					
					//截取字符串，用作图片的格式后缀
					String hzName = fileName.substring(fileName.lastIndexOf('.'));
					
					//这里可以将图片写入到你想要保存的电脑磁盘里，以物料代码为图片的名称
					//File uploadedFile = new File("D:/"+itemNo+hzName);
					
					//以时间戳+随机数的方式命名，这样不容易出现图片重名的问题，与hzName字符串拼接，形成图片名
					String file = new Date().getTime() + new Random().nextInt(1000000)+hzName;
					
					//写到指定的服务器目录中
					//先获取到服务器的根目录，this.getServletContext()是全局对象，相当于application
					String path = this.getServletContext().getRealPath("/");//返回的是http://localhost:8080/erp40/
					
					//System.out.println(this.getServletContext().getContextPath());//返回的是/erp40
					
					//写入带指定的服务器目录，保持图片名称的完整性，写入到的是tomcat服务器下的webapps下的upload中，MyEclipse中的upload看不到
					File uploadedFile = new File(path+"upload/"+file);
					
					//FileItem提供了写文件的方法
					fileItem.write(uploadedFile);
					
					//将获取到的图片名称fileName设置进数据库中
					
					//ItemManager manager = new ItemManagerImpl();
					
					try{
					
					  //先根据获取的itemNo得到一个item对象
				      Item item = manager.queryItemById(itemNo);
						
				      //将file_name字段设置进去
				      item.setFileName(file);
				      
				      //这样就有了原来的其他数据，加上新设置的file_name字段的数据
					  manager.modifyItems(item);
					
					}catch(Exception e){
						
						e.printStackTrace();
					}  
					
					//页面跳转
					request.getRequestDispatcher("/servlet/ItemUploadUIServlet?itemNo="+itemNo).forward(request, response);
					
					
				}
			}
			
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		 this.doGet(request, response);
	}

}

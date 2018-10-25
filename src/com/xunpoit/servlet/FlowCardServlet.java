package com.xunpoit.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JOptionPane;

import com.xunpoit.client.Client;
import com.xunpoit.entity.AimClient;
import com.xunpoit.entity.PageModel;
import com.xunpoit.fiscal.FiscalYearPeriod;
import com.xunpoit.flowcard.FlowCardDetail;
import com.xunpoit.flowcard.FlowCardMaster;
import com.xunpoit.flowcard.manager.FlowCardManager;
import com.xunpoit.flowcard.manager.FlowCardManagerImpl;
import com.xunpoit.item.Item;
import com.xunpoit.proxy.TransactionProxy;
import com.xunpoit.user.Constant;
import com.xunpoit.user.User;
import com.xunpoit.util.BeanFactory;

public class FlowCardServlet extends HttpServlet {
	
	//因为有多个方法需要调用，这里获取一个FlowManager的实例
	//FlowCardManager manager = FlowCardManagerImpl.getInstance();
	
	//这里用到动态代理，所以要重新获取FlowCardManager的实例
    
    static FlowCardManager flowManager; 
    
    static{
    	
    	String value = BeanFactory.getInstance().getMap("flowCardManager");
    	
    	try {
    		
    		//如果FlowCardManager用的是单例模式，那就不能在外部获取实例
			flowManager = (FlowCardManager)Class.forName(value).newInstance();
			
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
	FlowCardManager manager = (FlowCardManager)new TransactionProxy().getInstance(flowManager);
   
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String cmd = request.getParameter("cmd");
		
		if("UIFlow".equals(cmd)){
			
			//完成跳转
			this.doRequest(request,response);
			
		}else if("add".equals(cmd)){
			
			this.doAdd(request,response);
			
		}else if("all".equals(cmd)){
			
			this.findAll(request,response);
			
		}else if("only".equals(cmd)){
			
			this.findById(request,response);
			
		}else if("submit".equals(cmd)){
			
			this.doSubmit(request,response);
			
		}else if("remove".equals(cmd)){
			
			this.doRemove(request,response);
		}
		
	}

	/**
	 * 根据流向单号，来删除流向单信息的方法
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void doRemove(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		String[] flowCardIds = request.getParameterValues("selectFlag");
		
		String msg = "";
		
		try{
			
			manager.removeFlowCards(flowCardIds);
			
			msg = "删除成功！";
			
		}catch(Exception e){
			
			msg = "删除失败！";
			
			e.printStackTrace();
			
		}finally{
			
			request.setAttribute("msg", msg);
			
			request.getRequestDispatcher("/servlet/FlowCardServlet?cmd=all").forward(request, response);
			
		}
		
	}

	/**
	 * 送审流向单时，需要做的事情，就是调用manager中的方法
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void doSubmit(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		//获取选中的复选框的value值
		String[] flowCardIds = request.getParameterValues("selectFlag");
		
		String msg = "";
		
		try{
			
			//调用送审时候的方法
			manager.submitFlowCardList(flowCardIds);
			
			msg = "送审成功！";
			
		}catch(Exception e){
			
			msg = "送审失败！";
			
			e.printStackTrace();
			
		}finally{
			
			//保存信息
			request.setAttribute("msg",msg);
			
			//跳转的页面
			request.getRequestDispatcher("/servlet/FlowCardServlet?cmd=all").forward(request, response);
		}
	}

	/**
	 * 根据传递过来的流向单号，来获取主流向单的信息
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void findById(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String flowCardId = request.getParameter("flowCardId");
		
		try{
			
			FlowCardMaster master = manager.findFlowCardMasterById(flowCardId);
			
			List<FlowCardDetail> list = master.getFlowCardList();
			
			request.setAttribute("list", list);
			
		    request.setAttribute("master",master);	//保存进范围之中
			
		}catch(Exception e){
			
			e.printStackTrace();
			
		}
		
		//请求转发
		request.getRequestDispatcher("/flowcard/flow_card_detail.jsp").forward(request,response);
	}

	/**
	 * 首先实现分页查询
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void findAll(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String id = request.getParameter("cid");
		
		String clientId = request.getParameter("clientId");
		
		String clientName = request.getParameter("clientName");
		
		String beginDate = request.getParameter("beginDate");
		
		String endDate = request.getParameter("endDate");
	
		int pageSize = 3;
		
		int currentPage = 1;
		
		//获取分页的参数
		String currentStr = request.getParameter("currentPage");
		
		if(currentStr!=null&&!"".equals(currentStr)){
			
			currentPage = Integer.valueOf(currentStr);
		
		}
		
		//给开始时间和结束时间一个初始值
		String begin = "20180914";
		
		String end= new SimpleDateFormat("yyyyMMdd").format(new Date());//以当前日期为结束日期
		
		if(beginDate!=null&&endDate!=null&&!"".equals(beginDate)&&!"".equals(endDate)){
			
			begin = beginDate;
			
			end = endDate;
			
		}
		
		try{
		
			PageModel<FlowCardMaster> pm = manager.findAll(pageSize, currentPage, id, begin, end);
		
			request.setAttribute("pm",pm);
			
			request.setAttribute("clientId", clientId);
			
			request.setAttribute("id", id);
			
			request.setAttribute("clientName", clientName);
			
			request.setAttribute("beginDate", begin);
			
			request.setAttribute("endDate", end);
			
		}catch(Exception e){
			
			e.printStackTrace();
		}	
		
		//跳转到主页面
		request.getRequestDispatcher("/flowcard/flow_card_maint.jsp").forward(request, response);
	}
	
	/**
	 * 完成页面提交的信息加载功能
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void doAdd(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //主表的实体信息
		FlowCardMaster master = new FlowCardMaster();
		
		master.setOptType(Constant.A);//操作类型，a为流向单，b为盘点单
		
		//核算日期，因为项目有删除，所以这里给定id为死值
		FiscalYearPeriod  fiscalYearPeriod = new FiscalYearPeriod();
		
		fiscalYearPeriod.setId(108);
		
		master.setFiscalYearPeriodId(fiscalYearPeriod);
		
		//获取cid，保存进去
		String cid = request.getParameter("cid");
		
		Client client = new Client();
		
		client.setId(Integer.parseInt(cid));
		
		master.setClientId(client);
		
		//获取录入人，谁登陆谁就录入
		User user = (User)request.getSession().getAttribute("user02");
		
		master.setRecorderId(user);
		
        //当期日期
		Date date = new Date();
		
		master.setOptDate(date);
		
		//录入状态，死值N
		master.setVouSts(Constant.NO);
		
		//设置list集合中的flowCardDetail信息，因为1个流向单号可能有多个流向单信息
		List<FlowCardDetail> flowList = new ArrayList<FlowCardDetail>();
		
		//信息的流向单，是封装在数组之中，而且需方，物料，操作数量的长度相同
		String[] aids = request.getParameterValues("aid");
		
		String[] itemNos = request.getParameterValues("itemNo");
		
		String[] qtys = request.getParameterValues("qty");
		
		//因为有多个，有for循环，它们的长度是一样的
		for(int i=0;i<aids.length;i++){
			
			//封装进实体信息
			FlowCardDetail detail = new FlowCardDetail();
			
			//需方客户的实体类
			AimClient aim = new AimClient();
			
			aim.setId(Integer.parseInt(aids[i]));
			
			detail.setAimClientId(aim);
			
			//物料的实体类
			Item item = new Item();
			
			item.setItemNo(itemNos[i]);
			
			detail.setItemNo(item);
			
			//操作数量
			detail.setOptQty(Integer.valueOf(qtys[i]));
			
			//设置调整标记
			detail.setAdjustFlag(Constant.NO);
			
			//将一个个的详细信息放置进集合中
			flowList.add(detail);
		}
		
		//将设置好的集合，封装进主表中
		master.setFlowCardList(flowList);
		
		try {
			manager.addFlowCardDetail(master);
			
			JOptionPane.showMessageDialog(null,"添加成功！");
			
		} catch (SQLException e) {

			JOptionPane.showMessageDialog(null,"添加失败！");
			
			e.printStackTrace();
		}
		
		request.getRequestDispatcher("/servlet/FlowCardServlet?cmd=all").forward(request, response);
	}

	/**
	 * 完成跳转页面的小方法
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void doRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		//请求转发 this.getServletContext().getContextPath()获取的是  /工程名
		request.getRequestDispatcher("/flowcard/flow_card_add.jsp").forward(request, response);
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}

}

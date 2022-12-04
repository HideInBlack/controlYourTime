package controller.clientC;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import service.clientS.DoingService;
import service.clientS.PlanService;

public class Doing extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String url = request.getRequestURI();
		String methodName = url.substring(url.lastIndexOf("/") + 1, url.lastIndexOf("."));
		Method method = null;
		try {
			method = getClass().getDeclaredMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
			method.invoke(this, request, response);
		} catch (Exception e) {
			throw new RuntimeException("调用方法出错！");
		}
	}

	// 查看现在是否有自己正在做的 以及 自己今天所做的
	private void getDoingStatus(HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");

		PrintWriter out = response.getWriter();

		String pageNumber = request.getParameter("pageNumber");
		String pageSize = request.getParameter("pageSize");
		String order = request.getParameter("order");

		if ((String) session.getAttribute("names") == null) {
			out.println("{\"ok\":false,\"msg\":\"请求出错，登录已失效请重新登录！\"}");
		} else {
			int user_id = (int) session.getAttribute("ids");
			DoingService.me.getDoingStatus(out, pageNumber, pageSize, order, user_id);
		}
	}

	// 所有模板计划 及 表格的查询
	private void addDoingRecord(HttpServletRequest request, HttpServletResponse response) throws Exception {

		ServletContext application = this.getServletContext();
		HttpSession session = request.getSession();

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");

		String title = request.getParameter("title");
		String context = request.getParameter("context");
		String start_time = request.getParameter("start_time");

		PrintWriter out = response.getWriter();

		if ((String) session.getAttribute("names") == null) {
			out.println("{\"ok\":false,\"msg\":\"请求出错，登录已失效请重新登录！\"}");
		} else {
			int user_id = (int) session.getAttribute("ids");
			DoingService.me.addDoingRecord(out, user_id, title, context, start_time);
		}
	}

	// 修改今日计划完成情况
	private void updateDoing(HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");

		int user_id = (int) session.getAttribute("ids");
		String end_time = request.getParameter("end_time");

		PrintWriter out = response.getWriter();

		if ((String) session.getAttribute("names") == null) {
			out.println("{\"ok\":false,\"msg\":\"请求出错，登录已失效请重新登录！\"}");
		} else {
			DoingService.me.updateDoing(out, user_id, end_time);
		}
	}

}

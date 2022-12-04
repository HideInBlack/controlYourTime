package controller.clientC;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import service.clientS.PlanService;
import utils.DzySomeUtil;

public class Plan extends HttpServlet {

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

	// 所有模板计划 及 表格的查询
	private void getExamplePlan(HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");

		PrintWriter out = response.getWriter();

		String pageNumber = request.getParameter("pageNumber");
		String pageSize = request.getParameter("pageSize");
		String search = request.getParameter("search");

		if ((String) session.getAttribute("names") == null) {
			out.println("{\"ok\":false,\"msg\":\"请求出错，登录已失效请重新登录！\"}");
		} else {
			int user_id = (int) session.getAttribute("ids");
			PlanService.me.getExamplePlan(out, pageNumber, pageSize, search, user_id);
		}
	}

	// 获取今日计划清单（以及往日的计划清单）
	private void getTodayPlan(HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");

		PrintWriter out = response.getWriter();

		String pageNumber = request.getParameter("pageNumber");
		String pageSize = request.getParameter("pageSize");
		String search = request.getParameter("search");

		if ((String) session.getAttribute("names") == null) {
			out.println("{\"ok\":false,\"msg\":\"请求出错，登录已失效请重新登录！\"}");
		} else {
			int user_id = (int) session.getAttribute("ids");
			PlanService.me.getTodayPlan(out, pageNumber, pageSize, search, user_id);
		}
	}

	// 获取今日计划清单（以及往日的计划清单）
	private void getSeven(HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");

		PrintWriter out = response.getWriter();

		String pageNumber = request.getParameter("pageNumber");
		String pageSize = request.getParameter("pageSize");
		String search = request.getParameter("search");

		if ((String) session.getAttribute("names") == null) {
			out.println("{\"ok\":false,\"msg\":\"请求出错，登录已失效请重新登录！\"}");
		} else {
			int user_id = (int) session.getAttribute("ids");
			PlanService.me.getSeven(out, pageNumber, pageSize, search, user_id);
		}
	}

	// 添加模板计划
	private void addExamplePlan(HttpServletRequest request, HttpServletResponse response) throws Exception {

		ServletContext application = this.getServletContext();
		HttpSession session = request.getSession();

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");

		String title = request.getParameter("title");
		String context = request.getParameter("context");
		String start_time = request.getParameter("start_time");
		String end_time = request.getParameter("end_time");

		PrintWriter out = response.getWriter();

		if ((String) session.getAttribute("names") == null) {
			out.println("{\"ok\":false,\"msg\":\"请求出错，登录已失效请重新登录！\"}");
		} else {
			int user_id = (int) session.getAttribute("ids");
			PlanService.me.addExamplePlan(out, user_id, title, context, start_time, end_time);
		}
	}

	// 修改模板计划
	private void updateExamplePlan(HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");

		String id = request.getParameter("id");
		String title = request.getParameter("title");
		String context = request.getParameter("context");
		String start_time = request.getParameter("start_time");
		String end_time = request.getParameter("end_time");

		PrintWriter out = response.getWriter();

		if ((String) session.getAttribute("names") == null) {
			out.println("{\"ok\":false,\"msg\":\"请求出错，登录已失效请重新登录！\"}");
		} else {
			int user_id = (int) session.getAttribute("ids");
			PlanService.me.updateExamplePlan(out, id, user_id, title, context, start_time, end_time);
		}
	}

	// 修改今日计划完成情况
	private void updateTodayPlan(HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");

		String id = request.getParameter("id");
		String status = request.getParameter("status");

		PrintWriter out = response.getWriter();

		if ((String) session.getAttribute("names") == null) {
			out.println("{\"ok\":false,\"msg\":\"请求出错，登录已失效请重新登录！\"}");
		} else {
			PlanService.me.updateTodayPlan(out, id, status);
		}
	}

	// 删除某个模板计划及批量删除
	private void deleteExamplePlan(HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		String id = request.getParameter("id");
		String order = request.getParameter("order");

		PrintWriter out = response.getWriter();

		if ((String) session.getAttribute("names") == null) {
			out.println("{\"ok\":false,\"msg\":\"请求出错，登录已失效请重新登录！\"}");
		} else {
			if (order.equals("1")) {
				// 删除单个
				PlanService.me.deleteExamplePlan(out, id, order);
			} else if (order.equals("2")) {
				// 批量删除
				String[] ids = id.split(",");
				for (int i = 0; i < ids.length; i++) {
					PlanService.me.deleteExamplePlan(out, ids[i], order);
				}
				out.println("{\"ok\":true,\"msg\":\"所选模板计划删除成功！\"}");
			}

		}
	}

	// 删除某个模板计划及批量删除
	private synchronized void importExamplePlan(HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HttpSession session = request.getSession();

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");

		String id = request.getParameter("id");
		String order = request.getParameter("order");

		PrintWriter out = response.getWriter();

		if ((String) session.getAttribute("names") == null) {

			out.println("{\"ok\":false,\"msg\":\"请求出错，登录已失效请重新登录！\"}");
		} else {
			int user_id = (int) session.getAttribute("ids");
			if (order.equals("1")) {
				// 导入单个
				PlanService.me.importExamplePlan(out, user_id, id, order);
			} else if (order.equals("2")) {
				// 批量导入

				// 先删除今天已导入的，再导入
				PlanService.me.deletTodayExamplePlan(out, user_id, order);
				// TimeUnit.SECONDS.sleep(3);//秒
				// 再导入
				String[] ids = id.split(",");
				for (int i = 0; i < ids.length; i++) {
					PlanService.me.importExamplePlan(out, user_id, ids[i], order);
				}
				out.println("{\"ok\":true,\"msg\":\"成功导入至计划清单,可去计划清单查看！\"}");
			}

		}
	}

}

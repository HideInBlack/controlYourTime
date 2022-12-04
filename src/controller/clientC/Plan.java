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
			throw new RuntimeException("���÷�������");
		}
	}

	// ����ģ��ƻ� �� ���Ĳ�ѯ
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
			out.println("{\"ok\":false,\"msg\":\"���������¼��ʧЧ�����µ�¼��\"}");
		} else {
			int user_id = (int) session.getAttribute("ids");
			PlanService.me.getExamplePlan(out, pageNumber, pageSize, search, user_id);
		}
	}

	// ��ȡ���ռƻ��嵥���Լ����յļƻ��嵥��
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
			out.println("{\"ok\":false,\"msg\":\"���������¼��ʧЧ�����µ�¼��\"}");
		} else {
			int user_id = (int) session.getAttribute("ids");
			PlanService.me.getTodayPlan(out, pageNumber, pageSize, search, user_id);
		}
	}

	// ��ȡ���ռƻ��嵥���Լ����յļƻ��嵥��
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
			out.println("{\"ok\":false,\"msg\":\"���������¼��ʧЧ�����µ�¼��\"}");
		} else {
			int user_id = (int) session.getAttribute("ids");
			PlanService.me.getSeven(out, pageNumber, pageSize, search, user_id);
		}
	}

	// ���ģ��ƻ�
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
			out.println("{\"ok\":false,\"msg\":\"���������¼��ʧЧ�����µ�¼��\"}");
		} else {
			int user_id = (int) session.getAttribute("ids");
			PlanService.me.addExamplePlan(out, user_id, title, context, start_time, end_time);
		}
	}

	// �޸�ģ��ƻ�
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
			out.println("{\"ok\":false,\"msg\":\"���������¼��ʧЧ�����µ�¼��\"}");
		} else {
			int user_id = (int) session.getAttribute("ids");
			PlanService.me.updateExamplePlan(out, id, user_id, title, context, start_time, end_time);
		}
	}

	// �޸Ľ��ռƻ�������
	private void updateTodayPlan(HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");

		String id = request.getParameter("id");
		String status = request.getParameter("status");

		PrintWriter out = response.getWriter();

		if ((String) session.getAttribute("names") == null) {
			out.println("{\"ok\":false,\"msg\":\"���������¼��ʧЧ�����µ�¼��\"}");
		} else {
			PlanService.me.updateTodayPlan(out, id, status);
		}
	}

	// ɾ��ĳ��ģ��ƻ�������ɾ��
	private void deleteExamplePlan(HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		String id = request.getParameter("id");
		String order = request.getParameter("order");

		PrintWriter out = response.getWriter();

		if ((String) session.getAttribute("names") == null) {
			out.println("{\"ok\":false,\"msg\":\"���������¼��ʧЧ�����µ�¼��\"}");
		} else {
			if (order.equals("1")) {
				// ɾ������
				PlanService.me.deleteExamplePlan(out, id, order);
			} else if (order.equals("2")) {
				// ����ɾ��
				String[] ids = id.split(",");
				for (int i = 0; i < ids.length; i++) {
					PlanService.me.deleteExamplePlan(out, ids[i], order);
				}
				out.println("{\"ok\":true,\"msg\":\"��ѡģ��ƻ�ɾ���ɹ���\"}");
			}

		}
	}

	// ɾ��ĳ��ģ��ƻ�������ɾ��
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

			out.println("{\"ok\":false,\"msg\":\"���������¼��ʧЧ�����µ�¼��\"}");
		} else {
			int user_id = (int) session.getAttribute("ids");
			if (order.equals("1")) {
				// ���뵥��
				PlanService.me.importExamplePlan(out, user_id, id, order);
			} else if (order.equals("2")) {
				// ��������

				// ��ɾ�������ѵ���ģ��ٵ���
				PlanService.me.deletTodayExamplePlan(out, user_id, order);
				// TimeUnit.SECONDS.sleep(3);//��
				// �ٵ���
				String[] ids = id.split(",");
				for (int i = 0; i < ids.length; i++) {
					PlanService.me.importExamplePlan(out, user_id, ids[i], order);
				}
				out.println("{\"ok\":true,\"msg\":\"�ɹ��������ƻ��嵥,��ȥ�ƻ��嵥�鿴��\"}");
			}

		}
	}

}

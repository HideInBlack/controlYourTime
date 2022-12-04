package controller.baseC;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import service.baseS.MyselfService;
import service.clientS.DoingService;

public class Myself extends HttpServlet{
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 获取请求的URI地址信息
		String url = request.getRequestURI();
		// 截取其中的方法名
		String methodName = url.substring(url.lastIndexOf("/") + 1, url.lastIndexOf("."));
		Method method = null;
		try {
			// 使用反射机制获取在本类中声明了的方法
			method = getClass().getDeclaredMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
			// 执行方法
			method.invoke(this, request, response);
		} catch (Exception e) {
			throw new RuntimeException("调用方法出错！");
		}
	}
	// 修改今日计划完成情况
		private void updatepwd(HttpServletRequest request, HttpServletResponse response) throws Exception {

			HttpSession session = request.getSession();

			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html");

			int user_id = (int) session.getAttribute("ids");
			String password = request.getParameter("password");

			PrintWriter out = response.getWriter();

			if ((String) session.getAttribute("names") == null) {
				out.println("{\"ok\":false,\"msg\":\"请求出错，登录已失效请重新登录！\"}");
			} else {
				MyselfService.me.updatepwd(out, user_id, password,session);
			}
		}
	

}

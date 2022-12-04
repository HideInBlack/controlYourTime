package controller.baseC;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import service.baseS.LoginSomeService;
import utils.DzySomeUtil;
import utils.SendEmail;

/**
 * ��¼��������� 2019��12��25��17:23:53
 * 
 * @author ��
 *
 */
//@WebServlet("/*.login")
public class LoginSome extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * ʹ��һ��Servlet������������ �Զ��巴�䷽�� doGet doPost
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// ��ȡ�����URI��ַ��Ϣ
		String url = request.getRequestURI();
		// ��ȡ���еķ�����
		String methodName = url.substring(url.lastIndexOf("/") + 1, url.lastIndexOf("."));
		Method method = null;
		try {
			// ʹ�÷�����ƻ�ȡ�ڱ����������˵ķ���
			method = getClass().getDeclaredMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
			// ִ�з���
			method.invoke(this, request, response);
		} catch (Exception e) {
			throw new RuntimeException("���÷�������");
		}
	}

	/**
	 * ��¼�ж� 2019��11��27��10:49:26
	 * 
	 */
	private void loginCheck(HttpServletRequest request, HttpServletResponse response) throws Exception {

		System.out.println("�����¼�ж�...");
		// ��ȡ Application����
		ServletContext application = this.getServletContext();
		// ��ȡ session
		HttpSession session = request.getSession();

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");

		PrintWriter out = response.getWriter();
		String name = request.getParameter("name");
		String pwd = request.getParameter("pwd");
		String names = (String) session.getAttribute("names");
		if (names != null) {
			System.out.println("session�жϣ��Ѿ����˺Ŵ��ڵ�¼״̬��");
			out.println("{\"ok\":true,\"name\":\"" + (String) session.getAttribute("names")
					+ "\",\"msg\":\"�Ѿ��ڵ�¼״̬�������ٴε�¼��\" ,\"href\":\"/ControlYourTime/src/base/index.html\",\"roles\":"
					+ (String) session.getAttribute("roles") + "}");
		} else {
			System.out.println("session�жϣ���¼ʧЧ���������µ�¼��");
			LoginSomeService.me.loginCheck(name, pwd, out, session);
		}
	}

	/**
	 * �˳���¼2019��12��4��10:27:13
	 * 
	 */
	private void loginOut(HttpServletRequest request, HttpServletResponse response) throws Exception {

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");

		// ��ȡ session
		HttpSession session = request.getSession();
		String name = (String) session.getAttribute("names");
		// ʹ��sessionʧЧ
		session.invalidate();

		PrintWriter out = response.getWriter();

		if (name == null) {
			out.println(
					"{\"ok\":false,\"msg\":\"��¼�Ѿ�ʧЧ����ֱ���˳�����¼���棡\",\"href\":\"/ControlYourTime/src/base/login.html\"}");
		} else {
			out.println("{\"ok\":true,\"name\":\"" + name
					+ "\",\"msg\":\"�˳��ɹ��������µ�¼��\" ,\"href\":\"/ControlYourTime/src/base/login.html\"}");
			System.out.println("session�жϣ��˳���¼��");
		}
	}

	/**
	 * get sessionֵ
	 * 
	 * @throws Exception
	 */
	private void getSession(HttpServletRequest request, HttpServletResponse response) throws Exception {

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		// ��ȡ session

		HttpSession session = request.getSession();
		String name = (String) session.getAttribute("names");
		PrintWriter out = response.getWriter();
		if (name != null) {
			int role = (int) session.getAttribute("roles");
			out.println("{\"ok\":true,\"name\":\"" + (String) session.getAttribute("names")
					+ "\",\"msg\":\"������ҳ��\" ,\"roles\":" + role
					+ ",\"href\":\"/ControlYourTime/src/base/index.html\"}");
		} else {
			out.println("{\"ok\":false,\"msg\":\"��¼�Ѿ�ʧЧ�������µ�¼��\" }");
		}

	}

	// ע���ж�
	private void register(HttpServletRequest request, HttpServletResponse response) throws Exception {

		System.out.println("����ע��...");
		// ��ȡ Application����
		ServletContext application = this.getServletContext();
		// ��ȡ session
		HttpSession session = request.getSession();
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");

		PrintWriter out = response.getWriter();
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String pwd = request.getParameter("pwd");
		String sure = request.getParameter("sure");

		LoginSomeService.me.register(out ,name ,email ,pwd ,sure ,session);

	}

	// ��֤��ķ���
	private void sendSureCode(HttpServletRequest request, HttpServletResponse response) throws Exception {

		System.out.println("������֤��...");
		// ��ȡ Application����
		ServletContext application = this.getServletContext();
		// ��ȡ session
		HttpSession session = request.getSession();
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");

		PrintWriter out = response.getWriter();
		String email = request.getParameter("email");

		String sureCode = DzySomeUtil.getRandNum(6);
		SendEmail.sendSSLEmail(email, "���˴ε���֤��Ϊ:"+ sureCode, "��סʱ��:��ӭ��");
		//DzySomeUtil.sendEmail(email, sureCode);
		//��֤��浽session��
		session.setAttribute("sureCodes", sureCode);

		System.out.println((String) session.getAttribute("sureCodes"));
		out.println("{\"ok\":true,\"msg\":\"�ѷ�����֤�뵽�������䣡\" }");

	}

}

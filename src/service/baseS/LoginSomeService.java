package service.baseS;

import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.http.HttpSession;

import sun.security.util.Password;
import utils.DzySomeUtil;
import utils.JdbcSqlite;

/**
 * 2019��12��25��17:23:41 ��¼����߼����ݴ��������
 * 
 * @author ��
 *
 */
public class LoginSomeService {
	public static final LoginSomeService me = new LoginSomeService();

	// ��¼�жϷ���
	public void loginCheck(String num, String pwd, PrintWriter out, HttpSession session) {

		// jdbc���ݿ�����
		JdbcSqlite db;
		try {
			db = new JdbcSqlite("jdbc/sqlite");
			Object[][] name = db.queryToArray("select id,num ,password ,name ,role from cyt_user union\r\n"
					+ "select id,num ,password ,name ,role from cyt_admin");

			// role=0:û���˺� /role=1:web ��ҳ�� /role=2:client user
			int role = 0;
			// �������ж���û���˺�
			for (int i = 0; i < name.length; i++) {
				if (num.equals(name[i][1])) {
					// ���˺ţ��ж�������ȷ���
					role = 1;
					if (pwd.equals(name[i][2])) {
						out.println("{\"ok\":true,\"name\":\"" + (String) name[i][3]
								+ "\",\"msg\":\"��¼�ɹ���\" ,\"href\":\"/ControlYourTime/src/base/index.html\",\"roles\":"
								+ name[i][4] + "}");
						// ��¼�ɹ������浽session
						int ids = (int) name[i][0];
						int roles = (int) name[i][4];
						String nums = num;
						String names = (String) name[i][3];
						session.setAttribute("ids", ids);
						session.setAttribute("nums", nums);
						session.setAttribute("names", names);
						session.setAttribute("roles", roles);

					} else {
						out.println("{\"ok\":false,\"name\":\"" + num + "\",\"msg\":\"�������\" }");
					}
				}
			}
			if (role == 0) {
				out.println("{\"ok\":false,\"msg\":\"�˺Ų����ڣ�������ע�ᣡ\" }");
			}
			db.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// ע�����
	public void register(PrintWriter out, String namesql, String email, String pwd, String sure, HttpSession session) {

		// jdbc���ݿ�����
		JdbcSqlite db;
		try {
			String now_time = Long.toString(new Date().getTime());
			db = new JdbcSqlite("jdbc/sqlite");
			Object[][] name = db.queryToArray("select id,num ,password ,name ,role from cyt_user union\r\n"
					+ "select id,num ,password ,name ,role from cyt_admin");
			
			int role = 0;
			// �������ж���û���˺�
			for (int i = 0; i < name.length; i++) {
				if (email.equals(name[i][1])) {
					// ���˺ţ����Ѿ�ע������˺�
					role = 1;
					out.println("{\"ok\":false,\"name\":\"" + email + "\",\"msg\":\"ע��ʧ�ܣ����˺��ѱ�ע�����\" }");
				}
			}
			if (role == 0) {
				System.out.println((String) session.getAttribute("sureCodes"));
				System.out.println(sure);
				if(!sure.equals((String) session.getAttribute("sureCodes"))) {//��֤�����
					out.println("{\"ok\":false,\"name\":\"" + email + "\",\"msg\":\"��֤�����\" }");
				}else {//ע��ɹ�
					int result = db.update("INSERT into cyt_user(name , num , password ,create_time ) values ('"+namesql+"' ,'"+email+"' ,'"+pwd+"' ,'"+now_time+"')");
					if (result == 1) {
						out.println("{\"ok\":true,\"msg\":\"ע��ɹ��������ص���¼���棡\" ,\"href\":\"/ControlYourTime/src/base/login.html\"}");
					} else {
						out.println("{\"ok\":false,\"msg\":\"�����쳣��ע��ʧ�ܣ�\"}");
					}
				}
				
			}
			db.commit();
			db.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}

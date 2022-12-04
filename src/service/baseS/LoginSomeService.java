package service.baseS;

import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.http.HttpSession;

import sun.security.util.Password;
import utils.DzySomeUtil;
import utils.JdbcSqlite;

/**
 * 2019年12月25日17:23:41 登录相关逻辑数据处理服务类
 * 
 * @author 董
 *
 */
public class LoginSomeService {
	public static final LoginSomeService me = new LoginSomeService();

	// 登录判断服务
	public void loginCheck(String num, String pwd, PrintWriter out, HttpSession session) {

		// jdbc数据库驱动
		JdbcSqlite db;
		try {
			db = new JdbcSqlite("jdbc/sqlite");
			Object[][] name = db.queryToArray("select id,num ,password ,name ,role from cyt_user union\r\n"
					+ "select id,num ,password ,name ,role from cyt_admin");

			// role=0:没有账号 /role=1:web 网页端 /role=2:client user
			int role = 0;
			// 这里先判断有没有账号
			for (int i = 0; i < name.length; i++) {
				if (num.equals(name[i][1])) {
					// 有账号，判断密码正确与否
					role = 1;
					if (pwd.equals(name[i][2])) {
						out.println("{\"ok\":true,\"name\":\"" + (String) name[i][3]
								+ "\",\"msg\":\"登录成功！\" ,\"href\":\"/ControlYourTime/src/base/index.html\",\"roles\":"
								+ name[i][4] + "}");
						// 登录成功，保存到session
						int ids = (int) name[i][0];
						int roles = (int) name[i][4];
						String nums = num;
						String names = (String) name[i][3];
						session.setAttribute("ids", ids);
						session.setAttribute("nums", nums);
						session.setAttribute("names", names);
						session.setAttribute("roles", roles);

					} else {
						out.println("{\"ok\":false,\"name\":\"" + num + "\",\"msg\":\"密码错误！\" }");
					}
				}
			}
			if (role == 0) {
				out.println("{\"ok\":false,\"msg\":\"账号不存在，建议先注册！\" }");
			}
			db.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// 注册服务
	public void register(PrintWriter out, String namesql, String email, String pwd, String sure, HttpSession session) {

		// jdbc数据库驱动
		JdbcSqlite db;
		try {
			String now_time = Long.toString(new Date().getTime());
			db = new JdbcSqlite("jdbc/sqlite");
			Object[][] name = db.queryToArray("select id,num ,password ,name ,role from cyt_user union\r\n"
					+ "select id,num ,password ,name ,role from cyt_admin");
			
			int role = 0;
			// 这里先判断有没有账号
			for (int i = 0; i < name.length; i++) {
				if (email.equals(name[i][1])) {
					// 有账号，您已经注册过此账号
					role = 1;
					out.println("{\"ok\":false,\"name\":\"" + email + "\",\"msg\":\"注册失败，此账号已被注册过！\" }");
				}
			}
			if (role == 0) {
				System.out.println((String) session.getAttribute("sureCodes"));
				System.out.println(sure);
				if(!sure.equals((String) session.getAttribute("sureCodes"))) {//验证码错误
					out.println("{\"ok\":false,\"name\":\"" + email + "\",\"msg\":\"验证码错误！\" }");
				}else {//注册成功
					int result = db.update("INSERT into cyt_user(name , num , password ,create_time ) values ('"+namesql+"' ,'"+email+"' ,'"+pwd+"' ,'"+now_time+"')");
					if (result == 1) {
						out.println("{\"ok\":true,\"msg\":\"注册成功，将返回到登录界面！\" ,\"href\":\"/ControlYourTime/src/base/login.html\"}");
					} else {
						out.println("{\"ok\":false,\"msg\":\"发生异常，注册失败！\"}");
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

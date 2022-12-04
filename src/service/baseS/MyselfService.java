package service.baseS;

import java.io.PrintWriter;

import javax.servlet.http.HttpSession;

import utils.JdbcSqlite;

public class MyselfService {

	public static final MyselfService me = new MyselfService();

	// 按钮的结束点击事件
	public void updatepwd(PrintWriter out, int user_id, String password ,HttpSession session) {

		JdbcSqlite db;
		try {
			db = new JdbcSqlite("jdbc/sqlite");
			if (password.equals("")) {
				out.println("{\"ok\":false,\"msg\":\"数据有错，密码修改失败！\"}");
			} else {
				int result = db.update("update cyt_user set password='"+password+"' where id = "+user_id+"");
				if (result == 1) {
					out.println("{\"ok\":true,\"msg\":\"密码修改成功，需重新登录！\"}");
					session.invalidate();//密码修改成功，使得登录失效，需重新登录
				} else {
					out.println("{\"ok\":false,\"msg\":\"未知错误，密码修改失败！\"}");
				}
			}
			db.commit();
			db.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

package service.baseS;

import java.io.PrintWriter;

import javax.servlet.http.HttpSession;

import utils.JdbcSqlite;

public class MyselfService {

	public static final MyselfService me = new MyselfService();

	// ��ť�Ľ�������¼�
	public void updatepwd(PrintWriter out, int user_id, String password ,HttpSession session) {

		JdbcSqlite db;
		try {
			db = new JdbcSqlite("jdbc/sqlite");
			if (password.equals("")) {
				out.println("{\"ok\":false,\"msg\":\"�����д������޸�ʧ�ܣ�\"}");
			} else {
				int result = db.update("update cyt_user set password='"+password+"' where id = "+user_id+"");
				if (result == 1) {
					out.println("{\"ok\":true,\"msg\":\"�����޸ĳɹ��������µ�¼��\"}");
					session.invalidate();//�����޸ĳɹ���ʹ�õ�¼ʧЧ�������µ�¼
				} else {
					out.println("{\"ok\":false,\"msg\":\"δ֪���������޸�ʧ�ܣ�\"}");
				}
			}
			db.commit();
			db.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

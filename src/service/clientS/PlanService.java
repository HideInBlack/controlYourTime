package service.clientS;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import utils.DzySomeUtil;
import utils.JdbcSqlite;
import utils.LayuiUtil;

public class PlanService {

	public static final PlanService me = new PlanService();

	// ��ȡȫ��ģ��ƻ�
	public void getExamplePlan(PrintWriter out, String pageNumber, String pageSize, String search, int user_id) {

		JdbcSqlite db;
		try {
			db = new JdbcSqlite("jdbc/sqlite");

			Object[][] course;
			// �ж��Ƿ��ǲ�ѯ
			if (search == null || search.trim() == "") {
				course = db.queryToArray("SELECT * from cyt_willing_example  where user_id=" + user_id + " and del=0");
			} else {// ��ѯtitle
				course = db.queryToArray("SELECT * from cyt_willing_example  where user_id=" + user_id
						+ " and del=0 and title like '%" + search + "%'");
			}

			// �Է�ҳ����ش���
			int pageSizes = Integer.parseInt(pageSize);
			int pageNum = (Integer.parseInt(pageNumber) - 1) * pageSizes;

			int iLength;
			int iList = 0;
			if (course.length - pageNum > pageSizes) {
				iLength = pageNum + pageSizes;
			} else {
				// ���������һҳ��ʱ��
				iLength = course.length;
			}
			List<Map> list = new LinkedList<>();
			for (int i = pageNum; i < iLength; i++) {
				Map<String, Object> map = new HashMap<>();
				map.put("\"flow_id\"", i + 1);
				map.put("\"id\"", course[i][0]);
				map.put("\"title\"", "\"" + course[i][4] + "\"");
				map.put("\"start_time\"", course[i][2]);
				map.put("\"end_time\"", course[i][3]);
				map.put("\"context\"", "\"" + course[i][5] + "\"");
				list.add(iList, map);
				iList++;
			}
			Map<String, Object> result = LayuiUtil.setResultMap(0, "", list, course.length);
			out.println(result.toString().replace("=", ":"));
			db.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ��ȡ���ռƻ��嵥
	public void getTodayPlan(PrintWriter out, String pageNumber, String pageSize, String search, int user_id) {

		JdbcSqlite db;
		try {
			String now_time = Long.toString(new Date().getTime());
			db = new JdbcSqlite("jdbc/sqlite");

			Object[][] course;
			// �ж��Ƿ��ǲ�ѯ
			if (search == null || search.trim() == "") {
				course = db.queryToArray(
						"SELECT * from cyt_willing_plan  where user_id=" + user_id + " and del=0 and day_time ='"
								+ DzySomeUtil.dateToStamp(DzySomeUtil.stampToDate(now_time, "yyyy-MM-dd"), "yyyy-MM-dd")
								+ "' order by start_time");
			} else {// ��ѯ���յ��嵥
				course = db.queryToArray(
						"SELECT * from cyt_willing_plan  where user_id=" + user_id + " and del=0 and day_time ='"
								+ DzySomeUtil.dateToStamp(search, "yyyy-MM-dd") + "' order by start_time");
			}

			// �Է�ҳ����ش���
			int pageSizes = Integer.parseInt(pageSize);
			int pageNum = (Integer.parseInt(pageNumber) - 1) * pageSizes;

			int iLength;
			int iList = 0;
			if (course.length - pageNum > pageSizes) {
				iLength = pageNum + pageSizes;
			} else {
				// ���������һҳ��ʱ��
				iLength = course.length;
			}
			List<Map> list = new LinkedList<>();
			for (int i = pageNum; i < iLength; i++) {
				Map<String, Object> map = new HashMap<>();
				map.put("\"flow_id\"", i + 1);
				map.put("\"id\"", course[i][0]);
				map.put("\"title\"", "\"" + course[i][4] + "\"");
				map.put("\"start_time\"", course[i][2]);
				map.put("\"end_time\"", course[i][3]);
				map.put("\"context\"", "\"" + course[i][5] + "\"");
				map.put("\"status\"", "\"" + course[i][6] + "\"");
				map.put("\"day_time\"", "\"" + course[i][10] + "\"");
				map.put("\"search\"", "\"" + course[i][9] + "\"");
				list.add(iList, map);
				iList++;
			}
			Map<String, Object> result = LayuiUtil.setResultMap(0, "", list, course.length);
			out.println(result.toString().replace("=", ":"));
			db.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ��ȡ���ռƻ��嵥
	public void getSeven(PrintWriter out, String pageNumber, String pageSize, String search, int user_id) {
		
		
		ArrayList<String> sevenDay = DzySomeUtil.getManyDay(5);
		JdbcSqlite db;
		try {
			db = new JdbcSqlite("jdbc/sqlite");

			Object[][] course;
			// �ж��Ƿ��ǲ�ѯ
			if (search == null || search.trim() == "") {
				course = db.queryToArray(
						"SELECT day_time ,COUNT(case when status =1 then '1' end) as finish , COUNT(*) as countall from cyt_willing_plan  where user_id="+user_id+" and del=0 and day_time in"
						+ "('"+DzySomeUtil.dateToStamp(sevenDay.get(0),"yyyy-MM-dd")+"','"+DzySomeUtil.dateToStamp(sevenDay.get(1),"yyyy-MM-dd")+"','"+DzySomeUtil.dateToStamp(sevenDay.get(2),"yyyy-MM-dd")+"','"+DzySomeUtil.dateToStamp(sevenDay.get(3),"yyyy-MM-dd")+"','"+DzySomeUtil.dateToStamp(sevenDay.get(4),"yyyy-MM-dd")+"')  GROUP BY day_time");
			} else {// ��ѯ���յ��嵥 DzySomeUtil.dateToStamp(DzySomeUtil.stampToDate(now_time, "yyyy-MM-dd"), "yyyy-MM-dd")
				course = db.queryToArray(
						"SELECT * from cyt_willing_plan  where user_id=" + user_id + " and del=0 and day_time ='"
								+ DzySomeUtil.dateToStamp(search, "yyyy-MM-dd") + "' order by start_time");
			}
			// �Է�ҳ����ش���
			int pageSizes = Integer.parseInt(pageSize);
			int pageNum = (Integer.parseInt(pageNumber) - 1) * pageSizes;

			int iLength;
			int iList = 0;
			if (course.length - pageNum > pageSizes) {
				iLength = pageNum + pageSizes;
			} else {
				// ���������һҳ��ʱ��
				iLength = course.length;
			}
			List<Map> list = new LinkedList<>();
			for (int i = pageNum; i < iLength; i++) {
				Map<String, Object> map = new HashMap<>();
				map.put("\"flow_id\"", i + 1);
				map.put("\"date_time\"", "\""+DzySomeUtil.stampToDate(course[i][0].toString(), "MM-dd")+"\"");
				map.put("\"finish\"", course[i][1]);
				map.put("\"countAll\"", course[i][2]);
				if(course[i][2].equals(0) || course[i][1].equals(0)) {
					map.put("\"result\"", "\"0.00\"" );
				}else {
					map.put("\"result\"", "\""+DzySomeUtil.formatDouble((double)(Integer.parseInt(course[i][1].toString()) * 100.0 / Integer.parseInt(course[i][2].toString()))) +"\"" );
				}
				
				list.add(iList, map);
				iList++;
			}
			Map<String, Object> result = LayuiUtil.setResultMap(0, "", list, course.length);
			out.println(result.toString().replace("=", ":"));
			db.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ���ģ��ƻ�
	public void addExamplePlan(PrintWriter out, int user_id, String title, String context, String start_time,
			String end_time) {

		JdbcSqlite db;
		try {
			db = new JdbcSqlite("jdbc/sqlite");
			if (title.equals("") || context.equals("") || start_time.equals("") || end_time.equals("")) {
				out.println("{\"ok\":false,\"msg\":\"�����д��ƻ����ʧ�ܣ�\"}");
			} else {
				int result = db
						.update("insert into cyt_willing_example (user_id , title ,context ,start_time ,end_time)\r\n"
								+ "values ( " + user_id + " ,'" + title + "' ,'" + context + "' ,'" + start_time
								+ "' ,'" + end_time + "')");
				if (result == 1) {
					out.println("{\"ok\":true,\"msg\":\"�ƻ���ӳɹ���\"}");
				} else {
					out.println("{\"ok\":false,\"msg\":\"�ƻ����ʧ�ܣ�\"}");
				}
			}
			db.commit();
			db.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// �޸�ģ��ƻ�
	public void updateExamplePlan(PrintWriter out, String id, int user_id, String title, String context,
			String start_time, String end_time) {

		JdbcSqlite db;
		try {
			db = new JdbcSqlite("jdbc/sqlite");
			if (id.equals("") || title.equals("") || context.equals("") || start_time.equals("")
					|| end_time.equals("")) {
				out.println("{\"ok\":false,\"msg\":\"�����д��ƻ��޸�ʧ�ܣ�\"}");
			} else {
				int result = db.update("UPDATE cyt_willing_example SET title = '" + title + "', context ='" + context
						+ "' ,start_time='" + start_time + "' , end_time='" + end_time + "'  WHERE id =" + id
						+ " and  user_id =" + user_id + "");
				if (result == 1) {
					out.println("{\"ok\":true,\"msg\":\"�ƻ��޸ĳɹ���\"}");
				} else {
					out.println("{\"ok\":false,\"msg\":\"δ֪���󣬼ƻ��޸�ʧ�ܣ�\"}");
				}
			}
			db.commit();
			db.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// �޸Ľ��ռƻ�������
	public void updateTodayPlan(PrintWriter out, String id, String status) {

		JdbcSqlite db;
		try {
			db = new JdbcSqlite("jdbc/sqlite");
			if (id.equals("") || status.equals("")) {
				out.println("{\"ok\":false,\"msg\":\"�����д��ƻ�����ʧ�ܣ�\"}");
			} else {
				int result = db.update("UPDATE cyt_willing_plan SET status = " + status + " WHERE id =" + id + "");
				if (result == 1) {
					out.println("{\"ok\":true,\"msg\":\"�����ɹ���\"}");
				} else {
					out.println("{\"ok\":false,\"msg\":\"δ֪���󣬲���ʧ�ܣ�\"}");
				}
			}
			db.commit();
			db.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ɾ��ģ��ƻ�
	public void deleteExamplePlan(PrintWriter out, String id, String order) {

		JdbcSqlite db;
		try {
			db = new JdbcSqlite("jdbc/sqlite");
			if (id.equals("")) {
				out.println("{\"ok\":false,\"msg\":\"�����д��ƻ�ɾ��ʧ�ܣ�\"}");
			} else {
				int result = db.update("UPDATE cyt_willing_example SET del = 1  WHERE id = " + id + "");
				if (order.equals("1")) {// ����ɾ���Ż᷵��
					if (result == 1) {
						out.println("{\"ok\":true,\"msg\":\"�ƻ�ɾ���ɹ���\"}");
					} else {
						out.println("{\"ok\":false,\"msg\":\"δ֪���󣬼ƻ�ɾ��ʧ�ܣ�\"}");
					}
				}
			}
			db.commit();
			db.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ����ģ��ƻ�
	public synchronized void importExamplePlan(PrintWriter out, int user_id, String id, String order) {

		JdbcSqlite db;
		try {
			// ����ʱ��
			String import_time = Long.toString(new Date().getTime());

			// System.out.println(DzySomeUtil.stampToDate(import_time, "yyyy-MM-dd"));
			db = new JdbcSqlite("jdbc/sqlite");
			if (id.equals("")) {
				out.println("{\"ok\":false,\"msg\":\"�����д��ƻ�ɾ��ʧ�ܣ�\"}");
			} else {
				// ��ѯ�����д��û������ƻ���Ϣ
				Object[][] course = db.queryToArray(
						"SELECT * from cyt_willing_example  where user_id=" + user_id + " and del=0 and id=" + id + "");

				int result = db.update(
						"INSERT INTO cyt_willing_plan (user_id ,start_time ,end_time ,title ,context ,status ,import_time,day_time) \r\n"
								+ "VALUES( " + user_id + " ,'" + course[0][2] + "','" + course[0][3] + "','"
								+ course[0][4] + "','" + course[0][5] + "',0,'" + import_time + "','" + DzySomeUtil
										.dateToStamp(DzySomeUtil.stampToDate(import_time, "yyyy-MM-dd"), "yyyy-MM-dd")
								+ "')");

				if (order.equals("1")) {// ����ɾ���Ż᷵��
					if (result == 1) {
						out.println("{\"ok\":true,\"msg\":\"�ƻ�ɾ���ɹ���\"}");
					} else {
						out.println("{\"ok\":false,\"msg\":\"δ֪���󣬼ƻ�ɾ��ʧ�ܣ�\"}");
					}
				}
			}
			db.commit();
			db.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ���������嵥��Ҫɾ�����еĽ����֮ǰ�������嵥��
	public synchronized void deletTodayExamplePlan(PrintWriter out, int user_id, String order) {

		JdbcSqlite db;
		try {
			String now_time = Long.toString(new Date().getTime());
			db = new JdbcSqlite("jdbc/sqlite");

			int result = db.update("UPDATE cyt_willing_plan SET del = 1  WHERE del = 0 and user_id = " + user_id
					+ " and day_time = '"
					+ DzySomeUtil.dateToStamp(DzySomeUtil.stampToDate(now_time, "yyyy-MM-dd"), "yyyy-MM-dd") + "'");

			db.commit();
			db.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}

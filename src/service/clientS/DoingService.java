package service.clientS;

import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import utils.DzySomeUtil;
import utils.JdbcSqlite;
import utils.LayuiUtil;

public class DoingService {

	public static final DoingService me = new DoingService();

	// ��ȡ����������������
	public void getDoingStatus(PrintWriter out, String pageNumber, String pageSize, String order, int user_id) {

		JdbcSqlite db;
		try {
			String now_time = Long.toString(new Date().getTime());
			db = new JdbcSqlite("jdbc/sqlite");

			Object[][] course;
			String key = "7";
			if (key.equals(order)) {// order=7 ˵���ǲ�ѯ����һ��ļ�¼doing(�Ѿ���ɵ�)
				course = db.queryToArray(
						"SELECT * from cyt_doing_record  where user_id=" + user_id + " and del=0 and day_time='"
								+ DzySomeUtil.dateToStamp(DzySomeUtil.stampToDate(now_time, "yyyy-MM-dd"), "yyyy-MM-dd")
								+ "' and status=1 order by start_time desc");
			} else {
				course = db.queryToArray(// ��ѯ������û����������
						"SELECT * from cyt_doing_record  where user_id=" + user_id + " and del=0 and day_time='"
								+ DzySomeUtil.dateToStamp(DzySomeUtil.stampToDate(now_time, "yyyy-MM-dd"), "yyyy-MM-dd")
								+ "' and status=0");
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
				map.put("\"day_time\"", "\"" + course[i][9] + "\"");
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

	// ��ť�Ŀ�ʼ����¼�
	public void addDoingRecord(PrintWriter out, int user_id, String title, String context, String start_time) {

		JdbcSqlite db;
		try {
			String now_time = Long.toString(new Date().getTime());
			db = new JdbcSqlite("jdbc/sqlite");
			if (title.equals("") || context.equals("") || start_time.equals("")) {
				out.println("{\"ok\":false,\"msg\":\"�����д�����ʼʧ�ܣ�\"}");
			} else {
				int result = db.update(
						"insert into cyt_doing_record (user_id , title ,context ,start_time ,day_time ,status) values "
								+ "( " + user_id + " ,'" + title + "' , '" + context + "' ,'" + start_time + "' ,'"
								+ DzySomeUtil.dateToStamp(DzySomeUtil.stampToDate(now_time, "yyyy-MM-dd"), "yyyy-MM-dd")
								+ "' , 0)");
				if (result == 1) {
					out.println("{\"ok\":true,\"msg\":\"����ɹ���ʼ�����ͣ�\"}");
				} else {
					out.println("{\"ok\":false,\"msg\":\"��������ʧ�ܣ�\"}");
				}
			}
			db.commit();
			db.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ��ť�Ľ�������¼�
	public void updateDoing(PrintWriter out, int user_id, String end_time) {

		JdbcSqlite db;
		try {
			db = new JdbcSqlite("jdbc/sqlite");
			if (end_time.equals("")) {
				out.println("{\"ok\":false,\"msg\":\"�����д��ƻ�����ʧ�ܣ�\"}");
			} else {
				int result = db.update("UPDATE cyt_doing_record SET status = 1 ,end_time ='" + end_time
						+ "' WHERE status = 0 and user_id=" + user_id + " and del = 0 ");
				if (result == 1) {
					out.println("{\"ok\":true,\"msg\":\"������ɣ��������ͣ�\"}");
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

}

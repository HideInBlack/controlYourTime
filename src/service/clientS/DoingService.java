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

	// 获取现在正在做的任务
	public void getDoingStatus(PrintWriter out, String pageNumber, String pageSize, String order, int user_id) {

		JdbcSqlite db;
		try {
			String now_time = Long.toString(new Date().getTime());
			db = new JdbcSqlite("jdbc/sqlite");

			Object[][] course;
			String key = "7";
			if (key.equals(order)) {// order=7 说明是查询今天一天的记录doing(已经完成的)
				course = db.queryToArray(
						"SELECT * from cyt_doing_record  where user_id=" + user_id + " and del=0 and day_time='"
								+ DzySomeUtil.dateToStamp(DzySomeUtil.stampToDate(now_time, "yyyy-MM-dd"), "yyyy-MM-dd")
								+ "' and status=1 order by start_time desc");
			} else {
				course = db.queryToArray(// 查询现在有没有正在做的
						"SELECT * from cyt_doing_record  where user_id=" + user_id + " and del=0 and day_time='"
								+ DzySomeUtil.dateToStamp(DzySomeUtil.stampToDate(now_time, "yyyy-MM-dd"), "yyyy-MM-dd")
								+ "' and status=0");
			}

			// 对分页的相关处理
			int pageSizes = Integer.parseInt(pageSize);
			int pageNum = (Integer.parseInt(pageNumber) - 1) * pageSizes;

			int iLength;
			int iList = 0;
			if (course.length - pageNum > pageSizes) {
				iLength = pageNum + pageSizes;
			} else {
				// 这里是最后一页的时候
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

	// 按钮的开始点击事件
	public void addDoingRecord(PrintWriter out, int user_id, String title, String context, String start_time) {

		JdbcSqlite db;
		try {
			String now_time = Long.toString(new Date().getTime());
			db = new JdbcSqlite("jdbc/sqlite");
			if (title.equals("") || context.equals("") || start_time.equals("")) {
				out.println("{\"ok\":false,\"msg\":\"数据有错，任务开始失败！\"}");
			} else {
				int result = db.update(
						"insert into cyt_doing_record (user_id , title ,context ,start_time ,day_time ,status) values "
								+ "( " + user_id + " ,'" + title + "' , '" + context + "' ,'" + start_time + "' ,'"
								+ DzySomeUtil.dateToStamp(DzySomeUtil.stampToDate(now_time, "yyyy-MM-dd"), "yyyy-MM-dd")
								+ "' , 0)");
				if (result == 1) {
					out.println("{\"ok\":true,\"msg\":\"任务成功开始，加油！\"}");
				} else {
					out.println("{\"ok\":false,\"msg\":\"任务启动失败！\"}");
				}
			}
			db.commit();
			db.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 按钮的结束点击事件
	public void updateDoing(PrintWriter out, int user_id, String end_time) {

		JdbcSqlite db;
		try {
			db = new JdbcSqlite("jdbc/sqlite");
			if (end_time.equals("")) {
				out.println("{\"ok\":false,\"msg\":\"数据有错，计划操作失败！\"}");
			} else {
				int result = db.update("UPDATE cyt_doing_record SET status = 1 ,end_time ='" + end_time
						+ "' WHERE status = 0 and user_id=" + user_id + " and del = 0 ");
				if (result == 1) {
					out.println("{\"ok\":true,\"msg\":\"任务完成，继续加油！\"}");
				} else {
					out.println("{\"ok\":false,\"msg\":\"未知错误，操作失败！\"}");
				}
			}
			db.commit();
			db.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

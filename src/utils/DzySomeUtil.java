package utils;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import javax.servlet.http.HttpSession;

import org.apache.commons.mail.HtmlEmail;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;


public class DzySomeUtil {

	/**
	 * session的核实判断是否已经登录
	 * 
	 * @param session
	 * @throws Exception
	 */
	public static boolean sessionCheck(HttpSession session) throws Exception {

		// 已经登录：查看session session有值，说明已经登录，给出判定页面即可
		String roles = (String) session.getAttribute("roles");
		String names = (String) session.getAttribute("names");

		// 进行登录：session里没有roles值，说明登录已经失效，或者第一次登录，进行登录并把roles值绑定到session上
		if (roles == null) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 给字符串加双引号
	 */
	public String addMark(String old) {
		return "\"" + old + "\"";
	}

	/*
	 * 将时间转换为时间戳 format:yyyy-MM-dd HH:mm:ss
	 */
	public static String dateToStamp(String s, String format) throws Exception {
		String res;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
		Date date = simpleDateFormat.parse(s);
		long ts = date.getTime();
		res = String.valueOf(ts);
		return res;
	}

	/*
	 * 将时间戳转换为时间 format:yyyy-MM-dd HH:mm:ss
	 */
	public static String stampToDate(String s, String format) {
		String res;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
		long lt = new Long(s);
		Date date = new Date(lt);
		res = simpleDateFormat.format(date);
		return res;
	}

	/**
	 * 邮箱验证码
	 * 
	 * @param emailaddress 收件人地址
	 * @param code         验证码
	 * @return
	 */
	public static boolean sendEmail(String emailaddress, String code) {
		try {
			HtmlEmail email = new HtmlEmail();// 不用更改
			email.setHostName("smtp.qq.com");// 需要修改，126邮箱为smtp.126.com,163邮箱为163.smtp.com，QQ为smtp.qq.com
			email.setCharset("UTF-8");
			email.addTo(emailaddress);// 收件地址

			email.setFrom("1327293674@qq.com", "守住时间");// 此处填邮箱地址和用户名,用户名可以任意填写

			email.setAuthentication("1327293674@qq.com", "urldcqzouwfwigch");// 此处填写邮箱地址和客户端授权码

			email.setSubject("守住时间用户注册");// 此处填写邮件名，邮件名可任意填写
			email.setMsg("尊敬的用户您好,您本次注册的验证码是:" + code);// 此处填写邮件内容

			email.send();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	// 验证码生成6位
	public static String getRandNum(int charCount) {
		String charValue = "";
		for (int i = 0; i < charCount; i++) {
			char c = (char) (randomInt(0, 10) + '0');
			charValue += String.valueOf(c);
		}
		return charValue;
	}

	public static int randomInt(int from, int to) {
		Random r = new Random();
		return from + r.nextInt(to - from);
	}

	/**
	 * 获取过去或者未来 任意天内的日期数组
	 * 
	 * @param intervals intervals天内
	 * @return 日期数组
	 */
	public static ArrayList<String> getManyDay(int intervals) {
		ArrayList<String> pastDaysList = new ArrayList<>();
		ArrayList<String> fetureDaysList = new ArrayList<>();
		for (int i = 0; i < intervals; i++) {
			pastDaysList.add(getPastDate(i));
			fetureDaysList.add(getFetureDate(i));
		}
		return pastDaysList;
	}

	/**
	 * 获取过去第几天的日期
	 *
	 * @param past
	 * @return
	 */
	public static String getPastDate(int past) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past);
		Date today = calendar.getTime();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String result = format.format(today);
		//Log.e(null, result);
		return result;
	}

	/**
	 * 获取未来 第 past 天的日期
	 * 
	 * @param past
	 * @return
	 */
	public static String getFetureDate(int past) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + past);
		Date today = calendar.getTime();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String result = format.format(today);
		//Log.e(null, result);
		return result;
	}
	
	/**
	 * double取两位小数
	 * @param d
	 * @return
	 */
    public static String formatDouble(double d) {
        DecimalFormat df = new DecimalFormat("#.00");
        return df.format(d);
    }

}

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
	 * session�ĺ�ʵ�ж��Ƿ��Ѿ���¼
	 * 
	 * @param session
	 * @throws Exception
	 */
	public static boolean sessionCheck(HttpSession session) throws Exception {

		// �Ѿ���¼���鿴session session��ֵ��˵���Ѿ���¼�������ж�ҳ�漴��
		String roles = (String) session.getAttribute("roles");
		String names = (String) session.getAttribute("names");

		// ���е�¼��session��û��rolesֵ��˵����¼�Ѿ�ʧЧ�����ߵ�һ�ε�¼�����е�¼����rolesֵ�󶨵�session��
		if (roles == null) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * ���ַ�����˫����
	 */
	public String addMark(String old) {
		return "\"" + old + "\"";
	}

	/*
	 * ��ʱ��ת��Ϊʱ��� format:yyyy-MM-dd HH:mm:ss
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
	 * ��ʱ���ת��Ϊʱ�� format:yyyy-MM-dd HH:mm:ss
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
	 * ������֤��
	 * 
	 * @param emailaddress �ռ��˵�ַ
	 * @param code         ��֤��
	 * @return
	 */
	public static boolean sendEmail(String emailaddress, String code) {
		try {
			HtmlEmail email = new HtmlEmail();// ���ø���
			email.setHostName("smtp.qq.com");// ��Ҫ�޸ģ�126����Ϊsmtp.126.com,163����Ϊ163.smtp.com��QQΪsmtp.qq.com
			email.setCharset("UTF-8");
			email.addTo(emailaddress);// �ռ���ַ

			email.setFrom("1327293674@qq.com", "��סʱ��");// �˴��������ַ���û���,�û�������������д

			email.setAuthentication("1327293674@qq.com", "urldcqzouwfwigch");// �˴���д�����ַ�Ϳͻ�����Ȩ��

			email.setSubject("��סʱ���û�ע��");// �˴���д�ʼ������ʼ�����������д
			email.setMsg("�𾴵��û�����,������ע�����֤����:" + code);// �˴���д�ʼ�����

			email.send();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	// ��֤������6λ
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
	 * ��ȡ��ȥ����δ�� �������ڵ���������
	 * 
	 * @param intervals intervals����
	 * @return ��������
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
	 * ��ȡ��ȥ�ڼ��������
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
	 * ��ȡδ�� �� past �������
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
	 * doubleȡ��λС��
	 * @param d
	 * @return
	 */
    public static String formatDouble(double d) {
        DecimalFormat df = new DecimalFormat("#.00");
        return df.format(d);
    }

}

package utils;

import java.util.HashSet;
import java.util.Set;

/**
 * ���ʿ�����
 * @author ��
 *
 */
public final class AccessControl {
	//��¼ ���ص���roles��� �������ø�session
	public static String login(JdbcSqlite db,String name,String pwd)throws Exception{
		Object[][] a=db.queryToArray("select roles from user where name='"+name+"' and pwd='"+pwd+"' limit 1");
		if(a.length==1)return (String)a[0][0];
		return null;
	}
	//������ɫ
	public static boolean in(String role,String roles){
		Set<String> s=new HashSet<String>();
		String[] a=roles.split(",");
		for(String b:a)s.add(b);
		return s.contains(role);
	}
}

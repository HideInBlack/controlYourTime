package utils;

import java.sql.*;
import java.util.*;
import java.io.*;
import javax.naming.*;

public final class JdbcSqlite {
	public Connection connection = null;// ����
	public Statement statement = null;// ���
	public ResultSet resultSet = null;// �����
	public int updateCount = -1;// Ӱ���¼��
	public int moreResult=0;//��������0-�ޣ�1-��¼����2-�����

	// ���췽�����޲Σ�
	public JdbcSqlite(){}
	
	// ���췽�������ݿ��ļ�·����
	// ���췽����JDBC����Դ��
	public JdbcSqlite(String ds)throws Exception{
		//setConnection(ds);
		setDataSource(ds);
	}
	
	// �ر�����
	public void close() throws SQLException {
		closeStatement();
		if (connection != null)
			connection.close();
	}
	
	// �ر����
	public void closeStatement() throws SQLException {
		if (resultSet != null)
			resultSet.close();
		if (statement != null)
			statement.close();
	}

	// �������ӣ����ݿ��ļ�·����
	public void setConnection(String dbPath)
			throws Exception {
		Class.forName("org.sqlite.JDBC");// ����jdbc��������
		String cs = "jdbc:sqlite:"+dbPath;// ���ݿ������ַ���
		close();// �ر�����
		connection = DriverManager.getConnection(cs);// ��ȡ���ݿ�����
		connection.setAutoCommit(false);// ��������
		statement = connection.createStatement();// ����������
	}

	// ��������Դ(JDBC����Դ)
	public void setDataSource(String dataSource) throws Exception {
		Context initCtx = new InitialContext();// ��ʼ��������
		Context ctx = (Context) initCtx.lookup("java:comp/env");// ������
		// Object
		// obj=(Object)ctx.lookup("jdbc/Db");//����Դ���ƣ���server.xml�趨һ��
		Object obj = (Object) ctx.lookup(dataSource);// ����Դ���ƣ���server.xml�趨һ��
		javax.sql.DataSource ds = (javax.sql.DataSource) obj;// ����Դ
		connection = ds.getConnection();// ��ȡ���ݿ�����
		connection.setAutoCommit(false);// ��������
		statement = connection.createStatement();// ����������
	}

	// ִ�зǲ�ѯSQL���
	public int update(String sql) throws SQLException {
		//System.out.println(sql);
		updateCount=statement.executeUpdate(sql);
		return updateCount;
	}

	// ִ�в�ѯSQL���
	public ResultSet query(String sql) throws SQLException {
		if (resultSet != null)resultSet.close();
		resultSet = statement.executeQuery(sql);
		return resultSet;
	}

	// �����Զ��ύ��ʽ(true��false)
	public void setAutoCommit(boolean autoCommit) throws SQLException {
		connection.setAutoCommit(autoCommit);
	}

	// �����ύ
	public void commit()throws SQLException  {
		connection.commit();// �����ύ
	}
	
	// ����ع�
	public void rollback()throws SQLException  {
		connection.rollback();// ����ع�
	}

	// ִ��SQL����
	public int sql(String sql) throws SQLException {
		closeStatement();		
		statement = connection.createStatement();// ����������
		boolean  b= statement.execute(sql);// ִ����䣬���ض�����
		if(b)moreResult=2;
		else moreResult=1;
		return moreResult;
	}
	
	//��ȡ��һ������Ľ�������ǽ��������null
	public ResultSet getNextResults()throws SQLException {
		if(moreResult==2){
			resultSet = statement.getResultSet();// ��ȡ�����
			return resultSet;
		}
		return null;
	}
	
	//��ȡ��һ�������Ӱ���¼������Ӱ���¼������-2���������-1������һ�����
	public int getNextUpdateCount()throws SQLException {
		if(moreResult==1){
			updateCount = statement.getUpdateCount();// ��ȡ��Ӱ���¼��
			if(updateCount == -1){
				moreResult=0;//����һ�����
				closeStatement();
			}
			return updateCount;
		}
		return -2;
	}
	
	//�и�������0-�ޣ�1-��¼����2-�����
	public int getMoreResults()throws SQLException {
		if(moreResult==0)return 0;
		boolean b=statement.getMoreResults();
		if(b)moreResult=2;
		else moreResult=1;
		return moreResult;
	}
	
	//���SQL�������ִ���б�
	public void addBatch(String sql)throws SQLException {
		statement.addBatch(sql);		
	}
	
	//�������ִ�е�SQL�����б�
	public void clearBatch()throws SQLException {
		statement.clearBatch();		
	} 

	//�ύ����ִ�е�SQL�����б����ظ��¼�����ɵ�����
	public int[] executeBatch()throws SQLException {
		return statement.executeBatch();		
	} 

	// ��ѯΪ��������
	public Object[][] queryToArray(String sql) throws SQLException {
		return toArray(query(sql));
	}

	// ��ѯΪΪJSON�ַ���
	public String queryToJson(String sql) throws SQLException {
		return toJson(toArray(query(sql)));
	}

	// �����ת��Ϊ��������
	public static Object[][] toArray(ResultSet resultSet) throws SQLException {
		int c = resultSet.getMetaData().getColumnCount();// ��ȡ���������
		List<Object[]> list = new ArrayList<Object[]>();
		int limit=100;//ת����¼���޶�
		while (resultSet.next()) {
			if(--limit<0)break;//ת����¼�������޶�
			Object[] a = new Object[c];
			for (int i = 0; i < c; i++) {
				a[i] = resultSet.getObject(i + 1);
				// utils[i]=resultSet.getString(i+1);//null��ת��Ϊ""
			}
			list.add(a);
		}
		return (Object[][]) list.toArray(new Object[0][0]);
	}

	// ��ά��������ת��ΪJSON�ַ���
	public static String toJson(Object[][] a) {
		String s = "[";
		for (int i = 0; i < a.length; i++) {
			if (i != 0) s += ",";
			s += "[";
			for (int j = 0; j < a[i].length; j++) {
				if (j != 0) s += ",";
				if (a[i][j]==null) s +="undefined";
				else if(a[i][j].getClass().getName()=="java.lang.String")
					s += "\"" + a[i][j].toString() + "\"";
				else s += a[i][j].toString();
			}
			s += "]";
		}
		s += "]";
		return s;
	}

	// JSON�ַ���ת��Ϊ��ά�ַ�������
	public static String[][] toArray(String json) {
		String[][] aa = null;
		String[] a = null;
		String s = json;
		s = s.substring(2, s.length() - 2);
		a = s.split("],[");
		aa = new String[a.length][];
		for (int i = 0; i < a.length; i++) {
			aa[i] = a[i].split(",");
		}
		return aa;
	}
	
	//���ļ�ִ��SQL����(�ļ�·�������ַ����룩
	//SQL������;��β�����Կ�Խ����
	//ע��Ϊ//��ʼ��;��β�����Կ�Խ����
	public int[] sqlFile(String path,String encoding)throws Exception{
		FileInputStream fis = new FileInputStream(path);   
		InputStreamReader isr = new InputStreamReader(fis, encoding);   
		BufferedReader br = new BufferedReader(isr);   
		String line = null;
		String s="";
		while ((line = br.readLine()) != null) { 
			s += line;   
			s += "\r\n"; // ���ϻ��з�   
		}
		String[] a=s.split(";");
		for(String b:a){
			String c=b.trim();
			if(c.length()==0||c.startsWith("//"))continue;
			addBatch(c+";");
			//System.out.println(c+";");
		}
		int[] r=executeBatch();
		//commit();
		return r;
	}
}

package utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class DBUtils {

	/*
	 * ͨ��c3p0����Դ�������
	 */

	static private DataSource dataSource = null;

	static {
		// ����Դֻ�ܱ���ʼ��һ��
		dataSource = new ComboPooledDataSource("voteapp");
	}

	public static Connection getConnection() throws SQLException {

		return dataSource.getConnection();

	}

	/**
	 * �ر����ݿ���Դ
	 * 
	 * @param resultSet
	 * @param preparedStatement
	 * @param connection
	 */
	public static void closeResource(ResultSet resultSet,
			PreparedStatement preparedStatement, Connection connection) {

		try {
			if (resultSet != null) {
				resultSet.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				preparedStatement.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}
}

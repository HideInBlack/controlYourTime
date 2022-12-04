package utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class DBUtils {

	/*
	 * 通过c3p0数据源获得连接
	 */

	static private DataSource dataSource = null;

	static {
		// 数据源只能被初始化一次
		dataSource = new ComboPooledDataSource("voteapp");
	}

	public static Connection getConnection() throws SQLException {

		return dataSource.getConnection();

	}

	/**
	 * 关闭数据库资源
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

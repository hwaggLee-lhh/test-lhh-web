package com.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 恒生聚源DB
 * @author hwaggLee
 * @createDate 2016年10月24日
 */
public class JdbcTsmarketUtils {// 数据库用户名
	private static String USERNAME = "root";
	// 数据库密码
	private static String PASSWORD = "hp64123456";
	// 驱动信息
	private static String DRIVER = "com.mysql.jdbc.Driver";
	// 数据库地址
	private static String URL = "jdbc:mysql://61.152.154.23:3306/tsmarket?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull";
	private Connection connection;
	public JdbcTsmarketUtils() {
		getConnection();
	}
	/*private static N3BJdbcUtils utils ;
	public static N3BJdbcUtils getInstance(){
		if( utils == null ){
			utils = new N3BJdbcUtils();
		}
		return utils;
	}*/
	
	public JdbcTsmarketUtils(String username, String password, String driver, String url) {
		USERNAME = username;
		PASSWORD = password;
		DRIVER = driver;
		URL = url;
		getConnection();
	}

	/**
	 * 获得数据库的连接
	 * 
	 * @return
	 */
	private Connection getConnection() {
		try {
			Class.forName(DRIVER);
			connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return connection;
	}
	
	public List<String> findList(String sql,String key){
		List<String> list = new ArrayList<String>();
		ResultSet rs = null;
		Statement stmt = null;
		try {
			stmt = connection.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				list.add(rs.getString(key));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			closeResultSett(rs);
			closeStatement(stmt);
		}
		return list;
	}
	
	public void insert(String sql ){
		Statement stmt = null;
		try {
			stmt = connection.createStatement();
			stmt.execute(sql);
			/*while(rs.next()){  
	             System.out.println(rs.getString("n"));  
	         }*/
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			closeStatement(stmt);
		}
	}

	
	public void closeStatement(Statement stmt) {
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public Map<String, Object> findSimpleResult(String sql, List<Object> params)
			throws SQLException {
		Map<String, Object> map = new HashMap<String, Object>();
		int index = 1;
		ResultSet resultSet = null;
		PreparedStatement pstmt = null;
		pstmt = connection.prepareStatement(sql);
		if (params != null && !params.isEmpty()) {
			for (int i = 0; i < params.size(); i++) {
				pstmt.setObject(index++, params.get(i));
			}
		}
		resultSet = pstmt.executeQuery();// 返回查询结果
		ResultSetMetaData metaData = resultSet.getMetaData();
		int col_len = metaData.getColumnCount();
		while (resultSet.next()) {
			for (int i = 0; i < col_len; i++) {
				String cols_name = metaData.getColumnName(i + 1);
				Object cols_value = resultSet.getObject(cols_name);
				if (cols_value == null) {
					cols_value = "";
				}
				map.put(cols_name, cols_value);
			}
		}
		closeResultSett(resultSet);
		closeStatement(pstmt);
		return map;
	}
	
	public List<Map<String, Object>> findModeResult(String sql,
			List<Object> params) throws SQLException {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		int index = 1;
		ResultSet resultSet = null;
		PreparedStatement pstmt = null;
		pstmt = connection.prepareStatement(sql);
		if (params != null && !params.isEmpty()) {
			for (int i = 0; i < params.size(); i++) {
				pstmt.setObject(index++, params.get(i));
			}
		}
		resultSet = pstmt.executeQuery();
		ResultSetMetaData metaData = resultSet.getMetaData();
		int cols_len = metaData.getColumnCount();
		while (resultSet.next()) {
			Map<String, Object> map = new HashMap<String, Object>();
			for (int i = 0; i < cols_len; i++) {
				String cols_name = metaData.getColumnName(i + 1);
				Object cols_value = resultSet.getObject(cols_name);
				if (cols_value == null) {
					cols_value = "";
				}
				map.put(cols_name, cols_value);
			}
			list.add(map);
		}

		closeResultSett(resultSet);
		closeStatement(pstmt);
		return list;
	}


	public void closeResultSett(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void closeConnection() {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public void testJDBC() throws SQLException {
		Connection con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		// Querying
		try (Statement stmt = con.createStatement()) {
			ResultSet rs = stmt.executeQuery("MATCH (n) RETURN n");
			while (rs.next()) {
				System.out.println(rs.getString("n"));
			}
		}
	}
}

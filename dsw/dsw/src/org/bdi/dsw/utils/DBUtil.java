package org.bdi.dsw.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.bdi.dsw.domain.DBInfo;
import org.json.JSONException;
/**
 * 数据库操作工具类
 * @author 石夏靖,陈凯丰,廖敏敏,薛维聪,杨虹宇
 * 
 */
public class DBUtil {
	/**
	 * 建立与数据库的连接
	 * @param dbJsonFile 存放数据库信息的Json文件
	 * @return 数据库连接对象
	 * @throws FileNotFoundException Json文件不存在时会抛出异常
	 * @throws ClassNotFoundException 找不到数据库驱动类时会抛出异常
	 */
	public static Connection connToDB(File dbJsonFile) throws FileNotFoundException, ClassNotFoundException{
		DBInfo db = (DBInfo) JsonUtil.Json2Object(new BufferedReader(new FileReader(dbJsonFile)), DBInfo.class);
		Connection conn = null;
		
		Class.forName(db.getDriver());
		try {
			conn = DriverManager.getConnection(db.getUrl(), db.getUsername(), db.getPassword());			
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("数据库连接失败");
		}
		//返回连接对象
		return conn;
	}
	/**
	 * 数据库查询，得到json字符串
	 * @param sql 查询的sql语句
	 * @param qID 查询方法的id，用来确定数据库
	 * @return 结果的json字符串
	 */
	public static String query(String sql, int qID){
		String dbName = XmlUtil.getDatabaseByQID(qID);
		File dbJsonFile = dbJson2File(dbName);			
		
		Connection conn = null;
		Statement stat = null;
		ResultSet result = null;
		try {
			conn = connToDB(dbJsonFile);
			stat = conn.createStatement();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException("配置文件不存在");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException("数据库驱动类不存在");
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Statement创建失败");
		}
		try {
			result = stat.executeQuery(sql);
			return JsonUtil.resultSet2Json(result);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("查询数据库失败");
		} catch (JSONException e) {
			e.printStackTrace();
			throw new RuntimeException("json文件转换失败");
		}
	}
	/**
	 * 根据数据库类型读对应的数据库配置文件，得到数据库对象
	 * @param dbName 数据库名称
	 * @return 数据库文件对象
	 */
	public static File dbJson2File(String dbName){
		String filePath = "conf/dbjson";
		if(dbName == null){
			throw new RuntimeException("找不到对应的数据库");
		}else if(dbName.equals("-1")){
			throw new RuntimeException("方法对应了多个数据库映射");
		}
		return new File(filePath, dbName + ".json");
	}
	/**
	 * 释放连接资源
	 * @param conn 数据库连接对象
	 * @param st 数据库操作执行对象
	 * @param rs 数据库结果集对象
	 */
	public static void release(Connection conn, Statement st, ResultSet rs){		
		if(rs != null){
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException("ResultSet关闭失败");
			}
			rs = null;
		}
		if(st != null){
			try{
				st.close();
			}catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException("Statement关闭失败");
			}
		}
		
		if(conn != null){
			try{
				conn.close();
			}catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException("Statement关闭失败");
			}
		}
	}
}

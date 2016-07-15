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
 * ���ݿ����������
 * @author ʯ�ľ�,�¿���,������,Ѧά��,�����
 * 
 */
public class DBUtil {
	/**
	 * ���������ݿ������
	 * @param dbJsonFile ������ݿ���Ϣ��Json�ļ�
	 * @return ���ݿ����Ӷ���
	 * @throws FileNotFoundException Json�ļ�������ʱ���׳��쳣
	 * @throws ClassNotFoundException �Ҳ������ݿ�������ʱ���׳��쳣
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
			throw new RuntimeException("���ݿ�����ʧ��");
		}
		//�������Ӷ���
		return conn;
	}
	/**
	 * ���ݿ��ѯ���õ�json�ַ���
	 * @param sql ��ѯ��sql���
	 * @param qID ��ѯ������id������ȷ�����ݿ�
	 * @return �����json�ַ���
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
			throw new RuntimeException("�����ļ�������");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException("���ݿ������಻����");
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Statement����ʧ��");
		}
		try {
			result = stat.executeQuery(sql);
			return JsonUtil.resultSet2Json(result);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("��ѯ���ݿ�ʧ��");
		} catch (JSONException e) {
			e.printStackTrace();
			throw new RuntimeException("json�ļ�ת��ʧ��");
		}
	}
	/**
	 * �������ݿ����Ͷ���Ӧ�����ݿ������ļ����õ����ݿ����
	 * @param dbName ���ݿ�����
	 * @return ���ݿ��ļ�����
	 */
	public static File dbJson2File(String dbName){
		String filePath = "conf/dbjson";
		if(dbName == null){
			throw new RuntimeException("�Ҳ�����Ӧ�����ݿ�");
		}else if(dbName.equals("-1")){
			throw new RuntimeException("������Ӧ�˶�����ݿ�ӳ��");
		}
		return new File(filePath, dbName + ".json");
	}
	/**
	 * �ͷ�������Դ
	 * @param conn ���ݿ����Ӷ���
	 * @param st ���ݿ����ִ�ж���
	 * @param rs ���ݿ���������
	 */
	public static void release(Connection conn, Statement st, ResultSet rs){		
		if(rs != null){
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
				throw new RuntimeException("ResultSet�ر�ʧ��");
			}
			rs = null;
		}
		if(st != null){
			try{
				st.close();
			}catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException("Statement�ر�ʧ��");
			}
		}
		
		if(conn != null){
			try{
				conn.close();
			}catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException("Statement�ر�ʧ��");
			}
		}
	}
}

package org.bdi.dsw.test;

import java.io.File;
import java.sql.Connection;
import java.sql.Statement;

import org.bdi.dsw.utils.DBUtil;
/**
 * ������
 * @author ʯ�ľ�,�¿���,������,Ѧά��,�����
 * 
 */
public class Test {
	public static void main(String[] args) throws Exception{
//		String sql = "select MEAN10,MEAN25,UPPER_BOUND,LOWER_BOUND from MA_DEMO where TO_CHAR(TEST_TIME,'YYYYMMDD')='20150115' and VENDOR='Ezzconn' and CM_NAME='����' and BOM='1050AGE'";		
//		System.out.println(DBUtil.query(sql, 1));
		Connection conn = DBUtil.connToDB(new File("conf/dbjson","impala.json"));
		Statement stat = conn.createStatement();
		if(stat != null){
			System.out.println(stat.execute("show databases"));
			System.out.println("hahhahah");
		}
	}
}

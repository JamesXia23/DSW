package org.bdi.dsw.utils;

import java.io.BufferedReader;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
/**
 * Json工具类
 * @author 石夏靖,陈凯丰,廖敏敏,薛维聪,杨虹宇
 * 
 */
public class JsonUtil {
	
	/**
	 * 将ResultSet转化为Json文件
	 * @param rs 数据库返回的ResultSet
	 * @return Json字符串
	 * @throws SQLException
	 * @throws JSONException
	 */
	public static String resultSet2Json(ResultSet rs) throws SQLException,JSONException  
	{  
	   // json数组  
	   JSONArray array = new JSONArray();  
	    
	   // 获取列数  
	   ResultSetMetaData metaData = rs.getMetaData();  
	   int columnCount = metaData.getColumnCount();  

	   // 遍历ResultSet中的每条数据  
	    while (rs.next()) {  
	        JSONObject jsonObj = new JSONObject();  
	         
	        // 遍历每一列  
	        for (int i = 1; i <= columnCount; i++) {  
	            String columnName =metaData.getColumnLabel(i);  
	            String value = rs.getString(columnName);  
	            jsonObj.put(columnName, value);  
	        }   
	        array.put(jsonObj);   
	    }
	    
	   return array.toString();  
	}
   
    /**
     * 将对象转换成Json格式的字符串
     * @param object 要转换成Json的对象
     * @return String:Json格式的字符串
     */
    public static String Object2Json(Object object) {     
    	Gson gson = new Gson();   
    	//生成Json字符串
        return gson.toJson(object);
    } 
    
    /**
     * 将json转化为java对象
     * @param bufr json文件输入流
     * @param javaBean 要传化成的对象的Class类型
     * @return 返回转化好的java对象
     */
    public static Object Json2Object(BufferedReader bufr, Class<?>javaBean) {         
    	Gson gson = new Gson();     
        Object object = null;    
        object = gson.fromJson(bufr,javaBean);
        return object;  
    }
}

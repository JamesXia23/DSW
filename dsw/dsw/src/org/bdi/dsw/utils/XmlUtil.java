package org.bdi.dsw.utils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.*;

import java.util.List;
import java.io.*;

import org.jaxen.*;
/**
 * xml工具类
 * @author 石夏靖,陈凯丰,廖敏敏,薛维聪,杨虹宇
 * 
 */
public class XmlUtil {
	/**
	 * 根据方法的qID，查询配置文件，返回对应的数据库类型名称
	 * @param qID 查询方法qID
	 * @return 对应的数据库类型名称
	 */
	public static String getDatabaseByQID(int qID){
		String xmlPath = "conf/xml/HPDM_MA_DEMO.xml";
		Document xmlDoc = parserXml(xmlPath);
		if(!xmlDoc.hasContent())
     		return null;
		
		String Path = "//QID" + qID;
		List<Element> e = xmlDoc.selectNodes(Path);
		if(e.isEmpty())
			return null;
		else{
			if(e.size() > 1)
				return "-1";
			else
				return e.get(0).getText().toLowerCase();
		}
	}
	/**
	 * 解析xml文件，返回文档对象
	 * @param xmlPath xml文档的路径
	 * @return 对应的文档对象
	 */
	public static Document parserXml(String xmlPath){
		
		try{
			SAXReader reader = new SAXReader();			
			return reader.read(new File(xmlPath));
		}
		catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException("读取XML错误");
		}
	}
}

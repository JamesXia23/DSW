package org.bdi.dsw.utils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.*;

import java.util.List;
import java.io.*;

import org.jaxen.*;
/**
 * xml������
 * @author ʯ�ľ�,�¿���,������,Ѧά��,�����
 * 
 */
public class XmlUtil {
	/**
	 * ���ݷ�����qID����ѯ�����ļ������ض�Ӧ�����ݿ���������
	 * @param qID ��ѯ����qID
	 * @return ��Ӧ�����ݿ���������
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
	 * ����xml�ļ��������ĵ�����
	 * @param xmlPath xml�ĵ���·��
	 * @return ��Ӧ���ĵ�����
	 */
	public static Document parserXml(String xmlPath){
		
		try{
			SAXReader reader = new SAXReader();			
			return reader.read(new File(xmlPath));
		}
		catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException("��ȡXML����");
		}
	}
}
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.*;
import java.util.List;
import java.io.*;
import org.jaxen.*;
//import javax.xml.parsers.DocumentBuilderFactory; 
//import javax.xml.parsers.*;


public class ReadXml {
	Document document ;
	String filename;
	Element root;
	 
	public ReadXml(String Path){
		try{
			SAXReader reader=new SAXReader();			
			document = reader.read(new File(Path));
			root=document.getRootElement();  //���ڵ�
		}
		catch(Exception e){
		}
	}

	public List getTableXPath(String tablename){
		if(!document.hasContent())
     		return null;
		String xPath="//table[@name='"+tablename+"']/colum";
		List<Element> e=document.selectNodes(xPath);  //get the table
		return e;
	}
	public String getColum(Element e){
		String s=null;
		s=e.getText();
		return s;
	}
	public static void main(String []args){
		ReadXml x=new ReadXml("src/HPDM_MA_DEMO.xml");
		List <Element>l=null;
		int i;
		//Element e;
		l=x.getTableXPath("HPDM");
		System.out.println(l.size());
		for(i=0;i<l.size();i++){
			System.out.println(x.getColum(l.get(i)));
		}
	}
}

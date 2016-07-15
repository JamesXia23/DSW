package org.bdi.dsw.utils;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * DSW入口类
 * @author 石夏靖,陈凯丰,廖敏敏,薛维聪,杨虹宇
 * 
 */
@Path("APPID/")
public class DSWUtil {
	/**
	 * 通过vendor,cm_name,bom查询某个时间点的记录
	 * @param time get方法获取的时间点
	 * @param vendor get方法获取的vendor
	 * @param cm_name get方法获取的cm_name
	 * @param bom get方法获取的bom
	 * @return 查询结果的json字符串
	 */
	@GET
	@Path("QID1")
	@Produces(MediaType.TEXT_PLAIN)
	public String QID1(
			@QueryParam ("TEST_TIME") String time,
			@QueryParam ("VENDOR") String vendor,
			@QueryParam ("CM_NAME") String cm_name,
			@QueryParam ("BOM") String bom
			){
		String sql = "select MEAN10,MEAN25,UPPER_BOUND,LOWER_BOUND from MA_DEMO where TO_CHAR(TEST_TIME,'YYYYMMDD')='"+time+"' and VENDOR='"+vendor+"' and CM_NAME='"+cm_name+"' and BOM='"+bom+"'";
		return DBUtil.query(sql, 1);
	}
}

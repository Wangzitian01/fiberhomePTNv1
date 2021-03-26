package cn.edu.hbut.hfcrt.utils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.json.JSONArray;
import org.json.JSONObject;


public class MainDemo2 {
	private static String path001 = "D:\\Eclipse workspace\\hubt-fiberhome-ptn-reconfig-tools\\file\\黄石环 排序 （1003成功数据）（李星）.xls";
	private static String path002 = "D:\\Eclipse workspace\\hubt-fiberhome-ptn-reconfig-tools\\file\\nodeNeList";
	private static String path003 = "D:\\Eclipse workspace\\hubt-fiberhome-ptn-reconfig-tools\\file\\nodeTopology";
	private static String path004 = "D:\\Eclipse workspace\\hubt-fiberhome-ptn-reconfig-tools\\file\\环处理 - 副本(1).xls";//生成EXCEL表格的路径
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("---开始----");
		GiantRingDataGeneration();
		System.out.println("---结束----");
	}
	
	public static void main01() throws Exception{
		System.out.println("---开始----");
		GiantRingDataGeneration();
		System.out.println("---结束----");
	}
	
	private static void GiantRingDataGeneration() throws Exception {
		//第一列
		List getCircleSortMessage = GetCircleSortMessage(path001,3,5);
		//第三列，与左边相连的汇聚网元ID
		List getLeftHJNelistMessageID = GetLeftHJNelistMessageID(path002,path003,getCircleSortMessage);
		//第二列，与左边相连的汇聚网元
		List getLeftHJNelistMessageName = GetLeftHJNelistMessageName(path002,getLeftHJNelistMessageID);
		//第五列，与右边相连的汇聚网元ID
		List getRightHJNelistMessageID = GetRightHJNelistMessageID(path002,path003,getCircleSortMessage);
		//第四列，与右边相连的汇聚网元
		List getRightHJNelistMessageName = GetRightHJNelistMessageName(path002,getRightHJNelistMessageID);
		//生成EXCEL表格
		JHEXCEL(path004,getCircleSortMessage,getLeftHJNelistMessageName,getLeftHJNelistMessageID,getRightHJNelistMessageName,getRightHJNelistMessageID);
		//打印
		for (int i = 0; i < getRightHJNelistMessageName.size(); i++) {
			System.out.println(getCircleSortMessage.get(i)+"---"+getLeftHJNelistMessageName.get(i)+"---"+getLeftHJNelistMessageID.get(i)+"---"+getRightHJNelistMessageName.get(i)+"---"+getRightHJNelistMessageID.get(i));
		}
	
	}
/**
 * 功能：根据已有的数据生成表格
 * @param path 生成表格的路径
 * @param getCircleSortMessage 第一列
 * @param getLeftHJNelistMessageName 第二列
 * @param getLeftHJNelistMessageID 第三列
 * @param getRightHJNelistMessageName 第四列
 * @param getRightHJNelistMessageID 第五烈
 * @throws Exception
 */
	private static void JHEXCEL(String path,List getCircleSortMessage,
			List getLeftHJNelistMessageName, List getLeftHJNelistMessageID,
			List getRightHJNelistMessageName, List getRightHJNelistMessageID) throws Exception {
		Workbook wb  = new HSSFWorkbook();
		Sheet sheet2 = wb.createSheet("sheet");
		for (int i = 0; i < getCircleSortMessage.size(); i++) {
			Row row = sheet2.createRow(i+1);//创建一行
			row.createCell(0).setCellValue(getCircleSortMessage.get(i).toString());
			row.createCell(1).setCellValue(getLeftHJNelistMessageName.get(i).toString());
			row.createCell(2).setCellValue(getLeftHJNelistMessageID.get(i).toString());
			row.createCell(3).setCellValue(getRightHJNelistMessageName.get(i).toString());
			row.createCell(4).setCellValue(getRightHJNelistMessageID.get(i).toString());
		}
		FileOutputStream fileOut = new FileOutputStream(path);
		wb.write(fileOut);
		fileOut.close();
	}
/**
 * 功能：获取与左边环相连的所有网元信息
 * @param path01 环排序的表格路径
 * @param path02 底层的JSON文件路径
 * @param getCircleSortMessage 所有的环排序的结果
 * @return
 * @throws Exception
 */
	private static List GetLeftHJNelistMessageID(String path01,String path02,List getCircleSortMessage) throws Exception {
		//通过网元名字得到网元ID的集合 
		List getNeIDByNeName = GetNeIDByNeName(path01,getCircleSortMessage);
		//通过环中左边第一个网元得到与它相连的所有网元
		List leftConnectedNeList = getLeftConnectedNeList(path02,getNeIDByNeName);
		return leftConnectedNeList;
	}
	/**
	 * 功能：获取与右边环相连的所有网元信息
	 * @param path01 环排序的表格路径
	 * @param path02 底层的JSON文件路径
	 * @param getCircleSortMessage 所有的环排序的结果
	 * @return
	 * @throws Exception
	 */
	private static List GetRightHJNelistMessageID(String path01,String path02,List getCircleSortMessage) throws Exception {
		//通过网元名字得到网元ID的集合 
		List getNeIDByNeName = GetNeIDByNeName(path01,getCircleSortMessage);
		//通过环中右边第一个网元得到与它相连的所有网元
		List leftConnectedNeList = getRightConnectedNeList(path02,getNeIDByNeName);
		return leftConnectedNeList;
	}
	private static List getLeftConnectedNeList(String path,List getNeIDByNeName) throws Exception {
		String str01 = "";//获取最左边网元ID
		String str02 = "";//存储
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < getNeIDByNeName.size(); i++) {
			str01 = getNeIDByNeName.get(i).toString().split(";")[0];
			String judgeOneNeFindHuiJu = JudgeOneNeFindHuiJu(path,str01);//通过一个网元找到与其相连的多个网元
			//System.out.println(str01);
			//根据一个网元找相连的网元
			//System.out.println("环最左边的网元:"+judgeOneNeFindHuiJu);
			String[] split = judgeOneNeFindHuiJu.split(";");
			for (int j = 0; j < split.length; j++) {//根据网元判断是否是汇聚网元
				if(JudgeJJByNeName(split[j]).equals("true")){
					str02 = str02 + split[j] + ";";
					break;
				}
			}
			list.add(str02);
			//System.out.println(str02);
			str02 = "";
		}
		return list;
	}
	private static List getRightConnectedNeList(String path,List getNeIDByNeName) throws Exception {
		String str01 = "";//获取最左边网元ID
		String str02 = "";//存储
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < getNeIDByNeName.size(); i++) {
			str01 = getNeIDByNeName.get(i).toString().split(";")[getNeIDByNeName.get(i).toString().split(";").length-1];//通过一个网元找到与其相连的多个网元
			String judgeOneNeFindHuiJu = JudgeOneNeFindHuiJu(path,str01);
			//System.out.println(str01);
			//根据一个网元找相连的网元
			//System.out.println("环最左边的网元:"+judgeOneNeFindHuiJu);
			String[] split = judgeOneNeFindHuiJu.split(";");
			for (int j = 0; j < split.length; j++) {//根据网元判断是否是汇聚网元
				if(JudgeJJByNeName(split[j]).equals("true")){
					str02 = str02 + split[j] + ";";
					break;
				}
			}
			list.add(str02);
			//System.out.println(str02);
			str02 = "";
		}
		return list;
	}
	private static List GetNeIDByNeName(String path,List getExcelJH) throws Exception {
		String str = "";
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < getExcelJH.size(); i++) {
			String[] split = getExcelJH.get(i).toString().split(";");
			for (int j = 0; j < split.length; j++) {
				String readNeMapAndSearch = ReadNeMapAndSearch(path,split[j]);
//				System.out.print(i+":"+readNeMapAndSearch+"  ");
				str=str+readNeMapAndSearch+";";
			}
			list.add(str);
			//System.out.println(str);
			str="";
//			System.out.println();
		}
		return list;
	}
	public static String  ReadNeMapAndSearch(String path,String a) throws Exception{
		File file = new File(path);
		String str = FileUtils.readFileToString(file, "UTF-8");
		JSONArray jsonArray = new JSONArray(str);
		for (int i = 0; i < jsonArray.length(); i++) {
			String aaa = jsonArray.getJSONObject(i).get("neId").toString();
			String bbb = jsonArray.getJSONObject(i).get("neName").toString();
			if (bbb.equals(a)){
				return aaa;
			}
		}
		return null;
	}
	public static String  IDtoNeName(String path,String a) throws Exception{
		File file = new File(path);
		String str = FileUtils.readFileToString(file, "UTF-8");
		JSONArray jsonArray = new JSONArray(str);
		for (int i = 0; i < jsonArray.length(); i++) {
			String aaa = jsonArray.getJSONObject(i).get("neId").toString();
			String bbb = jsonArray.getJSONObject(i).get("neName").toString();
			if (aaa.equals(a)){
				return bbb;
			}
		}
		return null;
	}
	private static List GetLeftHJNelistMessageName(String path,List getLeftHJNelistMessageID) throws Exception {
		List<String> list = new ArrayList<String>();
		String iDtoNeName = "";
		for (int i = 0; i < getLeftHJNelistMessageID.size(); i++) {
			String[] split = getLeftHJNelistMessageID.get(i).toString().split(";");
			for (int j = 0; j < split.length; j++) {
				iDtoNeName = iDtoNeName+IDtoNeName(path,split[j])+";";
			}
			list.add(iDtoNeName);
//			System.out.println("++++"+iDtoNeName);
			iDtoNeName = "";
		}
		
		return list;
	}
	private static List GetRightHJNelistMessageName(String path,List getLeftHJNelistMessageID) throws Exception {
		List<String> list = new ArrayList<String>();
		String iDtoNeName = "";
		for (int i = 0; i < getLeftHJNelistMessageID.size(); i++) {
			String[] split = getLeftHJNelistMessageID.get(i).toString().split(";");
			for (int j = 0; j < split.length; j++) {
				iDtoNeName = iDtoNeName+IDtoNeName(path,split[j])+";";
			}
			list.add(iDtoNeName);
//			System.out.println("++++"+iDtoNeName);
			iDtoNeName = "";
		}
		
		return list;
	}
/**
 * 
 * @param path 文件路径
 * @param ii 读取的行
 * @param jj 读取的列
 * @return 返回的是巨环的环排序
 * @throws Exception
 */
	public static List GetCircleSortMessage(String path,int ii,int jj) throws Exception{//获取最后一个网元ID
		FileInputStream inputStream = new FileInputStream(path);
		POIFSFileSystem fileSystem = new POIFSFileSystem(inputStream);
		HSSFWorkbook workbook = new HSSFWorkbook(fileSystem);
		HSSFSheet hssfSheet = workbook.getSheetAt(3);
		List<String> list = new ArrayList<String>();
		int i = ii;
		for (; i < hssfSheet.getLastRowNum()-1; i++){
//		for (; i < 30; i++){//获取第一个巨环信息
			HSSFRow hssfRow = hssfSheet.getRow(i);//获取的是环排序结果
			if(hssfRow.getCell(jj-1).toString().equals("巨环")){//获取是巨环的环排序
				list.add(hssfRow.getCell(jj).toString());//获取的是环排序结果
				//System.out.println(hssfRow.getCell(jj).toString());
			}
		}
		return list;//list返回的是巨环的环排序
	}
	private static String JudgeJJByNeName(String string) throws Exception {
		String path = "D:\\Eclipse workspace\\hubt-fiberhome-ptn-reconfig-tools\\file\\nodeNeList";
		File file = new File(path);
		String str = FileUtils.readFileToString(file, "UTF-8");
		JSONArray jsonArray = new JSONArray(str);
		for (int i = 0; i < jsonArray.length(); i++) {
			String aaa = jsonArray.getJSONObject(i).get("neId").toString();
			String bbb = jsonArray.getJSONObject(i).get("convergence").toString();
			if (aaa.equals(string)){
				return bbb;
			}
		}
		return null;
	}
	private static String JudgeOneNeFindHuiJu(String path,String str) throws Exception {//根据一个网元名字找汇聚
		String topoNodeId1 = "";
		String str02 = new JsonRead().JsonRead(path);
		JSONObject jsonObject = new JSONObject(str02);
		String string = jsonObject.get("nodes").toString();
		JSONArray jsonArray02 = new JSONArray(string);
		for (int j = 0; j < jsonArray02.length(); j++) {
			JSONObject jsonObject2 = new JSONObject(jsonArray02.get(j).toString());
			String string3 = jsonObject2.getString("neId").toString();
			String string4 = jsonObject2.getString("topoNodeId").toString();
			if (string3.equals(str)) {
				topoNodeId1 = string4;
				break;
			}
		}
		List<String> list = new ArrayList<String>();
		String sss = "";
		String sting12  = jsonObject.get("lines").toString();
		JSONArray jsonArray12 = new JSONArray(sting12);
		for (int k = 0; k < jsonArray12.length(); k++) {
			JSONObject jsonObject13 = new JSONObject(jsonArray12.get(k).toString());
			String string3 = jsonObject13.getString("topoNodeId1").toString();
			String string4 = jsonObject13.getString("topoNodeId2").toString();
			if (string3.equals(topoNodeId1)||string4.equals(topoNodeId1)) {
//				System.out.println(topoNodeId1+"---"+jsonObject13.getString("topoNodeId1")+"---"+jsonObject13.getString("topoNodeId2"));
				String mm1 = topoNodeId1;
				String mm2 = jsonObject13.getString("topoNodeId1");
				String mm3 = jsonObject13.getString("topoNodeId2");
				if(mm1.equals(mm2)){
					sss = sss + mm3+";";
				}else{
					sss = sss + mm2+";";
				}
			}
		}
//		System.out.println("拓扑连纤:拓扑连纤ID"+str+":"+sss);
		
		//根据拓扑连纤ID转化为网元ID
		String vvv= "";
		String[] split = sss.split(";");
		for (int i = 0; i < split.length; i++) {
			for (int j = 0; j < jsonArray02.length(); j++) {
				JSONObject jsonObject2 = new JSONObject(jsonArray02.get(j).toString());
				String string3 = jsonObject2.getString("neId").toString();
				String string4 = jsonObject2.getString("topoNodeId").toString();
				if(split[i].equals(string4)){
					vvv = vvv + string3+";";
				}
			}
		}
//		System.out.println(vvv);
		return vvv;//返回的是要查找的与网元相连的网元
		
	}
}

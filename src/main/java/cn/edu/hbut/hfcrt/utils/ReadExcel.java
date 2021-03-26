package cn.edu.hbut.hfcrt.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import cn.edu.hbut.hfcrt.constant.BusinessList;
import cn.edu.hbut.hfcrt.constant.OldCircle;
import cn.edu.hbut.hfcrt.pojo.Convergence;
import cn.edu.hbut.hfcrt.service.message.ChlProblem;

/**
 * @author ZTW
 *
 */

public class ReadExcel {
	
	//获取环路信息  ==》该巨环有哪些网元
	public static List<String> readCircleFromExcel(String ExcelPath,int rowNum) throws IOException{
		List<String> list=new ArrayList<String>();
		InputStream is = new FileInputStream(ExcelPath);//
		POIFSFileSystem fs = new POIFSFileSystem(is);//
		HSSFWorkbook workbook = new HSSFWorkbook(fs);//
		HSSFSheet hssfSheet = workbook.getSheetAt(0);//读取表的index
		if (hssfSheet == null) {
			return null;
		}
		for (int i = 1; i < rowNum; i++) {
			HSSFRow hssfRow = hssfSheet.getRow(i);
			HSSFCell a1 = hssfRow.getCell(0);
			String aa1 = a1.toString();			
			list.add(aa1);
			 
		}	
		return list;
 
	}
	
	//将结果写入Excel表中去
	public static void writeListToExcel(Map<Integer,List<Integer>> map,String excelPath) throws IOException{ 
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("tunnel变化1情况表");
		List<Integer> keyList = ListUtils.findKeySetFromMap(map);
		for (int i = 0; i < map.size(); i++) {
		    int id = keyList.get(i);
		    List list=map.get(id);
			HSSFRow row = sheet.createRow(i);
			row.createCell(0).setCellValue(id);
			row.createCell(1).setCellValue(list.toString());
		 
		}
		FileOutputStream fileout = new FileOutputStream(excelPath);
		workbook.write(fileout);
		fileout.close();
	}
	
	
	
	//将列表清单结果写入Excel表中去
	public static void writeListToExcel1(List<BusinessList>BSL,String excelPath) throws IOException{ 
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("业务列表清单情况表");	 
		for (int i = 1; i <= BSL.size(); i++) {
			HSSFRow row = sheet.createRow(i);
			row.createCell(5).setCellValue(BSL.get(i-1).getTunnelId());
			row.createCell(6).setCellValue(BSL.get(i-1).getMidNeName());
			row.createCell(7).setCellValue(BSL.get(i-1).getConvergenceNeName());
			row.createCell(8).setCellValue(BSL.get(i-1).getGBoardId());
			row.createCell(9).setCellValue(BSL.get(i-1).getGNewPortName());	
			row.createCell(10).setCellValue(BSL.get(i-1).getGslotAddress());
			row.createCell(11).setCellValue(BSL.get(i-1).getCBoardId());
			row.createCell(12).setCellValue(BSL.get(i-1).getCNewPortName());	
			row.createCell(13).setCellValue(BSL.get(i-1).getCslotAddress());
			row.createCell(14).setCellValue(BSL.get(i-1).getGNeName_SlotNo_PortNo());
			row.createCell(15).setCellValue(BSL.get(i-1).getCNeName_SlotNo_PortNo());
			row.createCell(16).setCellValue(BSL.get(i-1).getLabel());		
		}
		FileOutputStream fileout = new FileOutputStream(excelPath);
		workbook.write(fileout);
		fileout.close();
	}
	
	//将列表清单结果写入Excel表中去
	public static void writeToatlListToExcel(List<BusinessList>BSL,String excelPath,List<OldCircle> CircleList,List<BusinessList>BSL1) throws IOException{ 
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("业务列表清单情况表");	 
		for (int i = 0;i<= BSL.size();i++) {
			if(i==0){
				HSSFRow row = sheet.createRow(i);
				row.createCell(0).setCellValue("环排序结果");
				row.createCell(1).setCellValue("与环左边相连的汇聚网元");
				row.createCell(2).setCellValue("与环左边相连的汇聚网元的网元ID");
				row.createCell(3).setCellValue("与环右边相连的汇聚网元");
				row.createCell(4).setCellValue("与环右边相连的汇聚网元的网元ID");			
				row.createCell(5).setCellValue("断点左边网元(网元名称-槽位号-端口号)");
				row.createCell(6).setCellValue("右汇聚网元(网元名称-槽位号-端口号)");
				row.createCell(7).setCellValue("操作的tunnel ID(左网元右汇聚)");
				row.createCell(8).setCellValue("操作的tunnel label(左网元右汇聚)");		
				row.createCell(9).setCellValue("断点右网元(网元名称-槽位号-端口号)");	
				row.createCell(10).setCellValue("左汇聚网元(网元名称-槽位号-端口号)");
				row.createCell(11).setCellValue("操作的tunnel ID(右网元左汇聚)");	
				row.createCell(12).setCellValue("操作的tunnel label(右网元左汇聚)");								 
			}
			else{
				HSSFRow row = sheet.createRow(i);
				row.createCell(0).setCellValue(CircleList.get(i-1).getCircleSortResult());
				row.createCell(1).setCellValue(CircleList.get(i-1).getCircleLeftConvergence());
				row.createCell(2).setCellValue(CircleList.get(i-1).getCircleLeftConvergenceId());
				row.createCell(3).setCellValue(CircleList.get(i-1).getCircleRightConvergence());
				row.createCell(4).setCellValue(CircleList.get(i-1).getCircleRightConvergenceId());
				row.createCell(5).setCellValue(BSL.get(i-1).getGNeName_SlotNo_PortNo());
				row.createCell(6).setCellValue(BSL.get(i-1).getCNeName_SlotNo_PortNo());
				row.createCell(7).setCellValue(BSL.get(i-1).getTunnelId());
				row.createCell(8).setCellValue(BSL.get(i-1).getLabel());
				row.createCell(9).setCellValue(BSL1.get(i-1).getGNeName_SlotNo_PortNo());	
				row.createCell(10).setCellValue(BSL1.get(i-1).getCNeName_SlotNo_PortNo());
				row.createCell(11).setCellValue(BSL1.get(i-1).getTunnelId());	
				row.createCell(12).setCellValue(BSL1.get(i-1).getLabel());	
			}	
		}
		FileOutputStream fileout = new FileOutputStream(excelPath);
		workbook.write(fileout);
		fileout.close();
	}
	
	
	
	public static List<String> readCircleFromExcel1(String ExcelPath,int rowNum) throws IOException{
		List<String> list=new ArrayList<String>();
		InputStream is = new FileInputStream(ExcelPath);//
		POIFSFileSystem fs = new POIFSFileSystem(is);//
		HSSFWorkbook workbook = new HSSFWorkbook(fs);//
		HSSFSheet hssfSheet = workbook.getSheetAt(0);//读取表的index
		if (hssfSheet == null) {
			return null;
		}
		for (int i = 1; i <= hssfSheet.getLastRowNum(); i++) {
			HSSFRow hssfRow = hssfSheet.getRow(i);
			HSSFCell a1 = hssfRow.getCell(0);
			String aa1 = a1.toString();			
			list.add(aa1);	 
		}
		return list;
	}
	
	//读旧表前五列,输入到新表中去
	public static List<OldCircle> ReadOldCircleFromExcel(String ExcelPath,int rowNum) throws IOException{
		List<OldCircle> oldCircleList=new ArrayList<OldCircle>();		
		List<String> list=new ArrayList<String>();
		InputStream is = new FileInputStream(ExcelPath);//
		POIFSFileSystem fs = new POIFSFileSystem(is);//
		HSSFWorkbook workbook = new HSSFWorkbook(fs);//
		HSSFSheet hssfSheet = workbook.getSheetAt(0);//读取表的index
		if (hssfSheet == null) {
			return null;
		}
		for (int i = 1; i <= hssfSheet.getLastRowNum(); i++) {			
			HSSFRow hssfRow = hssfSheet.getRow(i);
			HSSFCell a1 = hssfRow.getCell(0);
			HSSFCell a2 = hssfRow.getCell(1);
			HSSFCell a3 = hssfRow.getCell(2);
			HSSFCell a4 = hssfRow.getCell(3);
			HSSFCell a5 = hssfRow.getCell(4);
			String aa1 = a1.toString();	
			String aa2 = a2.toString();	
			String aa3 = a3.toString();	
			String aa4 = a4.toString();	
			String aa5 = a5.toString();	
			OldCircle oldCircle=new OldCircle();
			oldCircle.setCircleSortResult(aa1);
			oldCircle.setCircleLeftConvergence(aa2);
			oldCircle.setCircleLeftConvergenceId(aa3);
			oldCircle.setCircleRightConvergence(aa4);
			oldCircle.setCircleRightConvergenceId(aa5);		
			oldCircleList.add(oldCircle);	 
		}
		return oldCircleList;
	}
	
	
	//获取A端汇聚网元
	public static List<String> readAConvergenceFromExcel(String ExcelPath,int rowNum) throws IOException{		
		List<String> list =new ArrayList<String>();
		InputStream is = new FileInputStream(ExcelPath);//
		POIFSFileSystem fs = new POIFSFileSystem(is);//
		HSSFWorkbook workbook = new HSSFWorkbook(fs);//
		HSSFSheet hssfSheet = workbook.getSheetAt(0);//读取表的index
		if (hssfSheet == null) {
			return null;
		}
		for (int i = 1; i <= hssfSheet.getLastRowNum(); i++) {
			HSSFRow hssfRow = hssfSheet.getRow(i);

			HSSFCell a1 = hssfRow.getCell(2);

			String aa1 = a1.toString();
			
			if(aa1=="tunnel不经过汇聚"){
				aa1="123456";
			}		 						
			list.add(aa1);
			 
		}
		return list;
		
	}
	
	// 获取F端汇聚网元
	public static List<String> readFConvergenceFromExcel(String ExcelPath,int rowNum) throws IOException{		
		List<String> list =new ArrayList<String>();
		InputStream is = new FileInputStream(ExcelPath);//
		POIFSFileSystem fs = new POIFSFileSystem(is);//
		HSSFWorkbook workbook = new HSSFWorkbook(fs);//
		HSSFSheet hssfSheet = workbook.getSheetAt(0);//读取表的index
		if (hssfSheet == null) {
			return null;
		}
		for (int i = 1; i <= hssfSheet.getLastRowNum(); i++) {
			HSSFRow hssfRow = hssfSheet.getRow(i);

			HSSFCell a1 = hssfRow.getCell(4);

			String aa1 = a1.toString();	 						
			list.add(aa1);			 
		}
		return list;		
	}
	
	
	public static List<Convergence> readExcelToList2(String ExcelPath,int rowNum) throws IOException{		
		List<Convergence> list =new ArrayList<Convergence>();
		InputStream is = new FileInputStream(ExcelPath);//
		POIFSFileSystem fs = new POIFSFileSystem(is);//
		HSSFWorkbook workbook = new HSSFWorkbook(fs);//
		HSSFSheet hssfSheet = workbook.getSheetAt(3);//读取表的index
		if (hssfSheet == null) {
			return null;
		}
		for (int i = 4; i < hssfSheet.getLastRowNum(); i++) {
			HSSFRow hssfRow = hssfSheet.getRow(i);

			HSSFCell a1 = hssfRow.getCell(6);
			HSSFCell a2 = hssfRow.getCell(8);
			String aa1 = a1.toString();
			String aa2 = a2.toString();
			Convergence convergence=new Convergence();
			convergence.setAconvergence(Integer.valueOf(aa1));
			convergence.setFconvergence(Integer.valueOf(aa2));
			list.add(convergence);			 
		}	
		return list;		
	}

	
	public static List readExcelToList1(String ExcelPath,int rowNum) throws IOException{
		List<List<String>> lists=new ArrayList<List<String>>();
		List<String> list =new ArrayList<String>();
		InputStream is = new FileInputStream(ExcelPath);//
		POIFSFileSystem fs = new POIFSFileSystem(is);//
		HSSFWorkbook workbook = new HSSFWorkbook(fs);//
		HSSFSheet hssfSheet = workbook.getSheetAt(0);//读取表的index
		if (hssfSheet == null) {
			return null;
		}
		for (int i = 4; i < rowNum; i++) {
			HSSFRow hssfRow = hssfSheet.getRow(i);

			HSSFCell a2 = hssfRow.getCell(2);

			String aa2 = a2.toString();

			list.add(aa2);
			 
		}	
		return list;		
	}
	
	
	public static <type> List<type> readExeclToList(String ExcelPath,int rowNum) throws IOException{
		List<ChlProblem> list = new ArrayList<ChlProblem>();//
		InputStream is = new FileInputStream(ExcelPath);//
		POIFSFileSystem fs = new POIFSFileSystem(is);//
		HSSFWorkbook workbook = new HSSFWorkbook(fs);//
		HSSFSheet hssfSheet = workbook.getSheetAt(0);//读取表的index
		if (hssfSheet == null) {
			return null;
		}
		for (int i = 0; i < 2; i++) {
			HSSFRow hssfRow = hssfSheet.getRow(i);
			HSSFCell a1 = hssfRow.getCell(0);
			HSSFCell a2 = hssfRow.getCell(1);
			HSSFCell a3 = hssfRow.getCell(2);
			HSSFCell a4 = hssfRow.getCell(3);
			HSSFCell a5 = hssfRow.getCell(4);
			String aa1 = a1.toString();
			String aa2 = a2.toString();
			String aa3 = a3.toString();
			String aa4 = a4.toString();
			String aa5 = a5.toString();
			if (aa5.equals("gaoqing")) {
				continue;
			}
			ChlProblem chlP = new ChlProblem(aa1,aa2,aa3,aa4,aa5);
			list.add(chlP);	
		}
		for (int i = 5; i < rowNum; i++) {
			HSSFRow hssfRow = hssfSheet.getRow(i);
			HSSFCell a1 = hssfRow.getCell(0);
			HSSFCell a2 = hssfRow.getCell(1);
			HSSFCell a3 = hssfRow.getCell(2);
			HSSFCell a4 = hssfRow.getCell(3);
			HSSFCell a5 = hssfRow.getCell(4);
			HSSFCell a6 = hssfRow.getCell(5);
			String aa1 = a1.toString();
			String aa2 = a2.toString();
			String aa3 = a3.toString();
			String aa4 = a4.toString();
			String aa5 = a5.toString();
			String aa6 = a6.toString();
			if (aa6.equals("chenghuan")) {
				continue;
			}
			ChlProblem chlP = new ChlProblem(aa1,aa2,aa3,aa4,aa5,aa6);
			list.add(chlP);	
		}
		return (List<type>) list;
	}

}

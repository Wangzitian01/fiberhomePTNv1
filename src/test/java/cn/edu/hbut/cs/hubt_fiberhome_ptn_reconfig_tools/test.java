package cn.edu.hbut.cs.hubt_fiberhome_ptn_reconfig_tools;

import java.io.IOException;
import java.util.List;

import cn.edu.hbut.hfcrt.controller.RingController;

public class test {
	
	public static String ExcelPath="D:\\Eclipse workspace\\hubt-fiberhome-ptn-reconfig-tools\\file\\环处理 - 副本(1).xls";
	public static int rowNum=98;
	
	public static void main(String[] args) throws IOException {
		
		List<Integer> lList1= RingController.findRandomNeIdFromAllRingInfo(ExcelPath, rowNum);
//		System.out.println(lList1.get(0));
//		System.out.println(lList1.size());
		
	}

	
	
}

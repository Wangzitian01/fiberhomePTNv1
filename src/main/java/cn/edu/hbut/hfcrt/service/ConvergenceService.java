package cn.edu.hbut.hfcrt.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.Document;

import com.mongodb.client.MongoCollection;

import cn.edu.hbut.cs.hfcrt.databridge.dao.DataBridgeDAO;
import cn.edu.hbut.hfcrt.pojo.Convergence;
import cn.edu.hbut.hfcrt.utils.ReadExcel;
import cn.edu.hbut.hfcrt.utils.StringSplit;

/**
 * @author zitian
 *
 */
public class ConvergenceService {
	
	static MongoCollection<Document> convergenceNeListCol = new DataBridgeDAO().getCollection("convergenceNeList");
	
	
	public static int  findConvergenceNeId(String ConvergenceNeName){
		 
		return NeService.findNeId(ConvergenceNeName);
		
	}
	
	
	//根据网元的neId,在neMap查找网元的neId
	public static String  findNeName(int neId) {
		Document query=new Document().append("neId", neId);
		Document board=convergenceNeListCol.find(query).first();//查找neName符合的对象
		String neName = (String) board.get("neName");
		return neName;
		
	}

	//获取环路两端的汇聚网元
	public static Map<Integer,Map<Integer,Integer>> findConvergenceNeId1(String path,int rowNum) throws IOException{
		Map<Integer,Integer> map=new HashMap<Integer,Integer>();
		Map<Integer,Map<Integer,Integer>> map1=new HashMap<Integer,Map<Integer,Integer>>();
		List<Convergence> list = ReadExcel.readExcelToList2(path, rowNum);
		for(int i=0;i<list.size();i++){
			int AconvergenceNeId = list.get(i).getAconvergence();
			int FconvergenceNeId = list.get(i).getFconvergence();
			map.put(AconvergenceNeId, FconvergenceNeId);
			map1.put(i, map);
		}
		return map1;
	}
	
	//获取环路A端的汇聚网元
	public static List<String> findAConvergenceNeId(String path,int rowNum) throws IOException{
		List<String> list = ReadExcel.readAConvergenceFromExcel(path, rowNum);
		List<String> List = StringSplit.RemoveString(list);
		return List;
	}
	
	
	//获取环路F端的汇聚网元
	public static List<String> findFConvergenceNeId(String path,int rowNum) throws IOException{
		List<String> list = ReadExcel.readFConvergenceFromExcel(path, rowNum);
		List<String> List = StringSplit.RemoveString(list);
		return List;
	}

	
	

}

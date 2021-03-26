package cn.edu.hbut.hfcrt.service;

import java.util.List;

import cn.edu.hbut.hfcrt.constant.BusinessList;
import cn.edu.hbut.hfcrt.controller.RingController;

public class BusinessListServcie {
	
	public static BusinessList AddClass(List<Integer> List,int MidNeId,int ConvengerceNeId,double GboardId,String GPortName,double CboardId,String CPortName){
		BusinessList BL=new BusinessList();
		BL.setTunnelId(List.toString());
		
		BL.setMidNeId(MidNeId);
		BL.setMidNeName(NeService.findNeName(MidNeId));
		
		String GneName = NeService.findNeName(MidNeId);
		
		BL.setConvergenceNeName(ConvergenceService.findNeName(ConvengerceNeId));
		BL.setConvengerceNeId(ConvengerceNeId);
		String CNeName = ConvergenceService.findNeName(ConvengerceNeId);
		
		BL.setGBoardId(GboardId);
		BL.setGNewPortName(GPortName);
		BL.setGslotAddress(BoardService.findslotAddress((int)GboardId));
		
		String gslotNo =String.valueOf(BoardService.findslotAddress((int)GboardId));
		
		BL.setCBoardId(CboardId);
		BL.setCNewPortName(CPortName);
		BL.setCslotAddress(BoardService.findslotAddress((int)CboardId));
		
		String cslotNo = String.valueOf(BoardService.findslotAddress((int)CboardId));
		
		
	    String gNeName_SlotNo_PortNo=GneName+"-"+GPortName+"-"+gslotNo;
	    
	    String cNeName_SlotNo_PortNo=CNeName+"-"+CPortName+"-"+cslotNo;
	    
	    BL.setGNeName_SlotNo_PortNo(gNeName_SlotNo_PortNo);
	    
	    BL.setCNeName_SlotNo_PortNo(cNeName_SlotNo_PortNo);
	    
	    List<Integer> label = RingController.findLabelBytunnelId(List);
	    BL.setLabel(label.toString());
	    
		return BL;
		
		
	}

}

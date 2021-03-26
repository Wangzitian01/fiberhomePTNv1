//package cn.edu.hbut.hfcrt.service;
//
//import java.io.IOException;
//import java.util.ArrayList;
//
//import javax.print.Doc;
//
//import org.bson.Document;
//
//import com.mongodb.client.FindIterable;
//import com.mongodb.client.MongoCollection;
//import com.mongodb.client.MongoCursor;
//import com.mongodb.client.result.UpdateResult;
//
//import cn.edu.hbut.hftrt.service.message.CIROverlapRateRecord;
//
//public class CirService {
//
//	public static ArrayList<String> getStartInfo(CIROverlapRateRecord abnormalRecord, Document ne1) throws IOException {
//
//		ArrayList<String> list = new ArrayList<String>();
//
//		String boardTypeName1 = abnormalRecord.getSourceDiskName();
//		String slotport1 = abnormalRecord.getSourceSlotPort();
//		String[] split1 = slotport1.split("-");
//		int slotAddress1 = 0;
//		String portName1 = split1[1];
//		int neId1 = ne1.getInteger("neId");
//		String neTypeName = ne1.get("neTypeName").toString();
//		System.out.println("neTypeName:" +neTypeName);
//		System.out.println(split1[0]);
//		if (neTypeName.contains("630") | neTypeName.contains("640") | neTypeName.contains("660")) {
//			slotAddress1 = Integer.parseInt(split1[0], 16);
//		} else if (neTypeName.contains("650") | neTypeName.contains("680") | neTypeName.contains("690")) {
//			slotAddress1 = Integer.parseInt(split1[0]);
//		} else {
//			System.out.println("warning: unknown nedeviceType!");
//		}
//		list.add(neId1 + "");
//		list.add(portName1);
//		list.add(boardTypeName1);
//		list.add(slotAddress1 + "");
//
//		return list;
//	}
//	public static ArrayList<String> getEndInfo(CIROverlapRateRecord abnormalRecord, Document ne1) throws IOException {
//
//		ArrayList<String> list = new ArrayList<String>();
//
//		String boardTypeName1 = abnormalRecord.getDestDiskName();
//		String slotport1 = abnormalRecord.getDestSlotPort();
//		String[] split1 = slotport1.split("-");
//		int slotAddress1 = 0;
//		String portName1 = split1[1];
//
//		int neId1 = ne1.getInteger("neId");
//		String neTypeName = ne1.get("neTypeName").toString();
////		String nedeviceType = neTypeName.substring(neTypeName.length() - 3);
////		if (nedeviceType.equals("630") | nedeviceType.equals("640") | nedeviceType.equals("660")) {
////			slotAddress1 = Integer.parseInt(split1[0], 16);
////		} else if (nedeviceType.equals(" U3") | nedeviceType.equals("680") | nedeviceType.equals("690")
////				| nedeviceType.equals("U30") | nedeviceType.equals("U10") | nedeviceType.equals("U20")
////				| nedeviceType.equals("U30")) {
////			slotAddress1 = Integer.parseInt(split1[0]);
////		} else {
////			System.out.println("warning: unknown nedeviceType!");
////		}
////		System.out.println("neTypeName:" +neTypeName);
////		System.out.println(split1[0]);
//		
//		if (neTypeName.contains("620") |neTypeName.contains("630") | neTypeName.contains("640") | 
//				neTypeName.contains("660")) {
//			slotAddress1 = Integer.parseInt(split1[0], 16);//��EXCEl���е�16����ת��10����
//		} else if (neTypeName.contains("650") | neTypeName.contains("680") | neTypeName.contains("690")) {
//			slotAddress1 = Integer.parseInt(split1[0]);
//		} else {
//			System.out.println("warning: unknown nedeviceType!");
//		}
//		
//		list.add(neId1 + "");
//		list.add(portName1);
//		list.add(boardTypeName1);
//		list.add(slotAddress1 + "");
//
//		return list;
//	}
//
//	public static int getCirBoardId(MongoCollection<Document> boardMapCol, ArrayList<String> list) throws IOException {
//		int neId1 = Integer.valueOf(list.get(0));
//		String destboardTypeName = list.get(2);
//		int destslotAddress = Integer.valueOf(list.get(3));
//		int boardId = 0;
//		Document query = new Document().append("neId", neId1);
//		FindIterable<Document> find = boardMapCol.find(query);
//		MongoCursor<Document> mongocursor = find.iterator();
//		while (mongocursor.hasNext()) {
//			Document board = mongocursor.next();
//
//			String boardTypeName = board.getString("boardTypeName");
//			int slotAddress = Integer.valueOf(board.get("slotAddress").toString());
//			if (boardTypeName.equals(destboardTypeName) && slotAddress == destslotAddress) {
//				boardId = Integer.valueOf(board.get("boardId").toString());
//			}
//		}
//		return boardId;
//	}
//
//	public int getOneCirTopo(CIROverlapRateRecord abnormalRecord, Document ne1, Document ne2,
//			MongoCollection<Document> boardMapCol, MongoCollection<Document> topologylinesCol) throws IOException {
//		ArrayList<String> startinfolist =getStartInfo(abnormalRecord, ne1);
//		ArrayList<String> endinfolist = getEndInfo(abnormalRecord, ne2);
//		System.out.println("ne1:"+ startinfolist);
//		System.out.println("ne2:"+ endinfolist);
//		
//		int startboardid = getCirBoardId(boardMapCol, startinfolist);
//		int endboardid = getCirBoardId(boardMapCol, endinfolist);
//		System.out.println("startboardid:"+ startboardid);
//		System.out.println("endboardid:" + endboardid);
//		
//		String startportname = startinfolist.get(1);
//		String endportname = endinfolist.get(1);
//////		System.out.println("startportname:"+ startportname);
//////		System.out.println("endportname:" + endportname);
////		
//		Document query = new Document().append("boardId1", startboardid).append("boardId2", endboardid)
//				.append("portName1", startportname).append("portName2", endportname);
//		Document onetopo = topologylinesCol.find(query).first();
////		System.out.println(query);
//		
//		int topoLinkId=0;
//		
//		if(onetopo!=null) {
//			
//			topoLinkId=onetopo.getInteger("topoLinkId");
////			System.out.println("topoLinkId"+topoLinkId+" "+onetopo);
//			Document update = new Document().append("linkSpeedName", "100G");
//			topologylinesCol.updateOne(onetopo, new Document("$set", update));
//		}else
//			System.out.println("+++++++++++++++++not find topo");
//		return topoLinkId;
//	}
//
//}

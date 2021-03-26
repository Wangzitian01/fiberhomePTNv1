package cn.edu.hbut.hfcrt.utils;

import java.io.*;
import java.nio.channels.FileLock;

public  class JsonRead {

        public String JsonRead(String path){
            StringBuffer strbuffer = new StringBuffer();
            File myFile = new File(path);
            if (!myFile.exists()) {
                System.err.println("不能找到文件" + path+"，请棿¥文件地址");
            }
            FileLock fileLock=null;
            try {

                FileInputStream fis = new FileInputStream(path);
                InputStreamReader inputStreamReader = new InputStreamReader(fis, "UTF-8");//乱码的话可以换成 GBK    //将字节流转化为字符输入流
                BufferedReader in  = new BufferedReader(inputStreamReader);  // 创建丿¸ª使用默认大小输入缓冲区的缓冲字符输入流㿍
                String str;

                while ((str = in.readLine()) != null) {
                    strbuffer.append(str);  //new String(str,"UTF-8")
                }
                while (in ==null) {
                    in.close();
                }
                while (inputStreamReader ==null) {
                    inputStreamReader.close();
                }
                while (fis ==null) {
                    fis.close();
                }
            } catch (IOException e) {
                e.getStackTrace();
            }
       //     System.out.println(strbuffer.toString());
            return strbuffer.toString();
        }

    }




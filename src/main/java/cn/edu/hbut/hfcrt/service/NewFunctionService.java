package cn.edu.hbut.hfcrt.service;

import java.util.List;

public class NewFunctionService {
	
	public static int Random(int count,int max){
		int n = max/2;
		int a =  RandomService.random(count, max)[0];
//		System.out.println("a "+a);
		int b = RandomService.random(count, max)[1];
//		System.out.println("b "+b);
		int c = RandomService.random(count, max)[2];
//		System.out.println("c "+c);
		int x = min(a,b,c,n);
		return x;		
	}
	
	public static int min(int a,int b,int c,int n){
		int x=n/2;
		int a1=Math.abs(a-x);
//		System.out.println("a1 "+a1);
		int b1=Math.abs(b-x);
//		System.out.println("b1 "+b1);
		int c1=Math.abs(c-x);
//		System.out.println("c1 "+c1);
		a1=min1(a1,b1);
		a1=min1(a1,c1);
		return a1+x;
		
	}
	public static int min1(int a,int b){
		if(a>b){
			return b;
		}else{
			return a;
		}
	}
	
 
	

}

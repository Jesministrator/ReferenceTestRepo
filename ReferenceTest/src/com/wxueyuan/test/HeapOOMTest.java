package com.wxueyuan.test;

import java.util.List;
import java.util.ArrayList;
public class HeapOOMTest{
	static class OOMObject{

	}
	public static void main(String[] args){
		List<OOMObject> list = new ArrayList<OOMObject>();
		while(true){
			System.out.println(list.size());
			list.add(new OOMObject());
			
		}
	}
}


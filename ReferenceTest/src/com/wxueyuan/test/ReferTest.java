package com.wxueyuan.test;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class ReferTest {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		if(args.length!=0) {
			switch(args[0]) {
				case "strong" :
					strongReferenceTest();
					break;
				case "soft":
					softReferenceTest();
					break;
				case "weak":
					weakReferenceTest();
					break;
				case "phantom":
					phantomReferenceTest();
					break;
			}
		}
	}
	
	public static void strongReferenceTest() {
		List<ReferObject> list = new ArrayList<>();
		for(Integer i =1; i<=20; i++) {
			ReferObject obj = new ReferObject(i.toString());
			list.add(obj);
			System.out.println(obj.toString());
		}
	}
	
	public static void softReferenceTest() {
		List<SoftReference<ReferObject>> list = new ArrayList<>();
		for(Integer i =1; i<=20; i++) {
			ReferObject obj = new ReferObject(i.toString());
			list.add(new SoftReference<ReferObject>(obj) );
			System.out.println(list.get(i-1).get());
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static void weakReferenceTest() {
		List<WeakReference<ReferObject>> list = new ArrayList<>();
		for(Integer i =1; i<=20; i++) {
			ReferObject obj = new ReferObject(i.toString());
			list.add(new WeakReference<ReferObject>(obj) );
			System.out.println(list.get(i-1).get());
		}
	}
	
	public static void phantomReferenceTest() {
		ReferenceQueue<ReferObject> queue = new ReferenceQueue<>();
		for(Integer i =1; i<=20; i++) {
			PhantomReference<ReferObject> phantomReference = new PhantomReference<ReferObject>(new ReferObject(i.toString()), queue); 
			System.out.println(phantomReference.get());
		}
	}

	
}

 class ReferObject{
	private String id;
	//用来增大每个对象所占内存大小
	private double[] d = new double[20000]; 
	
	public ReferObject(String id) {
		this.id = id;
		System.out.println("create ReferObject "+id);
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "object "+this.id;
	}
	
	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		System.out.println("finalize method executed for object "+this.id);
	}
}
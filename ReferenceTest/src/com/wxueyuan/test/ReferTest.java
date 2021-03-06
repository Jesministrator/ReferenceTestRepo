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
		for(Integer i =1; i<=10; i++) {
			//实例化ReferObject
			ReferObject obj = new ReferObject(i.toString());
			//将对象放入list中防止被垃圾回收
			list.add(obj);
			System.out.println(obj);
		}
	}
	
	public static void softReferenceTest() {
		SoftReference<ReferObject> sr = new SoftReference<ReferObject>(new ReferObject("obj"));
		System.out.println(sr.get());
		//通知jvm可以进行垃圾回收
		System.gc();
		//等待gc工作
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("after gc worked " +sr.get());
		System.out.println("***************************");
		
		List<SoftReference<ReferObject>> list = new ArrayList<>();
		for(Integer i =1; i<=10; i++) {
			ReferObject obj = new ReferObject(i.toString());
			list.add(new SoftReference<ReferObject>(obj) );
			System.out.println(list.get(i-1).get());
			//每隔2s创建一个对象
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static void weakReferenceTest() {
		WeakReference<ReferObject> wr = new WeakReference<ReferObject>(new ReferObject("obj"));
		//通知jvm可以进行垃圾回收
		System.out.println(wr.get());
		//等待gc工作
		System.gc();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("after gc worked " +wr.get());
		System.out.println("***************************");
		
		List<WeakReference<ReferObject>> list = new ArrayList<>();
		for(Integer i =1; i<=10; i++) {
			ReferObject obj = new ReferObject(i.toString());
			list.add(new WeakReference<ReferObject>(obj) );
			
			//每隔2s创建一个对象
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(list.get(i-1).get());
		}
	}
	
	public static void phantomReferenceTest() {
		ReferObject obj = new ReferObject("obj");
		PhantomReference<ReferObject> phantomReference = new PhantomReference<ReferObject>(obj, new ReferenceQueue<>()); 
		System.out.println(phantomReference.get());
		//查看对象是否不在内存中
		System.out.println(phantomReference.isEnqueued());
		
		obj=null;
		//通知gc工作
		System.gc();
		//等待gc工作
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//查看对象是否不在内存中
		System.out.println(phantomReference.isEnqueued());
		//通知gc工作
		System.gc();
		//等待gc工作
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//查看对象是否不在内存中
		System.out.println(phantomReference.isEnqueued());
	}

	
}

 class ReferObject{
	private String id;
	//用来增大每个对象所占内存大小
	private double[] d = new double[30000]; 
	
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
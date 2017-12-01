package com.wxueyuan.test;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

public class ReferenceAnalysis {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		Object o = new Object();
		System.out.println(o);
		ReferenceQueue<Object> rq = new ReferenceQueue<>();
				
		WeakReference<Object> sr = new WeakReference<>(o,rq);
		System.out.println(sr);
		System.out.println(sr.get());
		System.out.println(sr.isEnqueued());
		System.out.println(rq.poll());
		/*o = null;
		System.gc();
		Thread.sleep(1000);
		System.out.println("---------------------");
		System.out.println(sr.get());
		System.out.println(sr);*/
		System.out.println(sr.get());
		sr.clear();
		System.out.println(sr);
		System.out.println(sr.get());
	}

}

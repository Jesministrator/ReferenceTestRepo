package com.wxueyuan.test;

public class ReviveObject
{
    public static ReviveObject obj=null;

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("finalize method executed for object ");
        //又将obj指向了当前对象实例
        obj = this;
    }

    public static void main(String[] args) throws InterruptedException {
        obj = new ReviveObject();
        System.out.println(obj);
        obj = null; //将obj设为null
        //通知gc工作
        System.out.println("let GC do its work");
        System.gc();
        Thread.sleep(1000);
        if(obj == null) {
            System.out.println("obj is null");
        } else {
            System.out.println("obj is alive");
            System.out.println(obj);
        }

        obj = null;//由于obj被复活，此处再次将obj设为null
        System.out.println("let GC do its work again");
        System.gc();
        Thread.sleep(1000);
        if(obj == null) {
            //对象的finalize方法仅仅会被调用一次，所以可以预见再次设置obj为null后，obj会直接被GC回收
            System.out.println("obj is null");
        } else {
            System.out.println("obj is alive");
        }
    }

}

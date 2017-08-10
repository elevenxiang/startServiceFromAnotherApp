package com.htc.eleven.startservicefromanotherapp;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;

public class MyService extends Service {
    private String mData = "default";
    private boolean running = false;
    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {

        return new RemoteBinder();
    }

    public class RemoteBinder extends IMyServiceAidlInterface.Stub{

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public void setData(String data) throws RemoteException {
            mData = data;
        }
    }
    @Override
    public void onCreate() {
        super.onCreate();

        System.out.println("MyService onCreate()");

        new Thread(){
            @Override
            public void run() {
                super.run();

                running = true;

                while (running){

                    System.out.println("当前的数据是: " + mData);

                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        System.out.println("MyService onDestroy()");

        running = false;
    }
}

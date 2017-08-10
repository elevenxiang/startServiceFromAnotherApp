package com.htc.eleven.clientapp;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.htc.eleven.startservicefromanotherapp.IMyServiceAidlInterface;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ServiceConnection {
    private Intent startServiceIntent = null;

    private IMyServiceAidlInterface mServiceBinder = null;
    private EditText et = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startServiceIntent = new Intent();
        ComponentName service = new ComponentName("com.htc.eleven.startservicefromanotherapp", "com.htc.eleven.startservicefromanotherapp.MyService");
        startServiceIntent.setComponent(service);

        et = (EditText) findViewById(R.id.et);

        findViewById(R.id.btnStartRemoteService).setOnClickListener(this);
        findViewById(R.id.btnStopRemoteService).setOnClickListener(this);
        findViewById(R.id.btnBindRemoteService).setOnClickListener(this);
        findViewById(R.id.btnUnbindRemoteService).setOnClickListener(this);
        findViewById(R.id.btnSendData).setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.btnStartRemoteService:
                startService(startServiceIntent);
                break;
            case R.id.btnStopRemoteService:
                stopService(startServiceIntent);
                break;
            case R.id.btnBindRemoteService:
                bindService(startServiceIntent,this, Context.BIND_AUTO_CREATE);
                break;
            case R.id.btnUnbindRemoteService:
                unbindService(this);
                break;
            case R.id.btnSendData:
                try {
                    mServiceBinder.setData(et.getText().toString());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        System.out.println("eleven: Bind Remote Service successfully !");
        System.out.println(iBinder);

        mServiceBinder = IMyServiceAidlInterface.Stub.asInterface(iBinder);
    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {

    }
}

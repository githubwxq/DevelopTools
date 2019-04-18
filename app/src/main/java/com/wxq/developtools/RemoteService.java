package com.wxq.developtools;

import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.wxq.developtools.aidl.RemoteConnection;

public class RemoteService extends Service {

    public static final String TAG = "ricky";
    private MyBinder binder;
    private MyServiceConnection conn;

    @Override
    public IBinder onBind(Intent intent) {
// TODO Auto-generated method stub
        return binder;
    }

    @Override
    public void onCreate() {
// TODO Auto-generated method stub
        super.onCreate();
        if (binder == null) {
            binder = new MyBinder();
        }
        conn = new MyServiceConnection();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        RemoteService.this.bindService(new Intent(RemoteService.this, LocalService.class), conn, Context.BIND_IMPORTANT);

        PendingIntent contentIntent = PendingIntent.getService(this, 0, intent, 0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setTicker("360")
                .setContentIntent(contentIntent)
                .setContentTitle("hehehe")
                .setAutoCancel(true)
                .setContentText("hehehe")
                .setWhen(System.currentTimeMillis());

        startForeground(startId, builder.build());
        return START_STICKY;
    }

    class MyBinder extends RemoteConnection.Stub {

        @Override
        public String getProcessName() throws RemoteException {
// TODO Auto-generated method stub
            return "LocalService";
        }

    }

    class MyServiceConnection implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.i(TAG, "建立连接成功！");

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.i(TAG, "LocalService服务被干掉了~~~~断开连接！");
            Toast.makeText(RemoteService.this, "断开连接", 0).show();
            //启动被干掉的
            RemoteService.this.startService(new Intent(RemoteService.this, LocalService.class));
            RemoteService.this.bindService(new Intent(RemoteService.this, LocalService.class), conn, Context.BIND_IMPORTANT);
        }

    }


}

package com.adouble.zxingdemo.Service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;

import com.adouble.zxingdemo.Receiver.AlarmReceiver;

public class LongRunningService extends Service {
    public LongRunningService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw null;
    }
    public int onStartCommand(Intent intent,int flags,int startId){
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.print("jjjjj");
             //   Vibrator vibrator=(Vibrator)getSystemService(Service.VIBRATOR_SERVICE);
              //  vibrator.vibrate(1000);
            }
        }).start();
        AlarmManager manager=(AlarmManager)getSystemService(ALARM_SERVICE);
        int anHour=60*1000;
        long triiggerAtTime= SystemClock.elapsedRealtime()+anHour;
        Intent i=new Intent(this, AlarmReceiver.class);
        PendingIntent pi=PendingIntent.getBroadcast(this,0,i,0);
        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,triiggerAtTime,pi);
        return super.onStartCommand(intent,flags,startId);
    }
}

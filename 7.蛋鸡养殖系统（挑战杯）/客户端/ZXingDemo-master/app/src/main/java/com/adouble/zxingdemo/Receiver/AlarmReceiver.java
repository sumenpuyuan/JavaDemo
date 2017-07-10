package com.adouble.zxingdemo.Receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.adouble.zxingdemo.Service.LongRunningService;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        Intent i=new Intent(context, LongRunningService.class);
        context.startService(i);
     //   throw new UnsupportedOperationException("Not yet implemented");
    }
}

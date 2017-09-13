package etong.bottomnavigation.demo.BroadCast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import etong.bottomnavigation.demo.Recevier.RunningService;

public class AlarmRecevier extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        Intent i = new Intent(context, RunningService.class);
        context.startService(i);
    }
}

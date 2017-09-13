package etong.bottomnavigation.demo.Recevier;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.os.SystemClock;

import etong.bottomnavigation.demo.BroadCast.AlarmRecevier;
import etong.bottomnavigation.demo.KetingActivity;
import etong.bottomnavigation.demo.R;
import etong.bottomnavigation.demo.Util.PostGetUtil;

public class RunningService extends Service {
    int flag=0;
    @Override

    public IBinder onBind(Intent intent) {
        return null;
    }
    public int getIfSafe(String gasData) {
        String[] realData = new String[6];
        String data[] = gasData.split("\n");
        // Toast.makeText(KetingActivity.this,data[10],Toast.LENGTH_LONG).show();

        Double co = (Double.parseDouble(data[3].substring(3))) / 10;
        //Toast.makeText(KetingActivity.this,co+"",Toast.LENGTH_SHORT).show();
        if (co > 100) {
           return -1;
        }

        int yan = Integer.parseInt(data[5].substring(3));
        if (yan > 2500) {
            return -1;
        }
        int huo = Integer.parseInt(data[6].substring(3));
        if (huo < 500) {
           return -1;
        }

        return 0;
    }
    @Override
    public int onStartCommand(Intent intent, final int flags, int startId) {

        new Thread(new Runnable() {

            @Override
            public void run() {
                final String url="http://121.42.211.211/lot/getDate.php";
                String res= PostGetUtil.sendGet(url,"");
                String realData[]=res.split(">");
              //  Updte(realData[1]);
               if(getIfSafe(realData[0]) == -1 && flag == 0){
                    Updte(flag+"");
                    flag=1;
                }else if(getIfSafe(realData[0]) == 0){
                    flag=0;
                }else{

               }
                //  Toast.makeText(KetingActivity.this,realData[1], Toast.LENGTH_LONG).show();


            }   }).start();
        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        int anHour =  5 * 1000;  // 这是的毫秒数
        long triggerAtTime = SystemClock.elapsedRealtime() + anHour;
        Intent i = new Intent(this, AlarmRecevier.class);
        PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, 0);
        manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pi);
        return super.onStartCommand(intent, flags, startId);
    }
    public void Updte(String res){
        Notification.Builder builder = new Notification.Builder(this.getApplicationContext()); //获取一个Notification构造器
        Intent nfIntent = new Intent(this, KetingActivity.class);
        builder.setContentIntent(PendingIntent.getActivity(this, 0, nfIntent, PendingIntent.FLAG_ONE_SHOT)) // 设置PendingIntent
                .setAutoCancel(true)
                .setLargeIcon(BitmapFactory.decodeResource(this.getResources(),
                        R.mipmap.ic_launcher)) // 设置下拉列表中的图标(大图标)
                .setContentTitle("房间出现异常！") // 设置下拉列表里的标题
                .setSmallIcon(R.mipmap.ic_launcher) // 设置状态栏内的小图标
                .setContentText("点击进行查看") // 设置上下文内容
                .setWhen(System.currentTimeMillis())

                .setVibrate(new long[] {0,300,500,700})
//没有成功！
                .setLights(0xff0000ff, 300, 1000)


        ;
        // 的时间
        Notification notification = builder.build(); // 获取构建好的Notification
        //    notification.defaults |= Notification.DEFAULT_VIBRATE; //设置为默认震动
        notification.defaults = Notification.DEFAULT_SOUND;
        notification.ledARGB = 0xff00ff00; // LED灯的颜色，绿灯
        notification.ledOnMS = 300; // LED灯显示的毫秒数，300毫秒
        notification.ledOffMS = 1000; // LED灯关闭的毫秒数，1000毫秒

        notification.flags |=Notification.FLAG_INSISTENT;
        notification.flags |= Notification.FLAG_SHOW_LIGHTS; // 必须加上这个标志

// 参数一：唯一的通知标识；参数二：通知消息。
        NotificationManager nm=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        nm.notify(9527, notification);
    }
}

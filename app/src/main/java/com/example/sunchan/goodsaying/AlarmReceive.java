package com.example.sunchan.goodsaying;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class AlarmReceive extends BroadcastReceiver {   //BroadcastReceiver 가필요함
    private static final String R_TAG = "MY_DEBUG - AlarmReceive";

    String INTENT_ACTION = Intent.ACTION_BOOT_COMPLETED;
    final String TAG = "BOOT_START_SERVICE";

    @Override
    public void onReceive(Context context, Intent intent) {//알람 시간이 되었을때 onReceive를 호출함

        Intent intentAlarm = new Intent(context.getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(context.getApplicationContext(), 0 /* Request code */, intentAlarm,
                PendingIntent.FLAG_ONE_SHOT);

        String title = "Notification Test";
        String txt = "알림 테스트";

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context.getApplicationContext())
                .setSmallIcon(R.drawable.ic_stat_ic_notification)
                .setContentTitle(title)
                .setContentText(txt)
                .setAutoCancel(true)                                    // 알림을 터치하면 사라짐.
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);                   // 알림을 터치하면 실행할 액티비티

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());


    }
}

package com.example.sunchan.goodsaying;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

public class AlarmReceive extends BroadcastReceiver {   //BroadcastReceiver 가필요함
    private static final String TAG = "MYD - AlarmReceive";

    Item mItem = null;

    @Override
    public void onReceive(Context context, Intent intent) {//알람 시간이 되었을때 onReceive를 호출함
        DBHandler db = new DBHandler(context.getApplicationContext());
        mItem = db.getNotiItem();
        if (mItem == null) {
            return;
        }

        String title = "Good Saying";
        String txt = mItem.getText();

        Intent intentAlarm = new Intent(context.getApplicationContext(), NotiActivity.class);
        intentAlarm.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intentAlarm.putExtra("text", txt);

        PendingIntent pendingIntent = PendingIntent.getActivity(context.getApplicationContext(), 0 /* Request code */, intentAlarm,
                PendingIntent.FLAG_ONE_SHOT);


        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context.getApplicationContext())
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle(title)
                .setContentText(txt)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);


        NotificationCompat.Builder mCompatBuilder = new NotificationCompat.Builder(context.getApplicationContext());
        mCompatBuilder.setSmallIcon(R.drawable.ic_launcher);
        mCompatBuilder.setContentTitle(title);
        mCompatBuilder.setContentText(txt);
        mCompatBuilder.setAutoCancel(true);                         // 알림을 터치하면 사라짐.
        mCompatBuilder.setSound(defaultSoundUri);
        mCompatBuilder.setContentIntent(pendingIntent);             // 알림을 터치하면 실행할 액티비티
        //mCompatBuilder.setTicker("This is Ticker");
        //mCompatBuilder.setWhen(System.currentTimeMillis());
        //mCompatBuilder.setNumber(10);
        //mCompatBuilder.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE);



        /*   This doesn't work.
        // 두번째 페이지 스타일을 위해 지정해줍니다.(Big Style)
        NotificationCompat.BigTextStyle secondPageStyle = new NotificationCompat.BigTextStyle();
        secondPageStyle.setBigContentTitle("Page 2")
                .bigText(txt);

        // 두번째 페이지 생성
        Notification secondPageNotification =
                new NotificationCompat.Builder(context.getApplicationContext())
                        .setStyle(secondPageStyle)
                        .build();

        // 두번째 페이지 까지 확장 시킵니다.
        Notification notification = mCompatBuilder
                .extend(new NotificationCompat.WearableExtender()
                        .addPage(secondPageNotification))
                .build();
        */


        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, mCompatBuilder.build());


        // count update
        db.updateItem(mItem.getId(), mItem.getText(), mItem.getCount()+1);

    }
}

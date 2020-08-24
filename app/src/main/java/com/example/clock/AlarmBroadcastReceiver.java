package com.example.clock;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Vibrator;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class AlarmBroadcastReceiver extends BroadcastReceiver {
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onReceive(Context context, Intent intent) {



       Vibrator vibrator = (Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(2000);

        NotificationChannel alarm = new NotificationChannel("alarmID", "Alarm", NotificationManager.IMPORTANCE_HIGH);
        alarm.enableLights(true);
        alarm.enableVibration(true);
        alarm.setLightColor(R.color.colorPrimary);
        alarm.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);


        NotificationManager manager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.createNotificationChannel(alarm);



        NotificationCompat.Builder notify = new NotificationCompat.Builder(context, "alarmID")
                .setContentTitle("Alarm")
                .setContentText("Alarm set for the Time")
                .setSmallIcon(R.drawable.ic_alarm)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        notificationManager.notify(200, notify.build());
 //      notify.flags|= Notification.FLAG_AUTO_CANCEL;
//        manager.notify(0, alarm);

        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);

        Ringtone r = RingtoneManager.getRingtone(context, notification);
       r.play();


    }
}

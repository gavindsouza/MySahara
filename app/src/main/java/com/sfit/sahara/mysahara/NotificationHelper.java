
package com.sfit.sahara.mysahara;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;

import com.sfit.sahara.mysahara.R;

public class NotificationHelper extends ContextWrapper{

    public NotificationHelper(Context base) {
        super(base);
        createChannel();
    }
    private static final String channel_id = "com.sfit.sahara.mysahara.SFIT";
    private static final String channel_name = "SFIT channel";
    private NotificationManager manager;
     public  void createChannel(){
         NotificationChannel sfitchannel = new NotificationChannel(channel_id,channel_name,NotificationManager.IMPORTANCE_DEFAULT);
         sfitchannel.enableLights(true);
         sfitchannel.enableVibration(true);
         sfitchannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);

         getManager().createNotificationChannel(sfitchannel);
     }

    public NotificationManager getManager(){
        if(manager==null)
            manager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        return manager;
    }

    public Notification.Builder getsfitchannelnotification(String title,String text){
        Intent intent = new Intent(this,junior_fifth_page_home.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        return new Notification.Builder(getApplicationContext(),channel_id)
                .setContentText(text)
                .setContentTitle(title)
                .setTicker(getString(R.string.ticker))
                .setSmallIcon(R.drawable.notif)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);
    }
}
